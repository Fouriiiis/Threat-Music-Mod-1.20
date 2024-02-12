// Import necessary classes
package com.example;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;

import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;

import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.TraderLlamaEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;


public class ThreatTracker implements EndTick {

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

    //list of entities to track
    //bee, caveSpider, dolphin, enderman, goat, ironGolem, Llama, Panda, Piglin, polarBear, Spider, striderJokey, traderLlama, wolf, zombifiedPiglin

    public static List<Class<? extends Entity>> passiveEntities = List.of(
        BeeEntity.class,
        CaveSpiderEntity.class,
        DolphinEntity.class,
        EndermanEntity.class,
        GoatEntity.class,
        IronGolemEntity.class,
        LlamaEntity.class,
        PandaEntity.class,
        PiglinEntity.class,
        PolarBearEntity.class,
        SpiderEntity.class,
        TraderLlamaEntity.class,
        WolfEntity.class,
        ZombifiedPiglinEntity.class
    );

    //
//Blaze	Chicken Jockey	Creeper	Drowned	Elder Guardian	Endermite	Evoker	Ghast	Guardian	Hoglin	Hoglin Jockey

//Husk	Magma Cube	Phantom	Piglin Brute	Pillager	Ravager	Ravager Jockey	Shulker	Silverfish	Skeleton	Skeleton Horseman

//Slime	Spider Jockey	Stray	Vex	Vindicator	Warden	Witch	Wither Skeleton	Zoglin	Zombie	Zombie Villager

    List<Class<? extends Entity>> hostileEntities = List.of(
        BlazeEntity.class,
        CreeperEntity.class,
        DrownedEntity.class,
        ElderGuardianEntity.class,
        EnderDragonEntity.class,
        EndermiteEntity.class,
        EvokerEntity.class,
        GhastEntity.class,
        GuardianEntity.class,
        HoglinEntity.class,
        HuskEntity.class,
        MagmaCubeEntity.class,
        PhantomEntity.class,
        PiglinBruteEntity.class,
        PillagerEntity.class,
        RavagerEntity.class,
        ShulkerEntity.class,
        SilverfishEntity.class,
        SkeletonEntity.class,
        SlimeEntity.class,
        StrayEntity.class,
        VexEntity.class,
        VindicatorEntity.class,
        WardenEntity.class,
        WitchEntity.class,
        WitherSkeletonEntity.class,
        ZoglinEntity.class,
        ZombieEntity.class,
        ZombieVillagerEntity.class
        );

        //warden, blaze, ghast, enderDragon, wither

        List<Class<? extends Entity>> longRangeEntities = List.of(
        WardenEntity.class,
        BlazeEntity.class,
        GhastEntity.class,
        EnderDragonEntity.class,
        WitherEntity.class
    );
        
    public static Map<Entity, Float> trackedEntities = new HashMap<Entity, Float>();

    List<Float> threatLevels = new ArrayList<Float>();

    @Override
    public void onEndTick(MinecraftClient client) {
        if (client != null && client.player != null && !client.isPaused()) {

            targetThreat = 0;

            threatLevels.clear();

            ClientPlayerEntity player = client.player;

            
        
            List<Entity> nearEntities = client.world.getEntitiesByClass(Entity.class, client.player.getBoundingBox().expand(blockRadius1, blockRadius1, blockRadius1), EntityPredicates.EXCEPT_SPECTATOR);

            List<Entity> farEntities = client.world.getEntitiesByClass(Entity.class, client.player.getBoundingBox().expand(blockRadius2, blockRadius2, blockRadius2), EntityPredicates.EXCEPT_SPECTATOR);

            //filter far entities to only contain longRangeEntities

            farEntities = farEntities.stream().filter(entity -> longRangeEntities.contains(entity.getClass())).collect(Collectors.toList());

            //filter near entities to only contain hostileEntities and passiveEntities

            nearEntities = nearEntities.stream().filter(entity -> hostileEntities.contains(entity.getClass()) || passiveEntities.contains(entity.getClass())).collect(Collectors.toList());

            trackedEntities.entrySet().removeIf(entry -> {
                entry.setValue(entry.getValue() + 1.0f);
                return entry.getValue() >= 600.0f;
            });

            for (Entity entity : nearEntities) {
                //if lineOfSight(entity, player) add to trackedEntities

                HitResult result = raycast(entity, player);

                if(lineOfSight(result)) {
                    trackedEntities.put(entity, 0.0f);
                }

                threatLevels.add(ThreatDetermination.threatOfEntity(entity, trackedEntities.get(entity), result, player));

            }

            for (Entity entity : farEntities) {
                //if lineOfSight(entity, player) add to trackedEntities

                HitResult result = raycast(entity, player);

                if(lineOfSight(result)) {
                    trackedEntities.put(entity, 0.0f);
                }

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

            //System.out.println("Current Threat: " + currentThreat);
            //System.out.println("Target Threat: " + targetThreat);
            //System.out.println("Threat Decline Counter: " + threatDeclineCounter);            

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
        //print if region is null
        System.out.println("region is :" + region == null);
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
}