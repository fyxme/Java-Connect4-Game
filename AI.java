import java.awt.Color;

public class AI implements Participant {
	private static final int ERROR = -1;
	private static final int EASY = 0;
	private static final int MEDIUM = 1;
	private static final int HARD = 2;
	
	private int id = ERROR;
	private int difficulty = ERROR;		// must be either EASY, MEDIUM or HARD
	
	private Color color = null;
	
	/**
	 * Constructor method for the AI class
	 * @param id ID number of the AI class
	 * @param difficulty Difficulty of the AI
	 */
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
	 * @return The chosen Column
	 */
	public int chooseColumn(Board bd, Participant other) {
		// Creates a copy of bd
		Board boardCopy = bd; 
//		new Board(bd.getNumberOfRows(), bd.getNumberOfColumns());
//		for (int moveNum = 0; moveNum < bd.getTurnNum(); moveNum++) {
//			boardCopy.addMove(bd.getHistory().get(moveNum));
//		}
		
		// Sets up an array to hold the scores given to each move
		// Each score is set to be zero, which is neither good nor bad
		int scoreOfCol[] = new int[7];
		for (int col = 0; col < 7; col++) {
			scoreOfCol[col] = 0;
		}
		
		// gets the score of all moves, two turns into the future (49 moves in total) and places them in the array.
		for (int colTurn1 = 0; colTurn1 < boardCopy.getNumberOfColumns(); colTurn1++) {
			Move mv = new Move(colTurn1, this);
			mv.setRow(boardCopy.getColumnSpace(colTurn1));
			boardCopy.addMove(mv); // adds the colTurn1 move to the board (the move this AI can make)
			if (boardCopy.scoreOfBoard(this) == 1000) {
				return colTurn1; // WIN
			}
			for (int colTurn2 = 0; colTurn2 < boardCopy.getNumberOfColumns(); colTurn2++) {
				// Adds the colTurn2 move to the board (the move the other player can make)
				mv = new Move(colTurn2, this);
				mv.setRow(boardCopy.getColumnSpace(colTurn2));
				boardCopy.addMove(mv);
				
				// updates the column score to be the minimum
				scoreOfCol[colTurn1] = Math.min(scoreOfCol[colTurn1], boardCopy.scoreOfBoard(this));
				
				boardCopy.undoLastMove();
			}
			boardCopy.undoLastMove(); // undoes the last move so we can simulate the case of the next column along
		}	
		
		return findCol(scoreOfCol);
	}
	

	@Override
	public int getPid() {
		return id;
	}
	
	
	/**
	 * A method to find a suitable column to place a counter in, given the scores of each column
	 * @param scoreOfCol	An array of the scores of each col, in order, ie. index 0 is for col 0;
	 * @return				An int corresponding to the column which the AI should place its counter
	 */
	private int findCol(int[] scoreOfCol) {
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
		if (this.difficulty == HARD) {
			return hardCol;
		} else if (this.difficulty == MEDIUM) {
			return mediumCol;
		} else if (this.difficulty == EASY) {
			return easyCol;
		} else {
			// Should never be the case
			return ERROR;
		}
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
}
