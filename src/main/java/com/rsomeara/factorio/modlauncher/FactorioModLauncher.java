package com.rsomeara.factorio.modlauncher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Properties;

import com.rsomeara.factorio.modlauncher.pack.ModLauncherPaths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point of the application, starts the launcher and creates the interface
 *
 * @author romeara
 * @since 0.1.0
 */
public class FactorioModLauncher extends Application {

    /**
     * Called by external commands to start the Java program
     *
     * @param args
     *            Any arguments provided on the command line when starting the application
     * @since 0.1.0
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize, if needed
        if (!Files.exists(ModLauncherPaths.getModPacksDirectory())) {
            performFirstTimeSetup();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/launcherUI.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Factorio Mod Launcher");
        stage.setScene(scene);
        stage.show();
    }

    private void performFirstTimeSetup() {
        try {
            // Create initial mod-pack from current
            String packName = System.getProperty("user.name") + "'s Modpack";
            String encodedName = Base64.getEncoder().encodeToString(packName.getBytes());

            // Create the modpack directory
            Files.createDirectories(ModLauncherPaths.getModPacksDirectory());

            // Save the current mod-list.json as the mod pack
            Path modPackPath = ModLauncherPaths.getModPacksDirectory().resolve(encodedName + ".json");
            Files.copy(FactorioPaths.getModList(), modPackPath);

            // TODO romeara move properties logic to general location
            // Save the current modpack as the active modpack
            Files.createFile(ModLauncherPaths.getPropertiesFile());
            Properties props = new Properties();
            props.put("current.modpack", encodedName);

            props.store(Files.newOutputStream(ModLauncherPaths.getPropertiesFile()), null);
        } catch (IOException e) {
            throw new RuntimeException("Error performing first-time setup", e);
        }
    }

}
