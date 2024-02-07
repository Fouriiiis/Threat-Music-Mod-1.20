package com.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;


public class MusicPlayer extends MovingSoundInstance {

    // A flag to indicate whether the sound has finished playing.
    private boolean finished = false;

    public MusicPlayer(SoundEvent sound, MinecraftClient client) {
        super(sound, SoundCategory.PLAYERS, SoundInstance.createRandom());
        this.repeat = false;
        this.repeatDelay = 0;
        this.relative = true;
        this.volume = 0.1f;
    }

    @Override
    public void tick() {
        if (this.finished) {
            // Perform your action here, e.g., print a message to the console.
            System.out.println("Sound has finished playing!");

            // Mark this sound instance as done to stop further ticking and to remove it.
            this.setDone();
            ThreatTracker.MusicPlaying = false;
            System.out.println("MusicPlaying set to false");
        }
    }

    // Call this method when you know the sound should be finished.
    public void markFinished() {
        this.finished = true;
    }
}
