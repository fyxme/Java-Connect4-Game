import java.util.HashMap;

public class Board {
	/**
	 * ERROR Constant
	 */
	private static final int ERROR = -1;
	
	/**
	 * 2D array of all the tiles on the board
	 */
	private Tile[][] tile = null; 
	
	/**
	 * # of rounds
	 */
	private int round_num = ERROR;
	
	/**
	 * Number of rows on the board
	 */
	private int rows = ERROR;
	
	/**
	 * Number of columns on the board
	 */
	private int columns = ERROR;
	
	/**
	 *  history of all the Past moves based on round number <RoundNumber, Move made>
	 */
	private HashMap<Integer, Move> history = null; // <roundNumber, Move>
	
	public Board(int rows, int columns) {
		this.columns = columns;
		this.rows = rows;
		this.tile = new Tile[rows][columns];
		// initialize tiles
		initTile();
		this.history = new HashMap<Integer,Move>();
		this.round_num = 0;
	}
	
	/**
	 * Initialize all the Tiles on the Board
	 */
	private void initTile() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				tile[i][j] = new Tile();
			}
		}
	}
	/**
	 * Increments the round number
	 */
	public void incrementRoundNumber() {
		this.round_num++;
	}
	
	/**
	 * Adds a Move made by Participant p to the board
	 * by adding the Move to the history and adding the occupant
	 * to the specific Tile
	 * @param mv Move made
	 * @param p Participant who made the move
	 */
	public void addMove(Move mv, Participant p) {
		history.put(round_num, mv);
		tile[mv.getRow()][mv.getCol()].setOccupant(p);
		incrementRoundNumber();
	}
	
	
	/**
	 * A method to be called by checkWinner, checks if any player has four of their counters placed
	 * in a column one after the other
	 * @param rowOfLastPlaced		The row of the last placed counter
	 * @param colOfLastPlaced		The column of the last placed counter
	 * @return						A number corresponding to the player that has four in a row, or 0 if neither
	 * 								ie. 1 for player1, 2 for player2, 0 for no-one.
	 */
	public Participant checkVertical(int rowOfLastPlaced, int colOfLastPlaced) {
		int sameInARow = 1;
		
		if (rowOfLastPlaced <= 2) {		// Can only win in a vertical line if you have placed your
										// last counter on or above the fourth highest row
			Participant lastPlayer =tile[rowOfLastPlaced][colOfLastPlaced].getOccupant();
	
			for (int row = rowOfLastPlaced + 1; row <= 5; row++) {
				if (tile[row][colOfLastPlaced].getOccupant() == lastPlayer) {
					sameInARow++;
					if (sameInARow == 4) {
						return lastPlayer;
					}
				} else {
					return null;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * A method to be called by checkWinner, checks if any player has four of their counters placed
	 * in a row one after the other
	 * @param rowOfLastPlaced		The row of the last placed counter
	 * @param colOfLastPlaced		The column of the last placed counter
	 * @return						A number corresponding to the player that has four in a row, or 0 if neither
	 * 								ie. 1 for player1, 2 for player2, 0 for no-one.
	 */
	public Participant checkHorizontal(int rowOfLastPlaced, int colOfLastPlaced) {
		int sameInARow = 0;
		Participant lastPlayer =tile[rowOfLastPlaced][colOfLastPlaced].getOccupant();

		for (int col = 0; col <= 6; col++) {
			if (tile[rowOfLastPlaced][col].getOccupant() == lastPlayer) {
				sameInARow++;
				if (sameInARow == 4) {
					return lastPlayer;
				}
			} else {
				sameInARow = 0;
			}
		}
		
		return null;
	}
	
	
	/**
	 * A method to be called by checkWinner, checks if any player has four of their counters placed
	 * one after the other diagonally. Checks both diagonals (bottom left to top right, and top left to bottom right)
	 * @param rowOfLastPlaced		The row of the last placed counter
	 * @param colOfLastPlaced		The column of the last placed counter
	 * @return						A number corresponding to the player that has four in a row, or 0 if neither
	 * 								ie. 1 for player1, 2 for player2, 0 for no-one.
	 */
	public Participant checkDiagonals(int rowOfLastPlaced, int colOfLastPlaced) {
		int sameInARow = 0;
		Participant lastPlayer = tile[rowOfLastPlaced][colOfLastPlaced].getOccupant();
		int row = rowOfLastPlaced;
		int col = colOfLastPlaced;
		
		// CHECKS THE DIAGONAL GOING FROM BOTTOM LEFT TO TOP RIGHT
		// gets to the top right of the diagonal you are in
		while (col < 6 && row > 0) {
			row--;
			col++;
		}
		
		while (col >= 0 && row <= 5) {
			if (tile[row][col].getOccupant() == lastPlayer) {
				sameInARow++;
				if (sameInARow == 4) {
					return lastPlayer;
				}
			} else {
				sameInARow = 0;
			}
			
			row++;
			col--;
			
		}
	
		sameInARow = 0;
		row = rowOfLastPlaced;
		col = colOfLastPlaced;
		
		// CHECKS THE DIAGONAL GOING FROM TOP LEFT TO BOTTOM RIGHT
		// gets to the top left of the diagonal you are in
		while (col > 0 && row > 0) {
			row--;
			col--;
		}
		
		while (row <= 5 && col <= 6) {
			if (tile[row][col].getOccupant() == lastPlayer) {
				sameInARow++;
				if (sameInARow == 4) {
					return lastPlayer;
				}
			} else {
				sameInARow = 0;
			}
			row++;
			col++;
		}
		return null;
	}
	
	
	/**
	 * A method to be called at the end of each turn to check if the last piece played resulted in 
	 * one player winning by having four counters in a row, column or diagonally one after the other.
	 * 
	 * If someone won, prints the details and returns true, otherwise returns false
	 * 
	 * @param rowOfLastPlaced		The row of the last placed counter
	 * @param colOfLastPlaced		The column of the last placed counter
	 * @return						A number corresponding to the player that has four in a row, or 0 if neither
	 * 								ie. 1 for player1, 2 for player2, 0 for no-one.
	 */
	public Participant getWinner () {
		if (round_num < 6) 	// can't win before the 7th round
			return null;
		
		int rowOfLastPlaced = history.get(round_num - 1).getRow(); // -1 since we want the move from the previous round
		int colOfLastPlaced = history.get(round_num - 1).getCol();
		
		// Checks vertical first
		Participant winnerId = this.checkVertical(rowOfLastPlaced, colOfLastPlaced);
		
		// Checks the horizontal second
		if (winnerId == null) {
			winnerId = this.checkHorizontal(rowOfLastPlaced, colOfLastPlaced);
		}
		
		//Checks the two diagonals last
		if (winnerId == null) {
			winnerId = this.checkDiagonals(rowOfLastPlaced, colOfLastPlaced);
		}
		
		return winnerId;
	}
	
	/**
	 * Check if the board has an empty Slot
	 * @return Return true if it does else return false
	 */
	public boolean hasEmptySlot() {
		return round_num >= (rows * columns)  ? false : true;
	}
	
	/**
	 * Returns a specific Tile based on the row and column
	 * @param row Row of the Tile asked
	 * @param col Column of the Tile asked
	 * @return The Tile which corresponds to the row and the column
	 * or null if that Tile does not exist
	 */
	public Tile getTile(int row, int col) {
		if ((row < this.rows) && (col < this.columns)) {
			return this.tile[row][col];
		}
		return null;
	}
	
	/**
	 * @return Returns the number of rows
	 */
	public int numRow() {
		return this.rows;
	}
	
	/**
	 * @return Returns the number of columns
	 */
	public int numCol() {
		return this.columns;
	}

	/**
	 * Finds the id of the first Free Space based on a column
	 * and returns the Row Number
	 * @param col Column to look at
	 * @return The Row number if it has a space available otherwise returns -1
	 */
	public int getColumnSpace(int col) {
		int space = ERROR;
		for (int i = 0; i < rows; i++) {
			if (tile[i][col].isFree()) {
				space = i;
			}
		}
		return space;
	}

	/**
	 * @return Return the round number
	 */
	public int getTurnNum() {
		return this.round_num;
	}
	
	/**
	 * @return Returns the history of all past Moves
	 * as a HashMap containing the Round Number as Key
	 */
	public HashMap<Integer, Move> getHistory() {
		return history;
	}
	
	/**
	 * @return Returns the number of columns
	 */
	public int getNumberOfColumns() {
		return columns;
	}
	
	/**
	 * @return Returns the Number of Rows
	 */
	public int getNumberOfRows() {
		return rows;
	}
}
