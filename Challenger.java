package application;

public class Challenger {
	private int rank;
	private int currScore; 
	private String name;
	
	public Challenger(int rank, String name) {
		this.rank = rank;
		this.name = name;
	}
	public int getCurrScore() {
		return currScore;
	}
	public void setCurrScore(int score) {
		currScore = score;
	}
	public String getName() {
		return name;
	}
	
}
