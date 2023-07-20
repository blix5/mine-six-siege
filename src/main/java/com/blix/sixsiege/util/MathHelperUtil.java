package com.blix.sixsiege.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public class MathHelperUtil {

    public static BlockPos getRaytracePos(BlockHitResult hitResult) {
        double x = hitResult.getPos().getX();
        double y = hitResult.getPos().getY();
        double z = hitResult.getPos().getZ();

        if(hitResult.getSide().equals(Direction.SOUTH)) {
            z -= 0.1;
        }

        if(hitResult.getSide().equals(Direction.EAST)) {
            x -= 0.1;
        }

        if(hitResult.getSide().equals(Direction.UP)) {
            y -= 0.1;
        }

        return new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }

}
