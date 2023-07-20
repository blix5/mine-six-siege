package com.blix.sixsiege.mixin;

import com.blix.sixsiege.client.ScopeHudOverlay;
import com.blix.sixsiege.event.KeyInputHandler;
import com.blix.sixsiege.item.custom.AnimatedItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

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

                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-1 * ScopeHudOverlay.getTiltStage()),
                        0.48f, -0.06f, 0.3f);
            }
        }
    }

}
