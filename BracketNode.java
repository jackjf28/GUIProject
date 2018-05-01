package application;

public class BracketNode {
	Challenger cOne; 
	Challenger cTwo;
	//boolean created so the next round is generated
	//once all scores have been submitted in a round
	boolean scoreSubmitted;
	//The number of the game starting at game 0
	//Used to determine where the winner goes in the next round.
	Integer gameNumber;
	
	public BracketNode() {
		cOne = null;
		cTwo = null;
		scoreSubmitted = false;
		gameNumber = null;
	}
	
	public void setCOne(Challenger cOne) {
		this.cOne = cOne;
	}
	
	public void setCTwo(Challenger cTwo) {
		this.cTwo = cTwo;
	}
	
	public void setScoreSubmitted(boolean value) {
		this.scoreSubmitted = value;
	}
	
	public void setGameNumber(Integer gameNum) {
		this.gameNumber = gameNum;
	}
	
	public Challenger getCOne() {
		return cOne;
	}
	
	public Challenger getCTwo() {
		return cTwo;
	}
	
	public boolean getScoreSubmitted() {
		return this.scoreSubmitted;
	}
	
	public Integer getGameNumber() {
		return this.gameNumber;
	}
	
	public Challenger getWinner() {
		if (cOne.getCurrScore() > cTwo.getCurrScore()) {
			return cOne;
		}
		return cTwo;
	}
	
	public Challenger getLoser() {
		if (cOne.getCurrScore() < cTwo.getCurrScore()) {
			return cOne;
		}
		return cTwo;
	}
}
