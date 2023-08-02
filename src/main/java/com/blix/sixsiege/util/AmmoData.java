package com.blix.sixsiege.util;

import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class AmmoData {

    public static int addAmmo(IEntityDataServer player, int amount) {
        int maxAmmo = getMaxAmmo(MinecraftClient.getInstance().player);
        NbtCompound nbt = player.getPersistentData();

        int ammo = nbt.getInt("ammo");
        if(ammo + amount >= maxAmmo) {
            ammo = maxAmmo;
        } else {
            ammo += amount;
        }

        nbt.putInt("ammo", ammo);
        syncAmmo(ammo, (ServerPlayerEntity) player);
        return ammo;
    }

    public static int removeAmmo(IEntityDataServer player, int amount) {
        NbtCompound nbt = player.getPersistentData();

        int ammo = nbt.getInt("ammo");
        if(ammo - amount < 0) {
            ammo = 0;
        } else {
            ammo -= amount;
        }

        nbt.putInt("ammo", ammo);
        syncAmmo(ammo, (ServerPlayerEntity) player);
        return ammo;
    }

    public static int setAmmo(IEntityDataServer player, int amount) {
        int maxAmmo = getMaxAmmo((ServerPlayerEntity) player);
        NbtCompound nbt = player.getPersistentData();

        int ammo;
        if(amount < 0) {
            ammo = 0;
        } else if(amount >= maxAmmo) {
            ammo = maxAmmo;
        } else {
            ammo = amount;
        }

        nbt.putInt("ammo", ammo);
        syncAmmo(ammo, (ServerPlayerEntity) player);
        return ammo;
    }

    public static void syncAmmo(int ammo, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(ammo);
        ServerPlayNetworking.send(player, ModMessages.AMMO_SYNC_ID, buffer);
    }

    private static int getMaxAmmo(PlayerEntity player) {
        if(player != null) {
            return 31;
        } else {
            return 0;
        }
    }

}
