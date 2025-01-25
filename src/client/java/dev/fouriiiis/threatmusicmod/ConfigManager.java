package dev.fouriiiis.threatmusicmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.google.gson.reflect.TypeToken;

public class ConfigManager {
    private static final String BIOME_CONFIG_FILE = "ThreatMusicBiomes.json";
    private static final String MASTER_BIOME_CONFIG_FILE = "/assets/threatmusicmod/ThreatMusicBiomes.json";
    private static Map<String, Map<String, String>> allBiomeConfigs = new HashMap<>();
    private static Map<String, Map<String, String>> defaultBiomeConfigs = new HashMap<>();

    private static final String BIOME_TAG_CONFIG_FILE = "ThreatMusicBiomeTags.json";
    private static final String MASTER_BIOME_TAG_CONFIG_FILE = "/assets/threatmusicmod/ThreatMusicBiomeTags.json";
    private static Map<String, LinkedHashMap<String, String>> allBiomeTagConfigs = new HashMap<>();
    private static Map<String, LinkedHashMap<String, String>> defaultBiomeTagConfigs = new HashMap<>();

    private static final String THREAT_MUSIC_CONFIG_FILE = "ThreatMusicSettings.json";
    private static final String MASTER_THREAT_MUSIC_CONFIG_FILE = "/assets/threatmusicmod/ThreatMusicSettings.json";
    private static Map<String, Object> allConfigs = new HashMap<>();
    private static Map<String, Object> defaultConfigs = new HashMap<>();

    static {
        init();
    }

    public static void init() {
        initBiomeConfig();
        System.out.println("Biome config initialized");
        initBiomeTagConfig();
        System.out.println("Biome tag config initialized");
        initThreatMusicConfig();
        System.out.println("Threat music config initialized");
    }

