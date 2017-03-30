package sfproj.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ClientGui {

	@FXML
	MenuItem genAccRep;
	@FXML
	MenuItem manEmpTimes;
	@FXML
	MenuItem manEmps;
	@FXML
	MenuItem manDepts;
	@FXML
	Button clockIn;
	@FXML
	Button clockOut;
	@FXML
	Label introMessage;
	
	private Stage clientStage;

	public ClientGui(Stage loginStage) throws IOException {
		this.clientStage = loginStage;
		//cnh = new ClientNetHandler(serverIPA, port);
	}
	
	public void manageDepartments(){
		try {

			ManageDepartments manDepts  = new ManageDepartments(clientStage);
			Stage ManageDepartments = new Stage();
			FXMLLoader fxml = new FXMLLoader(ManageDepartments.class.getResource("ManageDepartmentsGui.fxml"));
			fxml.setController(manDepts);
			ManageDepartments.setScene(new Scene(fxml.load()));
			ManageDepartments.setTitle("Manage Departments");
			ManageDepartments.show();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
	
	public void manageEmployees(){
		try {

			ManageEmployees manEmps  = new ManageEmployees(clientStage);
			Stage ManageEmployees = new Stage();
			FXMLLoader fxml = new FXMLLoader(ManageEmployees.class.getResource("ManageEmployeesGui.fxml"));
			fxml.setController(manEmps);
			ManageEmployees.setScene(new Scene(fxml.load()));
			ManageEmployees.setTitle("Manage Employees");
			ManageEmployees.show();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
}
