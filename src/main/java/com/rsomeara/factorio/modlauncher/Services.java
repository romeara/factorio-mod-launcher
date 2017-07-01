package com.rsomeara.factorio.modlauncher;

import com.rsomeara.factorio.modlauncher.service.IFactorioService;
import com.rsomeara.factorio.modlauncher.service.IFilePathService;
import com.rsomeara.factorio.modlauncher.service.IModPackService;
import com.rsomeara.factorio.modlauncher.service.impl.FactorioService;
import com.rsomeara.factorio.modlauncher.service.impl.FilePathService;
import com.rsomeara.factorio.modlauncher.service.impl.ModPackService;

/**
 * Simple substitute for a full-blown dependency injection system. Frameworks like Spring and Guice require more
 * finagaling to play nice with JavaFX's injection, and the size of this app doesn't yet lend to such effort
 *
 * @author romeara
 * @since 0.1.0
 */
public final class Services {

    private static IFilePathService filePathService;

    private static IModPackService modPackService;

    private static IFactorioService factorioService;

    static {
        filePathService = new FilePathService();
        modPackService = new ModPackService(filePathService);
        factorioService = new FactorioService(filePathService);
    }

    public static IFilePathService getFilePathService() {
        return filePathService;
    }

    public static IModPackService getModPackService() {
        return modPackService;
    }

    public static IFactorioService getFactorioService() {
        return factorioService;
    }

}
