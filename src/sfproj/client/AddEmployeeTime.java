package sfproj.client;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEmployeeTime {

	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	
	@FXML DatePicker day;
	@FXML Label dateLabel;
	@FXML Label hoursLbl;
	@FXML Label typeLbl;
	@FXML TextField hoursTxt;
	@FXML ComboBox<String> typeBox;
	@FXML Button addBlockBtn;
	@FXML
	private void initialize(){
		ObservableList<String> clockTypes = FXCollections.observableArrayList(); 
		clockTypes.add("Regular");
		clockTypes.add("Call-Back");
		clockTypes.add("Holiday");
		clockTypes.add("Vacation");
		clockTypes.add("Jury Duty");
		clockTypes.add("Bereavement");
		typeBox.setItems(clockTypes);
	}
	
	private Stage addEmployeeTimeStage;
	
	public AddEmployeeTime(Stage addEmployeeTimeStage) throws IOException{
		this.addEmployeeTimeStage = addEmployeeTimeStage;
		cnh = new ClientNetHandler(serverIPA, port);
	}
	
	public void addClock(){
		try {
			cnh.sendToServer("AddClock|" + day.getValue() + "|" + hoursTxt.getText() + "|" + typeBox.getValue());
			Stage stage = (Stage) addBlockBtn.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
