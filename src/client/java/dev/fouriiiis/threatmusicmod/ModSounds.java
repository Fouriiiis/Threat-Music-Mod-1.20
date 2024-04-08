package dev.fouriiiis.threatmusicmod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import dev.fouriiiis.threatmusicmod.mixin.client.GetBossBarsMixin;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;






public class ModSounds {

    // Define the Region outside of the registerSounds() method so it can be accessed globally.
    

    //current region
    public static Region currentRegion;

    //map of regions indexed by name
    public static Map<String, Region> regions = new HashMap<String, Region>();

    //map of region keys indexed by biome string
    public static Map<String, String> savedBiomeRegionKeys = new HashMap<String, String>();

    public static Map<String, String> defaultBiomeRegionKeys = new HashMap<String, String>();

    public static Map<String, String> biomeRegionKeys = new HashMap<String, String>();


    //map of sound events indexed by name
    public static Map<String, SoundEvent> soundEvents = new HashMap<String, SoundEvent>();

    public static Map<String, File> soundFiles = new HashMap<String, File>();

    public static ConfigManager configManager = new ConfigManager();

    private static final Path targetDirectoryPath = Paths.get(System.getProperty("user.dir"), "ThreatMusic");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier("threatmusicmod", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void saveBiomeRegionKeys() {
        ConfigManager.saveBiomeRegionKeys(biomeRegionKeys);
    }

    public static void LoadSounds() {
        Optional<Path> directoryPath = FabricLoader.getInstance()
                                           .getModContainer("threatmusicmod")
                                           .flatMap(modContainer -> modContainer.findPath("assets/threatmusicmod/sounds"));

        if (directoryPath.isPresent()) {
            try (Stream<Path> paths = Files.walk(directoryPath.get())) {
                paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".ogg"))
                    .forEach(path -> {
                        String fileName = path.getFileName().toString();
                        String soundName = fileName.substring(0, fileName.lastIndexOf('.'));
                        SoundEvent soundEvent = registerSoundEvent(soundName);
                        soundEvents.put(soundName, soundEvent);
                    });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Directory not found: assets/threatmusicmod/sounds");
        }

        
    }

