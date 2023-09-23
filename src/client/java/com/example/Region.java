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
        //convert each list of sound events to a list of sound players
        this.layers = layers;
        currLayers = new HashMap<Integer, SoundEvent>();
        randomizeLayers();
    }

    public void randomizeLayers() {
        //for all elements in the layers map, shuffle the list of sound events
        for (Map.Entry<Integer, List<SoundEvent>> entry : layers.entrySet()) {
            Collections.shuffle(entry.getValue());

            //if the first element in the list is not already in the currLayers map, add it
            for(SoundEvent soundEvent : entry.getValue()) {
                if(!currLayers.containsValue(soundEvent)) {
                    currLayers.put(entry.getKey(), soundEvent);
                    break;
                }
            }
        }
    }

    public void play(MinecraftClient client){
        //clear the sound players list
        soundPlayers.clear();

        SoundManager soundManager = client.getSoundManager();

        for(Map.Entry<Integer, SoundEvent> entry : currLayers.entrySet()) {
            SoundPlayer soundPlayer = new SoundPlayer(entry.getValue(), client, entry.getKey());
            soundPlayers.add(soundPlayer);
            soundManager.play(soundPlayer);
        }
    }

    public void stop(MinecraftClient client){
        if(soundPlayers != null) {
            for(int i = 0; i < soundPlayers.size(); i++) {
                client.getSoundManager().stop(soundPlayers.get(i));
            }
        }
    }

    public boolean isPlaying() {
        //if sound players is not null
        if(soundPlayers != null) {
            //if any sound player is playing, return true
            for(int i = 0; i < soundPlayers.size(); i++) {
                if(soundPlayers.get(i).getVolume() > 0.0f) {
                    return true;
                }
            }
        }
        return false;

    }
}
