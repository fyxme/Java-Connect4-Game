
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
		// find the best row and best column for the AI
		// int row = ERROR;
		int column = ERROR;

		Move ret = new Move(column,this);
		return ret;
	}

	@Override
	public int getPid() {
		return id;
	}
}
