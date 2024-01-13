package com.example;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.mob.MobEntity;

//include ThreatTracker.java


public class ThreatDetermination {

    

    static Float threatOfEntity(Entity entity, Float lastSeen, HitResult result, ClientPlayerEntity player) {
        //Threat = Base * Dead * Aggro * Find * Dist * Speed

        float D = (float) entity.getPos().distanceTo(player.getPos()) * 20;
        
        //System.out.println("D: " + D);

        float Base = baseThreat((LivingEntity) entity);
        float Dead = deadThreat((LivingEntity) entity);
        float Aggro = aggroThreat((LivingEntity) entity);
        float Find = findThreat((LivingEntity) entity, lastSeen);
        float Dist = distThreat(result, D);
        float Speed = speedThreat(entity, D);
        float Threat = Base * Dead * Aggro * Find * Dist * Speed;

        // System.out.println("Base: " + Base);
        // System.out.println("Dead: " + Dead);
        // System.out.println("Aggro: " + Aggro);
        // System.out.println("Find: " + Find);
        // System.out.println("Dist: " + Dist);
        // System.out.println("Speed: " + Speed);
        // System.out.println("Threat: " + Threat);

        return Threat;
    }

    private static Float baseThreat(LivingEntity entity) {

        Float totalHP = entity.getMaxHealth() + entity.getArmor();
        float base = (float) (Math.log10((1f/6f)*(totalHP + 6f))/1.4f);


        return MathHelper.clamp(base, 0, 1);
    }

    private static Float deadThreat(LivingEntity entity) {
        if(entity.isDead()) {
            return 0.3f;
        } else {
            return 1.0f;
        }
    }

    private static Float aggroThreat(LivingEntity entity) {
        // Check if the entity is a passive entity
        if (ThreatTracker.passiveEntities.contains(entity.getClass())) {
            // Cast to MobEntity and return 1 if it's attacking, 0 otherwise
            return ((MobEntity) entity).isAttacking() ? 1.0f : 0.0f;
        }
        // Additional logic for non-passive entities (if needed) goes here
    
        // Return a default value (e.g., 0.0f) if the entity is not passive
        // or if other conditions are not met
        return 1.0f;
    }
    

    private static Float findThreat(LivingEntity entity, Float lastSeen) {
        //(1+2*find(entity)^0.75)/3


        return (float) ((1f + 2f * Math.pow(find(lastSeen), 0.75f)) / 3f);
    }

    private static float find(Float lastSeen) {

        //if last seen is null, return 0
        if(lastSeen == null) {
            return 0;
        }



        float t1 = t1(lastSeen * 2);

        //System.out.println("t1: " + t1);

        //if t1 < 45

        if(t1 < 45) {
            // = Clamp(1, 0, [1.007 - (1 + exp[5 - (t1 / 12)])^-1]
            return MathHelper.clamp((float) (1.007 - Math.pow((1 + Math.exp(5 - (t1 / 12))), -1)), 0, 1);
        //if t1 >= 45
        // = 30 / (t1 - 7)
        } else {
            return 30 / (t1 - 7);
        }
    }

    private static Float t1(Float ticks) {
        return 10 * (ticks / 100) + (ticks / 4);
    }

    private static Float distThreat(HitResult result, Float D) {
        // float S = 1 if there is a line of sight between the player and the entity, 0 otherwise
        float S = 0;
        if(result.getType() == HitResult.Type.MISS) {
            S = 1;
        }

        // float D = the distance between the player and the entity

        //= 1 - (1 - 0.2 * S) * Clamp(0, 1, (d - 300) / 2100)

        return (1f - (1f - (0.2f * S)) * MathHelper.clamp((D - 300f) / 2100f, 0, 1));

    }

    private static Float speedThreat(Entity entity, Float D) {
        //float speed = the speed of the entity
        float velocity = (float) entity.getVelocity().length() * 20;
        //System.out.println("Velocity: " + velocity);

        // = (1 + Clamp(0, 1, ((300 - D)/280) * Clamp(0, 1, ((velocity - 2)/5)))

        float speedThreat = 1f + MathHelper.clamp(((300f - D)/280f), 0, 1) * MathHelper.clamp((velocity - 2f)/5f, 0, 1);

        return speedThreat;
    }
}
