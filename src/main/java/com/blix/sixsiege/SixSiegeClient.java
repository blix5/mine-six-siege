package com.blix.sixsiege;

import com.blix.sixsiege.client.AmmoHudOverlay;
import com.blix.sixsiege.client.CrosshairHudOverlay;
import com.blix.sixsiege.client.ReticleHudOverlay;
import com.blix.sixsiege.client.ScopeHudOverlay;
import com.blix.sixsiege.event.ClientPlayerTickHandler;
import com.blix.sixsiege.event.ClientPreAttackHandler;
import com.blix.sixsiege.event.KeyInputHandler;
import com.blix.sixsiege.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;

public class SixSiegeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();
        ModMessages.registerS2CPackets();

        ClientTickEvents.START_CLIENT_TICK.register(new ClientPlayerTickHandler());
        ClientPreAttackCallback.EVENT.register(new ClientPreAttackHandler());
        HudRenderCallback.EVENT.register(new AmmoHudOverlay());
        HudRenderCallback.EVENT.register(new ScopeHudOverlay());
        HudRenderCallback.EVENT.register(new ReticleHudOverlay());
        HudRenderCallback.EVENT.register(new CrosshairHudOverlay());

    }
}