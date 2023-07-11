package com.blix.sixsiege.networking.packet;

import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.util.AmmoData;
import com.blix.sixsiege.util.IEntityDataServer;
import com.blix.sixsiege.util.MathHelperUtil;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.RaycastContext;

public class ShootC2SPacket {

    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                PacketByteBuf buf, PacketSender responseSender) {
        if(player.getMainHandStack().getItem().getClass().equals(AnimatedItem.class)) {
            AnimatedItem weapon = (AnimatedItem) player.getMainHandStack().getItem();

            if (((IEntityDataServer) player).getPersistentData().getInt("ammo") > 0) {
                AmmoData.removeAmmo(((IEntityDataServer) player), 1);
                player.getServerWorld().playSound(null, player.getBlockPos(), ((AnimatedItem) player.getMainHandStack().getItem()).getShootSound(),
                        SoundCategory.PLAYERS, 1.0f, 1.0f);
                player.getServerWorld().spawnParticles(ParticleTypes.SMOKE, player.getX(), player.getY() + 1, player.getZ(), 1,
                        0.1, 0.1, 0.1, 0.005);

                double playerVel = Math.abs((Math.sqrt(Math.pow(player.getVelocity().getX(), 2) +
                        Math.pow((player.getVelocity().getY() + 0.0784f) / 3, 2) + Math.pow(player.getVelocity().getZ(), 2))));
                if(playerVel < 0.00001) {
                    playerVel = 0;
                }

                double offset = (2.0f * playerVel) + (2.0f / 15.0f);
                if(!player.isUsingItem()) {
                    if (offset > (2.0f / 3.0f)) {
                        offset = (2.0f / 3.0f);
                    }
                } else {
                    offset = 0.0f;
                }

                Vec3d vec3d = player.getCameraPosVec(1.0f);
                Vec3d vec3d2 = player.getRotationVec(1.0f).addRandom(Random.create(), (float)offset);
                Vec3d vec3d3 = vec3d.add(vec3d2.x * 300.0D, vec3d2.y * 300.0D, vec3d2.z * 300.0D);
                HitResult hitResult = player.getWorld().raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));

                Vec3d maxVec = hitResult.getPos();
                Vec3d playerCameraVec = player.getCameraPosVec(1.0f);

                BlockHitResult blockHitResult = player.getWorld().raycast(
                        new BlockStateRaycastContext(playerCameraVec, maxVec, AbstractBlock.AbstractBlockState::isOpaque));

                boolean softWall = false;
                if(blockHitResult != null) {
                    BlockState state = player.getWorld().getBlockState(MathHelperUtil.getRaytracePos(blockHitResult));
                    softWall = blockHitResult.getType().equals(HitResult.Type.MISS) && hitResult.getType().equals(HitResult.Type.BLOCK);
                    player.getServerWorld().spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), blockHitResult.getPos().getX(), blockHitResult.getPos().getY(),
                            blockHitResult.getPos().getZ(), 3, 0.2, 0.2, 0.2, 0.005);
                }
                EntityHitResult entityHitResult = ProjectileUtil.raycast(player, playerCameraVec,
                        softWall ? maxVec.add(maxVec.subtract(playerCameraVec)) : maxVec,
                        new Box(player.getX() - 300, player.getY() - 300, player.getZ() - 300, player.getX() + 300, player.getY() + 300,
                        player.getZ() + 300), EntityPredicates.VALID_LIVING_ENTITY, 10000.0d);

                if (entityHitResult != null) {
                    if (entityHitResult.getType().equals(HitResult.Type.ENTITY)) {
                        int weaponDam;

                        if (entityHitResult.getEntity().distanceTo(player) <= weapon.getDamageDropoffDist()) {
                            weaponDam = weapon.getCloseDamage();
                        } else {
                            weaponDam = weapon.getLongDamage();
                        }
                        entityHitResult.getEntity().damage(entityHitResult.getEntity().getDamageSources().playerAttack(player), weaponDam);
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
