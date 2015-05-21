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
		
		// This is where the calculations are made. It should go two moves into the future,
		// thereby calculating the scores of 49 moves and storing the best three moves to make.
		// Moves which allow you to win on that turn are scored with 1000. Moves that allow the
		// opponent to win on their next turn are scored with -1000.
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
				
		
		// calculates the best score and its corresponding column - for HARD
		int bestScore = -1000;
		int hardCol = ERROR;
		for (int colBest = 0; colBest < 7; colBest++) {
			if (scoreOfCol[colBest] >= bestScore) {
				bestScore = scoreOfCol[colBest];
				hardCol = colBest;
			}
		}

		// calculates the second best score and its corresponding column - for MEDIUM
		int secondBestScore = -1000;
		int mediumCol = ERROR;
		for (int colSecond = 0; colSecond < 7; colSecond++) {
			if ((scoreOfCol[colSecond] >= secondBestScore) && (colSecond != hardCol)) {
				secondBestScore = scoreOfCol[colSecond];
				mediumCol = colSecond;
			}
		}
				
		// calculates the third best score and its corresponding column - for EASY
		int thirdBestScore = -1000;
		int easyCol = ERROR;
		for (int colThird = 0; colThird < 7; colThird++) {
			if ((scoreOfCol[colThird] >= thirdBestScore) && (colThird != mediumCol) && (colThird != hardCol)) {
				thirdBestScore = scoreOfCol[colThird];
				easyCol = colThird;
			}
		}
		
		// Returns the move based on the difficulty of the AI.
		if (difficulty == HARD) {
			return new Move(hardCol, this);
		} else if (difficulty == MEDIUM) {
			return new Move(mediumCol, this);
		} else {
			return new Move(easyCol, this);
		}
	}
	

	@Override
	public int getPid() {
		return id;
	}
}
