package com.rsomeara.factorio.modlauncher.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.rsomeara.factorio.modlauncher.Services;
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
    public String create(String name) throws IOException {
        Objects.requireNonNull(name);
        name = name.trim();

        Preconditions.checkArgument(!name.isEmpty(), "Cannot create mod pack with empty name");

        // Check for duplicates
        Preconditions.checkArgument(!getAll().contains(name), "Mod pack with name '" + name + "' already exists");

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
        setActive(name);

        return name;
    }

    @Override
    public List<String> getAll() throws IOException {
        return Files.list(Services.getFilePathService().getModPacksDirectory())
                .map(Path::getFileName)
                .map(Object::toString)
                .filter(input -> input.endsWith(".json"))
                .map(input -> input.substring(0, input.length() - 5))
                .map(input -> Base64.getDecoder().decode(input))
                .map(String::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getActive() throws IOException {
        Properties props = new Properties();
        props.load(Files.newBufferedReader(Services.getFilePathService().getPropertiesFile()));

        String currentEncoded = props.getProperty("current.modpack");

        return new String(Base64.getDecoder().decode(currentEncoded));
    }

    @Override
    public void delete(String name) throws IOException {
        Objects.requireNonNull(name);

        Preconditions.checkArgument(!Objects.equals(name, getActive()), "Cannot delete enabled mod pack");

        String encodedName = Base64.getEncoder().encodeToString(name.getBytes());

        Path modPackPath = filePathService.getModPacksDirectory().resolve(encodedName + ".json");

        if (Files.exists(modPackPath)) {
            Files.delete(modPackPath);
        }
    }

    @Override
    public void setActive(String name) throws IOException {
        Objects.requireNonNull(name);

        String encodedName = Base64.getEncoder().encodeToString(name.getBytes());

        if (!Files.exists(filePathService.getPropertiesFile())) {
            Files.createFile(filePathService.getPropertiesFile());
        }

        Properties props = new Properties();
        props.put("current.modpack", encodedName);

        props.store(Files.newOutputStream(filePathService.getPropertiesFile()), null);
    }
}
