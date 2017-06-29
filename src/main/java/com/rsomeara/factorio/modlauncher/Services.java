package com.rsomeara.factorio.modlauncher;

import com.rsomeara.factorio.modlauncher.service.IFilePathService;
import com.rsomeara.factorio.modlauncher.service.IModPackService;
import com.rsomeara.factorio.modlauncher.service.impl.FilePathService;
import com.rsomeara.factorio.modlauncher.service.impl.ModPackService;

/**
 * Simple substituted for a full-blown dependency injection system. Frameworks like Spring and Guice require more
 * finangling to play nice with JavaFX's injection, and the size of this app doesn't yet lend to such effort
 *
 * @author romeara
 * @since 0.1.0
 */
public final class Services {

    private static IFilePathService filePathService;

    private static IModPackService modPackService;

    static {
        filePathService = new FilePathService();
        modPackService = new ModPackService(filePathService);
    }

    public static IFilePathService getFilePathService() {
        return filePathService;
    }

    public static IModPackService getModPackService() {
        return modPackService;
    }

}
