package com.blix.sixsiege.mixin;

import com.blix.sixsiege.client.ScopeHudOverlay;
import com.blix.sixsiege.event.KeyInputHandler;
import com.blix.sixsiege.item.custom.AnimatedItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow public abstract MinecraftClient getClient();

    private static float tiltStage = 0;

    @Inject(method = "renderWorld", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/Camera;update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V",
            shift = At.Shift.BEFORE))
    private void postCameraUpdate(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
        if(this.getClient().player != null) {
            if(this.getClient().player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                float m = this.getClient().getLastFrameDuration();

                if(KeyInputHandler.getTiltedLeft() && !this.getClient().player.isSprinting()) {
                    tiltStage = MathHelper.lerp(0.5f * m, tiltStage, -15);
                } else if(KeyInputHandler.getTiltedRight() && !this.getClient().player.isSprinting()) {
                    tiltStage = MathHelper.lerp(0.5f * m, tiltStage, 15);
                } else {
                    tiltStage = MathHelper.lerp(0.5f * m, tiltStage, 0);
                }

                matrix.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(tiltStage));
            }
        }
    }

}
