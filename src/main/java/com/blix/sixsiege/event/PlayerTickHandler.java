package com.blix.sixsiege.event;

import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

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
            }

        }
    }

}
