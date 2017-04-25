package sfproj.client;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Retire {

	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	private String eId;
	
	@FXML Button submitBtn;
	@FXML TextField vacaHrsTxt;
	@FXML Label hoursLbl;
	
	private Stage retireStage;
	
	public Retire(Stage retireStage){
		this.retireStage = retireStage;
	} 
	
	public void retireSend(){
		try {
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("Retire|" + eId + "|" + vacaHrsTxt.getText());
			Stage stage = (Stage) submitBtn.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getEId(String id){
		eId = id;
	}
}
