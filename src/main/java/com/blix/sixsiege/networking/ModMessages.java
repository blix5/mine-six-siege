package com.blix.sixsiege.networking;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.networking.packet.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {

    public static final Identifier START_RELOADING_ID = new Identifier(SixSiege.MOD_ID, "start_reloading");
    public static final Identifier RELOADING_ID = new Identifier(SixSiege.MOD_ID, "reloading");
    public static final Identifier AMMO_SYNC_ID = new Identifier(SixSiege.MOD_ID, "ammo_sync");
    public static final Identifier AMMO_SYNC_REQ_ID = new Identifier(SixSiege.MOD_ID, "ammo_sync_req");
    public static final Identifier SHOOTING_ID = new Identifier(SixSiege.MOD_ID, "shooting");
    public static final Identifier KNIFE_ID = new Identifier(SixSiege.MOD_ID, "knife");
    public static final Identifier LIGHT_ID = new Identifier(SixSiege.MOD_ID, "light");
    public static final Identifier COOLDOWN_ID = new Identifier(SixSiege.MOD_ID, "cooldown");
    public static final Identifier COOLDOWN_ID_S = new Identifier(SixSiege.MOD_ID, "cooldown_s");
    public static final Identifier CANCEL_RELOAD_ID = new Identifier(SixSiege.MOD_ID, "cancel_reload");
    public static final Identifier SET_RELOADING_ID = new Identifier(SixSiege.MOD_ID, "set_relaoding");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(START_RELOADING_ID, StartReloadingC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(RELOADING_ID, ReloadC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(SHOOTING_ID, ShootC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(KNIFE_ID, KnifeC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(COOLDOWN_ID_S, CooldownC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(AMMO_SYNC_REQ_ID, AmmoSyncRequestC2SPacket::recieve);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(AMMO_SYNC_ID, AmmoSyncDataS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(LIGHT_ID, LightS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(COOLDOWN_ID, CooldownS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(CANCEL_RELOAD_ID, CancelReloadS2CPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(SET_RELOADING_ID, SetReloadingS2CPacket::recieve);
    }

}
