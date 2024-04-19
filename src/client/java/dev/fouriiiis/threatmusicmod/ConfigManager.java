package dev.fouriiiis.threatmusicmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
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
    private static final String CONFIG_FILE = "ThreatMusicSettings.json";
    //path to the config file within the mod container
    private static final String MASTER_CONFIG_FILE = "/assets/threatmusicmod/ThreatMusicSettings.json";
    private static Map<String, Map<String, String>> allConfigs = new HashMap<>();
    private static Map<String, Map<String, String>> defaultConfigs = new HashMap<>();
    

    static {
        init();
    }

    public static void init() {
        try {
            
            FileReader reader = new FileReader(CONFIG_FILE);
            Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
            allConfigs = new Gson().fromJson(reader, type);
            reader.close();

            InputStream inputStream = ConfigManager.class.getResourceAsStream(MASTER_CONFIG_FILE);
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                defaultConfigs = new Gson().fromJson(bufferedReader, type);
                bufferedReader.close();
            }

            //merge the defaultBiomeRegionKeys with the biomeRegionKeys, overwriting any existing keys in biomeRegionKeys
            Map<String, String> defaultBiomeRegionKeys = defaultConfigs.getOrDefault("defaultBiomeRegionKeys", new HashMap<>());
            Map<String, String> biomeRegionKeys = allConfigs.getOrDefault("defaultBiomeRegionKeys", new HashMap<>());

            //print the defaultBiomeRegionKeys
            // System.out.println("defaultBiomeRegionKeys:");
            // for (Map.Entry<String, String> entry : defaultBiomeRegionKeys.entrySet()) {
            //     System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            // }

            //print the biomeRegionKeys
            // System.out.println("biomeRegionKeys:");
            // for (Map.Entry<String, String> entry : biomeRegionKeys.entrySet()) {
            //     System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            // }

            defaultBiomeRegionKeys.forEach(biomeRegionKeys::put);

            //print the merged biomeRegionKeys
            // System.out.println("merged biomeRegionKeys:");
            // for (Map.Entry<String, String> entry : biomeRegionKeys.entrySet()) {
            //     System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            // }

            //save the merged biomeRegionKeys
            allConfigs.put("defaultBiomeRegionKeys", biomeRegionKeys);
            saveConfig();

        } catch (IOException e) {
            createConfigFile();
        }
    }    

    private static void createConfigFile() {
        try {
            //find the file in the fabric mod container
            String modid = "threatmusicmod";
            String configPath = "assets/" + modid + "/ThreatMusicSettings.json";
            Files.copy(ConfigManager.class.getClassLoader().getResourceAsStream(configPath), Paths.get(CONFIG_FILE), StandardCopyOption.REPLACE_EXISTING);
            init();
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
