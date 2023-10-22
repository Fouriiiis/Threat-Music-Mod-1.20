package com.example.mixin.client;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.example.ThreatTracker;

@Mixin(ClientPlayNetworkHandler.class)
public class CheckDamageMixin {

    @Shadow private ClientWorld world;

    @Inject(method = "onEntityDamage", at = @At("HEAD"))
    public void test(EntityDamageS2CPacket packet, CallbackInfo ci) {
        Entity entity = world.getEntityById(packet.entityId());
        if (entity != null) {
            if (entity.isPlayer()) {
                Entity sourceCauseEntity = world.getEntityById(packet.sourceCauseId());
                if (sourceCauseEntity != null) {
                    if (sourceCauseEntity.isPlayer()) {
                        System.out.println("Cause: " + sourceCauseEntity);
                        // If the player is damaged by a player in any way
                        ThreatTracker.setHostile(sourceCauseEntity, 0);
                    }
                }
            }
        }
    }
}
