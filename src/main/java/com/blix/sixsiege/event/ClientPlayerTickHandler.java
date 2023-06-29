package com.blix.sixsiege.event;

import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.networking.ModMessages;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributes;

public class ClientPlayerTickHandler implements ClientTickEvents.StartTick{

    @Override
    public void onStartTick(MinecraftClient client) {

        if(client.player != null) {
            float speed;
            float cameraSpeed;

            if (client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                if (client.player.getActiveItem().getItem().getClass().equals(AnimatedItem.class)) {
                    speed = 0.2f;
                    cameraSpeed = 0.6f;
                } else {
                    speed = 0.1f;
                    cameraSpeed = 1.0f;
                }
            } else {
                speed = 0.1f;
                cameraSpeed = 1.0f;
            }

            client.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
            ClientPlayNetworking.send(ModMessages.MOVEMENT_SPEED_ID, PacketByteBufs.create());
        }

    }

}
