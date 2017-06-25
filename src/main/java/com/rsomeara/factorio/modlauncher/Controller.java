package com.rsomeara.factorio.modlauncher;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

public class Controller {

	@FXML
	private Button launchButton;
	
	@FXML
	private Button newButton;
	
	@FXML
	private TreeView modPackTree;
	
	@FXML
	private TextField nameField;
	
	@FXML
	private ListView modsList;
	
	//on event methods
	
	
    /**
     * this button will close the launcher and start Factorio
     */
	@FXML
	public void launchButtonClick(){

		System.out.println("you clicked launch");
	}
	
	
	/**
	 * this button will clone the current pack and create a new one with a user entered name
	 */
	@FXML
	public void newButtonClick(){
		System.out.println("you clicked new");
	}
	
	
	/**
	 *this will change which mod pack is selected,
	 *will update the values in modsList and nameField
	 */
	@FXML
	public void modPackTreeClick(){
		System.out.println("you clicked on the tree");
	}

}

