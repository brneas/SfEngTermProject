package sfproj.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
	private String eId;
	private String clockDateSet;
	private String clockInSet;
	private String clockOutSet;
	private String clockTypeSet;
	
	private ObservableList<timeList> employeeTimes = FXCollections.observableArrayList();
	
	@FXML Label manEmpTime;
	@FXML Button addClock;
	@FXML TableView<timeList> empTimes;
	@FXML TableColumn<timeList, String> clockIn;
	@FXML TableColumn<timeList, String> clockOut;
	@FXML TableColumn<timeList, String> clockDate;
	@FXML TableColumn<timeList, String> clockPay;
	@FXML TableColumn<timeList, String> tHours;
	@FXML TableColumn<timeList, String> callBack;
	@FXML Button editClockBtn;
	@FXML
	private void initialize() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/timeList.txt")));
			String line;
			String callback = "False";
			while((line = reader.readLine()) != null){
				String[] timeLines = ((String) line).split("\\|");
				if(timeLines[5].equals("1")){
					callback = "True";
				}
				else{
					callback = "False";
				}
				employeeTimes.add(new timeList(timeLines[0], timeLines[1], timeLines[2], timeLines[3], timeLines[4], callback));
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
		callBack.setCellValueFactory(new PropertyValueFactory<timeList, String>("callBack"));
		empTimes.setItems(employeeTimes);
	}
	
	private Stage manageEmpTimesStage;
	
	public ManageEmployeeTimes(Stage manageEmpTimesStage){
		this.manageEmpTimesStage = manageEmpTimesStage;
	}
	
	public void addClock(){
		try {
			AddEmployeeTime addEmp  = new AddEmployeeTime(manageEmpTimesStage);
			Stage addTimeStage = new Stage();
			FXMLLoader fxml = new FXMLLoader(AddEmployeeTime.class.getResource("AddEmployeeTimeGui.fxml"));
			fxml.setController(addEmp);
			addTimeStage.setScene(new Scene(fxml.load()));
			addTimeStage.setTitle("Add Clock");
			addTimeStage.setResizable(false);
			addEmp.setId(eId);
			addTimeStage.show();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
	
	public void editClock(){
		try {
			EditEmployeeClock empClock  = new EditEmployeeClock(manageEmpTimesStage);
			empClock.setEdit(eId, empTimes.getSelectionModel().getSelectedItem().getDate(), empTimes.getSelectionModel().getSelectedItem().getClockIn(), empTimes.getSelectionModel().getSelectedItem().getClockOut(), "Type");
			System.out.println("Writes" + eId + " | " + empTimes.getSelectionModel().getSelectedItem().getDate() + " | " + empTimes.getSelectionModel().getSelectedItem().getClockIn() + " | " + empTimes.getSelectionModel().getSelectedItem().getClockIn() + " | " + "Type");
			Stage editEmployeeStage = new Stage();
			FXMLLoader fxml = new FXMLLoader(EditEmployeeClock.class.getResource("EditEmployeeClockGui.fxml"));
			fxml.setController(empClock);
			editEmployeeStage.setScene(new Scene(fxml.load()));
			editEmployeeStage.setTitle("Edit Clock");
			editEmployeeStage.setResizable(false);
			editEmployeeStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setId(String id){
		eId = id;
	}
	
	public void setEdit(String id, String clockDate, String clockIn, String clockOut, String type){
		eId = id;
		clockDateSet = clockDate;
		clockInSet = clockIn;
		clockOutSet = clockOut;
		clockTypeSet = type;
	}
	
	public void enableEditClock(){
		editClockBtn.setDisable(false);;
	}
	
}
