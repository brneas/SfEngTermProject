package sfproj.client;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ClientGui {

	@FXML
	MenuItem genAccRep;
	@FXML
	MenuItem manEmpTimes;
	@FXML
	MenuItem manEmps;
	@FXML
	MenuItem manDepts;
	@FXML
	Button clockIn;
	@FXML
	Button clockOut;
	@FXML
	Label introMessage;

	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 800;

	public ClientGui(Stage loginStage) throws IOException {
		cnh = new ClientNetHandler(serverIPA, port);
	}
}
