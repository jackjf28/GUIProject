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
	int row;
	int col;
	
	public void setRowAndCol(int col, int row) {
		this.col = col;
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public int getRow() {
		return row;
	}
	
	public BracketNode(int gameNumber) {
		this.gameNumber = gameNumber;
		scoreSubmitted = false;
		cOne = null;
		cTwo = null;
	}
	
	public void setCOne(Challenger cOne) {
		this.cOne = cOne;
	}
	public void setCTwo(Challenger cTwo) {
		this.cTwo = cTwo;
	}
	
	public Challenger getChallenger(int i) {
		if (i == 1) {
			return cOne;
		}
		return cTwo;
	}
	
	public void setScoreSubmitted(boolean value) {
		this.scoreSubmitted = value;
	}
	
	public Challenger getWinner() {
		if (cOne.getCurrScore() > cTwo.getCurrScore()) {
			return cOne;
		}
		return cTwo;
	}
	
	public boolean getScoreSubmitted() {
		return this.scoreSubmitted;
	}
	
	public Integer getGameNumber() {
		return this.gameNumber;
	}
	
	public Challenger getLoser() {
		if (cOne.getCurrScore() < cTwo.getCurrScore()) {
			return cOne;
		}
		return cTwo;
	}
	public boolean hasTie() {
		if (cOne.getCurrScore() == cTwo.getCurrScore()) {
			return true;
		}
		return false;
	}
}
