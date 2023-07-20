package com.blix.sixsiege.networking.packet;

import com.blix.sixsiege.item.custom.AnimatedItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;

public class StartReloadingC2SPacket {

    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if(player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            player.getServerWorld().playSound(player, player.getBlockPos(),
                    ((AnimatedItem) player.getMainHandStack().getItem()).getReloadSound(),
                    SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }

}
