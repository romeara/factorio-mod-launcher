package com.rsomeara.factorio.modlauncher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.rsomeara.factorio.modlauncher.model.Mod;
import com.rsomeara.factorio.modlauncher.model.ModList;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class Controller {

    @FXML
    private Button launchButton;

    @FXML
    private Button newButton;

    @FXML
    private TreeView<String> modPackTree;

    @FXML
    private TextField nameField;

    @FXML
    private ListView<String> modsList;

    @FXML
    private void initialize() {
        //watch for focus on the nameField
        nameField.focusedProperty().addListener(this::textBoxFocusLost);
        
        // Load the current mod pack name, available packs, and mod list

        try {
            // Mod pack name
            Properties props = new Properties();
            props.load(Files.newBufferedReader(Services.getFilePathService().getPropertiesFile()));
            String currentEncoded = props.getProperty("current.modpack");
            String currentModPackName = new String(Base64.getDecoder().decode(currentEncoded));

            nameField.setText(currentModPackName);

            // Current mod list
            ModList list = ModList.fromFile(Services.getFilePathService().getFactorioModList());
            List<String> enabledMods = list.getMods().stream()
                    .filter(Mod::isEnabled)
                    .map(Mod::getName)
                    .collect(Collectors.toList());

            modsList.setItems(FXCollections.observableArrayList(enabledMods));

            // Mod pack list
            List<TreeItem<String>> modPackNames = Files.list(Services.getFilePathService().getModPacksDirectory())
                    .map(Path::getFileName)
                    .map(Object::toString)
                    .filter(input -> input.endsWith(".json"))
                    .map(input -> input.substring(0, input.length() - 5))
                    .map(input -> Base64.getDecoder().decode(input))
                    .map(String::new)
                    .map(input -> new TreeItem<>(input))
                    .collect(Collectors.toList());

            TreeItem<String> root = new TreeItem<>("ModPacks");
            root.setExpanded(true);

            modPackNames.stream()
            .forEach(input -> root.getChildren().add(input));

            modPackTree.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException("Error loading data", e);
        }
    }

    /**
     * this button will close the launcher and start Factorio
     */
    @FXML
    public void launchButtonClick() {
        System.out.println("you clicked launch");
    }

    /**
     * this button will clone the current pack and create a new one with a user entered name
     */
    @FXML
    public void newButtonClick() {
        System.out.println("you clicked new");
    }

    /**
     * this will change which mod pack is selected, will update the values in modsList and nameField
     */
    @FXML
    public void modPackTreeClick() {
        System.out.println("you clicked on the tree");
    }
    
    /**
     * this will trigger the name change when the enter key is hit
     */  
    @FXML
    public void onEnter(ActionEvent ae){
        System.out.println("you pressed enter");
    }
    
    /**
     * this will trigger the name change when the name box it loses focus
     */
    private void textBoxFocusLost(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
        if (!newPropertyValue){
            System.out.println("you removed focus from the name box");
        }
    }

}
