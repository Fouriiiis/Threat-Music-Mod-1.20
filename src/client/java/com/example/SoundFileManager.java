package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Stream;

import net.fabricmc.loader.api.FabricLoader;

public class SoundFileManager {

    public static void LoadSoundFiles() {
        Optional<Path> soundsDirectoryPath = FabricLoader.getInstance()
                                           .getModContainer("modid")
                                           .flatMap(modContainer -> modContainer.findPath("assets/modid/sounds"));
        Optional<Path> regionsDirectoryPath = FabricLoader.getInstance()
                                            .getModContainer("modid")
                                            .flatMap(modContainer -> modContainer.findPath("assets/modid/regions"));
        Path targetDirectoryPath = Paths.get(System.getProperty("user.dir"), "ThreatMusic");

        // Create the target directory if it doesn't exist
        if (!Files.exists(targetDirectoryPath)) {
            try {
                Files.createDirectories(targetDirectoryPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        copyFiles(soundsDirectoryPath, ".ogg", targetDirectoryPath);
        copyFiles(regionsDirectoryPath, ".json", targetDirectoryPath);
    }

    public static void copyFiles(Optional<Path> directory, String extension, Path targetDirectory) {
        if (directory.isPresent()) {
            try (Stream<Path> paths = Files.walk(directory.get())) {
                paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(extension))
                    .forEach(path -> {
                        try {
                            String fileName = path.getFileName().toString();
                            Path targetPath = targetDirectory.resolve(fileName);

                            // Check if the file already exists to avoid unnecessary copying
                            if (!Files.exists(targetPath)) {
                                Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                                System.out.println("Copied file: " + fileName + " to " + targetPath);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Directory not found: " + directory);
        }
    }
}

