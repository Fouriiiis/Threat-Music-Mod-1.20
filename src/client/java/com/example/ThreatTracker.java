package com.example;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.StartTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;


public class ThreatTracker implements StartTick {

    private static int blockRadius1 = 16;
    private static int blockRadius2 = 48;
    public static float currentThreat = 0;
    public static float targetThreat = 0;
    private static float threatDeclineCounter = 0;

    private boolean demo = false;

    private int lastPlayed = 0;
    private int maxTime = 75;
    private Boolean stopped = true;
    private Region region;
  
    public static Map<Entity, Float> trackedEntities = new HashMap<Entity, Float>();

    List<Float> threatLevels = new ArrayList<Float>();

    @Override
    public void onStartTick(MinecraftClient client) {
        if (client != null && client.player != null && !client.isPaused() && !demo) {

            threatLevels.clear();
            targetThreat = 0;

            ClientPlayerEntity player = client.player;

            
        
            List<LivingEntity> nearEntities = client.world.getEntitiesByClass(LivingEntity.class, client.player.getBoundingBox().expand(blockRadius1, blockRadius1, blockRadius1), EntityPredicates.EXCEPT_SPECTATOR);
            //print the size of nearEntities
            System.out.println("Near Entities: " + nearEntities.size());
            List<LivingEntity> farEntities = client.world.getEntitiesByClass(LivingEntity.class, client.player.getBoundingBox().expand(blockRadius2, blockRadius2, blockRadius2), EntityPredicates.EXCEPT_SPECTATOR);
            //print the size of farEntities
            System.out.println("Far Entities: " + farEntities.size());

            //filter far entities to only contain entities with getBaseThreat >= 0.9f

            farEntities = farEntities.stream().filter(entity -> entity instanceof CustomMobEntity && ((CustomMobEntity) entity).getBaseThreat() >= 0.9f && entity != player).collect(Collectors.toList());

            //filter near entities to only contain entities with getBaseThreat >= 0.9f
            nearEntities = nearEntities.stream().filter(entity -> entity instanceof CustomMobEntity && ((CustomMobEntity) entity).getBaseThreat() < 0.9f && entity != player).collect(Collectors.toList());

            Iterator<Map.Entry<Entity, Float>> iterator = trackedEntities.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Entity, Float> entry = iterator.next();
                // Increment the last seen timer for each entity
                entry.setValue(entry.getValue() + 1.0f);
                // Remove entity if it has been out of sight for too long or has been removed from the world
                if (entry.getValue() >= 600.0f || entry.getKey().isRemoved()) {
                    iterator.remove();
                }// else {
                    // If entity is in line of sight, reset its timer
                    //if (nearEntities.contains(entry.getKey()) || farEntities.contains(entry.getKey())) {
                        //entry.setValue(0.0f);
                    //}
                //}
            }

            for (Entity entity : nearEntities) {
                //if lineOfSight(entity, player) add to trackedEntities

                if (!trackedEntities.containsKey(entity) && !(entity instanceof PlayerEntity)) {
                    trackedEntities.put(entity, 600.0f);
                }
            }

            for (Entity entity : farEntities) {
                //if lineOfSight(entity, player) add to trackedEntities

                if (!trackedEntities.containsKey(entity) && !(entity instanceof PlayerEntity)) {
                    trackedEntities.put(entity, 600.0f);
                }
            }

            //for all tracked entities, calculate the threat level and add it to threatLevels
            for (Entity entity : trackedEntities.keySet()) {
                //check if entity has not despawned
                if (entity.isRemoved()) {
                    trackedEntities.remove(entity);
                    continue;
                }
                HitResult result = raycast(entity, player);
                //if lineOfSight(entity, player) set lastSeen to 0
                if (lineOfSight(result) && nearEntities.contains(entity)) {
                    trackedEntities.put(entity, 0.0f);
                } else if (lineOfSight(result) && farEntities.contains(entity)) {
                    trackedEntities.put(entity, 0.0f);
                }
                //print the contents of trackedEntities with an entity on each line
                System.out.println(entity + "Last Seen: " + trackedEntities.get(entity));
                threatLevels.add(ThreatDetermination.threatOfEntity(entity, trackedEntities.get(entity), result, player));
            }

            for (Float threat : threatLevels) {
                targetThreat = 0.25f * (targetThreat + threat + 3 * Math.max(targetThreat, threat));
            }

            //targetThreat is raised to the power of 1.2-0.4*Clamp 0 1 [(Sv-5)/25]

            targetThreat = (float) Math.pow(targetThreat, 1.2 - 0.4 * MathHelper.clamp((KeyInputHandler.getSv() - 5) / 25, 0, 1));

            //adjustment towards 50%

            //targetThreat is set to targetThreat + (0.5-TargetThreat)*Clamp 0 1 [(0.25-abs[TargetThreat-0.5])/0.25]
            targetThreat = targetThreat + (0.5f - targetThreat) * MathHelper.clamp((0.25f - Math.abs(targetThreat - 0.5f)) / 0.25f, 0, 1);

            //reduce threatDeclineCounter by 1 if it is greater than 0

            if (threatDeclineCounter > 0) {
                threatDeclineCounter--;
            }

            //if targetThreat is at least 35% lower than currentThreat, set threatDeclineCounter to 10 unless it is already greater than 10

            if (targetThreat <= 0.65f * currentThreat && threatDeclineCounter < 10) {
                threatDeclineCounter = 10;
            }

            //if trackedEntities becomes empty, set threatDeclineCounter to 120

            if (trackedEntities.isEmpty()) {
                threatDeclineCounter = 120;
            }

            if (currentThreat < targetThreat) {
                currentThreat = (float) Math.min(1, currentThreat + 1f / lerp(280f, 80f, targetThreat));
            }
            else if (threatDeclineCounter > 0) {
                currentThreat = (float) Math.max(0, currentThreat - 1f / lerp(800f, 4200f, targetThreat));
            }
            else {
                currentThreat = (float) Math.max(0, currentThreat - 1f / lerp(1600f, 22000f, Math.pow(targetThreat, 0.25f)));
            }
            
            //clamp currentThreat between 0 and 1
            currentThreat = MathHelper.clamp(currentThreat, 0, 1);

            //if the player is in spectator mode, set currentThreat to 0

            if (client.player.isSpectator()) {
                 currentThreat = 0;
            }

            System.out.println("Current Threat: " + currentThreat);
            System.out.println("Target Threat: " + targetThreat);
            System.out.println("Threat Decline Counter: " + threatDeclineCounter);            

            if (currentThreat > 0.05 && stopped) {
                // Start playing music
                region = ModSounds.changeRegion(client);
                region.play(client);
                //client.player.sendMessage(Text.of("Playing music"), false);
                lastPlayed = 0;
                stopped = false;
            } else if (currentThreat <= 0.05 && !stopped) {
                // If threat drops to 0, wait for 75 ticks before stopping the music
                if (lastPlayed >= maxTime) {
                    stopRegion(client);
                } else {
                    lastPlayed++;
                }
            } else if (currentThreat > 0.05 && !stopped) {
                // If the threat level rises again, reset the counter
                lastPlayed = 0;
            }
        //System.out.println("lastPlayed: " + lastPlayed);
        } else if(demo) {
            targetThreat = 1.0f;
            currentThreat = (float) Math.min(1, currentThreat + 1f / lerp(280f, 80f, targetThreat));
            //clamp currentThreat between 0 and 1
            currentThreat = MathHelper.clamp(currentThreat, 0, 1);
            System.out.println("threat level: " + ThreatTracker.currentThreat);
        }
        
    }

    // Helper function to mimic Unity's Mathf.Lerp
    private double lerp(double a, double b, double t) {
        t = Math.max(0, Math.min(1, t)); // Clamp t between 0 and 1
        return a + t * (b - a);
    }

    public boolean lineOfSight(HitResult result) {
        if (result.getType() == HitResult.Type.MISS) {
            return true;
        }
        return false;
    }

    public HitResult raycast(Entity entity, PlayerEntity player) {
        Vec3d eyePos = entity.getEyePos();
        Vec3d platerPos = player.getPos();
        Vec3d endVec = player.getEyePos();

        HitResult result = entity.getWorld().raycast(new RaycastContext(eyePos, endVec, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
        HitResult result2 = entity.getWorld().raycast(new RaycastContext(eyePos, platerPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
        if (result2.getType() == HitResult.Type.MISS) {
            return result2;
        }
        return result;

    }
    
    public void playDemo(Region thisRegion, MinecraftClient client) {
        //System.out.println("region is :" + region == null);
        //stop the current region
        MusicTracker musicTracker = client.getMusicTracker();
        //stop the threat music
        musicTracker.stop();
        region = thisRegion;
        currentThreat = 0.0f;
        demo = true;
        region.playDemo(client);
        stopped = false;
    }

    public void stopRegion(MinecraftClient client) {
        if (!stopped && region != null) {
            region.stop(client);
            region.randomizeLayers();
            //client.player.sendMessage(Text.of("Stopping music"), false);
            stopped = true;
            demo = false;
            currentThreat = 0;
            lastPlayed = 0;
        }
    }

    public boolean playing() {
        return !stopped;
    }

    public SoundInstance getMusic(MinecraftClient client) {
        if (!playing()) {
            region = ModSounds.changeRegion(client);
            return region.getMusic(client);
        }
        return null;
    }

    public static void trackEntity(Entity sourceEntity) {
        trackedEntities.put(sourceEntity, 0.0f);
    }
}