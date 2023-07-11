package com.blix.sixsiege.networking;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.networking.packet.AmmoSyncDataS2CPacket;
import com.blix.sixsiege.networking.packet.ReloadC2SPacket;
import com.blix.sixsiege.networking.packet.ShootC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {

    public static final Identifier RELOADING_ID = new Identifier(SixSiege.MOD_ID, "reloading");
    public static final Identifier AMMO_SYNC_ID = new Identifier(SixSiege.MOD_ID, "ammo_sync");
    public static final Identifier SHOOTING_ID = new Identifier(SixSiege.MOD_ID, "shooting");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(RELOADING_ID, ReloadC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(SHOOTING_ID, ShootC2SPacket::recieve);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(AMMO_SYNC_ID, AmmoSyncDataS2CPacket::recieve);
    }

}