    private static void initBiomeConfig() {
        try {
            FileReader reader = new FileReader(BIOME_CONFIG_FILE);
            Type biomeConfigType = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
            allBiomeConfigs = new Gson().fromJson(reader, biomeConfigType);
            reader.close();

            InputStream inputStream = ConfigManager.class.getResourceAsStream(MASTER_BIOME_CONFIG_FILE);
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                defaultBiomeConfigs = new Gson().fromJson(bufferedReader, biomeConfigType);
                bufferedReader.close();
            }

            mergeBiomeRegionKeys();
        } catch (IOException e) {
            // Create a new biome config file
            createConfigFile(BIOME_CONFIG_FILE, MASTER_BIOME_CONFIG_FILE);
        }
    }

    private static void initBiomeTagConfig() {
        try {
            FileReader tagReader = new FileReader(BIOME_TAG_CONFIG_FILE);
            Type biomeTagConfigType = new TypeToken<Map<String, LinkedHashMap<String, String>>>(){}.getType();
            allBiomeTagConfigs = new Gson().fromJson(tagReader, biomeTagConfigType);
            tagReader.close();

            InputStream tagInputStream = ConfigManager.class.getResourceAsStream(MASTER_BIOME_TAG_CONFIG_FILE);
            if (tagInputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(tagInputStream));
                defaultBiomeTagConfigs = new Gson().fromJson(bufferedReader, biomeTagConfigType);
                bufferedReader.close();
            }

            mergeBiomeTagKeys();
        } catch (IOException e) {
            // Create a new biome tag config file
            createConfigFile(BIOME_TAG_CONFIG_FILE, MASTER_BIOME_TAG_CONFIG_FILE);
        }
    }

    private static void initThreatMusicConfig() {
        try {
            FileReader configReader = new FileReader(THREAT_MUSIC_CONFIG_FILE);
            Type configType = new TypeToken<Map<String, Object>>(){}.getType();
            allConfigs = new Gson().fromJson(configReader, configType);
            configReader.close();

            InputStream inputStream = ConfigManager.class.getResourceAsStream(MASTER_THREAT_MUSIC_CONFIG_FILE);
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                defaultConfigs = new Gson().fromJson(bufferedReader, configType);
                bufferedReader.close();
            }
            mergeConfigs();

        } catch (IOException e) {
            // Create a new threat music config file
            createConfigFile(THREAT_MUSIC_CONFIG_FILE, MASTER_THREAT_MUSIC_CONFIG_FILE);
        }
    }

    private static void createConfigFile(String configFileName, String masterConfigPath) {
        try {
            InputStream inputStream = ConfigManager.class.getResourceAsStream(masterConfigPath);
            if (inputStream != null) {
                Files.copy(inputStream, Paths.get(configFileName), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Created new config file: " + configFileName);
            } else {
                System.err.println("Master config file not found: " + masterConfigPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mergeBiomeRegionKeys() {
        Map<String, String> defaultBiomeRegionKeys = defaultBiomeConfigs.getOrDefault("defaultBiomeRegionKeys", new HashMap<>());
        Map<String, String> biomeRegionKeys = allBiomeConfigs.getOrDefault("defaultBiomeRegionKeys", new HashMap<>());
        defaultBiomeRegionKeys.forEach(biomeRegionKeys::put);
        allBiomeConfigs.put("defaultBiomeRegionKeys", biomeRegionKeys);
        saveBiomeConfig();
    }

    private static void mergeBiomeTagKeys() {
        LinkedHashMap<String, String> defaultBiomeTagKeys = defaultBiomeTagConfigs.getOrDefault("defaultBiomeTagKeys", new LinkedHashMap<>());
        LinkedHashMap<String, String> biomeTagKeys = allBiomeTagConfigs.getOrDefault("defaultBiomeTagKeys", new LinkedHashMap<>());
        defaultBiomeTagKeys.forEach(biomeTagKeys::put);
        allBiomeTagConfigs.put("defaultBiomeTagKeys", biomeTagKeys);
        saveBiomeTagConfig();
    }

    private static void mergeConfigs() {
        defaultConfigs.forEach(allConfigs::put);
        saveConfig();
    }

    public static void saveBiomeConfig() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(BIOME_CONFIG_FILE);
            gson.toJson(allBiomeConfigs, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBiomeTagConfig() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(BIOME_TAG_CONFIG_FILE);
            gson.toJson(allBiomeTagConfigs, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(THREAT_MUSIC_CONFIG_FILE);
            gson.toJson(allConfigs, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBiomeRegionKeys(Map<String, String> biomeRegionKeys) {
        allBiomeConfigs.put("biomeRegionKeys", biomeRegionKeys);
        saveBiomeConfig();
    }

    public static void saveSavedBiomeRegionKeys(Map<String, String> savedBiomeRegionKeys) {
        allBiomeConfigs.put("savedBiomeRegionKeys", savedBiomeRegionKeys);
        saveBiomeConfig();
    }

    public static void saveDefaultBiomeRegionKeys(Map<String, String> defaultBiomeRegionKeys) {
        allBiomeConfigs.put("defaultBiomeRegionKeys", defaultBiomeRegionKeys);
        saveBiomeConfig();
    }

    public static Map<String, String> loadBiomeRegionKeys() {
        return allBiomeConfigs.getOrDefault("biomeRegionKeys", new HashMap<>());
    }

    public static Map<String, String> loadSavedBiomeRegionKeys() {
        return allBiomeConfigs.getOrDefault("savedBiomeRegionKeys", new HashMap<>());
    }

    public static Map<String, String> loadDefaultBiomeRegionKeys() {
        return allBiomeConfigs.getOrDefault("defaultBiomeRegionKeys", new HashMap<>());
    }

    // New methods for biome tag configs
    public static void saveBiomeTagKeys(LinkedHashMap<String, String> biomeTagKeys) {
        allBiomeTagConfigs.put("biomeTagKeys", biomeTagKeys);
        saveBiomeTagConfig();
    }

    public static LinkedHashMap<String, String> loadBiomeTagKeys() {
        return allBiomeTagConfigs.getOrDefault("biomeTagKeys", new LinkedHashMap<>());
    }

    public static LinkedHashMap<String, String> loadDefaultBiomeTagKeys() {
        return allBiomeTagConfigs.getOrDefault("defaultBiomeTagKeys", new LinkedHashMap<>());
    }

    public static LinkedHashMap<String, String> loadSavedBiomeTagKeys() {
        return allBiomeTagConfigs.getOrDefault("savedBiomeTagKeys", new LinkedHashMap<>());
    }

    // New methods for threat music configs
    public static void saveConfig(String key, Object value) {
        allConfigs.put(key, value);
        saveConfig();
    }

    public static Object loadConfig(String key) {
        return allConfigs.getOrDefault(key, defaultConfigs.get(key));
    }

    public static Object loadDefaultConfig(String key) {
        return defaultConfigs.get(key);
    }

    public static void saveSavedBiomeTagKeys(LinkedHashMap<String, String> savedBiomeTagRegionKeys) {
        allBiomeTagConfigs.put("savedBiomeTagKeys", savedBiomeTagRegionKeys);
        saveBiomeTagConfig();
    }
}
