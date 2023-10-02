package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import net.minecraft.sound.SoundEvent;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;

public class Region {

    private Map<Integer, SoundEvent> currLayers;
    private List<SoundPlayer> soundPlayers = new ArrayList<SoundPlayer>();
    private Map<Integer, List<SoundEvent>> layers;
    MinecraftClient client = MinecraftClient.getInstance();


    //constructor which takes a list of lists of sound events
    //each list of sound events is a layer

    public Region(HashMap<Integer, List<SoundEvent>> layers) {

        this.layers = layers;
        currLayers = new HashMap<Integer, SoundEvent>();
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
    
        // Create a list of map entries to sort them by threat level
        List<Map.Entry<Integer, List<SoundEvent>>> sortedEntries = new ArrayList<>(layers.entrySet());
        sortedEntries.sort(Map.Entry.comparingByKey());
    
        // Iterate through the sorted entries
        for (Map.Entry<Integer, List<SoundEvent>> entry : sortedEntries) {
            // for all sound events in the layer list
            for (SoundEvent soundEvent : entry.getValue()) {
                // if the sound event is not already in the currLayers map
                if (!currLayers.containsValue(soundEvent)) {
                    // get the sound event from the layer list and add it to the currLayers map
                    currLayers.put(entry.getKey(), soundEvent);
                    // create a new sound player with the sound event and add it to the soundPlayers list
                    soundPlayers.add(new SoundPlayer(soundEvent, client, entry.getKey()));
                    System.out.println("Added sound player with name " + soundEvent.getId().toString() + " and threat level " + entry.getKey());
                    break;
                }
            }
        }
    }
    

    public void play(MinecraftClient client){
        SoundManager soundManager = client.getSoundManager();
        //play all sound players and wait for them to load
        for(int i = 0; i < soundPlayers.size(); i++) {
            soundManager.play(soundPlayers.get(i));
        }
    }

    public void stop(MinecraftClient client){
        if(soundPlayers != null) {
            for(int i = 0; i < soundPlayers.size(); i++) {
                client.getSoundManager().stop(soundPlayers.get(i));
            }
        }
    }
}
