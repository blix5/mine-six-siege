package com.blix.sixsiege.mixin;

import com.blix.sixsiege.event.KeyInputHandler;
import com.blix.sixsiege.item.custom.AnimatedItem;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow private Entity focusedEntity;

    @Shadow protected abstract void moveBy(double x, double y, double z);

    @Inject(method = "update", at = @At("RETURN"))
    public void injectUpdate(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if(!thirdPerson && this.focusedEntity.isPlayer()) {
            if(((PlayerEntity) this.focusedEntity).getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                if(KeyInputHandler.getTiltedLeft()) {
                    //this.moveBy(0, 0, 0.2);
                }
                if(KeyInputHandler.getTiltedRight()) {
                    //this.moveBy(0, 0, -0.2);
                }
            }
        }
    }

}
