package com.blix.sixsiege.event;

import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String KEY_CATEGORY_SIXSIEGE = "key.category.sixsiege";
    public static final String KEY_RELOAD = "key.sixsiege.reload";
    public static final String KEY_LEAN_LEFT = "key.sixsiege.lean_left";
    public static final String KEY_LEAN_RIGHT = "key.sixsiege.lean_right";

    public static KeyBinding reloadingKey;
    public static KeyBinding leanLeftKey;
    public static KeyBinding leanRightKey;

    private static boolean reloadKeyReady = true;
    private static boolean readyLeftMouse = false;
    private static boolean tiltLeftReady = true;
    private static boolean tiltRightReady = true;

    private static boolean tiltedLeft = false;
    private static boolean tiltedRight = false;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.player != null) {
                if (client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
                    AnimatedItem weapon = (AnimatedItem) client.player.getMainHandStack().getItem();

                    if (reloadingKey.isPressed()) {
                        if (reloadKeyReady && !client.player.getItemCooldownManager().isCoolingDown(weapon) &&
                                (((IEntityDataServer) client.player).getPersistentData().getInt("ammo") < 31)) {
                            ((AnimatedItem) client.player.getMainHandStack().getItem()).setReloading(true);
                            reloadKeyReady = false;
                        }
                    } else {
                        reloadKeyReady = true;
                    }

                    if (!client.mouse.wasLeftButtonClicked() && readyLeftMouse) {
                        ClientPreAttackHandler.setCooldown(1);
                        readyLeftMouse = false;
                    }
                    if (client.mouse.wasLeftButtonClicked() && !readyLeftMouse) {
                        readyLeftMouse = true;
                    }

                    if (client.mouse.wasRightButtonClicked()) {
                        if (client.player != null) {
                            if (client.player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)
                                    && client.player.getItemCooldownManager().isCoolingDown(weapon)) {
                                weapon.setCancelReload(true);
                            }
                        }
                    }

                    if(!client.player.isSprinting() && !client.player.getItemCooldownManager().isCoolingDown(weapon)) {
                        if (leanLeftKey.isPressed()) {
                            if (tiltLeftReady) {
                                tiltedLeft = !tiltedLeft;
                                tiltedRight = false;
                                tiltLeftReady = false;
                            }
                        } else {
                            tiltLeftReady = true;
                        }

                        if (leanRightKey.isPressed()) {
                            if (tiltRightReady) {
                                tiltedRight = !tiltedRight;
                                tiltedLeft = false;
                                tiltRightReady = false;
                            }
                        } else {
                            tiltRightReady = true;
                        }
                    }
                }
            }
        });
    }

    public static void register() {
        reloadingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_RELOAD, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_SIXSIEGE));
        leanLeftKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_LEAN_LEFT, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Q, KEY_CATEGORY_SIXSIEGE));
        leanRightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_LEAN_RIGHT, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_E, KEY_CATEGORY_SIXSIEGE));

        registerKeyInputs();
    }

    public static boolean getTiltedLeft() {
        return tiltedLeft;
    }

    public static boolean getTiltedRight() {
        return tiltedRight;
    }

    public static void setTiltedLeft(boolean tiltedLeft) {
        KeyInputHandler.tiltedLeft = tiltedLeft;
    }

    public static void setTiltedRight(boolean tiltedRight) {
        KeyInputHandler.tiltedRight = tiltedRight;
    }

}
