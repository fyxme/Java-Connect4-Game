import java.util.HashMap;

public class Board {
	private static final int ERROR = -1;
	
	private Tile[][] tile = null; 
	
	private int round_num = ERROR;
	
	/**
	 * Number of rows on the board
	 */
	private int rows = ERROR;
	
	/**
	 * Number of columns on the board
	 */
	private int columns = ERROR;
	
	// history of all the moves ->> to be used for later implementations
	private HashMap<Integer, Move> history = null; // <roundNumber, Move>
	
	public Board(int rows, int columns) {
		this.columns = columns;
		this.rows = rows;
		this.tile = new Tile[rows][columns];
		// initialise tiles
		initTile();
		this.history = new HashMap<Integer,Move>();
		this.round_num = 0;
	}
	
	private void initTile() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				tile[i][j] = new Tile();
			}
		}
	}
	
	public void incrementRoundNumber() {
		this.round_num++;
	}
	
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
		
		// Can't win in the bottom right triangle of squares, or the top left triangle of squares.
		// Triangles that I am talking about have base length 3 and height 3
		if ((col == 0 && row <= 2) || (col == 1 && row <= 1) || (col == 2 && row == 0)) {
			return null;
		} else if ((col == 6 && row >= 3) || (col == 5 && row >= 4) || (col == 4 && row == 5)) {
			return null;
		} else {
		
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
		}
		
		
		sameInARow = 0;
		row = rowOfLastPlaced;
		col = colOfLastPlaced;

		
		// CHECKS THE DIAGONAL GOING FROM BOTTOM LEFT TO TOP RIGHT
		
		// Can't win in the bottom left triangle of squares, or the top right triangle of squares.
		// Triangles that I am talking about have base length 3 and height 3
		if ((col == 6 && row <= 2) || (col == 5 && row <= 1) || (col == 4 && row == 0)) {
			return null;
		} else if ((col == 0 && row >= 3) || (col == 1 && row >= 4) || (col == 2 && row == 5)) {
			return null;
		} else {
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
		if (round_num < 6) 	// cant win before the 7th round
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
	
	public boolean hasEmptySlot() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (tile[i][j].isFree()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void printWinner(Participant winner) {
		System.out.println();
	}

	public Tile getTile(int row, int col) {
		return this.tile[row][col];
	}
	
	public int numRow() {
		return this.rows;
	}
	
	public int numCol() {
		return this.columns;
	}

	public int getColumnSpace(int col) {
		int space = ERROR;
		for (int i = 0; i < rows; i++) {
			if (tile[i][col].isFree()) {
				space = i;
			}
		}
		
		return space;
	}

	public int getTurnNum() {
		return this.round_num;
	}
	
	public HashMap<Integer, Move> getHistory() {
		return history;
	}
	
	public int getNumberOfColumns() {
		return columns;
	}
	
	public int getNumberOfRows() {
		return rows;
	}
	
}
