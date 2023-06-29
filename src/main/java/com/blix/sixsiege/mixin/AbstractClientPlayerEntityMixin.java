package com.blix.sixsiege.mixin;

import com.blix.sixsiege.item.custom.AnimatedItem;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {

    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "getFovMultiplier()F", at = @At("RETURN"), cancellable = true)
    public void injectGetFovMultiplier(CallbackInfoReturnable<Float> cir) {
        ItemStack itemStack = this.getActiveItem();
        if (this.isUsingItem() && itemStack.getItem().getClass().equals(AnimatedItem.class) &&
                MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
            cir.setReturnValue(0.8f);
        } else if(this.getMainHandStack().getItem().getClass().equals(AnimatedItem.class) && !this.isSprinting()) {
            cir.setReturnValue(MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(), 0.8f, 1.0f));
        }
    }

}
