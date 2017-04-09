package sfproj.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManageEmployeeTimes {
	
	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	
	private ObservableList<timeList> employeeTimes = FXCollections.observableArrayList();
	
	@FXML Label manEmpTime;
	@FXML Button addClock;
	@FXML TableView<timeList> empTimes;
	@FXML TableColumn<timeList, String> clockIn;
	@FXML TableColumn<timeList, String> clockOut;
	@FXML TableColumn<timeList, String> clockDate;
	@FXML TableColumn<timeList, String> clockPay;
	@FXML TableColumn<timeList, String> tHours;
	@FXML
	private void initialize() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/timeList.txt")));
			String line;
			while((line = reader.readLine()) != null){
				String[] empLines = ((String) line).split("\\|");
				employeeTimes.add(new timeList(empLines[0], empLines[1], empLines[2], empLines[3], empLines[4]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clockIn.setCellValueFactory(new PropertyValueFactory<timeList, String>("clockIn"));
		clockOut.setCellValueFactory(new PropertyValueFactory<timeList, String>("clockOut"));
		clockDate.setCellValueFactory(new PropertyValueFactory<timeList, String>("date"));
		clockPay.setCellValueFactory(new PropertyValueFactory<timeList, String>("hoursWorked"));
		tHours.setCellValueFactory(new PropertyValueFactory<timeList, String>("totalPay"));
		empTimes.setItems(employeeTimes);
	}
	
	private Stage manageEmpTimesStage;
	
	public ManageEmployeeTimes(Stage manageEmpTimesStage){
		this.manageEmpTimesStage = manageEmpTimesStage;
	}
	
	public void addClock(){
		
	}
	
}
