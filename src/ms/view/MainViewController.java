/////////////////////////////////////////////////////////////////////////////////////////////////
package ms.view;
import java.io.IOException;
/////////////////////////////////////////////////////////////////////////////////////////////////
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import enums.EMode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import ms.ScoreManager;
import ms.ScoreRecord;
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

	private Cel[][] cels;

	private boolean initialClick = true;
	private boolean gameOver     = false;
	private int nbFlag;

	private Timeline timer = null;
	private int time = 0;

	/////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTimer();
		initGameGrid(EMode.EASY);
	}

	// INPUT EVENT METHODS //////////////////////////////////////////////////////////////////////
	public void onNewGameEasyPressed() {
		initGameGrid(EMode.EASY);
	}

	public void onNewGameNormPressed() {
		initGameGrid(EMode.NORMAL);
	}

	public void onNewGameHardPressed() {
		initGameGrid(EMode.HARD);
	}

	public void onNewGamePressed() {
		initGameGrid(mode);
	}	
	public void onQuitPressed() {
		stageReference.close();
	}
	
	// GAME LOGIC METHODS ///////////////////////////////////////////////////////////////////////
	public void initGameGrid(EMode mode) {

		nbFlag = mode.getNbMine();
		initialClick = true;
		gameOver = false;
		
		// restart the mine label if needed
		updateMineLabel();

		// restart the timer if needed
		time = 0;
		timerLabel.setText("000");
		timer.stop();
		
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

		// Fill the GridPane and array with Cels
		cels = new Cel[mode.getW()][mode.getH()];
		for(int x = 0; x < mode.getW(); x++) {
			for(int y = 0; y < mode.getH(); y++) {
				Cel cel = new Cel(this, CEL_SIZE, x, y);
				gameGrid.add(cel, x, y);
				cels[x][y] = cel;
			}
		}

		// Assign the GridPane to his container in the view
		gameGridContainer.setCenter(gameGrid);

		// Resize the window
		if(stageReference != null) {
			stageReference.sizeToScene();
		}

	}

	public void placeMines(int notHereX, int notHereY) {

		int nbMine = mode.getNbMine();

		int xx = 0;
		int yy = 0;

		for(int i = 0; i < nbMine; i++) {

			do {
				xx = (int)(Math.random()*mode.getW());
				yy = (int)(Math.random()*mode.getH());
			} 
			while(cels[xx][yy].isMine() || (xx == notHereX && yy == notHereY));

			cels[xx][yy].setMine(true);

			// Increment the nbMineAround attribute to all the cels around the mine	
			if(xx > 0) {cels[xx-1][yy].incrMineAround();}
			if(yy > 0) {cels[xx][yy-1].incrMineAround();}
			if(xx < mode.getW()-1) {cels[xx+1][yy].incrMineAround();}
			if(yy < mode.getH()-1) {cels[xx][yy+1].incrMineAround();}
			if(xx > 0 && yy > 0)   {cels[xx-1][yy-1].incrMineAround();}
			if(xx > 0 && yy < mode.getH()-1 ) {cels[xx-1][yy+1].incrMineAround();}
			if(xx < mode.getW()-1 && yy > 0)  {cels[xx+1][yy-1].incrMineAround();}
			if(xx < mode.getW()-1 && yy < mode.getH()-1 ) {cels[xx+1][yy+1].incrMineAround();}

		}

	}

	public void revealCel(Cel cel) {

		if(!gameOver) {

			int xx = cel.getxPos();
			int yy = cel.getyPos();

			if(initialClick) {
				placeMines(xx, yy);
				timer.play();
				initialClick = false;
			}

			if(!cel.isRevealed()) {

				if(!cel.isFlagged()) {

					cel.setRevealed(true);

					cel.setDisable(true);

					if(cel.isMine()) {

						gameOver = true;
						timer.stop();
						cel.setText("X");

					} else {

						if(cel.getNbMineAround() > 0) {

							cel.setText(""+cel.getNbMineAround());	

						} else {

							if(xx > 0) {revealCel(cels[xx-1][yy]);}
							if(yy > 0) {revealCel(cels[xx][yy-1]);}
							if(xx < mode.getW()-1) {revealCel(cels[xx+1][yy]);}
							if(yy < mode.getH()-1) {revealCel(cels[xx][yy+1]);}
							if(xx > 0 && yy > 0)   {revealCel(cels[xx-1][yy-1]);}
							if(xx > 0 && yy < mode.getH()-1 ) {revealCel(cels[xx-1][yy+1]);}
							if(xx < mode.getW()-1 && yy > 0)  {revealCel(cels[xx+1][yy-1]);}
							if(xx < mode.getW()-1 && yy < mode.getH()-1 ) {revealCel(cels[xx+1][yy+1]);}

						}
						
						cel.colorize();
						
					}

					if(isWin() && !gameOver) {					
						gameOver = true;
						timer.stop();
						try {
							if(ScoreManager.isRecord(mode, time)) {
								ScoreManager.writeScore(mode, new ScoreRecord("olivier",time));
							}
						} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
							e.printStackTrace();
						}
						
					}

				}

			}

		}

	}

	public void placeFlag(Cel cel) {
		
		if(!gameOver && !initialClick) {
			
			if(!cel.isRevealed()) {

				if(cel.isFlagged()) {

					cel.setText("");
					cel.setFlagged(false);
					nbFlag ++;

				} else {

					if(nbFlag > 0) {

						cel.setText("F");
						cel.setFlagged(true);
						nbFlag --;

					}

				}

				updateMineLabel();

			}

		}

	}

	public boolean isWin() {

		boolean isWin = false;
		int nbCellRemaining = 0;

		for(int x = 0; x < mode.getW(); x++) {
			for(int y = 0; y < mode.getH(); y++) {
				if(!cels[x][y].isRevealed()) {
					nbCellRemaining ++;
				}
			}
		}

		if(nbCellRemaining == mode.getNbMine()) {
			isWin = true;
		}

		return isWin;

	}

	public void updateMineLabel() {
		if(nbFlag == 0)  {mineLabel.setText("000");} else
		if(nbFlag < 10)  {mineLabel.setText("00"+nbFlag);} else
		if(nbFlag < 100) {mineLabel.setText("0"+nbFlag);}
	}
	
	// TIMER METHODS ///////////////////////////////////////////////////////////////////////////
	public void initTimer() {
		
		if(timer != null) {
			timer.playFromStart();
		} else {
			timer = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
				timerTick();
		    }));
			timer.setCycleCount(Timeline.INDEFINITE);
		}
		
	}

	public void timerTick() {
		time ++;
		if(time >= 1000) {
			gameOver = true;
			timer.stop();
		} else {
			if(time < 10)  {timerLabel.setText("00"+time);} else
			if(time < 100) {timerLabel.setText("0"+time);} else 
			{timerLabel.setText(""+time);}
		}
	}

	// MISCELLANEOUS ///////////////////////////////////////////////////////////////////////////
	public void setStageReference(Stage stageReference) {
		this.stageReference = stageReference;
	}



}
/////////////////////////////////////////////////////////////////////////////////////////////////