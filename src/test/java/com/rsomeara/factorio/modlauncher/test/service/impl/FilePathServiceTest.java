package com.rsomeara.factorio.modlauncher.test.service.impl;

import java.nio.file.Path;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rsomeara.factorio.modlauncher.service.impl.FilePathService;

public class FilePathServiceTest {

    private final FilePathService filePathService = new FilePathService();

    @Test(groups = "factorioInstalled")
    public void getFactorioModsDirectory() throws Exception {
        Path factorioModsDirectory = filePathService.getFactorioModsDirectory();

        Assert.assertNotNull(factorioModsDirectory);

        // No matter the OS, the final part of the path is "mods"
        Assert.assertEquals(factorioModsDirectory.getFileName().toString(), "mods");
    }

    @Test(groups = "factorioInstalled")
    public void getFactorioModsList() throws Exception {
        Path factorioModsDirectory = filePathService.getFactorioModList();

        Assert.assertNotNull(factorioModsDirectory);

        // No matter the OS, the final part of the path is "mod-list.json"
        Assert.assertEquals(factorioModsDirectory.getFileName().toString(), "mod-list.json");
    }

    @Test(groups = "factorioInstalled")
    public void getModPacksDirectory() throws Exception {
        Path factorioModsDirectory = filePathService.getModPacksDirectory();

        Assert.assertNotNull(factorioModsDirectory);

        // No matter the OS, the final part of the path is ".modPacks"
        Assert.assertEquals(factorioModsDirectory.getFileName().toString(), ".modPacks");
    }

    @Test(groups = "factorioInstalled")
    public void getPropertiesFile() throws Exception {
        Path factorioModsDirectory = filePathService.getPropertiesFile();

        Assert.assertNotNull(factorioModsDirectory);

        // No matter the OS, the final part of the path is ".modPacks"
        Assert.assertEquals(factorioModsDirectory.getFileName().toString(), "launcher.properties");
    }

}
