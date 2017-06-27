package com.rsomeara.factorio.modlauncher.pack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;
import java.util.Properties;

import com.rsomeara.factorio.modlauncher.FactorioPaths;

/**
 * Common operations for Mod Packs
 *
 * @author romeara
 * @since 0.1.0
 */
public final class ModPackService {

    public static void create(String name) throws IOException {
        Objects.requireNonNull(name);

        // Create initial mod-pack from current
        String encodedName = Base64.getEncoder().encodeToString(name.getBytes());

        // Create the modpack directory
        if (!Files.exists(ModLauncherPaths.getModPacksDirectory())) {
            Files.createDirectories(ModLauncherPaths.getModPacksDirectory());
        }

        // Save the current mod-list.json as the mod pack
        Path modPackPath = ModLauncherPaths.getModPacksDirectory().resolve(encodedName + ".json");
        Files.copy(FactorioPaths.getModList(), modPackPath);

        // Save the current modpack as the active modpack
        setCurrentModPack(encodedName);
    }

    private static void setCurrentModPack(String encodedName) throws IOException {
        if (!Files.exists(ModLauncherPaths.getPropertiesFile())) {
            Files.createFile(ModLauncherPaths.getPropertiesFile());
        }

        Properties props = new Properties();
        props.put("current.modpack", encodedName);

        props.store(Files.newOutputStream(ModLauncherPaths.getPropertiesFile()), null);
    }
}
