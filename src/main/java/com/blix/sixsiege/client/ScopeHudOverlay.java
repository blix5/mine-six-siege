package com.blix.sixsiege.client;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.event.KeyInputHandler;
import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.util.AmmoData;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class ScopeHudOverlay implements HudRenderCallback {

    private static final Identifier HOLO_A = new Identifier(SixSiege.MOD_ID, "textures/gui/scopes/holo_a.png");
    private int ammoSynced = 30;
    private float tiltStage = 0;
    private int aimProgress = 0;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if(ammoSynced != 0) {
            ServerPlayerEntity serverPlayer = client.getServer().getPlayerManager().getPlayer(client.player.getUuid());
            AmmoData.syncAmmo(((IEntityDataServer) serverPlayer).getPersistentData().getInt("ammo"), serverPlayer);
            ammoSynced--;
        }

        if (((IEntityDataServer) client.player).getPersistentData().getInt("ammo") > 0) {
            if (client.options.getPerspective().isFirstPerson()) {
                if (client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                    AnimatedItem weapon = (AnimatedItem) client.player.getMainHandStack().getItem();
                    float m = client.getLastFrameDuration();

                    if (client.player.getActiveItem().isOf(weapon)) {
                        this.renderScopeOverlay(drawContext, 1.125f);
                        this.aimProgress = MathHelper.lerp(m, this.aimProgress, 100);
                    } else {
                        this.aimProgress = 0;
                    }
                } else {
                    this.aimProgress = 0;
                }
            }
        }
    }

    private void renderScopeOverlay(DrawContext context, float scale) {
        float f;
        float g = f = (float)Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        float h = Math.min((float)context.getScaledWindowWidth() / f, (float)context.getScaledWindowHeight() / g) * scale;
        int i = MathHelper.floor(f * h);
        int j = MathHelper.floor(g * h);
        int k = (context.getScaledWindowWidth() - i) / 2;
        int l = (context.getScaledWindowHeight() - j) / 2;
        float m = MinecraftClient.getInstance().getLastFrameDuration();

        if(KeyInputHandler.getTiltedLeft()) {
            tiltStage = MathHelper.lerp(0.5f * m, tiltStage, -8);
        } else if(KeyInputHandler.getTiltedRight()) {
            tiltStage = MathHelper.lerp(0.5f * m, tiltStage, 8);
        } else {
            tiltStage = MathHelper.lerp(0.5f * m, tiltStage, 0);
        }
        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(tiltStage),
                (float)context.getScaledWindowWidth() / 2, (float)context.getScaledWindowHeight() / 2, 0);
        context.drawTexture(HOLO_A, k, l + 100 - aimProgress, -100, 2.0f, 0.0f, i, j, i, j);
    }

}
