package ms.view;

import javafx.scene.control.Button;

public class Cel extends Button {
	
	private int xPos;
	private int yPos;
	private int nbMineAround = 0;
	private boolean isMine     = false;
	private boolean isRevealed = false;
	private MainViewController controller;
	
	public Cel(MainViewController controller, int size, int xPos, int yPos) {
		
		this.controller = controller;
		
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.setMaxSize(size, size);
		this.setMinSize(size, size);
		this.setPrefSize(size, size);
		this.setText(" ");
		
		this.setOnMouseClicked(e -> this.controller.revealCel(this.xPos,this.yPos));	
		
		//this.setDisable(true);
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
	
	
	
	
	
	
}
