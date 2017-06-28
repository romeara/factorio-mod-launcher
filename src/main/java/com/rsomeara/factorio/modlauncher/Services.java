package com.rsomeara.factorio.modlauncher;

import com.rsomeara.factorio.modlauncher.pack.IModPackService;
import com.rsomeara.factorio.modlauncher.pack.ModPackService;

/**
 * Simple substituted for a full-blown dependency injection system. Frameworks like Spring and Guice require more
 * finangling to play nice with JavaFX's injection, and the size of this app doesn't yet lend to such effort
 *
 * @author romeara
 * @since 0.1.0
 */
public final class Services {

    private static IModPackService modPackService;

    static {
        modPackService = new ModPackService();
    }

    public static IModPackService getModPackService() {
        return modPackService;
    }
}
