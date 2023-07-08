package com.blix.sixsiege.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public class MathHelperUtil {

    public static BlockPos getRaytracePos(BlockHitResult hitResult) {
        int x = MathHelper.floor(hitResult.getPos().getX());
        int y = MathHelper.floor(hitResult.getPos().getY());
        int z = MathHelper.floor(hitResult.getPos().getZ());

        if(hitResult.getSide().equals(Direction.SOUTH)) {
            z--;
        }

        if(hitResult.getSide().equals(Direction.EAST)) {
            x--;
        }

        if(hitResult.getSide().equals(Direction.UP)) {
            y--;
        }

        return new BlockPos(x, y, z);
    }

}
