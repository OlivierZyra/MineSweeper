package ms.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MainViewController implements Initializable {

	@FXML private Button startButton;
	@FXML private Label timerLabel;
	@FXML private Label mineLabel;
	
	@FXML private GridPane gameGrid;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
