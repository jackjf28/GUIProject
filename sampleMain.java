package application;

import java.io.IOException;
import java.util.Scanner;

public class sampleMain {
	
	public static void main( String[] args ) {
		try {
			Scanner scr = new Scanner(System.in);
			Bracket bracket = new Bracket("challengers8.txt");
			Challenger cOne;
			Challenger cTwo;
			for (int game = 0; game < bracket.getNumChallenges() - 1; game ++) {
				cOne = bracket.getChallenges()[game].getCOne();
				cTwo = bracket.getChallenges()[game].getCTwo();
				System.out.print("Enter score for "+ cOne.getName() + ": ");
				cOne.setCurrScore(scr.nextInt());
				System.out.print("Enter score for "+ cTwo.getName() + ": ");
				cTwo.setCurrScore(scr.nextInt());
				bracket.updateChallenge(game);
			}
			System.out.println(bracket.printBracket());
			scr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
	
