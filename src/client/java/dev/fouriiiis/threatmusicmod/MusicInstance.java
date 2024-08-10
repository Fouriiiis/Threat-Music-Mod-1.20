package dev.fouriiiis.threatmusicmod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.client.sound.AudioStream;
import net.minecraft.client.sound.OggAudioStream;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundLoader;
import net.minecraft.client.sound.TickableSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

public class MusicInstance extends AbstractSoundInstance
    implements TickableSoundInstance {

        private boolean done;
        private InputStream inputStream;

    public MusicInstance(File filePath) {
        super(new Identifier("threatmusicmod", "custom_sound"), SoundCategory.MUSIC, SoundInstance.createRandom());
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.repeat = false;
        this.repeatDelay = 0;
        this.relative = true;
        this.volume = 0.005f;
    }

    @Override
    public CompletableFuture<AudioStream> getAudioStream(SoundLoader loader, Identifier id, boolean repeatInstantly) {
        try {
            return CompletableFuture.completedFuture(new OggAudioStream(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void tick() {
        //set threat level to 0

        if(!this.isDone()) {
            ThreatTracker.currentThreat = 0;
            this.volume = Math.min(this.volume + 0.005f, 1.0f);
        }
        
        //check if the music category is turned off
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.getSoundVolume(SoundCategory.MUSIC) == 0.0f) {
            this.done = true;
            System.out.println("Music stopped");
        }
    }

    protected final void setDone() {
        this.done = true;
        this.repeat = false;
    }

    @Override
    public boolean isDone() {
        return done;
    }
}
