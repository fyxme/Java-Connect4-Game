/**
 * Move class containing the details of a move made 
 * by a Participant on the Board
 * @version 1.0
 */
public class Move {
	private static final int ERROR = -1;
	/**
	 * Column of move made
	 */
	private int col = ERROR;
	/**
	 * Row of move made
	 */
	private int row  = ERROR;
	/**
	 * Participant who made the move
	 */
	private Participant part = null;
	
	/**
	 * Constructor method for the Move class
	 * Once constructed the row number will be -1
	 * You need to calculate it from the board you have
	 * and then set it using the this.setRow() method
	 * @param col Column in which to make the move in
	 * @param part Participant making the move
	 */
	public Move (int col, Participant part) {
		this.part = part;
		this.col = col;
	}
	
	/**
	 * @return Returns the row of the move
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * @return Returns the column of the move
	 */
	public int getCol() {
		return this.col;
	}

	/**
	 * Set the row of the move
	 * @param row Row to set the row of this Move to
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Get the participant who made the move
	 * @return Returns the Participant who made the move
	 */
	public Participant getParticipant() {
		return this.part;
	}
}