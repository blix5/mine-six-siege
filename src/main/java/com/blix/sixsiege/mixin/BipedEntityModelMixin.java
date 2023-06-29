package com.blix.sixsiege.mixin;

import com.blix.sixsiege.item.custom.AnimatedItem;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> {

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart head;

    @Shadow @Final public ModelPart leftArm;

    @Inject(method = "positionRightArm", at = @At("RETURN"))
    private void positionRightArm(T entity, CallbackInfo ci) {
        if(entity.isPlayer() && entity.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            AnimatedItem weapon = (AnimatedItem) entity.getMainHandStack().getItem();
            if(entity.getActiveItem().isOf(weapon)) {
                this.rightArm.yaw = -0.1f + this.head.yaw;
                this.leftArm.yaw = 0.1f + this.head.yaw + 0.4f;
                this.rightArm.pitch = -1.5707964f + this.head.pitch;
                this.leftArm.pitch = -1.5707964f + this.head.pitch;
            }
        }
    }

}
