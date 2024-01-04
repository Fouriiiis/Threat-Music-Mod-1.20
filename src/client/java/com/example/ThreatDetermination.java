package com.example;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;

//include ThreatTracker.java


public class ThreatDetermination {

    

    static Float threatOfEntity(Entity entity, Float lastSeen, HitResult result, ClientPlayerEntity player) {
        //Threat = Base * Dead * Aggro * Find * Dist * Speed

        float D = (float) result.getPos().distanceTo(player.getPos());

        return baseThreat((LivingEntity) entity) * 
        deadThreat((LivingEntity) entity) * 
        aggroThreat((LivingEntity) entity) * 
        findThreat((LivingEntity) entity, lastSeen) * 
        distThreat(entity, result, player, D) * 
        speedThreat(entity, D);


    }

    private static Float baseThreat(LivingEntity entity) {

        Float totalHP = entity.getHealth() + entity.getArmor();
    
        return (float) (Math.log((1/6)*(totalHP+6))/1.4);
    }

    private static Float deadThreat(LivingEntity entity) {
        if(entity.isDead()) {
            return 0.3f;
        } else {
            return 1.0f;
        }
    }

    private static Float aggroThreat(LivingEntity entity) {
        return 1.0f;
    }

    private static Float findThreat(LivingEntity entity, Float lastSeen) {
        //(1+2*find(entity)^0.75)/3

        //if last seen is null, return 0
        if(lastSeen == null) {
            return 0f;
        }
        return (float) ((1 + 2 * Math.pow(find(lastSeen), 0.75)) / 3);
    }

    private static float find(float lastSeen) {
        

        float t1 = t1(lastSeen);

        //if t1 < 45

        if(t1 < 45) {
            // = Clamp(1, 0, [1.007 - (1 + exp[5 - (t1 / 12)])^-1]
            return MathHelper.clamp(0, 1, (float) (1.007 - Math.pow((1 + Math.exp(5 - (t1 / 12))), -1)));
        //if t1 >= 45
        // = 30 / (t1 - 7)
        } else {
            return 30 / (t1 - 7);
        }
    }

    private static Float t1(Float ticks) {
        return 10 * ((ticks / 100) + 100) + (ticks / 4);
    }

    private static Float distThreat(Entity entity, HitResult result, ClientPlayerEntity player, Float D) {
        // float S = 1 if there is a line of sight between the player and the entity, 0 otherwise
        float S = 0;
        if(result.getType() == HitResult.Type.MISS) {
            S = 1;
        }

        // float D = the distance between the player and the entity

        //= 1 - (1 - 0.2 * S) * Clamp(0, 1, (d - 300) / 2100)
        return 1 - (1 - 0.2f * S) * MathHelper.clamp(0, 1, (D - 300) / 2100);
    }

    private static Float speedThreat(Entity entity, Float D) {
        //float speed = the speed of the entity
        float velocity = (float) entity.getVelocity().length();

        // = (1 + Clamp(0, 1, ((300 - D)/280) * Clamp(0, 1, ((velocity - 2)/5)))

        return (1 + MathHelper.clamp(0, 1, ((300 - D)/280) * MathHelper.clamp(0, 1, ((velocity - 2)/5))));
    }
}
