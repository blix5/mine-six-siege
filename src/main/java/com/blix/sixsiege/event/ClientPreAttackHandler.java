package com.blix.sixsiege.event;

import com.blix.sixsiege.client.ScopeHudOverlay;
import com.blix.sixsiege.item.ModItems;
import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.networking.ModMessages;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

@Environment(EnvType.CLIENT)
public class ClientPreAttackHandler implements ClientPreAttackCallback {

    private static int shootCooldown = 1;

    @Override
    public boolean onClientPlayerPreAttack(MinecraftClient client, ClientPlayerEntity player, int clickCount) {
        if(player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            if(clickCount > 0) shootCooldown = 1;
            if(shootCooldown == 1) {
                PacketByteBuf shootBuf = PacketByteBufs.create();
                shootBuf.writerIndex(48);
                shootBuf.setFloat(0, (float)player.getCameraPosVec(1.0f).getX());
                shootBuf.setFloat(8, (float)player.getCameraPosVec(1.0f).getY());
                shootBuf.setFloat(16, (float)player.getCameraPosVec(1.0f).getZ());
                shootBuf.setFloat(24, (float)player.getRotationVec(1.0f).getX());
                shootBuf.setFloat(32, (float)player.getRotationVec(1.0f).getY());
                shootBuf.setFloat(40, (float)player.getRotationVec(1.0f).getZ());

                ClientPlayNetworking.send(ModMessages.SHOOTING_ID, shootBuf);
                if (((IEntityDataServer) player).getPersistentData().getInt("ammo") > 0) {
                    ScopeHudOverlay.setShouldAddRecoil(true);
                    PositionedSoundInstance reloadSoundInstance = PositionedSoundInstance.master(((AnimatedItem) player.getMainHandStack().getItem())
                            .getShootSound(), 1.2f, 5.0f);
                    client.getSoundManager().play(reloadSoundInstance);
                }
                shootCooldown++;
            } else if(shootCooldown < ((AnimatedItem) player.getMainHandStack().getItem()).getFireRate()) {
                shootCooldown++;
            } else {
                shootCooldown = 1;
            }
            return true;
        } else if(player.getMainHandStack().isOf(ModItems.KNIFE)) {
            if(clickCount > 0 && player.getItemCooldownManager().getCooldownProgress(player.getMainHandStack().getItem(), 1.0f) == 0
                    && client.crosshairTarget != null) {
                player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 20);
                HitResult hit = client.crosshairTarget;
                if (hit.getPos().distanceTo(player.getCameraPosVec(1.0f)) < 1.5f) {
                    if (hit.getType().equals(HitResult.Type.BLOCK)) {
                        PacketByteBuf knifeBuf = PacketByteBufs.create();
                        knifeBuf.writeBlockHitResult((BlockHitResult) hit);

                        ClientPlayNetworking.send(ModMessages.KNIFE_ID, knifeBuf);
                    }
                } else {
                    if (hit.getType().equals(HitResult.Type.BLOCK)) {
                        client.interactionManager.cancelBlockBreaking();
                    }
                }
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

}
