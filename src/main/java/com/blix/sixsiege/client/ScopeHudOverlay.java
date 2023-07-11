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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;

import java.util.Random;

public class ScopeHudOverlay implements HudRenderCallback {

    private static final Identifier HOLO_A = new Identifier(SixSiege.MOD_ID, "textures/gui/scopes/holo_a.png");
    private int ammoSynced = 30;
    protected static float tiltStage = 0;
    protected static int aimProgress = 0;

    private static boolean shouldAddRecoil;
    private float f;
    protected static float g;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if(client.player != null) {
            if (shouldAddRecoil) {
                f = 3.5f;
                g = 0.5f;
                shouldAddRecoil = false;
            }
            f = MathHelper.lerp(1.5f * client.getLastFrameDuration(), f, 0.0f);
            if (f > 0.00001) {
                float fixed = f * client.getLastFrameDuration();
                client.player.setPitch(client.player.getPitch() - fixed);
                client.player.setYaw(client.player.getYaw() + new Random().nextFloat(fixed / -2, fixed / 2));
            } else {
                f = 0;
            }

            if (ammoSynced != 0) {
                ServerPlayerEntity serverPlayer = client.getServer().getPlayerManager().getPlayer(client.player.getUuid());
                AmmoData.syncAmmo(((IEntityDataServer) serverPlayer).getPersistentData().getInt("ammo"), serverPlayer);
                ammoSynced--;
            }


            if (client.options.getPerspective().isFirstPerson() && !client.isPaused()) {
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
        ServerPlayerEntity serverPlayer = client.getServer().getPlayerManager().getPlayer(client.player.getUuid());
        g = MathHelper.lerp(client.getLastFrameDuration() * 0.5f, g, 0.0f);
        float m = client.getLastFrameDuration();
        float fixed = g * m * 5;

        float f;
        float g = f = (float)Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        float h = Math.min((float)context.getScaledWindowWidth() / f, (float)context.getScaledWindowHeight() / g) * (float) 1.125 + fixed;
        int i = MathHelper.floor(f * h);
        int j = MathHelper.floor(g * h);
        int k = (context.getScaledWindowWidth() - i) / 2;
        int l = (context.getScaledWindowHeight() - j) / 2;

        float lightBlock = client.player.getWorld().getLightLevel(LightType.BLOCK, client.player.getBlockPos()) / 15.0f;
        float lightSky = (client.player.getWorld().getLightLevel(LightType.SKY, client.player.getBlockPos()) / 15.0f) -
                (serverPlayer.getServerWorld().isNight() ? 0.6f : 0.0f);
        float lighting = Math.max(lightBlock, lightSky);

        if(KeyInputHandler.getTiltedLeft() && !client.player.isSprinting()) {
            tiltStage = MathHelper.lerp(0.5f * m, tiltStage, -8);
        } else if(KeyInputHandler.getTiltedRight() && !client.player.isSprinting()) {
            tiltStage = MathHelper.lerp(0.5f * m, tiltStage, 8);
        } else {
            tiltStage = MathHelper.lerp(0.5f * m, tiltStage, 0);
        }

        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(tiltStage),
                (float)context.getScaledWindowWidth() / 2, (float)context.getScaledWindowHeight() / 2, 0);
        context.setShaderColor(lighting, lighting, lighting, 1.0f);

        context.drawTexture(this.getRenderTexture(), k, l + 100 - aimProgress, -100, 2.0f, 0.0f, i, j, i, j);
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
