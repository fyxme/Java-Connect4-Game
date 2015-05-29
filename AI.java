import java.awt.Color;

public class AI implements Participant {
	private static final int ERROR = -1;
	private static final int EASY = 0;
	private static final int MEDIUM = 1;
	private static final int HARD = 2;

	private int id = ERROR;
	private int difficulty = ERROR;		// must be either EASY, MEDIUM or HARD

	private Color color = null;
	private String name = null;

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

	static final int MAX_MOVES_AHEAD = 8; // number of moves the ai looks ahead

	static final float WIN_SCORE = 1f; // score of state that leads to a win

	static final float LOSE_SCORE = -1f; // score of state that leads to a loss

	static final float UNCERTAIN_SCORE = WIN_SCORE + LOSE_SCORE; // Mid score

	public int chooseColumn(Board bd, Participant other) {
		int ret = ERROR; // chosen column
		double max_val = 2. * Integer.MIN_VALUE; // max_value < LOSE_SCORE
		
		for (int col = 0; col < bd.getNumberOfColumns(); col++) {
			if (bd.getColumnSpace(col) != ERROR) {
				// compare move
				double new_move_value = moveValue(col, bd, other); // get the value of this move

				if (max_val < new_move_value) {
					
					max_val = new_move_value; // update the new best max_value
					ret = col; // update the new best column

					// if this is a win break and ret this value
					if (max_val == WIN_SCORE) {
						break;
					}
				}
			}
		}
		return ret;
	}

	private double moveValue (int col, Board bd, Participant other) {
		// make the move
		Move mv = new Move(col, this);
		mv.setRow(bd.getColumnSpace(col)); // column has available space since it has been looked at before
		bd.addMove(mv);
		// System.out.println("Making move for AI in column " + col);
		// calculate score
		double val_of_move = maxMin(MAX_MOVES_AHEAD, Integer.MIN_VALUE, Integer.MAX_VALUE, this, bd, other);
		bd.undoLastMove();
		return val_of_move;
	}

	// recursive tree function
	private double maxMin(int moves_ahead, double min, double max, 
			Participant currPlayer, Board bd, Participant other) {
		if (moves_ahead == 0 || bd.getWinner() != null) {
			
			double score_of_board = 0;
			// finished recursion because of moves_ahead == 0
			if (bd.getWinner() != null) { // if has winner
				// check if the  winner is player or AI and adjust score accordingly
				score_of_board = (bd.getWinner() == other)? LOSE_SCORE : WIN_SCORE; 
			} else {
				score_of_board = UNCERTAIN_SCORE;
			}
			// something that happens sooner has more value than something that happens later
			// System.out.println("Score of board = " + score_of_board);
			// System.out.println("Return score =  " + (score_of_board / ((MAX_MOVES_AHEAD - moves_ahead + 1) * 1.0)));
			return score_of_board / ((MAX_MOVES_AHEAD - moves_ahead + 1) * 1.0); // + 1 so we don't divide by zero and break the universe
		}

		// check if Participant is Player
		if (currPlayer != this) {
			for (int col = 0; col < bd.getNumberOfColumns(); col++) {
				if (bd.getColumnSpace(col) != ERROR) {
					Move mv = new Move(col, this);
					// System.out.println("Making move for AI in column " + col);
					mv.setRow(bd.getColumnSpace(col)); // column has available space since it has been looked at before
					bd.addMove(mv); // add move
					min = Math.max(min, maxMin(moves_ahead - 1, min, max, this, bd, other));
					bd.undoLastMove(); // undo move	
					if (max <= min)
						break;		
				}
			}
			return min;
		// otherwise Participant is a AI
		} else {
			for (int col = 0; col < bd.getNumberOfColumns(); col++) {
				if (bd.getColumnSpace(col) != ERROR) {
					Move mv = new Move(col, other); // make move for player
					// System.out.println("Making move for Player in column " + col);
					mv.setRow(bd.getColumnSpace(col)); // column has available space since it has been looked at before
					bd.addMove(mv); // add move
					max = Math.min(max, maxMin(moves_ahead - 1, min, max, other, bd, other));
					bd.undoLastMove(); // undo move	
					if (max <= min)
						break;
				}
			}
			return max;
		}
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
	public int findCol(int[] scoreOfCol) {
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

	@Override
	public void setName(String name) {
		this.name  = name;
	}
}
