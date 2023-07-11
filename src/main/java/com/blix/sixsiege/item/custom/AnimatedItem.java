package com.blix.sixsiege.item.custom;

import com.blix.sixsiege.item.client.AnimatedItemRenderer;
import com.blix.sixsiege.networking.ModMessages;
import com.blix.sixsiege.sound.ModSounds;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
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
        return 5000;
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
        ServerPlayerEntity serverPlayer = client.getServer().getPlayerManager().getPlayer(client.player.getUuid());
        if(((IEntityDataServer) serverPlayer).getPersistentData().getInt("ammo") > 0) {
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
            ServerPlayerEntity serverPlayer = client.getServer().getPlayerManager().getPlayer(client.player.getUuid());

            if(serverPlayer != null) {
                if (serverPlayer.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                    AnimatedItem weapon = (AnimatedItem) serverPlayer.getMainHandStack().getItem();
                    if (((IEntityDataServer) serverPlayer).getPersistentData()
                            .getInt("ammo") < weapon.maxAmmo) {

                        if (this.reloading && !serverPlayer.getItemCooldownManager().isCoolingDown(weapon) && !serverPlayer.getActiveItem().isOf(weapon) && !serverPlayer.isSprinting()) {
                            tAnimationState.resetCurrentAnimation();
                            tAnimationState.getController().setAnimationSpeed(1.0D);
                            tAnimationState.getController().setAnimation(RawAnimation.begin().thenPlay("animation." + localpath + ".reload"));
                            serverPlayer.getItemCooldownManager().set(weapon, weapon.reloadLength);
                            serverPlayer.getServerWorld().playSound(serverPlayer, serverPlayer.getBlockPos(),
                                    ((AnimatedItem) serverPlayer.getMainHandStack().getItem()).getReloadSound(),
                                    SoundCategory.PLAYERS, 1.0f, 1.0f);
                            this.reloadSoundInstance = PositionedSoundInstance.master(((AnimatedItem) serverPlayer.getMainHandStack().getItem()).getReloadSound(),
                                    1.0f, 1.0f);
                            client.getSoundManager().play(reloadSoundInstance);
                            this.reloading = false;
                            this.sentReload = false;
                        } else if (serverPlayer.getItemCooldownManager().isCoolingDown(weapon)) {
                            if ((serverPlayer.getItemCooldownManager().getCooldownProgress(weapon, 1) == 0) && !sentReload) {
                                ClientPlayNetworking.send(ModMessages.RELOADING_ID, PacketByteBufs.create());
                                this.sentReload = true;
                            }
                            if (this.cancelReload) {
                                serverPlayer.getItemCooldownManager().remove(weapon);
                                this.reloading = false;
                                this.cancelReload = false;
                                tAnimationState.resetCurrentAnimation();
                                tAnimationState.getController().setAnimationSpeed(0.0D);
                            }
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
