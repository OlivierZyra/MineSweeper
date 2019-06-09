package ms;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane mainView;
	private Scene scene;

	@Override
	public void start(Stage primaryStage) {


		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");

		initView();



	}

	public static void main(String[] args) {
		launch(args);
	}


	public void initView() {
		try {
			
			// Load scene from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainView.fxml"));
			mainView = (BorderPane) loader.load();

			// Show the scene 
			scene = new Scene(mainView);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
