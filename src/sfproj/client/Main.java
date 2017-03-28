package sfproj.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage = new Stage();
		FXMLLoader fxml = new FXMLLoader(LoginGui.class.getResource("LoginGui.fxml"));
		fxml.setController(new LoginGui(primaryStage));
		primaryStage.setScene(new Scene(fxml.load()));
		primaryStage.show();
	}
}
