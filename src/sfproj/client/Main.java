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
		LoginGui lg = new LoginGui(primaryStage);
		fxml.setController(lg);
		primaryStage.setScene(new Scene(fxml.load()));
		lg.createHandlers();
		primaryStage.show();
	}
}
