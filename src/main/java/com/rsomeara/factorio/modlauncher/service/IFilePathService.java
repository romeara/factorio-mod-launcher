package com.rsomeara.factorio.modlauncher.service;

import java.nio.file.Path;

/**
 * Provides locations for common file locations used by the application
 *
 * @author romeara
 * @since 0.1.0
 */
public interface IFilePathService {

    Path getFactorioModsDirectory();

    Path getFactorioModList();

    Path getModPacksDirectory();

    Path getPropertiesFile();

}
