// Import necessary classes
package com.example;
import java.util.List;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;


public class ThreatTracker implements EndTick {

    private static int blockRadius = 20;
    public static int threatLevel = 0;
    private int lastPlayed = 0;
    private int maxTime = 200;
    private Boolean stopped = true;

    @Override
    public void onEndTick(MinecraftClient client) {

        if (client != null && client.player != null) {
            // Check if there are any mobs categorized as hostile within the blockRadius targeting the player
            List<HostileEntity> Hentities = client.world.getEntitiesByClass(HostileEntity.class, client.player.getBoundingBox().expand(blockRadius, blockRadius, blockRadius), EntityPredicates.EXCEPT_SPECTATOR);

            threatLevel = 0;

            // For each hostile entity, check if it is targeting the player by checking if the player is in its line of sight using raytracing
            for (HostileEntity entity : Hentities) {
                if (isEntityLookingAtPlayer(entity, client.player)) {
                    // Handle the case where the entity is looking at the player
                    threatLevel += 1;
                }
            }

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
                } else {
                    lastPlayed++;
                }
            }
        }
    }

    public static boolean isEntityLookingAtPlayer(HostileEntity entity, PlayerEntity player) {

        
        Vec3d min = entity.getEyePos();
        //get eye angle of entity
        Vec3d eyeAngle = entity.getRotationVec(1.0F);
        //get the position of the max distance using the eye angle and block radius
        Vec3d max = min.add(eyeAngle.x * blockRadius, eyeAngle.y * blockRadius, eyeAngle.z * blockRadius);

        //get the bounding box of the player
        Box box = player.getBoundingBox();
        
        EntityHitResult result = ProjectileUtil.raycast(entity, min, max, box, (entityx) -> {
            return !entityx.isSpectator() && entityx.canHit();
          }, blockRadius * blockRadius);
          
          if (result != null) {
            Entity hitEntity = result.getEntity();

            if (hitEntity == player) {
                return true;
            }
          }
        return false;
    }
}