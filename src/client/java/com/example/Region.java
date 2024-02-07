package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import net.minecraft.sound.SoundEvent;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundManager;




public class Region {

    private Map<Float, SoundEvent> currLayers;
    private List<SoundPlayer> soundPlayers = new ArrayList<SoundPlayer>();
    private List<SoundPlayer> nightSoundPlayers = new ArrayList<SoundPlayer>();
    private Map<Float, List<SoundEvent>> layers;
    private Map<Float, List<SoundEvent>> nightLayers;

    private List<SoundEvent> music;

    //playing layers

    private List<SoundPlayer> playingLayers = new ArrayList<SoundPlayer>();

    MinecraftClient client = MinecraftClient.getInstance();
    float offset;
    boolean hasNightLayers = false;


    //constructor which takes a list of lists of sound events
    //each list of sound events is a layer

    public Region(List<List<SoundEvent>> dayLayers, Optional<List<List<SoundEvent>>> optionalNightLayers, Optional<List<SoundEvent>> optionalMusic) {
        this.layers = new HashMap<>();
        this.nightLayers = new HashMap<>();
        this.currLayers = new HashMap<>();
        this.soundPlayers = new ArrayList<>();
        this.nightSoundPlayers = new ArrayList<>();
        this.playingLayers = new ArrayList<>();
    
        // Set day layers
        offset = 0.95f / (dayLayers.size());
        float threatLevel = 0.05f;
        for (List<SoundEvent> layer : dayLayers) {
            this.layers.put(threatLevel, layer);
            threatLevel += offset;
        }
    
        // Check and set night layers if present
        if (optionalNightLayers.isPresent()) {
            hasNightLayers = true;
            List<List<SoundEvent>> nightLayers = optionalNightLayers.get();
            offset = 0.95f / (nightLayers.size());
            threatLevel = 0.05f;
            for (List<SoundEvent> layer : nightLayers) {
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
        //for each list of layers, shuffle the list
        for(List<SoundEvent> layer : layers.values()) {
            Collections.shuffle(layer);
        }
        System.out.println("Randomized layers");
        setupSoundPlayers();
        
    }

    public void setupSoundPlayers() {
        currLayers.clear();
        soundPlayers.clear();
        nightSoundPlayers.clear();
    
        // Create a list of map entries to sort them by threat level
        List<Map.Entry<Float, List<SoundEvent>>> sortedEntries = new ArrayList<>(layers.entrySet());
        sortedEntries.sort(Map.Entry.comparingByKey());
    
        // Iterate through the sorted entries
        for (Map.Entry<Float, List<SoundEvent>> entry : sortedEntries) {
            // for all sound events in the layer list
            for (SoundEvent soundEvent : entry.getValue()) {
                // if the sound event is not already in the currLayers map
                if (!currLayers.containsValue(soundEvent)) {
                    float key = entry.getKey();
                    // get the sound event from the layer list and add it to the currLayers map
                    currLayers.put(entry.getKey(), soundEvent);
                    // create a new sound player with the sound event and add it to the soundPlayers list
                    soundPlayers.add(new SoundPlayer(soundEvent, client, key, key + offset));
                    System.out.println("Added sound player with name " + soundEvent.getId().toString() + " and threat level " + key + " to " + (key + offset));
                    break;
                }
            }
        }

        if (hasNightLayers) {
            List<Map.Entry<Float, List<SoundEvent>>> nightSortedEntries = new ArrayList<>(nightLayers.entrySet());
            nightSortedEntries.sort(Map.Entry.comparingByKey());
        
            for (Map.Entry<Float, List<SoundEvent>> entry : nightSortedEntries) {
                for (SoundEvent soundEvent : entry.getValue()) {
                    if (!currLayers.containsValue(soundEvent)) {
                        float key = entry.getKey();
                        currLayers.put(entry.getKey(), soundEvent);
                        nightSoundPlayers.add(new SoundPlayer(soundEvent, client, key, key + offset));
                        System.out.println("Added night sound player with name " + soundEvent.getId().toString() + " and threat level " + key + " to " + (key + offset));
                        break;
                    }
                }
            }
        }
    }
    

    public void play(MinecraftClient client){
        SoundManager soundManager = client.getSoundManager();
        MusicTracker musicTracker = client.getMusicTracker();
        ExecutorService executor = Executors.newCachedThreadPool();
    
        musicTracker.stop();

        //C:\Users\charl\Downloads\New folder
    
        if (soundPlayers != null) {
            if (isDay(client) || !hasNightLayers) {
                for (SoundPlayer soundPlayer : soundPlayers) {
                    executor.execute(() -> soundManager.play(soundPlayer));
                    //add the sound player to the playingLayers list
                    playingLayers.add(soundPlayer);
                }
            } else {
                for (SoundPlayer soundPlayer : nightSoundPlayers) {
                    executor.execute(() -> soundManager.play(soundPlayer));
                    playingLayers.add(soundPlayer);
                }
            }
        }
        
        executor.shutdown();
    }

    public void playMusic(MinecraftClient client){
        //plays a single random music track from the list of music
        SoundManager soundManager = client.getSoundManager();
        MusicTracker musicTracker = client.getMusicTracker();
        ExecutorService executor = Executors.newCachedThreadPool();
        musicTracker.stop();
        if (music != null && !music.isEmpty()) {
            SoundEvent musicEvent = music.get((int) (Math.random() * music.size()));
            MusicPlayer musicPlayer = new MusicPlayer(musicEvent, client);
            executor.execute(() -> soundManager.play(musicPlayer));
            System.out.println("MusicPlaying set to true");
        }
        executor.shutdown();
    }

    public void stop(MinecraftClient client){
        SoundManager soundManager = client.getSoundManager();
        for (SoundPlayer soundPlayer : playingLayers) {
            soundManager.stop(soundPlayer);
        }
        playingLayers.clear();
    }

    public static boolean isDay(MinecraftClient client) {
        //client.world.isDay() doesn't work
        return client.world.getTimeOfDay() % 24000 < 12000;
    }
}
