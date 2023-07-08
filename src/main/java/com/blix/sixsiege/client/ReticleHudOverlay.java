package com.blix.sixsiege.client;

import com.blix.sixsiege.SixSiege;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ReticleHudOverlay extends ScopeHudOverlay {

    @Override
    protected Identifier getRenderTexture() {
        return new Identifier(SixSiege.MOD_ID, "textures/gui/scopes/reticles/holo_a_reticle.png");
    }

    @Override
    protected void renderScopeOverlay(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        float m = client.getLastFrameDuration();
        float fixed = this.g * m * 3;

        float f;
        float g = f = (float)Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        float h = Math.min((float)context.getScaledWindowWidth() / f, (float)context.getScaledWindowHeight() / g) * (float) 1.125 + fixed;
        int i = MathHelper.floor(f * h);
        int j = MathHelper.floor(g * h);
        int k = (context.getScaledWindowWidth() - i) / 2;
        int l = (context.getScaledWindowHeight() - j) / 2;

        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        context.drawTexture(this.getRenderTexture(), k + (i/2) - (i/10), (l + (j/2) - (j/10)) + 100 - aimProgress, -100, 0.0f, 0.0f,
                i / 5, j / 5, i / 5, j / 5);
    }

}
