package ms;

public class ScoreRecord {

	private String name;
	private int time;
	
	public ScoreRecord(String name, int time) {
		super();
		this.name = name;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public int getTime() {
		return time;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ScoreRecord [name=" + name + ", time=" + time + "]";
	}
	
}
