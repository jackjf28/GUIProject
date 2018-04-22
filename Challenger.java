package application;

public class Challenger {
	private int rank;
	private int currScore; 
	private String name;
	private String currScoreString;
	
	public Challenger(int rank, String name) {
		this.rank = rank;
		this.name = name;
	}
	public int getCurrScore() {
		return currScore;
	}
	public String getCurrScoreString() {
		return currScoreString;
	}
	public void setCurrScore(int score) {
		currScore = score;
	}
	public void setCurrScoreString(String score) {
		currScoreString = score;
	}
	public String getName() {
		return name;
	}
	
}
