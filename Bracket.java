package application;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Bracket {
    BracketNode[] challenges;
    int nChallenges;
    int nChallengers;
    
    public Bracket(String filename) throws IOException{
        
        // Read in file
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        
        ArrayList<String> teams = new ArrayList<String>();
        while(sc.hasNextLine()) {
            teams.add(sc.nextLine());
        }
        nChallengers = teams.size();
        nChallenges = nChallengers - 1;
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
    public boolean gameOver() {
    		if (challenges[nChallenges -1].getScoreSubmitted()) {
    			return true;
    		}
    		return false;
    }
    
    public int getNumChallenges() {
        return nChallenges;
    }
    
    public int getNumChallengers() {
    		return nChallengers;
    }
    
    public BracketNode[] getChallenges() {
    		return challenges;
    }
    
    public BracketNode getChallenge(int cNum) {
        return challenges[cNum];
    }
    
    public BracketNode updateChallenge(int cNum) {
        if (cNum % 2 == 0) {
            challenges[cNum + (nChallengers-cNum)/2].setCOne(challenges[cNum].getWinner());
        }
        else {
            challenges[cNum + (nChallengers-cNum)/2].setCTwo(challenges[cNum].getWinner());
        }
        return challenges[cNum + (nChallengers-cNum)/2];
    }
    
   
    public Challenger getFirst() {
    		return challenges[nChallenges-1].getWinner();
    }
    public Challenger getSecond() {
        return challenges[nChallenges-1].getLoser();
    }
    public Challenger getThird() {
        Challenger cOne = challenges[nChallenges -2].getLoser();
        Challenger cTwo = challenges[nChallenges-3].getLoser();
        if (cOne.getCurrScore() > cTwo.getCurrScore()) {
        		return cOne;
        }
        return cTwo;
    }
    
    public String printBracket() {
        String output = "";
        for (int i = 0; i < nChallenges; i++) {
            if (i == nChallenges - 1) {
                output += ("\n" + "CHAMPIONSHIP\n");
            }
            else if (i == nChallenges - 3) { 
                output += ("\n" + "SEMI-FINAL\n");
            }
            else if (i == nChallenges - 7) {
                output += ("\n" + "QUARTER-FINAL\n");
            }
            else if (i == nChallenges - 15) {
                output += ("\n" + "1st ROUND\n");
            }
            output += "game" + (i+1) + ": " + challenges[i].getChallenger(1).getName() 
                                  + " vs. " + challenges[i].getChallenger(2).getName() + "\n";
        }
        return output;
    }
    
}
