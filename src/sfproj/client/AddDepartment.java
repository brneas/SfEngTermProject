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
	
	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 800;
	
	@FXML Button addDept;
	@FXML Label departmentName;
	@FXML TextField deptName;
	
	private Stage addDepartmentStage;
	
	public AddDepartment(Stage addDepartmentStage) throws IOException{
		this.addDepartmentStage = addDepartmentStage;
		cnh = new ClientNetHandler(serverIPA, port);
	}
	
	public void addDepartment(){
		try{
			cnh.sendToServer("Add Department: " + deptName.getText());
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
