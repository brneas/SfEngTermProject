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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManageEmployees {

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
		empName.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
		deptName.setCellValueFactory(new PropertyValueFactory<Employee, String>("Department"));
		empRank.setCellValueFactory(new PropertyValueFactory<Employee, String>("Rank"));
		empHPay.setCellValueFactory(new PropertyValueFactory<Employee, String>("HPay"));
		empWPay.setCellValueFactory(new PropertyValueFactory<Employee, String>("WPay"));
		employeeData.add(new Employee("John Smith", "Department 1", "Employee", "2.25", "50.00"));
		employeeData.add(new Employee("Jane Doe", "Department 3", "Employee", "9.95", "497.35"));
		employeeData.add(new Employee("Shane Queen", "Department 1", "Manager", "20.00", "800.00"));
		employeeData.add(new Employee("Tylar Rhoades", "Department 2", "Manager", "10.00", "400.00"));
		employeeData.add(new Employee("Oles Uri", "Department 1", "Employee", "1.50", "108.00"));
		employeeData.add(new Employee("Branislava Hesiodos", "Department 1", "Employee", "3.50", "70.50"));
		employeeData.add(new Employee("Renee Suzy", "Department 3", "Manager", "15.00", "600.00"));
		employeeData.add(new Employee("Merlyn Lavonne", "Department 1", "Employee", "2.50", "25.50"));
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
