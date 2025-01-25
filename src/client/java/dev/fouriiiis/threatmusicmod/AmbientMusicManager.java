package dev.fouriiiis.threatmusicmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import java.util.ArrayList;
import java.util.List;

public class AmbientMusicManager {

    private static List<String> availableAmbientTracks = new ArrayList<>();

    /**
     * Returns a list of all available ambient tracks.
     *
     * @return A list of ambient track names.
     */
    public static List<String> getAvailableAmbient() {
        availableAmbientTracks = 
        return new ArrayList<>(availableAmbientTracks);
    }

    /**
     * Plays the specified ambient sound at the given volume.
     *
     * @param soundKey    The key of the sound to play.
     * @param targetVolume The volume at which to play the sound.
     */
    public static void playAmbientSound(String soundKey, float targetVolume) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && ModSounds.soundEvents.containsKey(soundKey)) {
            SoundInstance soundInstance = new SoundInstance(
                    ModSounds.soundEvents.get(soundKey).getId(),
                    SoundInstance.Type.MUSIC,
                    targetVolume,
                    1.0f,
                    false,
                    0,
                    SoundInstance.AttenuationType.NONE,
                    0,
                    0,
                    0,
                    true
            );
            client.getSoundManager().play(soundInstance);
        } else {
            System.out.println("Sound key not found: " + soundKey);
        }
    }

    /**
     * Stops all currently playing ambient sounds.
     */
    public static void stopAllAmbient() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            client.getSoundManager().stopSounds(null, SoundInstance.Type.MUSIC);
        }
    }

    /**
     * Adds a new ambient track to the list of available tracks.
     *
     * @param trackName The name of the track to add.
     */
    public static void registerAmbientTrack(String trackName) {
        if (!availableAmbientTracks.contains(trackName)) {
            availableAmbientTracks.add(trackName);
        }
    }
}
