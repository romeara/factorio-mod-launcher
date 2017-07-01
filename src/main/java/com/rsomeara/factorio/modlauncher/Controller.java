package com.rsomeara.factorio.modlauncher;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button deleteButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        // watch for focus on the nameField
        nameField.focusedProperty().addListener(this::textBoxFocusLost);
        modPackTree.setShowRoot(false);

        // Load the current mod pack name, available packs, and mod list
        try {
            updateCurrentModPackFields();

            updateModPackListFields();
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
     * this button will clone the current pack and create a new one with a user
     * entered name
     */
    @FXML
    public void newButtonClick() {
        String newName = nameField.getText() + " (Copy)";

        try {
            String name = Services.getModPackService().create(newName);

            Services.getModPackService().setActive(name);

            updateCurrentModPackFields();
            updateModPackListFields();
        } catch (IOException e) {
            throw new RuntimeException("Error creating modpack", e);
        }
    }

    /**
     * this will change which mod pack is selected, will update the values in
     * modsList and nameField
     */
    @FXML
    public void modPackTreeClick() {
        String selectedName = modPackTree.getSelectionModel().getSelectedItem().getValue();

        try {
            Services.getModPackService().setActive(selectedName);

            updateCurrentModPackFields();
        } catch (IOException e) {
            throw new RuntimeException("Error switching modpack", e);
        }
    }

    /**
     * this will trigger the name change when the enter key is hit
     */
    @FXML
    public void onEnter(ActionEvent ae) {
        String newName = nameField.getText();

        try {
            updateCurrentModPackName(newName);

            updateCurrentModPackFields();
            updateModPackListFields();
        } catch (IOException e) {
            throw new RuntimeException("Error updating modpack name", e);
        }
    }

    private void updateCurrentModPackFields() throws IOException {
        // Mod pack name
        String currentModPackName = Services.getModPackService().getActive();

        // Current mod list
        List<String> enabledMods = Services.getFactorioService().getEnabledMods();

        nameField.setText(currentModPackName);

        if (modsList.getItems() == null) {
            modsList.setItems(FXCollections.observableArrayList());
        }

        modsList.getItems().clear();
        modsList.getItems().addAll(enabledMods);
    }

    private void updateModPackListFields() throws IOException {
        // Mod pack list
        List<TreeItem<String>> modPackNames = Services.getModPackService().getAll().stream()
                .map(input -> new TreeItem<>(input)).collect(Collectors.toList());

        if (modPackTree.getRoot() == null) {
            TreeItem<String> root = new TreeItem<>("ModPacks");
            root.setExpanded(true);

            modPackTree.setRoot(root);
        }

        modPackTree.getRoot().getChildren().clear();

        modPackNames.stream().forEach(input -> modPackTree.getRoot().getChildren().add(input));

    }

    private void updateCurrentModPackName(String newName) throws IOException {
        // Get current name
        String currentModPackName = Services.getModPackService().getActive();

        if (!Objects.equals(currentModPackName, newName)) {
            // Create and switch to new modpack with name
            String name = Services.getModPackService().create(newName);
            Services.getModPackService().setActive(name);

            // Delete old mod pack
            Services.getModPackService().delete(currentModPackName);
        }
    }

    /**
     * this will trigger the name change when the name box it loses focus
     */
    private void textBoxFocusLost(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
            Boolean newPropertyValue) {
        if (!newPropertyValue) {
            String newName = nameField.getText();

            try {
                updateCurrentModPackName(newName);

                updateCurrentModPackFields();
                updateModPackListFields();
            } catch (IOException e) {
                throw new RuntimeException("Error updating modpack name", e);
            }
        }
    }

    /**
     * will delete the selected mod pack when clicked
     */
    public void deleteButtonClicked() {
        System.out.println("you clicked delete");
    }

    /**
     * this will show any errors that come up. otherwise there won't be text so
     * it won't show anything
     */
    public void errorOccured(String errorMsg) {
        // write to field errorLabel
    }

}
