package com.rsomeara.factorio.modlauncher.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.rsomeara.factorio.modlauncher.model.Mod;
import com.rsomeara.factorio.modlauncher.model.ModList;
import com.rsomeara.factorio.modlauncher.service.IFactorioService;
import com.rsomeara.factorio.modlauncher.service.IFilePathService;

public class FactorioService implements IFactorioService {

    private final IFilePathService filePathService;

    public FactorioService(IFilePathService filePathService) {
        this.filePathService = Objects.requireNonNull(filePathService);
    }

    @Override
    public List<String> getEnabledMods() throws IOException {
        ModList list = ModList.fromFile(filePathService.getFactorioModList());

        return list.getMods().stream()
                .filter(Mod::isEnabled)
                .map(Mod::getName)
                .collect(Collectors.toList());
    }

}
