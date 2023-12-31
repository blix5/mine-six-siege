package com.blix.sixsiege.client;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.event.KeyInputHandler;
import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.networking.packet.LightS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class ScopeHudOverlay implements HudRenderCallback {

    private static final Identifier HOLO_A = new Identifier(SixSiege.MOD_ID, "textures/gui/scopes/holo_a.png");
    protected static float tiltStage = 0;
    protected static int aimProgress = 0;

    private static boolean shouldAddRecoil;
    private static float recoil;
    private static float prevf = 0;
    protected static float scopeScale;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if(client.player != null) {
            if (shouldAddRecoil) {
                recoil = 1.0f;
                scopeScale = 0.5f;
                shouldAddRecoil = false;
            }
            recoil = MathHelper.lerp(0.5f * client.getLastFrameDuration(), recoil, 0.0f);
            if (recoil > 0.0001) {
                client.player.setPitch(client.player.getPitch() - Math.abs(recoil - prevf));
                client.player.setYaw(client.player.getYaw() + ((Random.create().nextFloat() - 0.5f) * Math.abs(recoil - prevf)));
                prevf = recoil;
            } else {
                recoil = 0;
            }

            if (client.options.getPerspective().isFirstPerson()) {
                if (client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                    AnimatedItem weapon = (AnimatedItem) client.player.getMainHandStack().getItem();
                    float m = client.getLastFrameDuration();

                    if (client.player.getActiveItem().isOf(weapon)) {
                        CrosshairHudOverlay.setCrossProg(0);
                        this.renderScopeOverlay(drawContext);
                        if(aimProgress < 90) {
                            aimProgress = MathHelper.lerp(m, aimProgress, 100);
                        } else {
                            aimProgress = 100;
                        }
                    } else {
                        aimProgress = 0;
                    }
                } else {
                    aimProgress = 0;
                }
            }
        }
    }

    protected void renderScopeOverlay(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        scopeScale = MathHelper.lerp(client.getLastFrameDuration() * 0.5f, scopeScale, 0.0f);
        float m = client.getLastFrameDuration();

        float f;
        float g = f = (float)Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        float h = Math.min((float)context.getScaledWindowWidth() / f, (float)context.getScaledWindowHeight() / g) * (float) 1.125 + (scopeScale * 2);
        int i = MathHelper.floor(f * h);
        int j = MathHelper.floor(g * h);
        int k = (context.getScaledWindowWidth() - i) / 2;
        int l = (context.getScaledWindowHeight() - j) / 2;

        if(client.player != null) {
            if (KeyInputHandler.getTiltedLeft() && !client.player.isSprinting()) {
                tiltStage = MathHelper.lerp(0.5f * m, tiltStage, -8);
            } else if (KeyInputHandler.getTiltedRight() && !client.player.isSprinting()) {
                tiltStage = MathHelper.lerp(0.5f * m, tiltStage, 8);
            } else {
                tiltStage = MathHelper.lerp(0.5f * m, tiltStage, 0);
            }
        }

        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(tiltStage),
                (float)context.getScaledWindowWidth() / 2, (float)context.getScaledWindowHeight() / 2, 0);
        context.setShaderColor(LightS2CPacket.getLighting(), LightS2CPacket.getLighting(), LightS2CPacket.getLighting(), 1.0f);
        context.drawTexture(this.getRenderTexture(), k, l + 100 - aimProgress, -100, 2.0f, 0.0f, i, j, i, j);
        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-1 * tiltStage),
                (float)context.getScaledWindowWidth() / 2, (float)context.getScaledWindowHeight() / 2, 0);
    }

    public static void setShouldAddRecoil(boolean bool) {
        shouldAddRecoil = bool;
    }

    protected Identifier getRenderTexture() {
        return HOLO_A;
    }

    public static float getTiltStage() {
        return tiltStage;
    }

    public static void setTiltStage(float tiltStage) {
        ScopeHudOverlay.tiltStage = tiltStage;
    }

}
