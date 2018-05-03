package application;


public class Challenger {
	private int currScore; 
	private String name;
	private String currScoreString;
	
	public Challenger(String name) {
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
