package sfproj.client;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditEmployeeClock {

	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	private String eId;
	private String clockDateSet;
	private String clockInSet;
	private String clockOutSet;
	private String clockTypeSet;
	
	@FXML Label clockInLbl;
	@FXML Label clockOutLbl;
	@FXML Label typeLbl;
	@FXML Button submit;
	@FXML TextField clockInTxt;
	@FXML TextField clockOutTxt;
	@FXML DatePicker clockInDate;
	@FXML DatePicker clockOutDate;
	@FXML ComboBox<String> clockType;
	@FXML
	public void initialize(){
		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		Time timeIn = Time.valueOf(clockInSet);
		Time timeOut = Time.valueOf(clockOutSet);
		LocalDate dateIn, dateOut;
		if(timeIn.after(timeOut)){
			dateIn = LocalDate.parse(clockDateSet).minusDays(1);
			dateOut = LocalDate.parse(clockDateSet);
		}
		else{
			dateIn = LocalDate.parse(clockDateSet);
			dateOut = LocalDate.parse(clockDateSet);
		}
		clockInDate.setValue(dateIn);
		clockOutDate.setValue(dateOut);
		//System.out.println(clockDateSet);
		clockInTxt.setText(clockInSet);
		clockOutTxt.setText(clockOutSet);
		
		ObservableList<String> clockTypes = FXCollections.observableArrayList(); 
		clockTypes.add("Regular");
		clockTypes.add("Call-Back");
		clockTypes.add("Holiday");
		clockTypes.add("Vacation");
		clockTypes.add("Jury Duty");
		clockTypes.add("Bereavement");
		clockType.setItems(clockTypes);
		clockType.getSelectionModel().select(clockTypeSet);
	}
	
	private Stage editEmployeeClock;
	
	public EditEmployeeClock(Stage editEmployeeClock){
		this.editEmployeeClock = editEmployeeClock;
	}
	
	public void editTimes(){
		try{
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("EditClock|" + eId + "|" + clockInTxt.getText() + "|" + clockOutTxt.getText() + "|" + clockInDate.getValue() + "|" + clockOutDate.getValue() + "|" + clockType.getValue());
			Stage stage = (Stage) submit.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setEdit(String id, String clockDate, String clockIn, String clockOut, String type){
		eId = id;
		clockDateSet = clockDate;
		clockInSet = clockIn;
		clockOutSet = clockOut;
		clockTypeSet = type;
	}
}