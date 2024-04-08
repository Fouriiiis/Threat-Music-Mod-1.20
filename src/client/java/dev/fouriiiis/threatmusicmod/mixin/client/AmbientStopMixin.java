package dev.fouriiiis.threatmusicmod.mixin.client;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.sound.MusicTracker;

import org.spongepowered.asm.mixin.injection.At;
@Debug(export = true)
@Mixin(MusicTracker.class)
public abstract class AmbientStopMixin {

    @Inject(method="play", at=@At(value="TAIL"))
    public void play(CallbackInfo info) {
        System.out.println("Playing ambient music");
    }
}
