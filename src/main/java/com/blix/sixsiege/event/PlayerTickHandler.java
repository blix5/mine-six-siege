package com.blix.sixsiege.event;

import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.networking.ModMessages;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.LightType;
import org.apache.logging.log4j.core.jmx.Server;

public class PlayerTickHandler implements ServerTickEvents.StartTick {

    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

            if(player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                AnimatedItem weapon = (AnimatedItem) player.getMainHandStack().getItem();
                if (player.isSprinting()) {
                    if (player.getActiveItem().getItem().equals(weapon)) {
                        player.setSprinting(false);
                        //player.velocityModified = true;
                    }
                }

                if(((IEntityDataServer) player).getPersistentData().getInt("ammo") == 0) {
                    player.setSprinting(false);
                    //player.velocityModified = true;
                }

                float lightBlock = player.getWorld().getLightLevel(LightType.BLOCK, player.getBlockPos()) / 15.0f;
                float lightSky = (player.getWorld().getLightLevel(LightType.SKY, player.getBlockPos()) / 15.0f) -
                        (player.getWorld().isNight() ? 0.6f : 0.0f);

                PacketByteBuf lightBuf = PacketByteBufs.create();
                lightBuf.writeFloat(Math.max(lightBlock, lightSky));
                ServerPlayNetworking.send(player, ModMessages.LIGHT_ID, lightBuf);

                PacketByteBuf cooldownBuf = PacketByteBufs.create();
                cooldownBuf.writerIndex(9);
                cooldownBuf.setBoolean(0, player.getItemCooldownManager().isCoolingDown(weapon));
                cooldownBuf.setFloat(1, player.getItemCooldownManager().getCooldownProgress(weapon, 1.0f));
                ServerPlayNetworking.send(player, ModMessages.COOLDOWN_ID, cooldownBuf);
            }

        }
    }

}
