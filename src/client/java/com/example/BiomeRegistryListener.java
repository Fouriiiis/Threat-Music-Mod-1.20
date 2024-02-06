package com.example;

import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class BiomeRegistryListener {

    public static void init() {
        DynamicRegistrySetupCallback.EVENT.register(registryView -> {
            registryView.registerEntryAdded(RegistryKeys.BIOME, (rawId, id, object) -> {
                onBiomeRegistered(rawId, id, object);
            });
        });
    }

    private static void onBiomeRegistered(int rawId, Identifier id, Biome biome) {
        System.out.println("Biome registered: " + id);

        // add the biome to the biome region keys with its value from savedBiomeRegionKeys
        // if it doesn't exist in savedBiomeRegionKeys, add it with a default value of "su" to both

        if (ModSounds.savedBiomeRegionKeys.containsKey(id.toString())) {
            System.out.println("Biome is in savedBiomeRegionKeys");
            ModSounds.biomeRegionKeys.put(id.toString(), ModSounds.savedBiomeRegionKeys.get(id.toString()));
        } else {
            System.out.println("Biome is not in savedBiomeRegionKeys");
            ModSounds.biomeRegionKeys.put(id.toString(), "Outskirts");
            ModSounds.savedBiomeRegionKeys.put(id.toString(), "Outskirts");
        }
    }
}
