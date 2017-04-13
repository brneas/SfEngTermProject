package sfproj.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class AddEmployee {
	
	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	
	@FXML Button addEmp;
	@FXML Label employeeNameLbl;
	@FXML Label deptLbl;
	@FXML Label pPHLbl;
	@FXML TextField employeeName;
	@FXML TextField payPerHour;
	@FXML ComboBox<String> employeeDept;
	@FXML ComboBox<String> rankBox;
	@FXML Label rankLbl;
	@FXML
	private void initialize(){
		ObservableList<String> empDept = FXCollections.observableArrayList(); 
		ObservableList<String> empRank = FXCollections.observableArrayList(); 
		try {
			String line;
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/departmentList.txt")));
			while((line = reader.readLine()) != null){
				String[] deptLines = ((String) line).split("\\|");
				empDept.add(deptLines[1]);
			}
			reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/rankList.txt")));
			while((line = reader.readLine()) != null){
				String[] deptLines = ((String) line).split("\\|");
				empRank.add(deptLines[1]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		employeeDept.setItems(empDept);
		rankBox.setItems(empRank);
	}
	
	private Stage addEmployeesStage;
	
	public AddEmployee(Stage addEmployeesStage) throws IOException{
		this.addEmployeesStage = addEmployeesStage;
		cnh = new ClientNetHandler(serverIPA, port);
	}
	
	public void addEmployee(){
		try{
			String line;
			BufferedReader reader;
			String deptId = "";
			String rankID = "";
			reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/departmentList.txt")));
			while((line = reader.readLine()) != null){
				String[] deptLines = ((String) line).split("\\|");
				if(deptLines[1].equals(employeeDept.getValue())){
					deptId = deptLines[0];
				}
			}
			reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/rankList.txt")));
			while((line = reader.readLine()) != null){
				String[] rankLines = ((String) line).split("\\|");
				if(rankLines[1].equals(rankBox.getValue())){
					rankID = rankLines[0];
				}
			}
			cnh.sendToServer("AddEmployee|"+ employeeName.getText() + "|" + deptId + "|" + payPerHour.getText() + "|" + rankID);
			//addEmployeesStage.close();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
}
