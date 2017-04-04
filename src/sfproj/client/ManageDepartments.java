package sfproj.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	
	private ObservableList<Department> departmentData = FXCollections.observableArrayList();
	
	@FXML Button addDept;
	@FXML TableView<Department> deptList;
	@FXML Label deptLabel;
	@FXML TableColumn<Department, String> nameCol;
	@FXML TableColumn<Department, String> empCol;
	@FXML
    private void initialize() {
		try {
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("RequestDepartment");
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/departmentList.txt")));
			String line;
			while((line = reader.readLine()) != null){
				String[] deptLines = ((String) line).split("\\|");
				departmentData.add(new Department(deptLines[0], deptLines[1]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//In this catch block just read from the departmentList.txt file. It should still have data
			e.printStackTrace();
		}
		nameCol.setCellValueFactory(new PropertyValueFactory<Department, String>("DepartmentName"));
		empCol.setCellValueFactory(new PropertyValueFactory<Department, String>("EmployeeNum"));
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
