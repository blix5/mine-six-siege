package com.blix.sixsiege.mixin;

import com.blix.sixsiege.event.KeyInputHandler;
import com.blix.sixsiege.item.custom.AnimatedItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    private float tiltStage = 0;

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    private void injectRenderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress,
                                             ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                             int light, CallbackInfo ci) {
        if(item.getItem().getClass().equals(AnimatedItem.class)) {
            if(player.getActiveItem().isOf(item.getItem())) {
                float m = MinecraftClient.getInstance().getLastFrameDuration();

                if(!player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())) {
                    matrices.translate(-0.48f, 0.06f, -0.3f);
                } else {
                    KeyInputHandler.setTiltedLeft(false);
                    KeyInputHandler.setTiltedRight(false);
                }
                matrices.scale(1.0f, 1.0f, 1.0f);

                if (KeyInputHandler.getTiltedLeft()) {
                    tiltStage = MathHelper.lerp(0.5f * m, tiltStage, 8);
                } else if (KeyInputHandler.getTiltedRight()) {
                    tiltStage = MathHelper.lerp(0.5f * m, tiltStage, -8);
                } else {
                    tiltStage = MathHelper.lerp(0.5f * m, tiltStage, 0);
                }
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(tiltStage),
                        0.48f, -0.06f, 0.3f);
            }
        }
    }

}
