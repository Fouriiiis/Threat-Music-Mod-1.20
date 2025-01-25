package dev.fouriiiis.threatmusicmod.mixin.client;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.fouriiiis.threatmusicmod.ModSounds.AmbientMusicMode;
import dev.fouriiiis.threatmusicmod.ThreatMusicModClient;
import dev.fouriiiis.threatmusicmod.ThreatTracker;

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

    

    private static AmbientMusicMode ambientMusicMode = AmbientMusicMode.ON;

    @Inject(method="Lnet/minecraft/client/sound/MusicTracker;stop()V", at=@At(value="TAIL"))
    public void stop(CallbackInfo info) {
        System.out.println("Stopping music");
        ThreatMusicModClient.stop();
        ThreatTracker.clearTrackedThreats();
        ThreatTracker.currentThreat = 0;

        if (timeUntilNextSong <= 0) {
            this.setTimeUntilNextSong(50);
        }
    }

    @Shadow @Nullable private SoundInstance current;
    @Shadow private int timeUntilNextSong;
    @Shadow private Random random;

    @Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        // Handle OFF mode
        if (ambientMusicMode == AmbientMusicMode.OFF) {
            ci.cancel();
            return;
        }

        // Handle VANILLA mode
        if (ambientMusicMode == AmbientMusicMode.VANILLA) {
            return; // Allow vanilla behavior
        }

        // Proceed with custom behavior for ON mode
        if ((client == null || client.player == null) && !ThreatTracker.demo) {
            return;
        }

        ci.cancel();

        MusicSound musicSound = client.getMusicType();
        int minDelay = (int) musicSound.getMinDelay();
        int maxDelay = (int) musicSound.getMaxDelay();

        if (minDelay <= 0) minDelay = 6000;
        if (maxDelay <= 0) maxDelay = 24000;

        if (this.current != null) {
            if (!client.getSoundManager().isPlaying(this.current)) {
                this.current = null;
                this.timeUntilNextSong = Math.min(this.timeUntilNextSong, MathHelper.nextInt(this.random, minDelay, maxDelay));
            }
        }

        this.timeUntilNextSong = Math.min(this.timeUntilNextSong, maxDelay);

        Boolean threatMusicPlaying = ThreatMusicModClient.getThreatTracker().playing();
        if (this.current == null && this.timeUntilNextSong-- <= 0 && !threatMusicPlaying) {
            playMusic(client);
        } else if (threatMusicPlaying && this.timeUntilNextSong < 400) {
            this.timeUntilNextSong = 400;
        }
    }

    public void playMusic(MinecraftClient client) {
        this.current = getCustomSoundInstanceToPlay(client);
        if (this.current != null) {
            client.getSoundManager().play(this.current);
        }
        this.timeUntilNextSong = Integer.MAX_VALUE;
    }

    private SoundInstance getCustomSoundInstanceToPlay(MinecraftClient client) {
        return ThreatMusicModClient.getThreatTracker().getMusic(client);
    }

    public void setTimeUntilNextSong(int time) {
        this.timeUntilNextSong = time;
    }
}
