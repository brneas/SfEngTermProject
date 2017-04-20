package sfproj.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
	@FXML Button manageTimes;
	@FXML TableView<Employee> empList;
	@FXML Label empLbl;
	@FXML TableColumn<Employee, String> empId;
	@FXML TableColumn<Employee, String> empName;
	@FXML TableColumn<Employee, String> deptName;
	@FXML TableColumn<Employee, String> empRank;
	@FXML TableColumn<Employee, String> empHPay;
	@FXML TableColumn<Employee, String> empWPay;
	@FXML
    private void initialize() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/employeeList.txt")));
			String line, lineAgain, lineAgainAgain, lineAgainAgainAgain, rankName = "", deptName = "";//Again Again Again Again Again Again Again Again
			Double totalPay = 0.00;
			Date mondayD, sundayD, clock;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			DecimalFormat dcf = new DecimalFormat("#.00");
			Calendar monday = GregorianCalendar.getInstance(Locale.US);
			Calendar sunday = GregorianCalendar.getInstance(Locale.US);
			monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			sunday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			sunday.add(Calendar.DATE, 6);
			mondayD = Date.valueOf(df.format(monday.getTime()).toString());
			sundayD = Date.valueOf(df.format(sunday.getTime()).toString());
			while((line = reader.readLine()) != null){
				String[] empLines = ((String) line).split("\\|");
				BufferedReader readerAgain = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/departmentList.txt")));
				while((lineAgain = readerAgain.readLine()) != null){
					String[] deptLines = ((String) lineAgain).split("\\|");
					if(deptLines[0].equals(empLines[2])){
						deptName = deptLines[1];
					}
				}
				BufferedReader readerAgainAgain = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/rankList.txt")));
				while((lineAgainAgain = readerAgainAgain.readLine()) != null){
					String[] rankLines = ((String) lineAgainAgain).split("\\|");
					if(rankLines[0].equals(empLines[4])){
						rankName = rankLines[1];
					}
				}
				BufferedReader readerAgainAgainAgain = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/fullTimeList.txt")));
				while((lineAgainAgainAgain = readerAgainAgainAgain.readLine()) != null){
					String[] payLines = ((String) lineAgainAgainAgain).split("\\|");
					if(payLines[0].equals(empLines[0])){
						clock = Date.valueOf(payLines[3]);
						if(!mondayD.after(clock) && !sundayD.before(clock)){
							totalPay = totalPay + Double.parseDouble(payLines[5]);
						}
						//totalPay = payLines[1];
					}
				}
				employeeData.add(new Employee(empLines[0], empLines[1], deptName, rankName, empLines[3], dcf.format(totalPay)));
				totalPay = 0.00;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		empId.setCellValueFactory(new PropertyValueFactory<Employee, String>("ID"));
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
		try {
			cnh = new ClientNetHandler(serverIPA, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addEmployee(){
		try {
			cnh.sendToServer("RequestDepartment");
			cnh.sendToServer("RequestRank");
			AddEmployee addEmp  = new AddEmployee(manageEmployeesStage);
			Stage manageEmployeesStage = new Stage();
			FXMLLoader fxml = new FXMLLoader(AddEmployee.class.getResource("AddEmployeeGui.fxml"));
			fxml.setController(addEmp);
			manageEmployeesStage.setScene(new Scene(fxml.load()));
			manageEmployeesStage.setTitle("Add Employee");
			manageEmployeesStage.setResizable(false);
			manageEmployeesStage.show();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
	
	public void manageTimes(){
		try {
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("RequestTimes|"+empList.getSelectionModel().getSelectedItem().getID());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ManageEmployeeTimes manTimes  = new ManageEmployeeTimes(manageEmployeesStage);
			Stage manageEmpTimesStage = new Stage();
			FXMLLoader fxml = new FXMLLoader(ManageEmployeeTimes.class.getResource("ManageTimesGUI.fxml"));
			fxml.setController(manTimes);
			manageEmpTimesStage.setScene(new Scene(fxml.load()));
			manageEmpTimesStage.setTitle("Manage Times");
			manageEmpTimesStage.setResizable(false);
			manTimes.setId(empList.getSelectionModel().getSelectedItem().getID());
			manageEmpTimesStage.show();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
	
}
