package com.blix.sixsiege.mixin;

import com.blix.sixsiege.item.custom.AnimatedItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public class MouseMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow private double cursorDeltaX;

    @Shadow private double cursorDeltaY;

    @Redirect(method = "updateMouse()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;cursorDeltaX:D", ordinal = 3))
    public double injectUpdateMouseX(Mouse instance) {
        double f = this.client.options.getMouseSensitivity().getValue() * (double)0.6f + (double)0.2f;
        double g = f * f * f;

        if (client.options.getPerspective().isFirstPerson() && client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            if(client.player.getActiveItem().getItem().getClass().equals(AnimatedItem.class)) {
                return this.cursorDeltaX * g * 13.0f;
            } else return this.cursorDeltaX;
        } else return this.cursorDeltaX;
    }

    @Redirect(method = "updateMouse()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;cursorDeltaY:D", ordinal = 3))
    public double injectUpdateMouseY(Mouse instance) {
        double f = this.client.options.getMouseSensitivity().getValue() * (double)0.6f + (double)0.2f;
        double g = f * f * f;

        if (client.options.getPerspective().isFirstPerson() && client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            if(client.player.getActiveItem().getItem().getClass().equals(AnimatedItem.class)) {
                return this.cursorDeltaY * g * 13.0f;
            } else return this.cursorDeltaY;
        } else return this.cursorDeltaY;
    }

}