    public static void LoadSoundPaths() {
        // Ensure the directory exists, though it should if LoadSoundFiles was called previously
        if (Files.exists(targetDirectoryPath)) {
            try (Stream<Path> paths = Files.walk(targetDirectoryPath)) {
                paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".ogg"))
                    .forEach(path -> {
                        String fileName = path.getFileName().toString();
                        String soundName = fileName.substring(0, fileName.lastIndexOf('.'));
                        // Assuming soundFiles is a Map<String, File> for mapping sound names to their files
                        soundFiles.put(soundName, path.toFile());
                    });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Directory not found: " + targetDirectoryPath);
        }
    }
    
    public static void registerSounds() {

        

        SoundFileManager.LoadSoundFiles();

        LoadSoundPaths();

        //for each json file in the regions directory, call makeRegion(jsonFile)
        Optional<Path> directoryPath = Optional.of(targetDirectoryPath);

        if (directoryPath.isPresent()) {
            //for each json file in the regions directory, call makeRegion(jsonFile)

            try (Stream<Path> paths = Files.walk(directoryPath.get())) {
                paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        //String fileName = path.getFileName().toString();
                        //String regionName = fileName.substring(0, fileName.lastIndexOf('.'));
                        Region region = makeRegion(path.toFile());
                        if (region != null && region.name() != null){
                            regions.put(region.name(), region);
                        }
                    });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Directory not found: assets/threatmusicmod/regions");
        }

        biomeRegionKeys = ConfigManager.loadBiomeRegionKeys();
        savedBiomeRegionKeys = ConfigManager.loadSavedBiomeRegionKeys();
        defaultBiomeRegionKeys = ConfigManager.loadDefaultBiomeRegionKeys();

        //check that the regions from biomeRegionKeys are in the regions map, if not, set them to "None" in all maps and save
        for (String key : biomeRegionKeys.keySet()) {
            if (!regions.containsKey(biomeRegionKeys.get(key))) {
                biomeRegionKeys.put(key, "None");
                savedBiomeRegionKeys.put(key, "None");
                defaultBiomeRegionKeys.put(key, "None");
            }
        }

        saveBiomeRegionKeys();

        regions.put("None", new Region("None", new ArrayList<>(), Optional.empty(), Optional.empty()));
    }

    public static Region changeRegion(MinecraftClient client) {
        // Get the current biome at the player's position
        //RegistryEntry<Biome> currentBiomeEntry = client.world.getBiome(client.player.getBlockPos());
        RegistryEntry<Biome> currentBiomeEntry = client.world.getBiome(client.player.getBlockPos());
        String currentBiomeName = (getBiomeName(currentBiomeEntry));
    
        // Print the current biome to the chat
        //client.player.sendMessage(Text.of(currentBiomeName), false);

        //print out all the current keys
        //for (String key : regions.keySet()) {
        //    System.out.println(key + " " + regions.get(key));
        //}

        Region currentRegion = regions.get(biomeRegionKeys.get(currentBiomeName));

        BossBarHud bossBarHud = client.inGameHud.getBossBarHud();
        Map<UUID, ClientBossBar> bossBars = ((GetBossBarsMixin) bossBarHud).getBossBars();

        //iterate through and check if the strings contain Raid
        for (Map.Entry<UUID, ClientBossBar> entry : bossBars.entrySet()) {
            if (entry.getValue().getName().getString().contains("Raid")) {
                currentRegion = regions.get("Metropolis");
            }
        }

        //if region is null, set it to "None" and notify the player
        if (currentRegion == null) {
            currentRegion = regions.get("None");
            client.player.sendMessage(Text.of("No region found for " + currentBiomeName), false);
        }

        return currentRegion;
    }
    
    


    public static String getBiomeName(RegistryEntry<Biome> biome) {
        return getBiomeIdentifier(biome).toString();
    }
    
    private static Identifier getBiomeIdentifier(RegistryEntry<Biome> biome) {
        return biome.getKeyOrValue().map(
            (biomeKey) -> {
                // Extract the biome key value
                Identifier biomeIdentifier = biomeKey.getValue();
    
                // Print the biome Identifier (which is the value of biomeKey)
                //System.out.println("Registered Biome Identifier: " + biomeIdentifier);
    
                // Return the Identifier directly
                return biomeIdentifier;
            },
            (biomeValue) -> {
                // This block is for unregistered biomes
                System.out.println("Unregistered Biome: " + biomeValue);
                return new Identifier("unregistered", biomeValue.getClass().getSimpleName());
            }
        );
    }
    
    

    public static boolean isDay(MinecraftClient client) {
        //client.world.isDay() doesn't work
        return client.world.getTimeOfDay() % 24000 < 12000;
    }

    //method to make a region given a json file
    @SuppressWarnings("unchecked")
    public static Region makeRegion(File file) {
        List<List<String>> layers = new ArrayList<>();
        List<List<String>> nightLayers = new ArrayList<>();
        List<String> music = new ArrayList<>();
        Region region;
        String name = "None";

        //parse the json file
        try {
            FileReader reader = new FileReader(file);
            Map<String, Object> data = new Gson().fromJson(reader, new TypeToken<Map<String, Object>>(){}.getType());
            if (data != null) {
                reader.close();

                name = data.containsKey("name") ? (String) data.get("name") : "None";
                if (data.containsKey("layers")) {
                    List<List<String>> temp = (List<List<String>>) data.get("layers");
                    for (List<String> list : temp) {
                        layers.add(list);
                    }
                }
                if (data.containsKey("nightLayers")) {
                    List<List<String>> temp = (List<List<String>>) data.get("nightLayers");
                    for (List<String> list : temp) {
                        nightLayers.add(list);
                    }
                }
            
                if (data.containsKey("music")) {
                    List<String> temp = (List<String>) data.get("music");
                    for (String s : temp) {
                        music.add(s);
                    }
                }
            } else {
                return null;
            }
        } catch (JsonParseException | IOException e) {
            e.printStackTrace();
            return null;
        }

        //convert the lists of strings to lists of sound files
        List<List<File>> layersList = new ArrayList<>();
        List<List<File>> nightLayersList = new ArrayList<>();
        List<File> musicList = new ArrayList<>();

        for (List<String> list : layers) {
            List<File> temp = new ArrayList<>();
            for (String s : list) {
                temp.add(soundFiles.get(s));
            }
            layersList.add(temp);
        }

        for (List<String> list : nightLayers) {
            List<File> temp = new ArrayList<>();
            for (String s : list) {
                temp.add(soundFiles.get(s));
            }
            nightLayersList.add(temp);
        }

        for (String s : music) {
            musicList.add(soundFiles.get(s));
        }

        //create the region
        if (nightLayersList.size() > 0) {
            region = new Region(name, layersList, Optional.of(nightLayersList), Optional.of(musicList));
        } else {
            region = new Region(name, layersList, Optional.empty(), Optional.of(musicList));
        }

        return region;
    }
}
