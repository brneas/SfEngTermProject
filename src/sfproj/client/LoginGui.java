package sfproj.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginGui {

	ClientNetHandler cnh;
	private final String serverIPA = "localhost";
	private final int port = 5000;
	
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
			String line;
			BufferedReader reader;
			cnh = new ClientNetHandler(serverIPA, port);
			cnh.sendToServer("Login|" + usernameField.getText());
			try {
				TimeUnit.SECONDS.sleep(3);//To set login stuff
				reader = new BufferedReader(new FileReader(new File("src/sfproj/client/dataSet/login.txt")));
				while((line = reader.readLine()) != null){
					String[] loginLine = ((String) line).split("\\|");
					if(loginLine[0].equals("Success")){
						BufferedWriter writer = writer = new BufferedWriter(new FileWriter(new File("src/sfproj/client/dataSet/user.txt")));
						writer.write(usernameField.getText());
						writer.flush();
						ClientGui client  = new ClientGui(loginStage);
						Stage clientStage = new Stage();
						FXMLLoader fxml = new FXMLLoader(ClientGui.class.getResource("ClientGui.fxml"));
						fxml.setController(client);
						clientStage.setScene(new Scene(fxml.load()));
						clientStage.show();
						loginStage.close();
					}
					else{
						JOptionPane.showMessageDialog(null, "That user does not exist.", "Login Error " + "Error", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
}
