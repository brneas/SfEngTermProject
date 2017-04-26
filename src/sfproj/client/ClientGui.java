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
import javafx.scene.control.CheckBox;
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
	@FXML Button logoutBtn;
	@FXML Label introMessage;
	@FXML CheckBox callBack;
	@FXML
	private void initialize(){
		String line;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/login.txt")));
			while((line = reader.readLine()) != null){
				String[] loginLine = ((String) line).split("\\|");
				if(Integer.parseInt(loginLine[1]) == 2){
					managerTools.setVisible(true);
					System.out.println("Manager Login");
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
			ManageDepartments.setResizable(false);
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
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("RequestEmployee");
			cnh.sendToServer("RequestFullTimes");
			cnh.sendToServer("RequestRank");
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
			ManageEmployees.setResizable(false);
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
			Boolean isCallBack = callBack.isSelected();
			String rank = "";
			if(isCallBack){
				rank = "1";
			}
			else{
				rank = "0";
			}
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/user.txt")));
			String username = reader.readLine();
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("ClockIn|"+username+"|"+rank);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clockOut(){
		try {
			Boolean isCallBack = callBack.isSelected();
			String rank = "";
			if(isCallBack){
				rank = "1";
			}
			else{
				rank = "0";
			}
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/user.txt")));
			String username = reader.readLine();
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("ClockOut|"+username+"|"+rank);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void logOut(){
		try {
			LoginGui login  = new LoginGui(clientStage);
			Stage LoginGui = new Stage();
			FXMLLoader fxml = new FXMLLoader(LoginGui.class.getResource("LoginGui.fxml"));
			fxml.setController(login);
			LoginGui.setScene(new Scene(fxml.load()));
			LoginGui.setTitle("Login");
			LoginGui.setResizable(false);
			LoginGui.show();
			Stage stage = (Stage) logoutBtn.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
