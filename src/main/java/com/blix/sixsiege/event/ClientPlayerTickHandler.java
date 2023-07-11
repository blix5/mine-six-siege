package com.blix.sixsiege.event;

import com.blix.sixsiege.client.ScopeHudOverlay;
import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.text.Text;

import java.util.UUID;

public class ClientPlayerTickHandler implements ClientTickEvents.StartTick {

    private static boolean hasAimed = false;
    private static final UUID AIM_SLOW_ID = UUID.fromString("165f5202-1ed2-11ee-be56-0242ac120002");

    @Override
    public void onStartTick(MinecraftClient client) {

        if(client.player != null) {
            if (client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                if (client.player.getActiveItem().getItem().getClass().equals(AnimatedItem.class)) {
                    if(!hasAimed) {
                        client.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                                .addTemporaryModifier(new EntityAttributeModifier(AIM_SLOW_ID,
                                        "aiming_gun", 2.0f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
                        hasAimed = true;
                    }
                } else {
                    if(hasAimed) {
                        client.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                                .removeModifier(AIM_SLOW_ID);
                        hasAimed = false;
                    }
                }
            } else {
                if(hasAimed) {
                    client.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                            .removeModifier(AIM_SLOW_ID);
                    hasAimed = false;
                }
            }

            if(client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                AnimatedItem weapon = (AnimatedItem) client.player.getMainHandStack().getItem();
                if (client.player.isSprinting()) {
                    if (client.player.getItemCooldownManager().isCoolingDown(weapon)) {
                        weapon.setCancelReload(true);
                    }
                    if (client.player.getActiveItem().getItem().equals(weapon)) {
                        client.player.setSprinting(false);
                    }
                }

                if (client.player.isSprinting() || client.player.getItemCooldownManager().isCoolingDown(weapon)) {
                    ScopeHudOverlay.setTiltStage(0);
                    KeyInputHandler.setTiltedRight(false);
                    KeyInputHandler.setTiltedLeft(false);
                }

                if(((IEntityDataServer) client.player).getPersistentData().getInt("ammo") == 0) {
                    client.player.setSprinting(false);
                }
            }
        }

    }

}
