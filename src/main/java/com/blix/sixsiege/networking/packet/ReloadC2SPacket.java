package com.blix.sixsiege.networking.packet;

import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.util.AmmoData;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class ReloadC2SPacket {

    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        AnimatedItem weapon = (AnimatedItem) player.getMainHandStack().getItem();

        AmmoData.setAmmo(((IEntityDataServer) player), weapon.getMaxAmmo());
    }

}
