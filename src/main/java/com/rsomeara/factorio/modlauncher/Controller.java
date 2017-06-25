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
	
	public void launchButtonClick(){
		//this button will close the launcher and start factorio
		System.out.println("you clicked launch");
	}
	
	public void newButtonClick(){
		//this button will clone the current pack and create a new one with a user entered name
		System.out.println("you clicked new");
	}
	
	public void modPackTreeClick(){
		//this will change which mod pack is selected
		//will update the values in modsList and nameField
		System.out.println("you clicked on the tree");
	}

}

