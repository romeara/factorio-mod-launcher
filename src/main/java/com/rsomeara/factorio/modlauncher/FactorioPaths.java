package com.rsomeara.factorio.modlauncher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.SystemUtils;

public final class FactorioPaths {

    public static Path getModsDirectory() {
        return getFactorioDirectory().resolve("mods");
    }

    public static Path getModList() {
        return getModsDirectory().resolve("mod-list.json");
    }

    private static Path getFactorioDirectory() {
        Path factorioPath = null;

        // Paths based on https://wiki.factorio.com/Application_directory
        if (SystemUtils.IS_OS_WINDOWS) {
            factorioPath = Paths.get(System.getProperty("user.home")).resolve("AppData").resolve("Roaming")
                    .resolve("Factorio");
        } else if (SystemUtils.IS_OS_MAC) {
            factorioPath = Paths.get(System.getProperty("user.home")).resolve("Library").resolve("Application Support")
                    .resolve("factorio");
        } else if (SystemUtils.IS_OS_LINUX) {
            factorioPath = Paths.get(System.getProperty("user.home")).resolve(".factorio");
        } else {
            throw new UnsupportedOperationException(
                    "Unknown operating system, cannot find mods folder (" + System.getProperty("os.name") + ")");
        }

        if (!Files.exists(factorioPath)) {
            throw new IllegalStateException(
                    "Did not find facotrio folder at expected location (" + factorioPath.toString() + ")");
        }

        return factorioPath;
    }

}
