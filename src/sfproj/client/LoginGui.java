package sfproj.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginGui {

	@FXML
	TextField usernameField;
	@FXML
	Button login;

	private Stage loginStage;

	public LoginGui(Stage loginStage) {
		this.loginStage = loginStage;

	}

	void createHandlers() {
		login.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				login();
			}

		});
	}

	private void login() {
		try {

			ClientGui client  = new ClientGui(loginStage);
			Stage clientStage = new Stage();
			FXMLLoader fxml = new FXMLLoader(ClientGui.class.getResource("ClientGui.fxml"));
			fxml.setController(client);
			clientStage.setScene(new Scene(fxml.load()));
			clientStage.show();
			loginStage.close();
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
}
