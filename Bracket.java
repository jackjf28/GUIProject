package application;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Bracket {
    Bracketnode[] challenges;
    int numChallenges;
    int numChallengers;
    
    public Bracket(String filename) throws IOException{
        
        // Read in file
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        
        ArrayList<String> teams = new ArrayList<String>();
        while(sc.hasNextLine()) {
            teams.add(sc.nextLine());
        }
        numChallengers = teams.size();
        numChallenges = numChallengers - 1;
        challenges = new Bracketnode[numChallenges];
        for (int i = 0; i < numChallenges; i++) {
            challenges[i] = new Bracketnode();
        }
        
        for (int j = 0; j <= numChallenges/2; j++) {
            challenges[j].setCOne(new Challenger(j, teams.get(j)));
        }
        for (int k = numChallenges/2; k >= 0; k--) {
            challenges[k].setCTwo(new Challenger(k, teams.get(numChallenges - k)));
        }
        
        sc.close();
    }
    
    public int getNumChallenges() {
        return numChallenges;
    }
    
    public Bracketnode[] getChallenges() {
        return challenges;
    }
    
    public Bracketnode[] updateChallenge(int cNum) {
        if (cNum % 2 == 0) {
            challenges[cNum + (numChallengers-cNum)/2].setCOne(challenges[cNum].getWinner());
        }
        else {
            challenges[cNum + (numChallengers-cNum)/2].setCTwo(challenges[cNum].getWinner());
        }
        return challenges;
    }
    public Challenger getThirdPlace(Challenger num1, Challenger num2) {
        if (num1.getCurrScore() > num2.getCurrScore()) {
            return num1;
        }
        return num2;
    }
    public String printBracket() {
        String output = "";
        for (int i = 0; i < numChallenges; i++) {
            if (i == numChallenges - 1) {
                output += ("\n" + "CHAMPIONSHIP\n");
            }
            else if (i == numChallenges - 3) { 
                output += ("\n" + "SEMI-FINAL\n");
            }
            else if (i == numChallenges - 7) {
                output += ("\n" + "QUARTER-FINAL\n");
            }
            else if (i == numChallenges - 15) {
                output += ("\n" + "1st ROUND\n");
            }
            output += "game" + (i+1) + ": " + challenges[i].getCOne().getName() 
                                  + " vs. " + challenges[i].getCTwo().getName() + "\n";
        }
        return output;
    }
    
}
