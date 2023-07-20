package com.blix.sixsiege.networking.packet;

import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class AmmoSyncDataS2CPacket {

    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ((IEntityDataServer) client.player).getPersistentData().putInt("ammo", buf.readInt());
    }

}
