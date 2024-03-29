package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


public class ConfigManager {
    private static final String CONFIG_FILE = "ThreatMusicSettings.json";
    private static Map<String, Map<String, String>> allConfigs = new HashMap<>();

    static {
        init();
    }

    public static void init() {
        try {
            FileReader reader = new FileReader(CONFIG_FILE);
            Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
            allConfigs = new Gson().fromJson(reader, type);
            reader.close();
        } catch (IOException e) {
            createConfigFile();
        }
    }

    private static void createConfigFile() {
        try {
            FileWriter writer = new FileWriter(CONFIG_FILE);
            writer.write("{}");
            writer.close();
            allConfigs.put("biomeRegionKeys", new HashMap<>());
            allConfigs.put("savedBiomeRegionKeys", new HashMap<>());
            allConfigs.put("defaultBiomeRegionKeys", new HashMap<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(CONFIG_FILE);
            gson.toJson(allConfigs, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBiomeRegionKeys(Map<String, String> biomeRegionKeys) {
        allConfigs.put("biomeRegionKeys", biomeRegionKeys);
        saveConfig();
    }

    public static void saveSavedBiomeRegionKeys(Map<String, String> savedBiomeRegionKeys) {
        allConfigs.put("savedBiomeRegionKeys", savedBiomeRegionKeys);
        saveConfig();
    }

    public static void saveDefaultBiomeRegionKeys(Map<String, String> defaultBiomeRegionKeys) {
        allConfigs.put("defaultBiomeRegionKeys", defaultBiomeRegionKeys);
        saveConfig();
    }

    public static Map<String, String> loadBiomeRegionKeys() {
        return allConfigs.getOrDefault("biomeRegionKeys", new HashMap<>());
    }

    public static Map<String, String> loadSavedBiomeRegionKeys() {
        return allConfigs.getOrDefault("savedBiomeRegionKeys", new HashMap<>());
    }

    public static Map<String, String> loadDefaultBiomeRegionKeys() {
        return allConfigs.getOrDefault("defaultBiomeRegionKeys", new HashMap<>());
    }
}
