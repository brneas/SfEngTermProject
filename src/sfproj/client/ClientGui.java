package sfproj.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
	
	private Stage clientStage;

	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 800;

	public ClientGui(Stage loginStage) throws IOException {
		cnh = new ClientNetHandler(serverIPA, port);
	}
	
	public void manageDepartments(){
		try {

			ManageDepartments manDepts  = new ManageDepartments(clientStage);
			Stage ManageDepartments = new Stage();
			FXMLLoader fxml = new FXMLLoader(ManageDepartments.class.getResource("ManageDepartmentsGui.fxml"));
			fxml.setController(manDepts);
			ManageDepartments.setScene(new Scene(fxml.load()));
			ManageDepartments.show();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
}
