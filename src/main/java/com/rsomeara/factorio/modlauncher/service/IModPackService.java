package com.rsomeara.factorio.modlauncher.service;

import java.io.IOException;
import java.util.List;

/**
 * Common operations for Mod Packs
 *
 * @author romeara
 * @since 0.1.0
 */
public interface IModPackService {

    String create(String name) throws IOException;

    List<String> getAll() throws IOException;

    void delete(String name) throws IOException;

    String getActive() throws IOException;

    void setActive(String name) throws IOException;

}
