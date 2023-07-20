package com.blix.sixsiege.client;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.item.custom.AnimatedItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CrosshairHudOverlay implements HudRenderCallback {

    private static final Identifier CROSSHAIR = new Identifier(SixSiege.MOD_ID, "textures/gui/crosshair.png");

    private static float crossProg = 0;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client.player != null) {
            if (client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class) && !client.player.isUsingItem()) {
                AnimatedItem weapon = (AnimatedItem) client.player.getMainHandStack().getItem();
                if(!client.player.getItemCooldownManager().isCoolingDown(weapon)) {
                    renderCrosshairOverlay(drawContext);
                }
            }
        }
    }

    protected void renderCrosshairOverlay(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();

        float f;
        float g = f = (float)Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        float h = Math.min((float)context.getScaledWindowWidth() / f, (float)context.getScaledWindowHeight() / g) * 1.125f;
        int i = MathHelper.floor(f * h);
        int j = MathHelper.floor(g * h);
        int k = (context.getScaledWindowWidth() - i) / 2;
        int l = (context.getScaledWindowHeight() - j) / 2;

        double playerVel = Math.abs((Math.sqrt(Math.pow(client.player.getVelocity().getX(), 2) +
                Math.pow((client.player.getVelocity().getY() + 0.0784f) / 3, 2) + Math.pow(client.player.getVelocity().getZ(), 2))));
        if(playerVel < 0.00001) {
            playerVel = 0;
        }

        float m = client.getLastFrameDuration();
        crossProg = MathHelper.lerp(m, crossProg, 1.0f);

        int offset = (int)(i * playerVel);
        if(offset > (i / 3)) {
            offset = (i / 3);
        } else if (offset < (i / 15)) {
            offset = (i / 15);
        }
        offset *= crossProg;

        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        context.drawTexture(CROSSHAIR, k + (i/2) - (i/80), l + (j/2) - (j/80), -100, (i / (80.0f / 3)), (j / (80.0f / 3)),
                (i / 40), (j / 40), i / 10, j / 10);

        context.drawTexture(CROSSHAIR, k + (i/2) - (i/50) - offset, l + (j/2) - (j/50), -100, 0.0f, (j / 33.0f),
                (i / 25), (j / 25), i / 10, j / 10);
        context.drawTexture(CROSSHAIR, k + (i/2) - (i/50), l + (j/2) - (j/50) - offset, -100, (i / 33.0f), 0.0f,
                (i / 25), (j / 25), i / 10, j / 10);
        context.drawTexture(CROSSHAIR, k + (i/2) - (i/50), l + (j/2) - (j/50) + offset, -100, (i / 33.0f), (j / 16.5f),
                (i / 25), (j / 25), i / 10, j / 10);
        context.drawTexture(CROSSHAIR, k + (i/2) - (i/50) + offset, l + (j/2) - (j/50), -100, (i / 16.5f), (j / 33.0f),
                (i / 25), (j / 25), i / 10, j / 10);
    }

    public static void setCrossProg(float crossProg) {
        CrosshairHudOverlay.crossProg = crossProg;
    }

}
