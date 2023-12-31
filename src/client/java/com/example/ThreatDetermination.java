package com.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ThreatDetermination {
    private Float threatOfEntity(Entity entity, MinecraftClient client) {
        return null;
        //Threat = Base * Dead * Aggro * Find * Dist * Speed

    }

    private Float baseThreat(Entity entity) {
        //Base threat is a value between 0 and 1 that is determined by the health of the entity
        //health of 100 should be around 0.9
        //health of 20 should be around 0.45
        //health of 0 should be around 0.0




        //float baseThreat = -0.00016875f * health * health + 0.025875f * health;

        Float baseThreat = 1f;

        // use clamp to make sure the value is between 0 and 1
        baseThreat = MathHelper.clamp(baseThreat, 0.0f, 1.0f);

        return baseThreat;
        
    }
}
