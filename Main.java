
	//Bracket object based on the file passed in
	private static Bracket bracket;
	
	// The underlying structure for the GUI bracket output
	private static GridPane gridPane;
	
	// A VBox Array to allow all parts of the program access to all nodes in the gridPane
	private VBox[] challenges;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// if one or zero teams
			if (bracket.getNumChallengers() <= 1) {
				oneOrNoTeams();
				return;
			}
			
			// Set up main stage
			primaryStage.setTitle("Tournament Bracket");
			
			// Create GridPane
			gridPane = new GridPane();
			gridPane.setStyle("-fx-background-color: #C0C0C0;"); // Set Color
			
			// Use ScrollPane because 16 teams is too large for screen size
			ScrollPane sp = new ScrollPane();
			sp.setStyle("-fx-background: #C0C0C0;"); // Set Color
			sp.setContent(gridPane);
			
			// Add elements to Scene
			Scene scene = new Scene(sp, 600, 500, Color.DARKGRAY);
			
			// Initialize the current round of the tournament	
			int round = 0;
			// Calculate the total number of rounds possible
			int totalRounds = (int)(Math.log10(bracket.getNumChallengers())/Math.log10(2.0));
			
			// rounds contains the game numbers that indicate a new round has started
			ArrayList<Integer> rounds = new ArrayList<Integer>();
			// Find the game numbers that indicate a new round, and add to rounds
			int num = bracket.getNumChallenges();
			for (int i = 0; i < totalRounds - 1; i++) {
				num -= Math.pow(2.0, i);
				rounds.add(num);
			}
			challenges = new VBox[bracket.getNumChallenges()];
			// Add all challenges to the display
			for (int game = 1; game < bracket.getNumChallengers(); game++) {
				// Increment round if game number indicates new round has started.
				for (int j : rounds) {
					if (j == game-1) {
						round ++;
					}
				}
				BracketNode challenge = bracket.getChallenge(game-1);
				// Calculate row and column based on game number and round
				int col = round;
				int row = (int) (((game-1) - ((Math.pow(2.0, round))-1)*Math.pow(2.0, totalRounds-round)));
				challenge.setRowAndCol(col, row); 
				
				// Add challenge VBox to the gridpane and array of challenges
				VBox nChallenge = createChallenge(challenge);
				challenges[game -1] = nChallenge;
				gridPane.add(nChallenge, col, row);
			}
			// Set gridPane spacing
			gridPane.setHgap(40.0);
			gridPane.setVgap(40.0);
			
			// Display finished scene
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
	 * @param challenge the BracketNode for a given match
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
				// Create a new stage (window) for submitting scores
				VBox vBox = new VBox();
				Scene scene = new Scene(vBox, 400, 100, Color.GRAY);
				Stage stage = new Stage();
				
				// ???
				boolean insertBtn = true;
				
				// Loop through twice, once for each challenger
				for(int i = 0; i < 2; i ++) {
					Challenger team;
					team = challenge.getChallenger(i+1);
					vBox.getChildren().add(challengersInButton(team));
					
					if(insertBtn) {
						// This button will assign scores to their respective team
						// when the user enters an integer
						Button button = new Button("Submit Match Score");
						button.setOnAction(new EventHandler<ActionEvent>()
								{
									public void handle(ActionEvent e) {
										for (int i = 0; i < scoreLabelList.length; i++) {
											scoreLabelList[i].setText(challenge.getChallenger(i+1).getCurrScoreString());
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
											stage.close();
										}
									}
								});
						vBox.getChildren().add(button);
					}
					insertBtn = false;
				}
				vBox.getChildren().add(new Label("You have to hit enter after typing"
						+ " the score into each text field! :^)"));
				
				// Display the completed stage
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
			bracket = new Bracket("challengers0.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		launch(args);
	}
	
	/**
	 * This function handles the end game pop-up for one or zero teams
	 */
	public void oneOrNoTeams() {
		Label label = new Label();
		if (bracket.getNumChallengers() == 0) {
			label.setText("There were no teams, and therefore \n no challenges and no winners.");
			label.setAlignment(Pos.CENTER);
			
		}
		else {
			label.setText("There was only one team, and therefore no challenges \n\n WINNER: "
		                  + bracket.getFirst().getName());
			label.setAlignment(Pos.CENTER);
		}
		Scene scene = new Scene(label, 400, 200, Color.DARKGRAY);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * This function creates a pop-up window displaying 1st, 2nd, and 3rd place
	 * at the end of the tournament.
	 */
	public void endGame() {
		// Set up new window
		VBox vBox = new VBox();
		Scene scene = new Scene(vBox, 400, 100, Color.GRAY);
		Label winner = new Label("1st: " + bracket.getFirst().getName());
		Label second = new Label("2nd: " + bracket.getSecond().getName());
		if (bracket.getNumChallengers() > 3) {
			Label third = new Label("3rd: " + bracket.getThird().getName());
			vBox.getChildren().addAll(winner, second, third);
		}
		else {
			vBox.getChildren().addAll(winner, second);
		}
		
		
		// Create new window and display
		Stage stage = new Stage();
		stage.setTitle("FINAL RESULTS");
		stage.setScene(scene);
		stage.show();
	}
}
