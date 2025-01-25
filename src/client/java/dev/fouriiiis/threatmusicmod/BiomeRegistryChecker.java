package dev.fouriiiis.threatmusicmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import java.util.List;

public class BiomeRegistryChecker {

    public static void checkWorldBiomes() {
        MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        if (world != null) {
            Registry<Biome> biomeRegistry = world.getRegistryManager().get(RegistryKeys.BIOME);

            // Clear the biomeRegionKeys map
            ModSounds.biomeRegionKeys.clear();
            ModSounds.biomeTagRegionKeys.clear();

            for (RegistryKey<Biome> key : biomeRegistry.getKeys()) {
                RegistryEntry<Biome> biomeEntry = biomeRegistry.getEntry(key).orElse(null);

                // Ensure the biome entry is not null
                if (biomeEntry != null) {
                    String biomeName = key.getValue().toString();
                    
                    // Check for saved biomes in ModSounds.savedBiomeRegionKeys
                    if (ModSounds.savedBiomeRegionKeys.containsKey(biomeName)) {
                        ModSounds.biomeRegionKeys.put(biomeName, ModSounds.savedBiomeRegionKeys.get(biomeName));
                    } else {
                        ModSounds.biomeRegionKeys.put(biomeName, "Outskirts");
                        ModSounds.savedBiomeRegionKeys.put(biomeName, "Outskirts");
                        ModSounds.defaultBiomeRegionKeys.put(biomeName, "Outskirts");
                    }

                    List<TagKey<Biome>> biomeTags = biomeRegistry.entryOf(key).streamTags().toList();
                    List<TagKey<Biome>> biomeTags2 = biomeEntry.streamTags().toList();
                    if (biomeTags.isEmpty()) {
                        System.out.println("  No tags associated with this biome.");
                        //message the player that there are no tags associated with this biome
                        client.player.sendMessage(Text.of("No tags associated with biome: " + biomeName), false);
                    } else {
                        for (TagKey<Biome> tag : biomeTags) {
                            client.player.sendMessage(Text.of("Biome: " + biomeName + " has tag: " + tag.id().toString()), false);
                            // Add unique tags to the biomeTags map with region "none"
                            String tagName = tag.id().toString();
                            if (ModSounds.savedBiomeTagRegionKeys.containsKey(tagName)) {
                                ModSounds.biomeTagRegionKeys.put(tagName, ModSounds.savedBiomeTagRegionKeys.get(tagName));
                            } else {
                                ModSounds.biomeTagRegionKeys.put(tagName, "none");
                                ModSounds.savedBiomeTagRegionKeys.put(tagName, "none");
                                ModSounds.defaultBiomeTagRegionKeys.put(tagName, "none");
                            }
                        }
                    }
                }
            }
            // Save the updated biome region keys and tag keys to the JSON files
            ConfigManager.saveBiomeRegionKeys(ModSounds.biomeRegionKeys);
            ConfigManager.saveSavedBiomeRegionKeys(ModSounds.savedBiomeRegionKeys);
            ConfigManager.saveBiomeTagKeys(ModSounds.biomeTagRegionKeys);
            ConfigManager.saveSavedBiomeTagKeys(ModSounds.savedBiomeTagRegionKeys);
        }
    }

    // public static void checkBiomeTags() {
    //     MinecraftClient minecraft = MinecraftClient.getInstance();
    //     if (minecraft.player != null) {
    //         // Get the current biome at the player's position
    //         Biome biome = minecraft.level.getBiome(minecraft.player.blockPosition()).value();

    //         // Access the biome registry
    //         Registry<Biome> biomeRegistry = minecraft.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);

    //         // Get the ResourceKey for the current biome
    //         ResourceKey<Biome> biomeKey = biomeRegistry.getResourceKey(biome).orElseThrow(() -> new IllegalStateException("Biome not found in registry"));

    //         // Retrieve the tags for the current biome
    //         List<TagKey<Biome>> biomeTags = biomeRegistry.entryOf(biomeKey).streamTags().toList();

    //         // Perform operations with biomeTags
    //         biomeTags.forEach(tag -> System.out.println("Biome Tag: " + tag.location()));
    //     }
    // }

}
