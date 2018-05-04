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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/** 
 * This Class represents a tournament bracket. It takes a file of ranked competitors 
 * and places them into challenges, matching first to last, second to second-to-last, etc.
 * It also creates additional rounds of the tournament, and moves the winners of each challenge
 * to the next position in the tournament.
 */
public class Bracket {
	// An Array containing all challenges within the bracket
    BracketNode[] challenges;
    // Number of Challenges
    int nChallenges;
    // Number of Challengers
    int nChallengers;
    
    /**
     * Constructor for a Bracket. Reads in a file and builds list of BracketNodes
     * which are challenges between two teams from the provided list.
     * 
     * @param filename the name of the file containing the list of teams
     * @throws IOException if file provided can't be found
     */
    public Bracket(String filename) throws IOException{
        
        // Read in file
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        
        // Create a list of all the names of teams in the tournament
        ArrayList<String> teams = new ArrayList<String>();
        while(sc.hasNextLine()) {
            teams.add(sc.nextLine());
        }
        
        // Initialize number of challengers and challenges
        nChallengers = teams.size();
        nChallenges = nChallengers - 1;
        
        // Check for one or no teams
        if (nChallengers <= 1) {
        		if (nChallengers == 1) {
        			challenges = new BracketNode[1];
        			challenges[0] = new BracketNode(0);
        			challenges[0].setCOne(new Challenger(teams.get(0)));
        		}
        		sc.close();
        		return;
        }
        // If two or more teams
        // Build challenges array
        challenges = new BracketNode[nChallenges];
        for (int i = 0; i < nChallenges; i++) {
        		challenges[i] = new BracketNode(i);
        }
        for (int j = 0; j <= nChallenges/2; j++) {
            challenges[j].setCOne(new Challenger(teams.get(j)));
        }
        for (int k = nChallenges/2; k >= 0; k--) {
            challenges[k].setCTwo(new Challenger(teams.get(nChallenges - k)));
        }
        sc.close(); 
    }
    
    
    /**
     * Checks if the final match has been completed. If the final match has been completed
     * return true. If it has not been completed yet return false.
     * @return boolean
     */
    public boolean gameOver() {
    		if (challenges[nChallenges -1].getScoreSubmitted()) {
    			return true;
    		}
    		return false;
    }
    
    /** Returns the number of Challenges in the bracket */
    public int getNumChallenges() {
        return nChallenges;
    }
    
    /** Returns the number of Challengers in the bracket */
    public int getNumChallengers() {
    		return nChallengers;
    }
    
    /** Returns the specific challenge matching the provided game number */
    public BracketNode getChallenge(int cNum) {
        return challenges[cNum];
    }
    
    /** 
     * Updates the bracket after a game has been played. Moves the winner of the specific
     * challenge to a new game, corresponding to general tournament bracket rules.
     *
     * @param cNum int number representing game number of the challenge thats been completed
     * @return BracketNode of the challenge the winner moved to. 
     */
    public BracketNode updateChallenge(int cNum) {
        if (cNum % 2 == 0) {
            challenges[cNum + (nChallengers-cNum)/2].setCOne(challenges[cNum].getWinner());
        }
        else {
            challenges[cNum + (nChallengers-cNum)/2].setCTwo(challenges[cNum].getWinner());
        }
        return challenges[cNum + (nChallengers-cNum)/2];
    }
    
    /** Returns the first place Challenger */
    public Challenger getFirst() {
    		if (nChallenges == 0) {
    			return challenges[nChallenges].getWinner();
    		}
    		return challenges[nChallenges-1].getWinner();
    }
    
    /** Returns the second place Challenger */
    public Challenger getSecond() {
        return challenges[nChallenges-1].getLoser();
    }
    
    /** Returns the third place Challenger */
    public Challenger getThird() {
        Challenger newOne = challenges[nChallenges - 2].getLoser();
        Challenger newTwo = challenges[nChallenges - 3].getLoser();
        if (newOne.getCurrScore() > newTwo.getCurrScore()) {
        		return newOne;
        }
        return newTwo;
    }
    
}
