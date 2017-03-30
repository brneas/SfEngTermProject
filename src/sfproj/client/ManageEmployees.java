package sfproj.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageEmployees {

	@FXML
	Button addEmp;
	@FXML
	TableView<String> empList;
	@FXML
	Label empLbl;
	
	private Stage manageEmployeesStage;
	final ObservableList<String> listItems = FXCollections.observableArrayList("Add Items here"); 
	
	public ManageEmployees(Stage manageEmployeesStage){
		this.manageEmployeesStage = manageEmployeesStage;
	}
	
	public void addEmployee(){
		try {
			AddEmployee addEmp  = new AddEmployee(manageEmployeesStage);
			Stage manageEmployeesStage = new Stage();
			FXMLLoader fxml = new FXMLLoader(AddEmployee.class.getResource("AddEmployeeGui.fxml"));
			fxml.setController(addEmp);
			manageEmployeesStage.setScene(new Scene(fxml.load()));
			manageEmployeesStage.setTitle("Add Employee");
			manageEmployeesStage.show();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
	
}
