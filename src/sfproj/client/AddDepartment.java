package sfproj.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddDepartment {
	@FXML
	Button addDept;
	@FXML
	Label departmentName;
	@FXML
	TextField deptName;
	
	private Stage addDepartmentsStage;
	
	public AddDepartment(Stage addDepartmentsStage){
		this.addDepartmentsStage = addDepartmentsStage;
	}
	
	public void addDepartment(){
		System.out.println(deptName.getText() + " added.");
		addDepartmentsStage.close();
		//This will throw errors and not close.
	}
}
