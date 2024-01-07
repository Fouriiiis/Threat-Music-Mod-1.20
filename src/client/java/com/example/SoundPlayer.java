package com.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;


public class SoundPlayer extends MovingSoundInstance {
    private int requiredThreatLevel;
    public SoundPlayer(SoundEvent sound, MinecraftClient client, int requiredThreatLevel) {
        super(sound, SoundCategory.PLAYERS, SoundInstance.createRandom());
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.01f;
        this.relative = true; 
        this.requiredThreatLevel = requiredThreatLevel;
    }

    @Override
    public void tick() {

        if(ThreatTracker.currentThreat > requiredThreatLevel && this.volume < 1.0f) {
            this.volume += 0.01f;
        } else if(this.volume > 0.0f) {
            this.volume -= 0.005f;
        }
    }
}
