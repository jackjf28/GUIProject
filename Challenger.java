///////////////////////////////////////////////////////////////////////////////
// Title: 		Tournament Bracket
// Due Date: 	        May 4th, 2018
// Files:		Main.java Challenger.java BracketNode.java Bracket.java
// Course:		CS400, Spring 2018
//
// Authors:		Jack Farrell, Matt White, Jay Desai, Sam Fetherston, Megan Fischer
// Email:		jfarrell3@wisc.edu, jdesai2@wisc.edu, sfetherston@wisc.edu, mfischer9@wisc.edu,
//			mwhite34@wisc.edu
// Lecturer:	        Deb Deppeler
//
// Bugs: Team names change color when GUI is clicked
///////////////////////////////////////////////////////////////////////////////

package application;

/**
 * This class represents a competitor with a name and score.
 */
public class Challenger {
	// The score for the challenger within their current competition
	private int currScore; 
	// The challenger's name
	private String name;
	// A string version of score, to be used with a GUI
	private String currScoreString;
	
	/** Constructor initializes the challenger's name */
	public Challenger(String name) {
		this.name = name;
	}
	
	/** Return the challenger's current score (int) */
	public int getCurrScore() {
		return currScore;
	}
	
	/** Return the challenger's current score (String) */
	public String getCurrScoreString() {
		return currScoreString;
	}
	
	/** Update the challenger's current score (int) */
	public void setCurrScore(int score) {
		currScore = score;
	}
	
	/** Update the challenger's current score (String) */
	public void setCurrScoreString(String score) {
		currScoreString = score;
	}
	
	/** Return the challenger's name */
	public String getName() {
		return name;
	}
}
