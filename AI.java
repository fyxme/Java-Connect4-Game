import java.awt.Color;

public class AI implements Participant {
	private static final int EASY = 3;
	private static final int MEDIUM = 6;
	private static final int HARD = 9;
	private static final int ERROR = -1;
	private static final float WIN_SCORE = 1f; // score of state that leads to a win
	private static final float LOSE_SCORE = -1f; // score of state that leads to a loss
	private static final float UNCERTAIN_SCORE = WIN_SCORE + LOSE_SCORE; // Mid score

	private int id = ERROR;
	private int difficulty = ERROR; // number of moves the ai looks ahead
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
	public int chooseColumn(Board bd, Participant other) {
		boolean reduced_diff = false;
		int orig_diff = this.difficulty;
		if (bd.getTurnNum() < 4) {
			this.difficulty = Math.min(this.difficulty, MEDIUM);
			reduced_diff = true;
		}
		
		System.out.println(this.getDifficulty());
		int ret = ERROR; // chosen column
		double max_val = 2. * Integer.MIN_VALUE; // max_value < LOSE_SCORE
		
		System.out.println(this.getDifficulty());
		
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
		if (reduced_diff)
			this.difficulty = orig_diff;
		return ret;
	}

	private double moveValue(int col, Board bd, Participant other) {
		// make the move
		Move mv = new Move(col, this);
		mv.setRow(bd.getColumnSpace(col)); // column has available space since it has been looked at before
		bd.addMove(mv);
		// System.out.println("Making move for AI in column " + col);
		// calculate score
		double val_of_move = maxMin(this.getDifficulty(), Integer.MIN_VALUE, Integer.MAX_VALUE, this, bd, other);
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
			// System.out.println("Return score =  " + (score_of_board / ((this.getDifficulty() - moves_ahead + 1) * 1.0)));
			return score_of_board / ((this.getDifficulty() - moves_ahead + 1) * 1.0); // + 1 so we don't divide by zero and break the universe
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
	
	private int getDifficulty() { 
		return this.difficulty;
	}
}
