package com.blix.sixsiege.networking.packet;

import com.blix.sixsiege.item.custom.AnimatedItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class CooldownC2SPacket {

    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if(player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            buf.readerIndex(9);
            if(buf.getInt(0) == 0) {
                player.getItemCooldownManager().set(player.getMainHandStack().getItem(), buf.getInt(1));
            } else if(buf.getInt(0) == 1) {
                player.getItemCooldownManager().remove(player.getMainHandStack().getItem());
            }
        }
    }

}
