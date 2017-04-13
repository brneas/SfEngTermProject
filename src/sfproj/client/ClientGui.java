package sfproj.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ClientGui {

	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	
	@FXML MenuItem genAccRep;
	@FXML MenuItem manEmps;
	@FXML MenuItem manDepts;
	@FXML MenuButton managerTools;
	@FXML Button clockIn;
	@FXML Button clockOut;
	@FXML Label introMessage;
	@FXML
	private void initialize(){
		String line;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/login.txt")));
			while((line = reader.readLine()) != null){
				String[] loginLine = ((String) line).split("\\|");
				if(loginLine[1].equals("2")){
					managerTools.setVisible(true);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Stage clientStage;

	public ClientGui(Stage loginStage) throws IOException {
		this.clientStage = loginStage;
		cnh = new ClientNetHandler(serverIPA, port);
	}
	
	public void manageDepartments(){
		try {
			cnh.sendToServer("RequestDepartment");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			cnh.sendToServer("RequestEmployee");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public void clockIn(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/user.txt")));
			String username = reader.readLine();
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("ClockIn|"+username);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clockOut(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/user.txt")));
			String username = reader.readLine();
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("ClockOut|"+username);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
