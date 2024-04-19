package dev.fouriiiis.threatmusicmod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;

import java.io.File;


public class Region {

    private Map<Float, File> currLayers;
    private List<ThreatMusicInstance> soundPlayers = new ArrayList<ThreatMusicInstance>();
    private List<ThreatMusicInstance> nightSoundPlayers = new ArrayList<ThreatMusicInstance>();
    private String name;
    private Map<Float, List<File>> layers;
    private Map<Float, List<File>> nightLayers;

    private List<File> music;

    //playing layers

    private List<ThreatMusicInstance> playingLayers = new ArrayList<ThreatMusicInstance>();

    MinecraftClient client = MinecraftClient.getInstance();
    float offset;
    boolean hasNightLayers = false;


    //constructor which takes a list of lists of sound events
    //each list of sound events is a layer

    public Region(String name, List<List<File>> dayLayers, Optional<List<List<File>>> optionalNightLayers, Optional<List<File>> optionalMusic) {
        this.name = name;
        this.layers = new HashMap<>();
        this.nightLayers = new HashMap<>();
        this.currLayers = new HashMap<>();
        this.soundPlayers = new ArrayList<>();
        this.nightSoundPlayers = new ArrayList<>();
        this.playingLayers = new ArrayList<>();
    
        // Set day layers
        offset = 0.95f / (dayLayers.size());
        float threatLevel = 0.05f;
        for (List<File> layer : dayLayers) {
            this.layers.put(threatLevel, layer);
            threatLevel += offset;
        }
    
        // Check and set night layers if present
        if (optionalNightLayers.isPresent()) {
            hasNightLayers = true;
            List<List<File>> nightLayers = optionalNightLayers.get();
            offset = 0.95f / (nightLayers.size());
            threatLevel = 0.05f;
            for (List<File> layer : nightLayers) {
                this.nightLayers.put(threatLevel, layer);
                threatLevel += offset;
            }
        } else {
            hasNightLayers = false;
        }
    
        // Set music if present
        this.music = optionalMusic.orElseGet(Collections::emptyList);
    
        setupSoundPlayers();
    }

    public void randomizeLayers() {
        // For each list of layers, shuffle the list
        for (List<File> layer : layers.values()) {
            Collections.shuffle(layer);
        }
        
        // Shuffle night layers if they exist
        if (hasNightLayers) {
            for (List<File> nightLayer : nightLayers.values()) {
                Collections.shuffle(nightLayer);
            }
        }
        
        System.out.println("Randomized layers");
        setupSoundPlayers();
    }

    public void setupSoundPlayers() {
        currLayers.clear();
        soundPlayers.clear();
        nightSoundPlayers.clear();
    
        // Create a list of map entries to sort them by threat level
        List<Map.Entry<Float, List<File>>> sortedEntries = new ArrayList<>(layers.entrySet());
        sortedEntries.sort(Map.Entry.comparingByKey());
    
        // Iterate through the sorted entries
        for (Map.Entry<Float, List<File>> entry : sortedEntries) {
            // for all sound events in the layer list
            for (File soundEvent : entry.getValue()) {
                // if the sound event is not already in the currLayers map
                if (!currLayers.containsValue(soundEvent)) {
                    float key = entry.getKey();
                    // get the sound event from the layer list and add it to the currLayers map
                    currLayers.put(entry.getKey(), soundEvent);
                    // create a new sound player with the sound event and add it to the soundPlayers list
                    soundPlayers.add(new ThreatMusicInstance(soundEvent, key, key + offset));
                    System.out.println("Added sound player with name " + soundEvent.getPath() + " and threat level " + key + " to " + (key + offset));
                    break;
                }
            }
        }

        if (hasNightLayers) {
            List<Map.Entry<Float, List<File>>> nightSortedEntries = new ArrayList<>(nightLayers.entrySet());
            nightSortedEntries.sort(Map.Entry.comparingByKey());
        
            for (Map.Entry<Float, List<File>> entry : nightSortedEntries) {
                for (File soundEvent : entry.getValue()) {
                    if (!currLayers.containsValue(soundEvent)) {
                        float key = entry.getKey();
                        currLayers.put(entry.getKey(), soundEvent);
                        nightSoundPlayers.add(new ThreatMusicInstance(soundEvent, key, key + offset));
                        System.out.println("Added night sound player with name " + soundEvent.getPath() + " and threat level " + key + " to " + (key + offset));
                        break;
                    }
                }
            }
        }
    }

    public String name() {
        return name;
    }
    

    public void play(MinecraftClient client){
        SoundManager soundManager = client.getSoundManager();
        //MusicTracker musicTracker = client.getMusicTracker();
    
        //musicTracker.stop();

        //C:\Users\charl\Downloads\New folder
    
        if (soundPlayers != null) {
            if (isDay(client) || !hasNightLayers) {
                for (ThreatMusicInstance soundPlayer : soundPlayers) {
                    soundManager.play(soundPlayer);
                    //add the sound player to the playingLayers list
                    playingLayers.add(soundPlayer);
                }
            } else {
                for (ThreatMusicInstance soundPlayer : nightSoundPlayers) {
                    soundManager.play(soundPlayer);
                    playingLayers.add(soundPlayer);
                }
            }
        }
    }

    public void playDemo(MinecraftClient client) {
        SoundManager soundManager = client.getSoundManager();
        //MusicTracker musicTracker = client.getMusicTracker();
    
        

        //C:\Users\charl\Downloads\New folder
    
        if (soundPlayers != null) {
            if (Math.random() < 0.5 || !hasNightLayers) {
                for (ThreatMusicInstance soundPlayer : soundPlayers) {
                    playingLayers.add(soundPlayer);
                    
                }
            } else {
                for (ThreatMusicInstance soundPlayer : nightSoundPlayers) {
                    playingLayers.add(soundPlayer);
                }
            }
            //play all the sound players in the playingLayers list
            for (ThreatMusicInstance soundPlayer : playingLayers) {
                //set volume to 1.0f
                if (client.isInSingleplayer()) {
                    //hacky way to make the demo sound good in singleplayer
                    soundPlayer.setVolume(1.0f);
                }
                soundManager.play(soundPlayer);
            }
        }
    }


    public void stop(MinecraftClient client){
        SoundManager soundManager = client.getSoundManager();
        for (ThreatMusicInstance soundPlayer : playingLayers) {
            soundManager.stop(soundPlayer);
        }
        playingLayers.clear();
    }

    public static boolean isDay(MinecraftClient client) {
        //client.world.isDay() doesn't work
        return client.world.getTimeOfDay() % 24000 < 12000;
    }

    public SoundInstance getMusic(MinecraftClient client) {
        //create a new music player with a random music track from the list of music
        if (music != null && !music.isEmpty()) {
            File musicEvent = music.get((int) (Math.random() * music.size()));
            //print the name of the music track
            System.out.println("Playing music: " + musicEvent.getPath());
            return new MusicInstance(musicEvent);
        }
        return null;
    }

    public void updateVolumes(float threatLevel) {
        for (ThreatMusicInstance soundPlayer : playingLayers) {
            soundPlayer.updateVolume(threatLevel);
        }
    }

    public boolean hasLayers() {
        //return false if both layers and nightLayers are empty otherwise return true if either layers or nightLayers is not empty
        
        System.out.println("Returning" + (!layers.isEmpty() || !nightLayers.isEmpty()));
        return !layers.isEmpty() || !nightLayers.isEmpty();
    }

    public boolean hasMusic() {
        return !music.isEmpty();
    }
}
