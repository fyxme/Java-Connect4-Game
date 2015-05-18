public class Move {
	private static final int ERROR = -1;
	private int col = ERROR;
	private int row  = ERROR;
	
	public Move (int row, int col) {
		this.col = col;
		this.row = row;
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
}