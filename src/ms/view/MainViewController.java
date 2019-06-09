/////////////////////////////////////////////////////////////////////////////////////////////////
package ms.view;
/////////////////////////////////////////////////////////////////////////////////////////////////
import java.net.URL;
import java.util.ResourceBundle;
import enums.EMode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
/////////////////////////////////////////////////////////////////////////////////////////////////
public class MainViewController implements Initializable {
	/////////////////////////////////////////////////////////////////////////////////////////////

	private final int CEL_SIZE = 32;
	
	@FXML private MenuItem newEasyButton;
	@FXML private MenuItem newNormButton;
	@FXML private MenuItem newHardButton;
	@FXML private MenuItem quitButton;

	@FXML private Button startButton;
	@FXML private Label timerLabel;
	@FXML private Label mineLabel;

	@FXML private BorderPane gameGridContainer;
	@FXML private BorderPane rootNode;
	
	private GridPane gameGrid;
	private Stage stageReference = null;
	
	private EMode mode;
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initGameGrid(EMode.EASY);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	public void initGameGrid(EMode mode) {
		
		// Assign difficulty mode
		this.mode = mode;
		
		// Create and the GridPane node 
		gameGrid = new GridPane();
		gameGrid.setGridLinesVisible(true);
		gameGrid.setAlignment(Pos.CENTER);

		// Initiate the GridPane with the right number of rows and columns
		for (int i = 0; i < mode.getW(); i++) {
			ColumnConstraints column = new ColumnConstraints(CEL_SIZE);
			gameGrid.getColumnConstraints().add(column);
		}
		for (int i = 0; i < mode.getH(); i++) {
			RowConstraints row = new RowConstraints(CEL_SIZE);
			gameGrid.getRowConstraints().add(row);
		}
		
		// Fill the GridPane with Cels
		for(int i = 0; i < mode.getW(); i++) {
			for(int j = 0; j < mode.getH(); j++) {
				Button l = new Cel(this, CEL_SIZE, i, j);
				gameGrid.add(l, i, j);
			}
		}
		
		// Assign the GridPane to his container in the view
		gameGridContainer.setCenter(gameGrid);

		// Resize the window
		if(stageReference != null) {
			stageReference.sizeToScene();
		}

	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	public void onNewGameEasyPressed() {
		initGameGrid(EMode.EASY);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	public void onNewGameNormPressed() {
		initGameGrid(EMode.NORMAL);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	public void onNewGameHardPressed() {
		initGameGrid(EMode.HARD);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////	
	public void setStageReference(Stage stageReference) {
		this.stageReference = stageReference;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	public void revealCel(int x, int y) {
		System.out.println("Button " + x + " - " + y + " pressed");
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
}
/////////////////////////////////////////////////////////////////////////////////////////////////