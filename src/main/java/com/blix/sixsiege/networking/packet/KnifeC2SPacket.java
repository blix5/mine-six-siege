package com.blix.sixsiege.networking.packet;

import com.blix.sixsiege.block.custom.BarricadeBlock;
import com.blix.sixsiege.sound.ModSounds;
import com.blix.sixsiege.util.MathHelperUtil;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class KnifeC2SPacket {

    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        HitResult hit = player.getCameraEntity().raycast(10.0D, 1.0f, false);
        BlockPos pos = MathHelperUtil.getRaytracePos((BlockHitResult) hit);
        BlockState state = player.getWorld().getBlockState(pos);

        if(state.getBlock().getClass().equals(BarricadeBlock.class)) {
            if(state.get(BarricadeBlock.DAMAGE_STAGE) < 5) {
                player.getWorld().setBlockState(pos, state.with(BarricadeBlock.DAMAGE_STAGE, state.get(BarricadeBlock.DAMAGE_STAGE) + 6)
                        .with(BarricadeBlock.DAMAGED, true));
                player.getServerWorld().playSound(null, player.getBlockPos(), ModSounds.BARRICADE_HIT, SoundCategory.PLAYERS,
                        1.0f, 0.8f + (Random.create().nextFloat() * 2f / 5f));
            } else {
                player.getWorld().breakBlock(pos, false);
            }
        } else if(state.getBlock().getClass().equals(GlassBlock.class) || state.getBlock().getClass().equals(StainedGlassPaneBlock.class)
                || state.getBlock().equals(Blocks.GLASS_PANE) || state.getBlock().getClass().equals(StainedGlassBlock.class)) {
            player.getWorld().breakBlock(pos, false);
        }
    }

}
