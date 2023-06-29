package com.blix.sixsiege.networking.packet;

import com.blix.sixsiege.item.custom.AnimatedItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class MovementSpeedC2SPacket {

    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        if(player != null) {
            float speed;

            if (player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                if (player.getActiveItem().getItem().getClass().equals(AnimatedItem.class)) {
                    speed = 0.2f;
                } else {
                    speed = 0.1f;
                }
            } else {
                speed = 0.1f;
            }

            player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
        }

    }

}
