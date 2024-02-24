package com.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;


public class ThreatMusicPlayer extends MovingSoundInstance {
    private float MinThreatLevel;
    private float MaxThreatLevel;

    public ThreatMusicPlayer(SoundEvent sound, SoundCategory category, MinecraftClient client, float MinThreatLevel, float MaxThreatLevel) {
        super(sound, category, SoundInstance.createRandom());
        this.repeat = true;
        this.repeatDelay = 0;
        this.relative = true;
        this.MinThreatLevel = MinThreatLevel;
        this.MaxThreatLevel = MaxThreatLevel;
        // Initialize volume to a minimum value or based on current threat level
        this.volume = calculateVolume(ThreatTracker.currentThreat);
    }

    @Override
    public void tick() {
        float threatLevel = ThreatTracker.currentThreat;
        this.volume = calculateVolume(threatLevel);
        // Print the volume for debugging
        //System.out.println("Volume: " + this.volume);
    }

    private float calculateVolume(float threatLevel) {
        //System.out.println("Threat level used: " + threatLevel);
        if (threatLevel <= MinThreatLevel) {
            //System.out.println("Volume: 0.01");
            return 0.01f; // Minimum volume
        } else if (threatLevel >= MaxThreatLevel) {
            //System.out.println("Volume: 1.0");
            return 1.0f; // Maximum volume
        } else if (MaxThreatLevel != MinThreatLevel) {
            //System.out.println("Volume: " + (threatLevel - MinThreatLevel) / (MaxThreatLevel - MinThreatLevel));
            return (threatLevel - MinThreatLevel) / (MaxThreatLevel - MinThreatLevel);
        } else {
            // In case MinThreatLevel == MaxThreatLevel, handle gracefully
            System.out.println("Volume: 0.5");
            return 0.5f; // This is arbitrary, choose a sensible default
        }
    }

    public void stop() {
        this.setDone();
    }

    public Object getSoundEvent() {
        return this.sound;
    }
}

