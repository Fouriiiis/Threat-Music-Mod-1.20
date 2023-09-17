// Import necessary classes
package com.example;
import java.util.List;
import java.util.stream.Collectors;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.GuardianEntity;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ShulkerEntity;

import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;

import net.minecraft.util.hit.HitResult;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;


public class ThreatTracker implements EndTick {

    private static int blockRadius = 20;
    public static int threatLevel = 0;
    private int lastPlayed = 0;
    private int maxTime = 200;
    private Boolean stopped = true;

    @Override
    public void onEndTick(MinecraftClient client) {

        threatLevel = 0;

        if (client != null && client.player != null) {
        
            List<Entity> entities = client.world.getEntitiesByClass(Entity.class, client.player.getBoundingBox().expand(blockRadius, blockRadius, blockRadius), EntityPredicates.EXCEPT_SPECTATOR);

            List<Entity> filteredEntities = entities.stream()
                    .filter(entity -> entity instanceof MobEntity || entity instanceof ShulkerEntity)
                    .collect(Collectors.toList());

            // For each hostile entity, check if it is targeting the player by checking if the player is in its line of sight using raytracing
            for (Entity entity : filteredEntities) {
                if (entity instanceof WardenEntity) {
                    if(((WardenEntity) entity).getAnger() > 0) {
                        threatLevel += ((LivingEntity) entity).getMaxHealth();
                    }
                } else if (entity instanceof ShulkerEntity) {
                    if(lineofSight(entity, client.player)) {
                        threatLevel += ((LivingEntity) entity).getMaxHealth();
                    }
                } else if (entity instanceof GuardianEntity || entity instanceof ElderGuardianEntity) {
                        if(lineofSight(entity, client.player) && ((GuardianEntity) entity).hasBeamTarget()) {
                            threatLevel += ((LivingEntity) entity).getMaxHealth();
                        }
                    } else if (entity instanceof MobEntity) {
                        if(lineofSight(entity, client.player) && ((MobEntity) entity).isAttacking()) {
                            threatLevel += ((LivingEntity) entity).getMaxHealth();
                    } else if (entity instanceof WitherEntity) {
                        threatLevel += ((LivingEntity) entity).getMaxHealth();
                    }
                }
            }
            System.out.println(threatLevel);

            if (threatLevel > 0 && stopped) {
                ModSounds.changeRegion(client);
                ModSounds.currentRegion.play(client);
                client.player.sendMessage(Text.of("Playing music"), false);
                lastPlayed = 0;
                stopped = false;
            } else if (threatLevel == 0 && !stopped) {
                if (lastPlayed >= maxTime) {
                    ModSounds.currentRegion.stop(client);
                    ModSounds.currentRegion.randomizeLayers();
                    client.player.sendMessage(Text.of("Stopping music"), false);
                    stopped = true;
                    lastPlayed = 0;
                } else if (threatLevel != 0) {
                    lastPlayed = 0;
                } else {
                    lastPlayed++;
                }
            }
        }
    }
    public static boolean lineofSight(Entity entity, PlayerEntity player) {
        Vec3d eyePos = entity.getEyePos();
        Vec3d platerPos = player.getPos();
        Vec3d endVec = player.getEyePos();
        
        HitResult result = entity.getWorld().raycast(new RaycastContext(eyePos, endVec, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
        HitResult result2 = entity.getWorld().raycast(new RaycastContext(eyePos, platerPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
        if (result.getType() == HitResult.Type.MISS || result2.getType() == HitResult.Type.MISS) {
            return true;
        }
        return false;
    }

    
}