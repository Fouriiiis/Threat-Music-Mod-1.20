package dev.fouriiiis.threatmusicmod.mixin.client;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method="Lnet/minecraft/client/sound/MusicTracker;stop()V", at=@At(value="TAIL"))
    public void stop(CallbackInfo info) {
        System.out.println("Stopping music");
        ThreatMusicModClient.stop();
        ThreatTracker.clearTrackedThreats();
        //set threat level to 0
        ThreatTracker.currentThreat = 0;
        //set time until next song to 10
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

        // Proceed with vanilla behavior if client or player is null
        if ((client == null || client.player == null) && ThreatTracker.demo == false) {
            //System.out.println("Vanilla behavior");
            return;
        }

        // Prevent the method from proceeding to vanilla behavior
        ci.cancel();

        MusicSound musicSound = client.getMusicType();

        if (this.current != null) {
            // Only check if the current sound has finished playing; do not replace if different
            if (!client.getSoundManager().isPlaying(this.current)) {
                this.current = null;
                this.timeUntilNextSong = Math.min(this.timeUntilNextSong, MathHelper.nextInt((Random)this.random, (int)musicSound.getMinDelay(), (int)musicSound.getMaxDelay()));
            }
        }

        this.timeUntilNextSong = Math.min(this.timeUntilNextSong, musicSound.getMaxDelay());

        // Only start a new sound if there is currently no sound playing
        Boolean playing = ThreatMusicModClient.getThreatTracker().playing();
        System.out.println("Playing: " + playing);
        if (this.current == null && this.timeUntilNextSong-- <= 0 && !playing) {
            playMusic(client);
        }
        System.out.println("Time until next song: " + this.timeUntilNextSong);
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

    //method to manually set the time until next song
    public void setTimeUntilNextSong(int time) {
        this.timeUntilNextSong = time;
    }

    

}

