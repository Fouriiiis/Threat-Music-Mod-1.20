package com.example;

import java.util.List;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class ThreatTracker implements EndTick {

    private int blockRadius = 10;
    public static int threatLevel = 0;
    private int lastPlayed = 0;
    private int maxTime = 200;
    private Boolean stopped = true;

    
    @Override
    public void onEndTick(MinecraftClient client) {
        
        if (client != null && client.player != null) {
            // Check if there are any mobs categorized as hostile within the blockRadius targeting the player
            List<HostileEntity> Hentities = client.world.getEntitiesByClass(net.minecraft.entity.mob.HostileEntity.class, client.player.getBoundingBox().expand(blockRadius, blockRadius, blockRadius), EntityPredicates.EXCEPT_SPECTATOR);

            threatLevel = Hentities.size();

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
    
}