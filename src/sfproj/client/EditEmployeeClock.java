package sfproj.client;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
		System.out.println(clockDateSet);
		clockInTxt.setText(clockInSet);
		clockOutTxt.setText(clockOutSet);
	}
	
	private Stage editEmployeeClock;
	
	public EditEmployeeClock(Stage editEmployeeClock){
		this.editEmployeeClock = editEmployeeClock;
	}
	
	public void setEdit(String id, String clockDate, String clockIn, String clockOut, String type){
		eId = id;
		clockDateSet = clockDate;
		clockInSet = clockIn;
		clockOutSet = clockOut;
		clockTypeSet = type;
	}
}