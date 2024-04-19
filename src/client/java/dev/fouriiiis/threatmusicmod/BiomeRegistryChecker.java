package dev.fouriiiis.threatmusicmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class BiomeRegistryChecker {

    public static void checkWorldBiomes() {
        MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        if (world != null) {
            Registry<Biome> biomeRegistry = world.getRegistryManager().get(RegistryKeys.BIOME);
            //clear the biomeRegionKeys map
            ModSounds.biomeRegionKeys.clear();
            for (RegistryKey<Biome> key : biomeRegistry.getKeys()) {
                //System.out.println(key.getValue());
                //client.player.sendMessage(Text.of(key.getValue().toString()), false);
                if (ModSounds.savedBiomeRegionKeys.containsKey(key.getValue().toString().toString())) {
                    //System.out.println("Biome is in savedBiomeRegionKeys");
                    ModSounds.biomeRegionKeys.put(key.getValue().toString().toString(), ModSounds.savedBiomeRegionKeys.get(key.getValue().toString().toString()));
                } else {
                    //System.out.println("Biome is not in savedBiomeRegionKeys");
                    ModSounds.biomeRegionKeys.put(key.getValue().toString().toString(), "Outskirts");
                    ModSounds.savedBiomeRegionKeys.put(key.getValue().toString().toString(), "Outskirts");
                    ModSounds.defaultBiomeRegionKeys.put(key.getValue().toString().toString(), "Outskirts");
                }
            }
        }
    }
}
