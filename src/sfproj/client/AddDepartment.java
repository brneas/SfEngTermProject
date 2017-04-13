package sfproj.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AddDepartment {
	
	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	
	@FXML Button addDept;
	@FXML Label departmentName;
	@FXML Label typeLbl;
	@FXML TextField deptName;
	@FXML ComboBox<String> typeBox;
	@FXML
	private void initialize(){
		ObservableList<String> deptRanks = FXCollections.observableArrayList(); 
		deptRanks.add("Production");
		deptRanks.add("Indirect Production");
		typeBox.setItems(deptRanks);
	}
	private Stage addDepartmentStage;
	
	public AddDepartment(Stage addDepartmentStage) throws IOException{
		this.addDepartmentStage = addDepartmentStage;
		cnh = new ClientNetHandler(serverIPA, port);
	}
	
	public void addDepartment(){
		try{
			//cnh.sendToServer("Add Department: " + deptName.getText());
			cnh.sendToServer("AddDepartment|" + deptName.getText() + "|" + typeBox.getValue());
			addDepartmentStage.close();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
}
