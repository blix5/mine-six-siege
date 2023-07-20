package com.blix.sixsiege.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class CooldownS2CPacket {

    private static boolean isCoolingDown;
    private static float cooldownProgress;

    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        buf.readerIndex(9);
        isCoolingDown = buf.getBoolean(0);
        cooldownProgress = buf.getFloat(1);
    }

    public static boolean getCoolingDown() {
        return isCoolingDown;
    }

    public static float getCooldownProgress() {
        return cooldownProgress;
    }

}
