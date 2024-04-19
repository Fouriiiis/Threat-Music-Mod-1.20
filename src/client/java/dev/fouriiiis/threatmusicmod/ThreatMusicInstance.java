package dev.fouriiiis.threatmusicmod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.client.sound.AudioStream;
import net.minecraft.client.sound.OggAudioStream;
import net.minecraft.client.sound.RepeatingAudioStream;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundLoader;
import net.minecraft.client.sound.TickableSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

class ThreatMusicInstance extends AbstractSoundInstance
    implements TickableSoundInstance 
    {
    private InputStream inputStream;

    private float MinThreatLevel;
    private float MaxThreatLevel;

    private boolean done;

    ThreatMusicInstance(File filePath, float MinThreatLevel, float MaxThreatLevel) {
        super(new Identifier("threatmusicmod", "custom_sound"), SoundCategory.BLOCKS, SoundInstance.createRandom());
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.repeat = true;
        this.repeatDelay = 0;
        this.relative = true;
        this.MinThreatLevel = MinThreatLevel;
        this.MaxThreatLevel = MaxThreatLevel;
        // Initialize volume to a minimum value or based on current threat level
        this.volume = 0.0001f;
    }

    @Override
    public CompletableFuture<AudioStream> getAudioStream(SoundLoader loader, Identifier id, boolean repeatInstantly) {
        try {
            return CompletableFuture.completedFuture(new RepeatingAudioStream(OggAudioStream::new, inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void tick() {
        //float threatLevel = ThreatTracker.currentThreat;
        //this.volume = calculateVolume(threatLevel);
        // Print the volume for debugging
        //System.out.println("Volume: " + this.volume);
    }

    private float calculateVolume(float threatLevel) {
        //System.out.println("Threat level used: " + threatLevel);
        if (threatLevel <= MinThreatLevel) {
            //System.out.println("Volume: 0.01");
            return 0.0001f; // Minimum volume
        } else if (threatLevel >= MaxThreatLevel) {
            //System.out.println("Volume: 1.0");
            return 1.0f; // Maximum volume
        } else if (MaxThreatLevel != MinThreatLevel) {
            //System.out.println("Volume: " + (threatLevel - MinThreatLevel) / (MaxThreatLevel - MinThreatLevel));
            return (threatLevel - MinThreatLevel) / (MaxThreatLevel - MinThreatLevel);
        } else {
            // In case MinThreatLevel == MaxThreatLevel, handle gracefully
            //System.out.println("Volume: 0.5");
            return 0.5f; // This is arbitrary, choose a sensible default
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

    public void updateVolume(float threatLevel) {
        this.volume = calculateVolume(threatLevel);
        //System.out.println("Volume: " + this.volume);
    }

    public void setVolume(float f) {
        this.volume = f;
    }
}
