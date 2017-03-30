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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEmployee {
	@FXML
	Button addEmp;
	@FXML
	Label employeeNameLbl;
	@FXML
	TextField employeeName;
	@FXML
	ComboBox<?> employeeDept;
	
	private Stage addEmployeesStage;
	
	public AddEmployee(Stage addEmployeesStage){
		this.addEmployeesStage = addEmployeesStage;
	}
	
	public void addEmployee(){
		System.out.println(employeeName.getText() + " added.");
		addEmployeesStage.close();
		//This will throw errors and not close.
	}
}
