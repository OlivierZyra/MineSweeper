package enums;

public enum EMode {
	
	EASY(8,8,10),     // 8,8,10
	NORMAL(16,16,40), // 16,16,40
	HARD(30,16,99);   // 30,16,99
	
	private int w;
	private int h;
	private int nbMine;
	
	private EMode(int w, int h, int nbMine) {
		this.w = w;
		this.h = h;
		this.nbMine = nbMine;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int getNbMine() {
		return nbMine;
	}
	
}
