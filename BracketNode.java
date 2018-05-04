package application;

public class BracketNode {
	Challenger cOne; 
	Challenger cTwo;
	// boolean that tracks completion of challenge
	boolean scoreSubmitted;
	// The number of the game starting at game 0
	Integer gameNumber;
	// Number of row and column location in GridPane
	int row;
	int col;
	
	/** Set GridPane location */
	public void setRowAndCol(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	/** Return column int */
	public int getCol() {
		return col;
	}
	
	/** Return row int */
	public int getRow() {
		return row;
	}
	
	/** 
	 * BracketNode constructor. Creates a new BracketNode taking an int value for gameNumber
	 * and initalizing the both challengers to null. 
	 * @param gameNumber the number of the game in the bracket order.
	 */
	public BracketNode(int gameNumber) {
		this.gameNumber = gameNumber;
		scoreSubmitted = false;
		cOne = null;
		cTwo = null;
	}
	
	/** Return the Integer gameNumber of this challenge */
	public Integer getGameNumber() {
		return this.gameNumber;
	}
	
	/** Set the first Challenger */
	public void setCOne(Challenger cOne) {
		this.cOne = cOne;
	}
	
	/** Set the second Challenger */
	public void setCTwo(Challenger cTwo) {
		this.cTwo = cTwo;
	}
	
	/** Return a Challenger (one or two) based on argument i */
	public Challenger getChallenger(int i) {
		if (i == 1) {
			return cOne;
		}
		return cTwo;
	}
	
	/** Sets scoreSumbitted to true (challenge is completed)
	 * or false (challenge has not been completed)
	 */
	public void setScoreSubmitted(boolean value) {
		this.scoreSubmitted = value;
	}
	
	/** Return the status of the challenge; true = completed */
	public boolean getScoreSubmitted() {
		return this.scoreSubmitted;
	}
	
	/** Return challenger with the higher currScore */
	public Challenger getWinner() {
		if (cTwo == null) {
			return cOne;
		}
		else if (cOne.getCurrScore() > cTwo.getCurrScore()) {
			return cOne;
		}
		return cTwo;
	}
	
	/** Return challenger with the lower currScore */
	public Challenger getLoser() {
		if (cOne.getCurrScore() < cTwo.getCurrScore()) {
			return cOne;
		}
		return cTwo;
	}
	
	/** Check for a tie between the Challengers; true = there is a tie */
	public boolean hasTie() {
		if (cOne.getCurrScore() == cTwo.getCurrScore()) {
			return true;
		}
		return false;
	}
}
