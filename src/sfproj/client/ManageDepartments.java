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

public class ManageDepartments {

	private ObservableList<Department> departmentData = FXCollections.observableArrayList();
	
	@FXML Button addDept;
	@FXML TableView<Department> deptList;
	@FXML Label deptLabel;
	@FXML TableColumn<Department, String> nameCol;
	@FXML TableColumn<Department, String> empCol;
	@FXML
    private void initialize() {
		nameCol.setCellValueFactory(new PropertyValueFactory<Department, String>("DepartmentName"));
		empCol.setCellValueFactory(new PropertyValueFactory<Department, String>("EmployeeNum"));
		departmentData.add(new Department("Department 1", "15"));
		departmentData.add(new Department("Department 2", "7"));
		departmentData.add(new Department("Department 3", "45"));
		deptList.setItems(departmentData);
	}
	
	private Stage manageDepartmentsStage;
	
	public ManageDepartments(Stage manageDepartmentsStage){
		this.manageDepartmentsStage = manageDepartmentsStage;
	}
	
	public void addDepartment(){
		try {
			AddDepartment addDept  = new AddDepartment(manageDepartmentsStage);
			Stage addDepartmentStage = new Stage();
			FXMLLoader fxml = new FXMLLoader(AddDepartment.class.getResource("AddDepartmentGui.fxml"));
			fxml.setController(addDept);
			addDepartmentStage.setScene(new Scene(fxml.load()));
			addDepartmentStage.setTitle("Add Department");
			addDepartmentStage.show();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
	
}
