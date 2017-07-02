package com.rsomeara.factorio.modlauncher;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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

        try {
            // Check that the current mod pack is unaltered
            List<String> savedModList = Services.getModPackService().getCurrentSavedModList();
            List<String> activeModList = Services.getFactorioService().getEnabledMods();

            if (savedModList.size() != activeModList.size() || !savedModList.containsAll(activeModList)) {
                handleCurrentModsMismatch();
            }

            // Load the current mod pack name, available packs, and mod list
            updateCurrentModPackFields();

            updateModPackListFields();
        } catch (

                IOException e) {
            throw new RuntimeException("Error loading data", e);
        }
    }

    /**
     * this button will close the launcher and start Factorio
     */
    @FXML
    public void launchButtonClick() {
        clearError();

        try {
            Services.getFactorioService().launch();
        } catch (Exception e) {
            errorOccured("Error launching Factorio: " + e.getMessage());
            throw new RuntimeException("Error launching Factorio", e);
        }
    }

    /**
     * this button will clone the current pack and create a new one with a user
     * entered name
     */
    @FXML
    public void newButtonClick() {
        clearError();

        try {
            createNewModpack(getUnusedName(nameField.getText()));
        } catch (Exception e) {
            errorOccured("Error naming modpack: " + e.getMessage());
            throw new RuntimeException("Error naming modpack", e);
        }
    }

    /**
     * this will change which mod pack is selected, will update the values in
     * modsList and nameField
     */
    @FXML
    public void modPackTreeClick() {
        clearError();

        if (modPackTree.getSelectionModel().getSelectedItem() != null) {
            String selectedName = modPackTree.getSelectionModel().getSelectedItem().getValue();

            try {
                Services.getModPackService().setActive(selectedName);

                updateCurrentModPackFields();
            } catch (Exception e) {
                errorOccured("Error switching modpack: " + e.getMessage());
                throw new RuntimeException("Error switching modpack", e);
            }
        }
    }

    /**
     * this will trigger the name change when the enter key is hit
     */
    @FXML
    public void onEnter(ActionEvent ae) {
        clearError();

        String newName = nameField.getText();

        try {
            updateCurrentModPackName(newName);

            updateCurrentModPackFields();
            updateModPackListFields();
        } catch (Exception e) {
            errorOccured("Error updating modpack name: " + e.getMessage());
            throw new RuntimeException("Error updating modpack name", e);
        }
    }

    /**
     * will delete the selected mod pack when clicked
     */
    public void deleteButtonClicked() {
        clearError();

        try {
            String currentModPackName = Services.getModPackService().getActive();

            // Find next on list
            Optional<String> next = Services.getModPackService().getAll().stream()
                    .filter(input -> !Objects.equals(input, currentModPackName)).findFirst();

            if (!next.isPresent()) {
                errorOccured("Cannot delete last mod pack");
            } else {
                // Update current pack
                Services.getModPackService().setActive(next.get());
                updateCurrentModPackFields();

                // Remove target pack
                Services.getModPackService().delete(currentModPackName);
                updateModPackListFields();
            }
        } catch (Exception e) {
            errorOccured("Error deleting modpack: " + e.getMessage());
            throw new RuntimeException("Error deleting modpack", e);
        }

    }

    /**
     * this will show any errors that come up. otherwise there won't be text so
     * it won't show anything
     */
    public void errorOccured(String errorMsg) {
        // write to field errorLabel
        errorLabel.setText(errorMsg);
    }

    public void clearError() {
        errorLabel.setText("");
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

    private void handleCurrentModsMismatch() throws IOException {
        String activeModPack = Services.getModPackService().getActive();

        // Open a dialog box linked into event calls (update current, create new from, discard changes)
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Mods pack changed");
        alert.setHeaderText(
                "The mods in " + activeModPack + " were changed in factorio. Would you like to save the changes?");

        ButtonType buttonTypeSave = new ButtonType("Save", ButtonData.LEFT);
        ButtonType buttonTypeSaveAs = new ButtonType("Save As...", ButtonData.LEFT);
        ButtonType buttonTypeCancel = new ButtonType("Discard Changes", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeSaveAs, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeSave) {
            // ... user chose "save"
            Services.getModPackService().updateActiveMods();
        } else if (result.get() == buttonTypeSaveAs) {
            // ... user chose "save as"
            createNewModpack(getUnusedName(activeModPack));
        } else {
            // ... user chose CANCEL or closed the dialog
            Services.getModPackService().restoreActiveMods();
        }
    }

    private String getUnusedName(String initialName) throws IOException {
        List<String> usedNames = Services.getModPackService().getAll();

        String name = initialName;

        while (usedNames.contains(name)) {
            name = name + " (Copy)";
        }

        return name;
    }

    private void createNewModpack(String newName) {
        try {
            String name = Services.getModPackService().create(newName);

            Services.getModPackService().setActive(name);

            updateCurrentModPackFields();
            updateModPackListFields();
        } catch (Exception e) {
            errorOccured("Error creating modpack" + e.getMessage());
            throw new RuntimeException("Error creating modpack", e);
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

}
