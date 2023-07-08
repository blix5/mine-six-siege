package com.blix.sixsiege.client;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AmmoHudOverlay implements HudRenderCallback {

    private static Identifier WEAPON_HUD;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            AnimatedItem weapon = (AnimatedItem) client.player.getMainHandStack().getItem();
            WEAPON_HUD = new Identifier(SixSiege.MOD_ID, "textures/gui/" + weapon.getLocalpath() + "_hud.png");
            TextRenderer textRenderer = client.textRenderer;

            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();
            int x = 0;
            int y = height;

            drawContext.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            drawContext.drawTexture(WEAPON_HUD, x + 10, y - 30, 0, 0, 48, 24, 48, 24);

            drawContext.drawCenteredTextWithShadow(textRenderer, Text.literal("" + ((IEntityDataServer) client.player).getPersistentData().getInt("ammo")),
                    x + 30, y - 40, 0xFFFFFF);
        }
    }

}
