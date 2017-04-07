package sfproj.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ManageEmployeeTimes {
	
	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	
	@FXML Label manEmpTime;
	@FXML Button addClock;
	@FXML TableView<String> empTimes;
	@FXML TableColumn<Employee, String> clockIn;
	@FXML TableColumn<Employee, String> clockOut;
	@FXML TableColumn<Employee, String> clockDate;
	@FXML TableColumn<Employee, String> clockPay;
	@FXML TableColumn<Employee, String> tHours;
	
	private Stage manageEmpTimesStage;
	
	public ManageEmployeeTimes(Stage manageEmpTimesStage){
		this.manageEmpTimesStage = manageEmpTimesStage;
	}
	
	public void addClock(){
		
	}
	
}
