package application;
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Main extends Application {
	//Bracket object based on the file passed in
	private static Bracket bracket;
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Tournament Bracket");
			HBox primaryPane = new HBox(10.0);
			VBox firstRound = new VBox(10.0);
			Scene scene = new Scene(primaryPane, 800, 700, Color.DARKGRAY);
			//The current round of the tournament
			ArrayList<VBox> round = new ArrayList<VBox>();
			boolean round1 = true;
			if(round1) {
				//TODO figure out a good way to iterate and create new matchups
				for (int game = 0; game < bracket.numChallengers/2; game++) {
					//Stores the two teams being matched up
					ArrayList<Challenger> challengers = new ArrayList<Challenger>();
					challengers.add(bracket.getChallenges()[game].getCOne());
					challengers.add(bracket.getChallenges()[game].getCTwo());
	
					round.add(createChallenge(challengers));
	//				bracket.updateChallenge(game);
				}
				round1 = false;
			}
			else {
				int i = 2;
				while(bracket.numChallengers/(2*i) != 1) {
					VBox successorRound = new VBox(10.0);
					ArrayList<VBox> rounds = new ArrayList<VBox>();
					for(int game = 0; game < bracket.numChallengers/(2*i); game++) {
//						rounds.add(createEmptyChallenge());
					}
					i += 2;
					successorRound.getChildren().addAll(rounds);
					primaryPane.getChildren().addAll(successorRound);
				}
				
			}
			firstRound.getChildren().addAll(round);
			//sets the left side as the first round
			primaryPane.getChildren().addAll(firstRound);
//			primaryPane.setCenter(new Label("hello"));
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
	private VBox createChallenge(ArrayList<Challenger> challengers) {
		//matchup includes both team names, their respective scores,
		//and a button to update their scores
		VBox matchup = new VBox(10.0);
		boolean insertBtn = true;
		for(Challenger team : challengers) {
			//Stores the team name and score
			HBox teamsAndScores = new HBox(10.0);
			Label teamLabel = new Label();
			teamLabel.setAlignment(Pos.CENTER);
			teamLabel.setMinHeight(25);
			teamLabel.setText(team.getName());
			teamLabel.setTextFill(Color.RED);
			
			Label scoreLabel = new Label();
			scoreLabel.setAlignment(Pos.CENTER);
			scoreLabel.setMinHeight(25);
			scoreLabel.setText("Score");
			
			teamsAndScores.getChildren().addAll(teamLabel, scoreLabel);
			matchup.getChildren().add(teamsAndScores);
			
			if(insertBtn) {
				matchup.getChildren().add(createScoreButton(challengers));
			}
			insertBtn = false;
		}
		return matchup;
	}
	
	/**
	 * This method creates a button for the primary GUI, which when pressed,
	 * will open a new window where the user can input scores for a match
	 * 
	 * @param challengers the list of the 2 teams in a given match
	 * @return the button for a given match
	 */
	private Button createScoreButton(ArrayList<Challenger> challengers) {
		Button button = new Button("Submit Scores");
		button.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				VBox vBox = new VBox();
				Scene scene = new Scene(vBox, 300, 100, Color.GRAY);
				boolean insertBtn = true;
				for(Challenger team : challengers) {
					vBox.getChildren().add(challengersInButton(team));
					if(insertBtn) {
						//This button will assign scores to their respective team
						//when the user enters an integer
						Button button = new Button("Submit Match Score");
						button.setOnAction(new EventHandler<ActionEvent>()
								{
									public void handle(ActionEvent e) {
										if(challengers.get(0).getCurrScore() > challengers.get(1).getCurrScore()) {
											//Test to make sure comparisons are working
											System.out.println(challengers.get(0).getName() + " wins!");
										}
										//TODO edit for cases where the scores are equal/ scores aren't entered
										else {
											
										}
									}
								});
						vBox.getChildren().add(button);
					}
					insertBtn = false;
				}
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
						int finalScore = Integer.parseInt(insertedScore.getText());
						team.setCurrScore(finalScore);
					}
				});
		
		teamAndScore.getChildren().addAll(teamLabel, insertedScore);	
		return teamAndScore;
	}
	
	public static void main(String[] args) {
		try {
			Scanner scr = new Scanner(System.in);
			bracket = new Bracket("challengers8.txt");
			scr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		launch(args);
	}
}
