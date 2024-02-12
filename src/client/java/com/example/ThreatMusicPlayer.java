package com.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;


public class ThreatMusicPlayer extends MovingSoundInstance {
    private float MinThreatLevel;
    private float MaxThreatLevel;
    public ThreatMusicPlayer(SoundEvent sound, MinecraftClient client, float MinThreatLevel, float MaxThreatLevel) {
        super(sound, SoundCategory.PLAYERS, SoundInstance.createRandom());
        this.repeat = true;
        this.repeatDelay = 0;
        this.relative = true;
        this.volume = 1.0f;
        this.MinThreatLevel = MinThreatLevel;
        this.MaxThreatLevel = MaxThreatLevel;
    }


    @Override
    public void tick() {
        //set the volume to the % current threat level is between the min and max threat level
        //volume should be 1 when the threat level is past the max threat level

        float threatLevel = ThreatTracker.currentThreat;

        if(threatLevel < MinThreatLevel) {
            this.volume = 0.01f;
        } else if(threatLevel > MaxThreatLevel) {
            this.volume = 1f;
        } else {
            this.volume = (threatLevel - MinThreatLevel) / (MaxThreatLevel - MinThreatLevel);
        }

        //print the volume
        //System.out.println("Volume: " + this.volume);
    }

    public void stop() {
        this.setDone();
        this.volume = 0f;
    }


    public Object getSoundEvent() {
        return this.sound;
    }
}
