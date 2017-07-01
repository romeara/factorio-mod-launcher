package com.rsomeara.factorio.modlauncher.service;

import java.io.IOException;
import java.util.List;

public interface IFactorioService {

    List<String> getEnabledMods() throws IOException;

}
