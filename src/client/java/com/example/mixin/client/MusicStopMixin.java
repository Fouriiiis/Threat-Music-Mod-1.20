package com.example.mixin.client;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.example.ExampleModClient;
import com.example.ThreatTracker;

import net.minecraft.client.sound.MusicTracker;

import org.spongepowered.asm.mixin.injection.At;
@Debug(export = true)
@Mixin(MusicTracker.class)
public abstract class MusicStopMixin {

    @Inject(method="Lnet/minecraft/client/sound/MusicTracker;stop()V", at=@At(value="TAIL"))
    public void stop(CallbackInfo info) {
        ExampleModClient.stop();
        //set threat level to 0
        ThreatTracker.currentThreat = 0;
    }
}
