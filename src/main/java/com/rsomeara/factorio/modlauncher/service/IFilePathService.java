package com.rsomeara.factorio.modlauncher.service;

import java.nio.file.Path;

/**
 * Provides locations for common file locations used by the application
 *
 * @author romeara
 * @since 0.1.0
 */
public interface IFilePathService {

    /**
     * @return Path to the Factorio directory where mod files are stored
     * @since 0.1.0
     */
    Path getFactorioModsDirectory();

    /**
     * @return Path to the file Factorio reads from to determined if mods are enabled or disabled
     * @since 0.1.0
     */
    Path getFactorioModList();

    /**
     * @return Path to the directory where all mod launcher specific information is stored
     * @since 0.1.0
     */
    Path getModPacksDirectory();

    /**
     * @return The path to the mod pack launcher's properties file, where persisted data is stored
     * @since 0.1.0
     */
    Path getPropertiesFile();

}
