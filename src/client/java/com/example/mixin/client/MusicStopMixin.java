package com.example.mixin.client;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.example.ExampleModClient;
import com.example.ThreatTracker;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

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
    @Shadow @Nullable private SoundInstance current;
    @Shadow private int timeUntilNextSong;
    @Shadow private Random random;

    @Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        // Proceed with vanilla behavior if client or player is null
        if (client == null || client.player == null) {
            //System.out.println("Vanilla behavior");
            return;
        }

        // Prevent the method from proceeding to vanilla behavior
        ci.cancel();

        MusicSound musicSound = client.getMusicType();

        if(this.current != null) {
            if (!client.getSoundManager().isPlaying(this.current)) {
                this.current = null;
                this.timeUntilNextSong = Math.min(this.timeUntilNextSong, MathHelper.nextInt((Random)this.random, (int)musicSound.getMinDelay(), (int)musicSound.getMaxDelay()));
            }
        }
        if (musicSound.getMaxDelay() != 0) {
            this.timeUntilNextSong = Math.min(this.timeUntilNextSong, musicSound.getMaxDelay());
        }
        if (this.current == null && this.timeUntilNextSong-- <= 0) {
            this.playMusic(client);
        }
        System.out.println("Time until next song: " + this.timeUntilNextSong);
    }

    public void playMusic(MinecraftClient client) {
        this.current = getCustomSoundInstanceToPlay(client);
        if (this.current != null) {
            client.getSoundManager().play(this.current);
        }
        resetTimeUntilNextSongWithCustomValue();
    }

    private SoundInstance getCustomSoundInstanceToPlay(MinecraftClient client) {
        // Your logic to return a custom SoundInstance based on your conditions
        return ExampleModClient.getThreatTracker().getMusic(client);
    }

    private void resetTimeUntilNextSongWithCustomValue() {
        // Your logic to set 'timeUntilNextSong' to a custom value, ensuring custom sounds don't replace each other
        this.timeUntilNextSong = Integer.MAX_VALUE; // Example: Set to MAX_VALUE to effectively disable automatic playback
    }
}

