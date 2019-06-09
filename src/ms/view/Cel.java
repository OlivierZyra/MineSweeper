package ms.view;

import com.sun.glass.events.MouseEvent;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

public class Cel extends Button {
	
	private int xPos;
	private int yPos;
	private int nbMineAround = 0;
	
	private boolean isMine     = false;
	private boolean isRevealed = false;
	private boolean isFlagged  = false;
	
	private MainViewController controller;
	
	public Cel(MainViewController controller, int size, int xPos, int yPos) {
		
		this.controller = controller;
		
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.setMaxSize(size, size);
		this.setMinSize(size, size);
		this.setPrefSize(size, size);
		this.setText(" ");
		this.setStyle("-fx-border-style: solid; -fx-border-width: 2; -fx-border-radius: 2;");
		//this.setOnMouseClicked(e -> this.controller.revealCel(this));	
		this.setOnMouseClicked(e -> {
			if(e.getButton().equals(MouseButton.PRIMARY)) {
				this.controller.revealCel(this);
			} 
			else 
			if(e.getButton().equals(MouseButton.SECONDARY)) {
				this.controller.placeFlag(this);
			}
			
		});
	}
	
	public void colorize() {	
		
		switch(nbMineAround) {
			case 0 : {setStyle(""); break;}
			case 1 : {setStyle("-fx-font-family: \"impact\"; -fx-font-size: 16px; -fx-text-fill: blue;"); break;}
			case 2 : {setStyle("-fx-font-family: \"impact\"; -fx-font-size: 16px; -fx-text-fill: green;"); break;}
			case 3 : {setStyle("-fx-font-family: \"impact\"; -fx-font-size: 16px; -fx-text-fill: red;"); break;}
			case 4 : {setStyle("-fx-font-family: \"impact\"; -fx-font-size: 16px; -fx-text-fill: mediumpurple;"); break;}
			case 5 : {setStyle("-fx-font-family: \"impact\"; -fx-font-size: 16px; -fx-text-fill: navy;"); break;}
			case 6 : {setStyle("-fx-font-family: \"impact\"; -fx-font-size: 16px; -fx-text-fill: black;"); break;}
			case 7 : {setStyle("-fx-font-family: \"impact\"; -fx-font-size: 16px; -fx-text-fill: black;"); break;}
			case 8 : {setStyle("-fx-font-family: \"impact\"; -fx-font-size: 16px; -fx-text-fill: black;"); break;}
			default : {break;}
		}
		
		
	}
	
	
	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public int getNbMineAround() {
		return nbMineAround;
	}

	public void setNbMineAround(int nbMineAround) {
		this.nbMineAround = nbMineAround;
	}

	public void incrMineAround() {
		this.nbMineAround ++;
	}
	
	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public boolean isRevealed() {
		return isRevealed;
	}

	public void setRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}


	public boolean isFlagged() {
		return isFlagged;
	}


	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}
	
}
