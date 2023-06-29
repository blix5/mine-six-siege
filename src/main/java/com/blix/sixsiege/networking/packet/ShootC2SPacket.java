package com.blix.sixsiege.networking.packet;

import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.util.AmmoData;
import com.blix.sixsiege.util.IEntityDataServer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ShootC2SPacket {

    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                PacketByteBuf buf, PacketSender responseSender) {
        if(player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            AnimatedItem weapon = (AnimatedItem) player.getMainHandStack().getItem();

            if (((IEntityDataServer) player).getPersistentData().getInt("ammo") > 0) {
                AmmoData.removeAmmo(((IEntityDataServer) player), 1);
                player.getServerWorld().playSound(null, player.getBlockPos(), ((AnimatedItem) player.getMainHandStack().getItem()).getShootSound(),
                        SoundCategory.PLAYERS, 1.0f, 1.0f);

                Vec3d maxVec = player.raycast(300.0D, 1.0f, true).getPos();
                EntityHitResult hitResult = ProjectileUtil.raycast(player, player.getCameraPosVec(1.0f), maxVec,
                        new Box(player.getX() - 300, player.getY() - 300, player.getZ() - 300, player.getX() + 300, player.getY() + 300,
                                player.getZ() + 300), EntityPredicates.VALID_LIVING_ENTITY, 10000.0d);

                if (hitResult != null) {
                    if (hitResult.getType().equals(HitResult.Type.ENTITY)) {
                        int weaponDam;

                        if (hitResult.getEntity().distanceTo(player) <= weapon.getDamageDropoffDist()) {
                            weaponDam = weapon.getCloseDamage();
                        } else {
                            weaponDam = weapon.getLongDamage();
                        }
                        hitResult.getEntity().damage(hitResult.getEntity().getDamageSources().playerAttack(player), weaponDam);
                    }
                }
                if (player.getItemCooldownManager().isCoolingDown(weapon)) {
                    weapon.setCancelReload(true);
                }
            } else if (!player.getItemCooldownManager().isCoolingDown(weapon) && (((IEntityDataServer) player).getPersistentData().getInt("ammo") < 31)) {
                weapon.setReloading(true);
            }
        }
    }

}
