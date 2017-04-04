package sfproj.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManageEmployees {

	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	
	private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
	
	@FXML Button addEmp;
	@FXML TableView<Employee> empList;
	@FXML Label empLbl;
	@FXML TableColumn<Employee, String> empName;
	@FXML TableColumn<Employee, String> deptName;
	@FXML TableColumn<Employee, String> empRank;
	@FXML TableColumn<Employee, String> empHPay;
	@FXML TableColumn<Employee, String> empWPay;
	@FXML
    private void initialize() {
		try {
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("RequestEmployee");
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/employeeList.txt")));
			String line;
			while((line = reader.readLine()) != null){
				String[] empLines = ((String) line).split("\\|");
				employeeData.add(new Employee(empLines[0], empLines[1], "Employee", empLines[2], "100"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		empName.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
		deptName.setCellValueFactory(new PropertyValueFactory<Employee, String>("Department"));
		empRank.setCellValueFactory(new PropertyValueFactory<Employee, String>("Rank"));
		empHPay.setCellValueFactory(new PropertyValueFactory<Employee, String>("HPay"));
		empWPay.setCellValueFactory(new PropertyValueFactory<Employee, String>("WPay"));
		empList.setItems(employeeData);
	}
	
	private Stage manageEmployeesStage;
	
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
