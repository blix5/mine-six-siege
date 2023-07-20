package com.blix.sixsiege.client;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class AmmoHudOverlay implements HudRenderCallback {

    private static Identifier WEAPON_HUD;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            AnimatedItem weapon = (AnimatedItem) client.player.getMainHandStack().getItem();
            WEAPON_HUD = new Identifier(SixSiege.MOD_ID, "textures/gui/weapon_icons/" + weapon.getLocalpath() + "_hud.png");
            renderAmmoOverlay(drawContext);
        }
    }

    protected void renderAmmoOverlay(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        AnimatedItem weapon = (AnimatedItem) client.player.getMainHandStack().getItem();

        float f;
        float g = f = (float)Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        float h = Math.min((float)context.getScaledWindowWidth() / f, (float)context.getScaledWindowHeight() / g) * (float) 1.125;
        int i = MathHelper.floor(f * h);
        int j = MathHelper.floor(g * h);
        int k = (context.getScaledWindowWidth() - i) / 2;
        int l = (context.getScaledWindowHeight() - j) / 2;

        int color = ((IEntityDataServer) client.player).getPersistentData().getInt("ammo") < (weapon.getMaxAmmo() / 3) ? 0xD90000 : 0xFFFFFF;

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        context.drawTexture(WEAPON_HUD, (i / 30), context.getScaledWindowHeight() - (j / 9), -100, 0.0f, 0.0f, (i / 7), (j / 14), (i / 7), (j / 14));
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("" + ((IEntityDataServer) client.player).getPersistentData().getInt("ammo")
                        + " / " + weapon.getMaxAmmo()), (i / 10), context.getScaledWindowHeight() - (j / 7), color);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable(weapon.getTranslationKey()), (i / 10),
                context.getScaledWindowHeight() - (j / 30), 0xFFFFFF);
    }

}
