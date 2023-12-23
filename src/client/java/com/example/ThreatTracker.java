// Import necessary classes
package com.example;

import java.util.List;
import java.util.stream.Collectors;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.entity.mob.MobEntity;
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

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;


public class ThreatTracker implements EndTick {

    private static int blockRadius1 = 16;
    private static int blockRadius2 = 48;
    public static int threatLevel = 0;
    private int lastPlayed = 0;
    private int maxTime = 250;
    private Boolean stopped = true;
    private Region region;

    //list of entities to track
    //bee, caveSpider, dolphin, enderman, goat, ironGolem, Llama, Panda, Piglin, polarBear, Spider, striderJokey, traderLlama, wolf, zombifiedPiglin

    List<Class<? extends Entity>> passiveEntities = List.of(
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
        


    @Override
    public void onEndTick(MinecraftClient client) {

  


        if (client != null && client.player != null) {

            threatLevel = 0;

            ClientPlayerEntity player = client.player;
        
            List<Entity> nearEntities = client.world.getEntitiesByClass(Entity.class, client.player.getBoundingBox().expand(blockRadius1, blockRadius1, blockRadius1), EntityPredicates.EXCEPT_SPECTATOR);

            List<Entity> farEntities = client.world.getEntitiesByClass(Entity.class, client.player.getBoundingBox().expand(blockRadius2, blockRadius2, blockRadius2), EntityPredicates.EXCEPT_SPECTATOR);

            //filter far entities to only contain longRangeEntities

            farEntities = farEntities.stream().filter(entity -> longRangeEntities.contains(entity.getClass())).collect(Collectors.toList());

            //filter near entities to only contain hostileEntities and passiveEntities

            nearEntities = nearEntities.stream().filter(entity -> hostileEntities.contains(entity.getClass()) || passiveEntities.contains(entity.getClass())).collect(Collectors.toList());

            //for all near entities, if they are hostile && line of sight, add to threatLevel

            for (Entity entity : nearEntities) {
                //if hostileEntities contains entity.getClass() add 1 to threatLevel
                //if lineOfSight(entity, player) add entity.getMaxHealth() to threatLevel
                if(hostileEntities.contains(entity.getClass())) {
                    threatLevel += (((LivingEntity) entity).getMaxHealth() + ((LivingEntity) entity).getArmor()) / 5;
                    if(lineOfSight(entity, player)) {
                        threatLevel += ((LivingEntity) entity).getMaxHealth() + ((LivingEntity) entity).getArmor();
                    }
                }
                else if (passiveEntities.contains(entity.getClass())) {
                    if(entity instanceof MobEntity && ((MobEntity)entity).isAttacking()) {
                        threatLevel += (((LivingEntity) entity).getMaxHealth() + ((LivingEntity) entity).getArmor()) / 5;
                        if(lineOfSight(entity, player)) {
                            threatLevel += ((LivingEntity) entity).getMaxHealth() + ((LivingEntity) entity).getArmor();
                        }
                    }
                }
            }

            //for all far entities, if they are longRangeEntities && line of sight, add health to threatLevel

            for (Entity entity : farEntities) {
                if(lineOfSight(entity, player) && !nearEntities.contains(entity)) {
                    threatLevel += ((LivingEntity) entity).getMaxHealth() + ((LivingEntity) entity).getArmor();
                }
            }

            //print threatLevel to chat

            //client.player.sendMessage(Text.of("Threat Level: " + threatLevel), false);

            

            if (threatLevel > 0 && stopped) {
                region = ModSounds.changeRegion(client);
                region.play(client);
                //client.player.sendMessage(Text.of("Playing music"), false);
                lastPlayed = 0;
                stopped = false;
            } else if (threatLevel == 0 && !stopped) {
                if (lastPlayed >= maxTime) {
                    stopRegion(client);
                } else {
                    lastPlayed++;
                }
            } else if (threatLevel > 0 && !stopped) {
                lastPlayed = 0;
            }
        }
        //System.out.println(lastPlayed);
    }

    
    
    public boolean lineOfSight(Entity entity, PlayerEntity player) {
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

    public void stopRegion(MinecraftClient client) {
        if (!stopped){
            region.stop(client);
            region.randomizeLayers();
            client.player.sendMessage(Text.of("Stopping music"), false);
            stopped = true;
            lastPlayed = 0;
        }
    }

    public boolean playing() {
        return !stopped;
    }
}