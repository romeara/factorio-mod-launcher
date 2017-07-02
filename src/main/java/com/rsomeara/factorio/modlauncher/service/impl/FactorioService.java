package com.rsomeara.factorio.modlauncher.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SystemUtils;

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

    @Override
    public void launch() throws IOException {
        if (SystemUtils.IS_OS_WINDOWS) {
            Path shortcutPath = filePathService.getModPacksDirectory().resolve("Factorio.url");

            if (!Files.exists(shortcutPath)) {
                try (InputStream input = getClass().getClassLoader().getResourceAsStream("Factorio.url")) {
                    Files.copy(input, shortcutPath);
                }
            }

            Runtime.getRuntime().exec(new String[] { "cmd", "/c", shortcutPath.toString() });
        } else {
            throw new IOException("OS not yet supported for native launch - launch via Steam");
        }
    }

}
