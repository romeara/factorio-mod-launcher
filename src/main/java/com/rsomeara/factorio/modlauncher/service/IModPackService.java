package com.rsomeara.factorio.modlauncher.service;

import java.io.IOException;

/**
 * Common operations for Mod Packs
 *
 * @author romeara
 * @since 0.1.0
 */
public interface IModPackService {

    void create(String name) throws IOException;

}
