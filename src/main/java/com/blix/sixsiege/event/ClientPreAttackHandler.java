package com.blix.sixsiege.event;

import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.networking.ModMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class ClientPreAttackHandler implements ClientPreAttackCallback {

    private static int cooldown = 1;

    @Override
    public boolean onClientPlayerPreAttack(MinecraftClient client, ClientPlayerEntity player, int clickCount) {
        if(player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            if(cooldown == 1) {
                ClientPlayNetworking.send(ModMessages.SHOOTING_ID, PacketByteBufs.create());
                cooldown++;
            } else if(cooldown < ((AnimatedItem) player.getMainHandStack().getItem()).getFireRate()) {
                cooldown++;
            } else {
                cooldown = 1;
            }
            return true;
        } else {
            return false;
        }
    }

    public static void setCooldown(int cooldown) {
        ClientPreAttackHandler.cooldown = cooldown;
    }

}
