public class AI implements Participant {
	private static final int ERROR = -1;
	
	private int id = ERROR;
	
	public AI(int id) {
		this.id = id;
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
				// TODO:
				// adds the colTurn2 move to the board (the move the other player can make)
				// should be similar to boardCopy.addMove(new Move(colTurn1, this), OTHERPLAYER);
				// but im not sure how to do this
				
				// updates the column score to be the minimum
				scoreOfCol[colTurn1] = Math.min(scoreOfCol[colTurn1], boardCopy.scoreOfBoard(this));
				
				// TODO: NEED TO REMOVE THE ADDED colTurn2 from boardCopy. (AKA undo)
			}
			// TODO: NEED TO REMOVE THE ADDED colTurn1 from boardCopy. (AKA undo)
		}	
				
				
		int colToGoWith = ERROR;
		int bestScore = -1000;
		for (int col = 0; col < 7; col++) {
			if (scoreOfCol[col] > bestScore) {
				bestScore = scoreOfCol[col];
				colToGoWith = col;
			}
		}


		
		// find the best row and best column for the AI
		// int row = ERROR;
		int column = colToGoWith;

		Move ret = new Move(column,this);
		return ret;
	}
	

	@Override
	public int getPid() {
		return id;
	}
}
