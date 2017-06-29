package com.rsomeara.factorio.modlauncher.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;
import java.util.Properties;

import com.rsomeara.factorio.modlauncher.service.IFilePathService;
import com.rsomeara.factorio.modlauncher.service.IModPackService;

/**
 * Common operations for Mod Packs
 *
 * @author romeara
 * @since 0.1.0
 */
public final class ModPackService implements IModPackService {

    private final IFilePathService filePathService;

    public ModPackService(IFilePathService filePathService) {
        this.filePathService = Objects.requireNonNull(filePathService);
    }

    @Override
    public void create(String name) throws IOException {
        Objects.requireNonNull(name);

        // Create initial mod-pack from current
        String encodedName = Base64.getEncoder().encodeToString(name.getBytes());

        // Create the modpack directory
        if (!Files.exists(filePathService.getModPacksDirectory())) {
            Files.createDirectories(filePathService.getModPacksDirectory());
        }

        // Save the current mod-list.json as the mod pack
        Path modPackPath = filePathService.getModPacksDirectory().resolve(encodedName + ".json");
        Files.copy(filePathService.getFactorioModList(), modPackPath);

        // Save the current modpack as the active modpack
        setCurrentModPack(encodedName);
    }

    private void setCurrentModPack(String encodedName) throws IOException {
        if (!Files.exists(filePathService.getPropertiesFile())) {
            Files.createFile(filePathService.getPropertiesFile());
        }

        Properties props = new Properties();
        props.put("current.modpack", encodedName);

        props.store(Files.newOutputStream(filePathService.getPropertiesFile()), null);
    }
}
