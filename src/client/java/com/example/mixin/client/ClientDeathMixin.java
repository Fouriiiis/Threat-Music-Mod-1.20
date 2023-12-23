package com.example.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.example.ExampleModClient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientDeathMixin {

    @Inject(method="requestRespawn", at=@At(value="TAIL"))
    public void requestRespawn(CallbackInfo info) {
        //call stop on the threat tracker
        MinecraftClient client = MinecraftClient.getInstance();
        client.getMusicTracker().stop();
    }
}