package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;




public class ModSounds {

    // Define the Region outside of the registerSounds() method so it can be accessed globally.
    

    //current region
    public static Region currentRegion;

    //map of regions indexed by name
    public static Map<String, Region> regions = new HashMap<String, Region>();

    //map of regions indexed by list of biomes
    public static Map<List<String>, Region> biomeRegions = new HashMap<List<String>, Region>();


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier("modid", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        SoundEvent th_su_arps = registerSoundEvent("th_su_arps");
        SoundEvent th_su_bass = registerSoundEvent("th_su_bass");
        SoundEvent th_su_hits = registerSoundEvent("th_su_hits");
        SoundEvent th_su_kick = registerSoundEvent("th_su_kick");
        SoundEvent th_su_lead = registerSoundEvent("th_su_lead");
        SoundEvent th_su_noise = registerSoundEvent("th_su_noise");
        SoundEvent th_su_perc1 = registerSoundEvent("th_su_perc1");
        SoundEvent th_su_shaker = registerSoundEvent("th_su_shaker");
        
        SoundEvent th_sl_arps = registerSoundEvent("th_sl_arps");
        SoundEvent th_sl_bass = registerSoundEvent("th_sl_bass");
        SoundEvent th_sl_kick = registerSoundEvent("th_sl_kick");
        SoundEvent th_sl_lead = registerSoundEvent("th_sl_lead");
        SoundEvent th_sl_noise = registerSoundEvent("th_sl_noise");
        SoundEvent th_sl_perc1 = registerSoundEvent("th_sl_perc1");
        SoundEvent th_sl_perc2 = registerSoundEvent("th_sl_perc2");
        SoundEvent th_sl_snare = registerSoundEvent("th_sl_snare");

        SoundEvent th_ss_bass = registerSoundEvent("th_ss_bass");
        SoundEvent th_ss_kick = registerSoundEvent("th_ss_kick");
        SoundEvent th_ss_lead = registerSoundEvent("th_ss_lead");
        SoundEvent th_ss_noise = registerSoundEvent("th_ss_noise");
        SoundEvent th_ss_pop = registerSoundEvent("th_ss_pop");

        // Create a new region called "su" and add layers to it
        // Layer : TH_SU - KICK, TH_SU - SHAKER
        // Layer : TH_SU - KICK, TH_SU - SHAKER, TH_SU - PERC1, TH_SU - NOISE
        // Layer : TH_SU - KICK, TH_SU - SHAKER, TH_SU - PERC1, TH_SU - NOISE, TH_SU - HITS, TH_SU - BASS
        // Layer : TH_SU - KICK, TH_SU - SHAKER, TH_SU - PERC1, TH_SU - ARPS, TH_SU - NOISE, TH_SU - HITS, TH_SU - BASS
        // Layer : TH_SU - LEAD, TH_SU - BASS
        regions.put("su", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(1, new ArrayList<SoundEvent>() {{
                    add(th_su_kick);
                    add(th_su_shaker);
                }});
                put(2, new ArrayList<SoundEvent>() {{
                    add(th_su_kick);
                    add(th_su_shaker);
                    add(th_su_perc1);
                    add(th_su_noise);
                }});
                put(3, new ArrayList<SoundEvent>() {{
                    add(th_su_kick);
                    add(th_su_shaker);
                    add(th_su_perc1);
                    add(th_su_noise);
                    add(th_su_hits);
                    add(th_su_bass);
                }});
                put(4, new ArrayList<SoundEvent>() {{
                    add(th_su_kick);
                    add(th_su_shaker);
                    add(th_su_perc1);
                    add(th_su_arps);
                    add(th_su_noise);
                    add(th_su_hits);
                    add(th_su_bass);
                }});
                put(5, new ArrayList<SoundEvent>() {{
                    add(th_su_lead);
                    add(th_su_bass);
                }});
            }}
        ));

        //Layer : TH_SL - KICK, TH_SL - BASS
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS, TH_SL - NOISE, TH_SL - SNARE
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS, TH_SL - NOISE, TH_SL - SNARE, TH_SL - PERC2, TH_SL - LEAD
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS, TH_SL - NOISE, TH_SL - SNARE, TH_SL - PERC2, TH_SL - LEAD
        regions.put("sl", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(1, new ArrayList<SoundEvent>() {{
                    add(th_sl_kick);
                    add(th_sl_bass);
                }});
                put(2, new ArrayList<SoundEvent>() {{
                    add(th_sl_kick);
                    add(th_sl_bass);
                    add(th_sl_perc2);
                    add(th_sl_arps);
                }});
                put(3, new ArrayList<SoundEvent>() {{
                    add(th_sl_kick);
                    add(th_sl_bass);
                    add(th_sl_perc2);
                    add(th_sl_arps);
                    add(th_sl_noise);
                    add(th_sl_snare);
                }});
                put(4, new ArrayList<SoundEvent>() {{
                    add(th_sl_kick);
                    add(th_sl_bass);
                    add(th_sl_perc2);
                    add(th_sl_arps);
                    add(th_sl_noise);
                    add(th_sl_snare);
                    add(th_sl_perc2);
                    add(th_sl_lead);
                }});
                put(5, new ArrayList<SoundEvent>() {{
                    add(th_sl_kick);
                    add(th_sl_bass);
                    add(th_sl_perc2);
                    add(th_sl_arps);
                    add(th_sl_noise);
                    add(th_sl_snare);
                    add(th_sl_perc2);
                    add(th_sl_lead);
                }});
            }}
        ));

        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK
        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK, TH_SS - POP
        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK, TH_SS - POP, TH_SS - LEAD
        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK, TH_SS - POP, TH_SS - LEAD

        regions.put("ss", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(1, new ArrayList<SoundEvent>() {{
                    add(th_ss_noise);
                    add(th_ss_bass);
                    add(th_ss_kick);
                }});
                put(2, new ArrayList<SoundEvent>() {{
                    add(th_ss_noise);
                    add(th_ss_bass);
                    add(th_ss_kick);
                    add(th_ss_pop);
                }});
                put(3, new ArrayList<SoundEvent>() {{
                    add(th_ss_noise);
                    add(th_ss_bass);
                    add(th_ss_kick);
                    add(th_ss_pop);
                    add(th_ss_lead);
                }});
                put(4, new ArrayList<SoundEvent>() {{
                    add(th_ss_noise);
                    add(th_ss_bass);
                    add(th_ss_kick);
                    add(th_ss_pop);
                    add(th_ss_lead);
                }});
            }}
        ));

        
        

        //add regions to biomeRegions
        //ss is for all ocean biomes
        biomeRegions.put(new ArrayList<String>() {{
            add("Ocean");
            add("Deep Ocean");
            add("Frozen Ocean");
            add("Deep Frozen Ocean");
            add("Cold Ocean");
            add("Deep Cold Ocean");
            add("Lukewarm Ocean");
            add("Deep Lukewarm Ocean");
            add("Warm Ocean");
            add("Deep Warm Ocean");
            add("River");
            add("Frozen River");
            add("Beach");
            add("Stone Shore");
            add("Snowy Beach");
            add("Mushroom Fields Shore");
            add("Desert Lakes");

        }}, regions.get("sl"));

        //ss is for all end biomes
        biomeRegions.put(new ArrayList<String>() {{
            add("The End");
            add("Small End Islands");
            add("End Midlands");
            add("End Highlands");
            add("End Barrens");
        }}, regions.get("ss"));

        //set default region to su
        currentRegion = regions.get("su");
    }

    public static void changeRegion(MinecraftClient client) {
        //get the current biome e.g. minecraft:plains using the value in the f3 menu
        RegistryEntry<Biome> var27 = client.world.getBiome(client.player.getBlockPos());
        String biome = I18n.translate(getBiomeName(var27));

        //print the current biome to the chat
        client.player.sendMessage(Text.of(biome), false);

        //change the region to the region whose list of biomes contains the current biome
        Region newRegion = null;
        for (Map.Entry<List<String>, Region> entry : biomeRegions.entrySet()) {
            if (entry.getKey().contains(biome)) {
                newRegion = entry.getValue();
                break;
            }
            //otherwise, set the region to the default region
            newRegion = regions.get("su");
        }

        //if the new region is different from the current region, stop the current region
        if (currentRegion != null && !newRegion.equals(currentRegion)) {
            
            currentRegion = newRegion;
            client.player.sendMessage(Text.of("changing music"), false);
        }
    }

    public static String getBiomeName(RegistryEntry<Biome> biome) {
        return I18n.translate(getBiomeTranslationKey(biome));
    }

    private static String getBiomeTranslationKey(RegistryEntry<Biome> biome) {
        return biome.getKeyOrValue().map(
            (biomeKey) -> "biome." + biomeKey.getValue().getNamespace() + "." + biomeKey.getValue().getPath(),
            (biomeValue) -> "[unregistered " + biomeValue + "]" // For unregistered biome
        );
    }
}
