package ms;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import enums.EMode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ms.view.MainViewController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane mainView;
	private Scene scene;

	@Override
	public void start(Stage primaryStage) {
		
		try {
			ScoreManager.getScore(EMode.EASY);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MineSweeper");
		this.primaryStage.setResizable(false);
		
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
			
			// Gives stage reference to the controller (required for resizing the window when the game mode is changed) 
			MainViewController controller = (MainViewController) loader.getController();
			controller.setStageReference(primaryStage);
					
			// Show the scene 
			scene = new Scene(mainView);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	



}
