package com.blix.sixsiege.networking.packet;

import com.blix.sixsiege.block.ModBlocks;
import com.blix.sixsiege.block.custom.BarricadeBlock;
import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.networking.ModMessages;
import com.blix.sixsiege.sound.ModSounds;
import com.blix.sixsiege.util.AmmoData;
import com.blix.sixsiege.util.IEntityDataServer;
import com.blix.sixsiege.util.MathHelperUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.BlockStateParticleEffect;
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

                player.getServerWorld().playSoundFromEntity(player, player, ((AnimatedItem) player.getMainHandStack().getItem()).getShootSound(),
                            SoundCategory.PLAYERS, 5.0f, 1.2f);
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

                //Raytracing is fucking annoying
                buf.writerIndex(48);
                Vec3d vec3d = new Vec3d(buf.getFloat(0), buf.getFloat(8), buf.getFloat(16));
                Vec3d vec3d2 = new Vec3d(buf.getFloat(24), buf.getFloat(32), buf.getFloat(40)).addRandom(Random.create(), (float)offset);
                Vec3d vec3d3 = vec3d.add(vec3d2.x * 300.0D, vec3d2.y * 300.0D, vec3d2.z * 300.0D);
                HitResult hitResult = player.getWorld().raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE,
                        RaycastContext.FluidHandling.NONE, player));

                Vec3d maxVec = hitResult.getPos();

                BlockHitResult blockHitResult = player.getWorld().raycast(
                        new BlockStateRaycastContext(vec3d, hitResult.getPos(), AbstractBlock.AbstractBlockState::isOpaque));

                boolean softWall;

                BlockPos pos = MathHelperUtil.getRaytracePos(blockHitResult);
                BlockState state = player.getWorld().getBlockState(pos);

                softWall = blockHitResult.getType().equals(HitResult.Type.MISS) && hitResult.getType().equals(HitResult.Type.BLOCK);
                player.getServerWorld().spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), blockHitResult.getPos().getX(),
                        blockHitResult.getPos().getY(), blockHitResult.getPos().getZ(), 3, 0.2, 0.2, 0.2, 0.005);

                HitResult tempHitResult = blockHitResult;
                BlockHitResult tempBlockHitResult;
                if(softWall) {
                    if(state.getBlock().getClass().equals(BarricadeBlock.class)) {
                        if(state.get(BarricadeBlock.DAMAGE_STAGE) < 10) {
                            if(state.get(BarricadeBlock.DAMAGE_STAGE) > 5) {
                                player.getWorld().setBlockState(pos, state.with(BarricadeBlock.DAMAGE_STAGE, state.get(BarricadeBlock.DAMAGE_STAGE) + 1)
                                        .with(BarricadeBlock.DAMAGED, true));
                                if(state.get(BarricadeBlock.DAMAGE_STAGE) == 6) {
                                    player.getServerWorld().playSound(null, pos, ModSounds.BARRICADE_HIT, SoundCategory.BLOCKS,
                                            1.0f, 0.8f + (Random.create().nextFloat() * 2f / 5f));
                                }
                            } else {
                                player.getWorld().setBlockState(pos, state.with(BarricadeBlock.DAMAGE_STAGE, state.get(BarricadeBlock.DAMAGE_STAGE) + 1));
                            }
                        } else {
                            player.getWorld().breakBlock(pos, false);
                        }
                    } else if(state.getBlock().getClass().equals(GlassBlock.class) || state.getBlock().getClass().equals(StainedGlassPaneBlock.class)
                            || state.getBlock().equals(Blocks.GLASS_PANE) || state.getBlock().getClass().equals(StainedGlassBlock.class)) {
                        player.getWorld().breakBlock(pos, false);
                    }

                    for (int i = 0; i < 15; i++) {
                        HitResult prevTempHitResult = tempHitResult;
                        tempHitResult = player.getWorld().raycast(new RaycastContext(tempHitResult.getPos(), vec3d3, RaycastContext.ShapeType.OUTLINE,
                                RaycastContext.FluidHandling.NONE, player));
                        tempBlockHitResult = player.getWorld().raycast(
                                new BlockStateRaycastContext(prevTempHitResult.getPos(), hitResult.getPos(), AbstractBlock.AbstractBlockState::isOpaque));

                        if (tempBlockHitResult.getType().equals(HitResult.Type.BLOCK)) {
                            maxVec = tempHitResult.getPos();
                            break;
                        }
                    }
                }

                EntityHitResult entityHitResult = ProjectileUtil.raycast(player, vec3d, maxVec, new Box(player.getX() - 300, player.getY() - 300,
                        player.getZ() - 300, player.getX() + 300, player.getY() + 300, player.getZ() + 300),
                        EntityPredicates.VALID_LIVING_ENTITY, 10000.0d);

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
                    ServerPlayNetworking.send(player, ModMessages.CANCEL_RELOAD_ID, PacketByteBufs.create());
                }
            } else if (!player.getItemCooldownManager().isCoolingDown(weapon) && (((IEntityDataServer) player).getPersistentData().getInt("ammo") < 31)) {
                ServerPlayNetworking.send(player, ModMessages.SET_RELOADING_ID, PacketByteBufs.create());
            }
        }
    }

}
