package com.rsomeara.factorio.modlauncher.pack;

import java.nio.file.Path;

import com.rsomeara.factorio.modlauncher.FactorioPaths;

public final class ModLauncherPaths {

    public static Path getModPacksDirectory() {
        return FactorioPaths.getModsDirectory().resolve(".modPacks");
    }

    public static Path getPropertiesFile() {
        return getModPacksDirectory().resolve("launcher.properties");
    }

}
