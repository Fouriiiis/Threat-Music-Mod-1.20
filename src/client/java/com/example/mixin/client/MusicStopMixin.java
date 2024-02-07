package com.example.mixin.client;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.example.ExampleModClient;
import com.example.ThreatTracker;

import net.minecraft.client.sound.MusicTracker;
import net.minecraft.sound.MusicSound;

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

    @Shadow
    private int timeUntilNextSong;

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/MusicTracker;play(Lnet/minecraft/sound/MusicSound;)V"))
    private void onPlayRedirect(MusicTracker musicTracker, MusicSound type) {
        // Prevent the original play method from being called
        // And call a custom method instead
        customAction();

        // Reset the timer with a custom value
        resetTimeUntilNextSongWithCustomValue();
    }

    private void customAction() {
        // Your custom action here. This will be called instead of playing music.
        ExampleModClient.playMusic();
    }

    private void resetTimeUntilNextSongWithCustomValue() {
        // Set the timeUntilNextSong to a custom value, e.g., 200 ticks
        this.timeUntilNextSong = getCustomTimeValue();
    }

    // Method to determine the custom time value, potentially allowing for dynamic adjustments
    private int getCustomTimeValue() {
        // Return a custom time value, e.g., 200
        // This could be made dynamic or configurable as needed
        return Integer.MAX_VALUE;
    }
}

