package dev.fouriiiis.threatmusicmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;


public class MusicPlayer extends MovingSoundInstance {

    public MusicPlayer(SoundEvent sound, MinecraftClient client) {
        super(sound, SoundCategory.MUSIC, SoundInstance.createRandom());
        this.repeat = false;
        this.repeatDelay = 0;
        this.relative = true;
        this.volume = 0.01f;
    }

    @Override
    public void tick() {
        //set threat level to 0

        if(!this.isDone()) {
            ThreatTracker.currentThreat = 0;
            //increment the volume of the music if less than 1
            if (this.volume < 1.0f) {
                this.volume += 0.01f;
            }
            System.out.println("Threat level set to 0");
        }
        

        //check if the music category is turned off
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.getSoundVolume(SoundCategory.MUSIC) == 0.0f) {
            this.setDone();
            System.out.println("Music stopped");
        }
    }
}
