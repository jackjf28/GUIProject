///////////////////////////////////////////////////////////////////////////////
// Title: 		Tournament Bracket
// Due Date: 	May 4th, 2018
// Files:		Main.java Challenger.java BracketNode.java Bracket.java
// Course:		CS400, Spring 2018
//
// Authors:		Jack Farrell, Matt White, Jay Desai, Sam Fetherston, Megan Fischer
// Email:		jfarrell3@wisc.edu, jdesai2@wisc.edu, sfetherston@wisc.edu, mfischer9@wisc.edu,
//				mwhite34@wisc.edu
// Lecturer:	Deb Deppeler
//
// Bugs: Team names change color when GUI is clicked
///////////////////////////////////////////////////////////////////////////////

package application;

/** 
 * This Class represents a competition between two challengers. It stores
 * both of the challengers, whether the competition has been completed or not, 
 * it's game number relative to the Bracket Class, and it's column and row position
 * in the gridPane in the GUI.
 */
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
	// Stores the loser of the challenge
	Challenger loser;
	
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
		if (value) {
			setLoser();
		}
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
	
	/** Set the challenger with the lower currScore to loser */
	public void setLoser() {
		if (cOne.getCurrScore() < cTwo.getCurrScore()) {
			loser = cOne;
		}
		else {
			loser = cTwo;
		}
	}
	
	/** Return loser */ 
	public Challenger getLoser() {
		return loser;
	}
	
	/** Check for a tie between the Challengers; true = there is a tie */
	public boolean hasTie() {
		if (cOne.getCurrScore() == cTwo.getCurrScore()) {
			return true;
		}
		return false;
	}
}
