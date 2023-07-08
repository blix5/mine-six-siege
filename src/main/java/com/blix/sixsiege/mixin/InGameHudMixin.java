package com.blix.sixsiege.mixin;

import com.blix.sixsiege.item.custom.AnimatedItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/option/Perspective;isFirstPerson()Z"))
    private boolean redirectRenderCrosshair(Perspective instance) {
        return instance.isFirstPerson() && !client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class);
    }

}
