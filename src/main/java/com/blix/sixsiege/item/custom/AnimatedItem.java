package com.blix.sixsiege.item.custom;

import com.blix.sixsiege.item.client.AnimatedItemRenderer;
import com.blix.sixsiege.networking.ModMessages;
import com.blix.sixsiege.networking.packet.AmmoSyncDataS2CPacket;
import com.blix.sixsiege.networking.packet.CooldownS2CPacket;
import com.blix.sixsiege.sound.ModSounds;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AnimatedItem extends Item implements GeoItem {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private String localpath;

    private int closeDamage;
    private int longDamage;
    private int damageDropoffDist;
    private int fireRate;
    private int reloadLength;
    private int maxAmmo;

    private SoundEvent shootingSound;
    private SoundEvent reloadingSound;

    private boolean reloading = false;
    private boolean sentReload = false;
    private boolean cancelReload = false;

    private SoundInstance reloadSoundInstance;

    public AnimatedItem(Settings settings, String path, int closeDam, int longDam, int damageDropoff, int tpr, int reloadLength, int maxAmmo,
                        SoundEvent shootSound, SoundEvent reloadSound) {
        super(settings);
        this.localpath = path;
        this.closeDamage = closeDam;
        this.longDamage = longDam;
        this.damageDropoffDist = damageDropoff;
        this.fireRate = tpr;
        this.reloadLength = reloadLength;
        this.maxAmmo = maxAmmo;
        this.shootingSound = shootSound;
        this.reloadingSound = reloadSound;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.ITEM_SPYGLASS_USE, 1.0f, 1.0f);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        this.playStopUsingSound(user);
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 10000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        this.playStopUsingSound(user);
    }

    private void playStopUsingSound(LivingEntity user) {
        user.playSound(SoundEvents.ITEM_SPYGLASS_STOP_USING, 1.0f, 1.0f);
    }

    public String getLocalpath() {
        return localpath;
    }

    public int getCloseDamage() {
        return closeDamage;
    }

    public int getLongDamage() {
        return longDamage;
    }

    public int getDamageDropoffDist() {
        return damageDropoffDist;
    }

    public int getFireRate() {
        return fireRate;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public SoundEvent getShootSound() {
        return shootingSound;
    }

    public SoundEvent getReloadSound() {
        return reloadingSound;
    }

    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    public void setCancelReload(boolean cancelReload) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(((IEntityDataServer) client.player).getPersistentData().getInt("ammo") > 0) {
            this.cancelReload = cancelReload;
            client.getSoundManager().stop(reloadSoundInstance);
        }
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final AnimatedItemRenderer renderer = new AnimatedItemRenderer();

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::reloadPredicate));
    }

    private <T extends GeoAnimatable> PlayState reloadPredicate(AnimationState<T> tAnimationState) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            if (client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                AnimatedItem weapon = (AnimatedItem) client.player.getMainHandStack().getItem();
                if (((IEntityDataServer) client.player).getPersistentData().getInt("ammo") < weapon.maxAmmo) {

                    if (this.reloading && !CooldownS2CPacket.getCoolingDown() && !client.player.getActiveItem().isOf(weapon)
                            && !client.player.isSprinting()) {
                        tAnimationState.resetCurrentAnimation();
                        tAnimationState.getController().setAnimationSpeed(1.0D);
                        tAnimationState.getController().setAnimation(RawAnimation.begin().thenPlay("animation." + localpath + ".reload"));

                        PacketByteBuf cooldownBuf = PacketByteBufs.create();
                        cooldownBuf.writerIndex(9);
                        cooldownBuf.setInt(0, 0);
                        cooldownBuf.setInt(1, reloadLength);
                        ClientPlayNetworking.send(ModMessages.COOLDOWN_ID_S, cooldownBuf);

                        ClientPlayNetworking.send(ModMessages.START_RELOADING_ID, PacketByteBufs.create());
                        this.reloadSoundInstance = PositionedSoundInstance.master(((AnimatedItem) client.player.getMainHandStack().getItem()).getReloadSound(),
                                1.0f, 1.0f);
                        client.getSoundManager().play(reloadSoundInstance);

                        this.reloading = false;
                        this.sentReload = false;
                    } else if (CooldownS2CPacket.getCoolingDown()) {
                        if ((CooldownS2CPacket.getCooldownProgress() == 0) && !sentReload) {
                            ClientPlayNetworking.send(ModMessages.RELOADING_ID, PacketByteBufs.create());
                            this.sentReload = true;
                        }
                        if (this.cancelReload) {
                            PacketByteBuf cooldownBuf = PacketByteBufs.create();
                            cooldownBuf.writerIndex(9);
                            cooldownBuf.setInt(0, 1);
                            ClientPlayNetworking.send(ModMessages.COOLDOWN_ID_S, cooldownBuf);

                            this.reloading = false;
                            this.cancelReload = false;
                            tAnimationState.resetCurrentAnimation();
                            tAnimationState.getController().setAnimationSpeed(0.0D);
                        }
                    }
                }
            }
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }



}
