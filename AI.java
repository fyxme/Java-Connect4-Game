public class AI implements Participant {
	private static final int ERROR = -1;
	private static final int EASY = 1;
	private static final int MEDIUM = 2;
	private static final int HARD = 3;
	
	private int id = ERROR;
	private int difficulty = ERROR;		// must be either EASY, MEDIUM or HARD
	
	public AI(int id, int difficulty) {
		this.id = id;
		this.difficulty = difficulty;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Move makeMove(int col) {
		Move ret = new Move(col,this);
		return ret;
	}

	/**
	 * Method mostly called by the makeMove method.
	 * Allows the player to choose his move 
	 * and the AI to calculate the best move possible
	 * and choose that move.
	 * 
	 * This method also allows the AI to help a player
	 * if a Hint is requested 
	 * 
	 * @return The chosen Move
	 */
	public Move chooseMove(Board bd) {
		// Creates a copy of bd
		Board boardCopy = new Board(bd.getNumberOfRows(), bd.getNumberOfColumns());
		for (int moveNum = 0; moveNum < bd.getTurnNum(); moveNum++) {
			boardCopy.addMove(bd.getHistory().get(moveNum), bd.getHistory().get(moveNum).getParticipant());
		}
		
		// Sets up an array to hold the scores given to each move
		// Each score is set to be zero, which is neither good nor bad
		int scoreOfCol[] = new int[7];
		for (int col = 0; col < 7; col++) {
			scoreOfCol[col] = 0;
		}
		
		// gets the score of all moves, two turns into the future (49 moves in total) and places them in the array.
		for (int colTurn1 = 0; colTurn1 < boardCopy.getNumberOfColumns(); colTurn1++) {
			boardCopy.addMove(new Move(colTurn1, this), this); // adds the colTurn1 move to the board (the move this AI can make)
			
			for (int colTurn2 = 0; colTurn2 < boardCopy.getNumberOfColumns(); colTurn2++) {
				// TODO: adds the colTurn2 move to the board (the move the other player can make)
				// should be similar to boardCopy.addMove(new Move(colTurn1, this), OTHERPLAYER);
				// NOT SURE HOW TO DO THIS, NEED A WAY TO GET THE OTHER PARTICIPANT
				
				// updates the column score to be the minimum
				scoreOfCol[colTurn1] = Math.min(scoreOfCol[colTurn1], boardCopy.scoreOfBoard(this));
				
				boardCopy.undoLastMove();
			}
			boardCopy.undoLastMove(); // undoes the last move so we can simulate the case of the next column along
		}	
				
		
		int bestScore = -1001;
		int hardCol = ERROR;
		int secondBestScore = -1001;
		int mediumCol = ERROR;
		int thirdBestScore = -1001;
		int easyCol = ERROR;
		
		// calculates the best score and its corresponding column - for HARD
		// calculates the second best score and its corresponding column - for MEDIUM
		// calculates the third best score and its corresponding column - for EASY
		for (int col = 0; col < 7; col++) {
			if (scoreOfCol[col] > bestScore) {
				thirdBestScore = secondBestScore;	// Updates third score, to be the old second score
				easyCol = mediumCol;				// Updates third col, to be old second col
				secondBestScore = bestScore;		// Updates second score, to be old first score
				mediumCol = hardCol;				// Updates second col, to be old first col
				bestScore = scoreOfCol[col];		// Updates first score, to be new best score
				hardCol = col;						// Updates first col, to be new best col
			} else if (scoreOfCol[col] > secondBestScore) {
				thirdBestScore = secondBestScore;	// Updates third score, to be the old second score
				easyCol = mediumCol;				// Updates third col, to be old second col
				secondBestScore = scoreOfCol[col];	// Updates second score, to be new second score
				mediumCol = col;					// Updates second col, to be new second col
			} else if (scoreOfCol[col] > thirdBestScore) {
				thirdBestScore = scoreOfCol[col];	// Updates third score, to be new third score
				easyCol = col;						// Updates third col, to be new third col
			}
		}
		
		// Returns the move based on the difficulty of the AI.
		if (difficulty == HARD) {
			return new Move(hardCol, this);
		} else if (difficulty == MEDIUM) {
			return new Move(mediumCol, this);
		} else if (difficulty == EASY) {
			return new Move(easyCol, this);
		} else {
			// Should never be the case
			return new Move(ERROR, this);
		}
	}
	

	@Override
	public int getPid() {
		return id;
	}
}
