package application;
	
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

//TODO List
//Figure out how to overwrite "Score" with the team's final score
//Figure out how to move the winner to their respective slot in the next round
//Create a box that lists the final rankings from 1st-3rd place

public class Main extends Application {
	//Bracket object based on the file passed in
	private static Bracket bracket;
	private static GridPane gridPane;
	private VBox[] challenges;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Tournament Bracket");
			gridPane = new GridPane();
			Scene scene = new Scene(gridPane, 600, 500, Color.DARKGRAY);
			
			//The current round of the tournament	
			int round = 0;
			int totalRounds = (int)(Math.log10(bracket.getNumChallengers())/Math.log10(2.0));
			ArrayList<Integer> rounds = new ArrayList<Integer>();
			int num = bracket.getNumChallenges();
			for (int i = 0; i < totalRounds - 1; i++) {
				num -= Math.pow(2.0, i);
				rounds.add(num);
			}
			challenges = new VBox[bracket.getNumChallenges()];
			for (int i = 1; i < bracket.getNumChallengers(); i++) {
				for (int j : rounds) {
					if (j == i-1) {
						round ++;
					}
				}
				BracketNode challenge = bracket.getChallenge(i-1);
				int col = round;
				int row = (int) (((i-1) - ((Math.pow(2.0, round))-1)*Math.pow(2.0, totalRounds-round)));
				challenge.setRowAndCol(col, row);
				
				VBox nChallenge = createChallenge(challenge);
				challenges[i -1] = nChallenge;
				gridPane.add(nChallenge, col, row);
			}
			gridPane.setHgap(40.0);
			gridPane.setVgap(40.0);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method creates a vertical box that includes 2 team and final score labels
	 * and a button for the user to manually update the final scores of the match
	 * 
	 * @param challengers the list of 2 teams in a given match
	 * @return a vertical box containing 2 horizontal boxes and a button.
	 */
	private VBox createChallenge(BracketNode challenge) {
		// matchup includes both team names, their respective scores,
		// and a button to update their scores
		VBox matchup = new VBox(5.0);
//		boolean insertBtn = true;
		Label[] scoreLabelList = new Label[2];
		for(int i = 0; i < 2; i++) {
			// Stores the team name and score
			HBox teamsAndScores = new HBox(10.0);
			Label teamLabel = new Label();
			teamLabel.setAlignment(Pos.CENTER);
			teamLabel.setMinHeight(25);
			// Assigns the label TBA if the match isn't in the first round
			if (challenge.getChallenger(i+1) == null)
					teamLabel.setText("TBA");
			else {
				teamLabel.setText(challenge.getChallenger(i+1).getName());
			}
			teamLabel.setTextFill(Color.RED);
			
			Label scoreLabel = new Label();
			scoreLabel.setAlignment(Pos.CENTER);
			scoreLabel.setMinHeight(25);
			scoreLabel.setText("Score");
			
			teamsAndScores.getChildren().addAll(teamLabel, scoreLabel);
			matchup.getChildren().add(teamsAndScores);
			scoreLabelList[i] = scoreLabel;
//			if(insertBtn /*&& !teamLabel.getText().equals("TBA")*/) {
//			insertBtn = false;
		}
		matchup.getChildren().add(1, createScoreButton(challenge, scoreLabelList));

		return matchup;
	}
	
	/**
	 * This method creates a button for the primary GUI, which when pressed,
	 * will open a new window where the user can input scores for a match
	 * 
	 * @param challengers the list of the 2 teams in a given match
	 * @return the button for a given match
	 */
	private Button createScoreButton(BracketNode challenge, Label[] scoreLabelList) {
		Button button = new Button("Submit Scores");
		button.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e) {
				VBox vBox = new VBox();
				Scene scene = new Scene(vBox, 400, 100, Color.GRAY);
				boolean insertBtn = true;
				for(int i = 0; i <= 1; i ++) {
					Challenger team;
					team = challenge.getChallenger(i+1);
					vBox.getChildren().add(challengersInButton(team));
					if(insertBtn) {
						//This button will assign scores to their respective team
						//when the user enters an integer
						Button button = new Button("Submit Match Score");
						button.setOnAction(new EventHandler<ActionEvent>()
								{
									public void handle(ActionEvent e) {
										for (int i = 0; i < scoreLabelList.length; i++) {
											scoreLabelList[i].setText(team.getCurrScoreString());
										}
										if (challenge.hasTie()) {
											System.out.println("You cannot have a tie!");
										}
										else {
											System.out.println(challenge.getWinner().getName() + " wins!");
											challenge.setScoreSubmitted(true);
											if (bracket.gameOver()) {
												endGame();
											}
											else {
												BracketNode newChal = bracket.updateChallenge(challenge.getGameNumber());
												gridPane.getChildren().remove(challenges[newChal.getGameNumber()]);
												VBox nChal = createChallenge(newChal);
												challenges[newChal.getGameNumber()] = nChal;											
												gridPane.add(nChal, newChal.getCol(), newChal.getRow());
											}
											
										}
									}
								});
						vBox.getChildren().add(button);
					}
					insertBtn = false;
				}
				vBox.getChildren().add(new Label("You have to hit enter after typing"
						+ " the score into each text field! :^)"));
				Stage stage = new Stage();
				stage.setTitle("Submit Match Scores");
				stage.setScene(scene);
				stage.show();
			}
		});
		return button;
	}
	
	/**
	 * This method helps display the team and score when the "Submit Scores"
	 * button is pressed in the primary bracket GUI
	 * 
	 * @param team
	 * @return the horizontal box including the team label and a text field
	 * 			to input the final score
	 */
	private HBox challengersInButton(Challenger team) {
		HBox teamAndScore = new HBox(10.0);
		Label teamLabel = new Label();
		teamLabel.setAlignment(Pos.CENTER);
		teamLabel.setMinHeight(25);
		teamLabel.setText(team.getName());
		teamLabel.setTextFill(Color.RED);		

		TextField insertedScore = new TextField();
		insertedScore.setMaxHeight(20); insertedScore.setMaxWidth(100);
		insertedScore.setPromptText("Enter Score");
		//Changes score when enter is hit on the text box
		insertedScore.setOnAction(new EventHandler<ActionEvent>()
				{
					public void handle(ActionEvent e) {				
						Integer finalScore = Integer.parseInt(insertedScore.getText());
						team.setCurrScore(finalScore);
						team.setCurrScoreString(insertedScore.getText());
					}
				});
		
		teamAndScore.getChildren().addAll(teamLabel, insertedScore);	
		return teamAndScore;
	}
	
	public static void main(String[] args) {
		try {
			bracket = new Bracket("challengers8.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		launch(args);
	}
	public void endGame() {
		VBox vBox = new VBox();
		Scene scene = new Scene(vBox, 400, 100, Color.GRAY);
		Label winner = new Label("The Winner is: " + bracket.getFirst().getName());
		Label second = new Label("Second place is: " + bracket.getSecond().getName());
		Label third = new Label("Third place is: " + bracket.getThird().getName());
		vBox.getChildren().addAll(winner, second, third);
		
		Stage stage = new Stage();
		stage.setTitle("FINAL RESULTS");
		stage.setScene(scene);
		stage.show();
	}
}
