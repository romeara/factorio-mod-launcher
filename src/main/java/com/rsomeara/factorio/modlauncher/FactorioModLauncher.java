package com.rsomeara.factorio.modlauncher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point of the application, starts the launcher and creates the
 * interface
 *
 * @author romeara
 * @since 0.1.0
 */
public class FactorioModLauncher extends Application {

	/**
	 * Called by external commands to start the Java program
	 *
	 * @param args
	 *            Any arguments provided on the command line when starting the
	 *            application
	 * @since 0.1.0
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/launcherUI.fxml"));

		Scene scene = new Scene(root);

		stage.setTitle("Factorio Mod Launcher");
		stage.setScene(scene);
		stage.show();
	}

}
