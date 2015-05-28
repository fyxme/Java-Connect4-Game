import java.util.HashMap;
import java.util.Stack;
/**
 * Board containing all the Tiles where a Participant can place a Button
 * Board Class also keeps count of the round_num and the history of Moves
 * @version 0.1
 */
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

	/**
	 * List of all the Moves Undone before a new Move is made
	 */
	private Stack<Move> undone_move = null;

	/**
	 * List of the locations of the winning tiles. There will be four winning tiles so
	 * this is a 2D array which holds the row and col numbers of the winning tiles. I.e.
	 * 		index	0	1	2	3
	 *		row		r0	r1	r2	r3
	 *		col 	c0	c1	c2	c3
	 * 
	 */
	private int[][] winningTiles = new int[4][2];

	/**
	 * Constructor Method for Board Class
	 * @param rows Number of Rows
	 * @param columns Number of Columns
	 */
	public Board(int rows, int columns) {
		this.columns = columns;
		this.rows = rows;
		this.tile = new Tile[rows][columns];
		// initialize tiles
		initTile();
		this.history = new HashMap<Integer,Move>();
		this.round_num = 0;
		this.undone_move = new Stack<Move>();

		// Initialise the winningTiles
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 2; c++) {
				winningTiles[r][c] = ERROR;
			}
		}
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
	 * Adds a Move made by Participant p to the board
	 * by adding the Move to the history and adding the occupant
	 * to the specific Tile
	 * @param mv Move made
	 * @param p Participant who made the move
	 */
	public void addMove(Move mv) {
		this.history.put(this.round_num, mv);
		this.tile[mv.getRow()][mv.getCol()].setOccupant(mv.getParticipant());
		this.tile[mv.getRow()][mv.getCol()].setMove(mv);
		round_num++;
	}

	/**
	 * Clears the list of undone Moves
	 */
	public void clearUndoneMoves() {
		if (!undone_move.isEmpty()) 
			this.undone_move.clear();
	}

	/**
	 * Removes the last Move made on the board
	 * and makes the Tile empty again
	 * @return True if Move undone else returns False
	 */
	public boolean undoLastMove() {
		// Cannot undo if there have been no counters placed so far
		if (round_num == 0 || history.isEmpty()) {
			return false;
		}

		round_num--;
		Move mv = history.remove(round_num);
		undone_move.push(mv);
		tile[mv.getRow()][mv.getCol()].removeParticipant();
		return true;
	}

	/**
	 * Add back the latest undone Move
	 */
	public void redoLastMove() {
		if (!undone_move.isEmpty()) {
			addMove(undone_move.pop());
		}	
	}


	/**
	 * A method to be called by checkWinner, checks if any player has four of their counters placed
	 * in a column one after the other
	 * @param rowOfLastPlaced		The row of the last placed counter
	 * @param colOfLastPlaced		The column of the last placed counter
	 * @return						The participant that won, null if no one won
	 */
	public Participant checkVertical(int rowOfLastPlaced, int colOfLastPlaced) {
		if (rowOfLastPlaced <= 2) {		// Can only win in a vertical line if you have placed your
			// last counter on or above the fourth highest row

			Participant lastPlayer = tile[rowOfLastPlaced][colOfLastPlaced].getOccupant();
			int sameInARow = 1;

			for (int row = rowOfLastPlaced + 1; row <= 5; row++) {
				if (tile[row][colOfLastPlaced].getOccupant() == lastPlayer) {
					sameInARow++;
					if (sameInARow == 4) {
						// Updates the winning tiles
						winningTiles[0][0] = row - 3;
						winningTiles[0][1] = colOfLastPlaced;
						winningTiles[1][0] = row - 2;
						winningTiles[1][1] = colOfLastPlaced;
						winningTiles[2][0] = row - 1;
						winningTiles[2][1] = colOfLastPlaced;
						winningTiles[3][0] = row;
						winningTiles[3][1] = colOfLastPlaced;
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
	 * @return						The participant that won, null if no one won
	 */
	public Participant checkHorizontal(int rowOfLastPlaced, int colOfLastPlaced) {
		int sameInARow = 0;
		Participant lastPlayer = tile[rowOfLastPlaced][colOfLastPlaced].getOccupant();

		for (int col = 0; col <= 6; col++) {
			if (tile[rowOfLastPlaced][col].getOccupant() == lastPlayer) {
				sameInARow++;
				if (sameInARow == 4) {
					// Updates the winning tiles
					winningTiles[0][0] = rowOfLastPlaced;
					winningTiles[0][1] = col - 3;
					winningTiles[1][0] = rowOfLastPlaced;
					winningTiles[1][1] = col - 2;
					winningTiles[2][0] = rowOfLastPlaced;
					winningTiles[2][1] = col - 1;
					winningTiles[3][0] = rowOfLastPlaced;
					winningTiles[3][1] = col;
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
	 * @return						The participant that won, null if no one won
	 */
	public Participant checkDiagonals(int rowOfLastPlaced, int colOfLastPlaced) {
		int sameInARow;
		int row = rowOfLastPlaced;
		int col = colOfLastPlaced;
		Participant lastPlayer = tile[rowOfLastPlaced][colOfLastPlaced].getOccupant();

		for (int iteration = 0; iteration < 2; iteration++) {

			lastPlayer = tile[rowOfLastPlaced][col].getOccupant();
			sameInARow = 0;
			
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
						// account for board rotation
						if (iteration == 1) {
							// -1 since the number of columns does not account for the 0 column
							col = this.getNumberOfColumns() - col - 1; 
							rotateBoard();
							winningTiles[0][0] = row - 3;
							winningTiles[0][1] = col - 3;
							winningTiles[1][0] = row - 2;
							winningTiles[1][1] = col - 2;
							winningTiles[2][0] = row - 1;
							winningTiles[2][1] = col - 1;
							winningTiles[3][0] = row;
							winningTiles[3][1] = col;
						} else {
							// Updates the winning tiles
							winningTiles[0][0] = row - 3;
							winningTiles[0][1] = col + 3;
							winningTiles[1][0] = row - 2;
							winningTiles[1][1] = col + 2;
							winningTiles[2][0] = row - 1;
							winningTiles[2][1] = col + 1;
							winningTiles[3][0] = row;
							winningTiles[3][1] = col;
						}
						return lastPlayer;
					}
				} else {
					sameInARow = 0;
				}
				row++;
				col--;
			}
			col = this.getNumberOfColumns() - colOfLastPlaced - 1;
			row = rowOfLastPlaced;
			rotateBoard();
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
		if (round_num <= 6) {
			// can't win before the 7th round - dont need to calculate anything
			return null;
		}

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

		// If there is no winner, makes sure that the winningTiles are still ERROR.
		// This safegaurds against undoing and redoing errors
		if (winnerId == null) {
			for (int r = 0; r < 4; r++) {
				for (int c = 0; c < 2; c++) {
					winningTiles[r][c] = ERROR;
				}
			}
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

	/**
	 * @return An array with the locations of the four winning tiles
	 */
	public int[][] getWinningTiles() {
		return winningTiles;
	}

	/**
	 * This function split the board horizontally 
	 * and moves the tiles from the left side to the right side and vice versa
	 * Useful when writing code which tests diagonals
	 */
	private void rotateBoard() {
		for (int row = 0; row < this.getNumberOfRows(); row++) {
			for (int i = 0 , j = this.getNumberOfColumns() - 1; i < j; i++, j--) {
				Tile temp = this.tile[row][i];
				this.tile[row][i] = this.tile[row][j];
				this.tile[row][j] = temp;
			}
		}
	}


	private int calculateDiagonalScore(Participant currentParticipant) {
		// CHECKS THE DIAGONALS GOING FROM BOTTOM LEFT TO TOP RIGHT
		int score = 0;
		int startingRow = 3;
		int startingCol = 0;
		while (startingCol < this.getNumberOfColumns() - 3) {

			int row = startingRow;
			int sameInARow = 0;
			int col = startingCol;
			Participant participantWithDiagonal = null;
			boolean gapBefore = false;
			boolean gapBetween = false;
			boolean gapAfter = false;

			while (col < this.getNumberOfColumns() && row >= 0) {
				if (tile[row][col].isFree()) {

					// In this case there are two free tiles within a max of four tile,
					// therefore, we cannot have three in a row for these tiles, move on
					if (gapBetween && sameInARow != 3) {
						gapBetween = false;
						sameInARow = 0;
						participantWithDiagonal = null;
					}
					// There is a free tile in the row before other occupied tiles
					if (sameInARow == 0) {
						gapBefore = true;

						// There is a free tile in the row between a row of 3
					} else if (sameInARow > 0 && sameInARow < 3) {
						gapBetween = true;
						gapBefore = false;
						gapAfter = false;

						// There is a free tile in the row after 3 occupied tiles
					} else if (sameInARow == 3) {
						gapAfter = true;
					}

					// This is the first occupied tile
				} else if (participantWithDiagonal == null) {
					participantWithDiagonal = tile[row][col].getOccupant();
					sameInARow = 1;

					// There is another occupied tile
				} else {
					if (participantWithDiagonal == tile[row][col].getOccupant()) {
						sameInARow++;
						if (sameInARow >= 3 && (gapBetween || gapBefore || gapAfter)) {	
							break;
						}
					} else {
						if (sameInARow >= 3 && (gapBetween || gapBefore || gapAfter)) {	
							break;
						} else {
							sameInARow = 1;
							participantWithDiagonal = tile[row][col].getOccupant();
							gapBefore = gapBetween;
							gapBetween = false;
							gapAfter = false;
						}
					}
				}
				row--;
				col++;
			}

			if (sameInARow >= 3 && (gapBetween || gapBefore || gapAfter)) {
				// You have 3 in a row, with either a gap in between, at the beginning,
				// or at the end of your three
				score += compareParticipants(participantWithDiagonal, currentParticipant) * 300;
				System.out.println("3 diagonal");


				// Your three can be completed on either side (i.e in two ways)
				if (!gapBetween && gapBefore && gapAfter) {
					score += compareParticipants(participantWithDiagonal, currentParticipant) * 50;
					System.out.println(" with two wins possible");
				}
			}

			if (startingRow == this.getNumberOfRows() - 1) {
				startingCol++;
			} else {
				startingRow++;
			}
		}
		return score;
	}

	/**
	 * A method to be used by the AI to determine who is in a better position in the current board
	 * @param currentParticipant	The participant that you want to score the board for
	 * @return						A score given to the board state. 1000 if currentParticipant won
	 */
	public int scoreOfBoard(Participant currentParticipant) {
		int score = 0;

		if (this.getWinner() != null) {
			return compareParticipants(this.getWinner(), currentParticipant) * 1000;

		} else {

			// DO CALCULATIONS

			// This block of code calculates if there are any counters placed 3 vertical, and could
			// be used for a win. If so, increases the score by 300 if they are the currentParticipants
			// or decreases it by 300 if it is the other participants.
			for (int col = 0; col < this.getNumberOfColumns(); col ++) {
				for (int firstRow = 0; firstRow < this.getNumberOfRows() - 2; firstRow++) {	
					int sameInARow = 0;

					if (!tile[firstRow][col].isFree()) {

						Participant participantAtTop = tile[firstRow][col].getOccupant(); 
						sameInARow++;

						for (int row = firstRow + 1; row < this.getNumberOfRows(); row++) {
							if (tile[row][col].getOccupant() == participantAtTop) {
								sameInARow++;
							} else if (tile[row][col].getOccupant() != participantAtTop || (row == this.getNumberOfRows() - 1)) {
								break;
							}
						}

						if (sameInARow == 3) {
							System.out.println("3 in a col");
							score += compareParticipants(participantAtTop, currentParticipant) * 300;
						}
						break;
					}
				}	
			}

			// This block of code calculates if there are any counters placed horizontally, and could
			// be used for a win, these could be in the form of 3 horizontal one after the other, or 
			// 4 horizontal with a gap in the middle. If so, increases the score by 300 if they are the 
			// currentParticipants or decreases it by 300 if it is the other participants.
			for (int row = 0; row < this.getNumberOfRows(); row++) {
				int sameInARow = 0;
				boolean gapBefore = false;
				boolean gapBetween = false;
				boolean gapAfter = false;
				Participant currentP = null;

				for (int col = 0; col < this.getNumberOfColumns(); col ++) {

					if (tile[row][col].isFree()) {

						if (gapBetween && sameInARow != 3) {
							gapBetween = false;
							sameInARow = 0;
							currentP = null;
						}
						// There is a free tile in the row before other occupied tiles
						if (sameInARow == 0) {
							gapBefore = true;

							// There is a free tile in the row between a row of 3
						} else if (sameInARow > 0 && sameInARow < 3) {
							gapBetween = true;

							// There is a free tile in the row after 3 occupied tiles
						} else if (sameInARow == 3) {
							gapAfter = true;
						}

						// This is the first occupied tile
					} else if (currentP == null) {
						currentP = tile[row][col].getOccupant();
						sameInARow = 1;

						// There is another occupied tile
					} else {
						if (currentP == tile[row][col].getOccupant()) {
							sameInARow++;
							if (sameInARow >= 3 && (gapBetween || gapBefore || gapAfter)) {	
								break;
							}
						} else {
							if (sameInARow >= 3 && (gapBetween || gapBefore || gapAfter)) {
								break;
							}
							sameInARow = 1;
							currentP = tile[row][col].getOccupant();
							gapBefore = gapBetween;
							gapBetween = false;
							gapAfter = false;
						}
					}
				}

				if (sameInARow >= 3 && (gapBetween || gapBefore || gapAfter)) {
					System.out.print("3 in a row");
					// you have three in row on the bottom row with free tiles on either side
					if (!gapBetween && gapBefore && gapAfter && row == this.getNumberOfRows() - 1) {
						System.out.println(" with two wins possible on bottom");
						return compareParticipants(currentP, currentParticipant) * 999;

					} else {
						// You have 3 in a row, with either a gap in between, at the beginning,
						// or at the end of your three
						score += compareParticipants(currentP, currentParticipant) * 300;
						System.out.println(" with one win possible");


						// Your three can be completed on either side (i.e in two ways)
						if (!gapBetween && gapBefore && gapAfter) {
							score += compareParticipants(currentP, currentParticipant) * 50;
							System.out.println(" with two wins possible");
						}
					}
				}
			}


			// CHECKS THE DIAGONALS GOING FROM BOTTOM LEFT TO TOP RIGHT
			score += calculateDiagonalScore(currentParticipant);

			// CHECKS THE DIAGONALS GOING FROM BOTTOM RIGHT TO TOP LEFT
			rotateBoard();
			score += calculateDiagonalScore(currentParticipant);
			rotateBoard();
		}
		System.out.println(score); // DEBUGGING
		return score;
	}


	/**
	 * A method which takes in two participants and returns 1 if they are the same, and -1 if 
	 * they are different
	 * @param p1		The first participant to be compared
	 * @param p2		The second participant to be compared
	 * @return			1 if p1 == p2, or -1 of p1 != p2
	 */
	private int compareParticipants(Participant p1, Participant p2) {
		if (p1 == p2) {
			return 1;
		} else {
			return -1;
		}
	}


	/**
	 * Prints the full board using O for player 1 and X for player 2
	 * on the System console
	 * THIS METHOD IS MAINLY USED FOR DEBUGGING
	 */
	public void printBoard(Participant p1, Participant p2) {
		System.out.println(" -- ROUND " + this.round_num);
		System.out.println(" >> Player 1 : X");
		System.out.println(" >> Player 2 : O");
		for (int i = 0; i < (rows * 2 + 1); i++) {
			for (int j = 0; j < (columns * 2 + 1); j++) {
				System.out.print(" ");
				if (i%2 == 0)
					if (j%2 == 0)
						System.out.print("+");
					else
						System.out.print("-");
				else
					if (j%2 == 0) {
						System.out.print("|");
					} else {
						Move mv = tile[(i-1)/2][(j-1)/2].getMove();
						if (mv == null) {
							System.out.print(" ");
						} else {
							Participant p = mv.getParticipant();
							if (p == p1)
								System.out.print("X");
							else 
								System.out.print("O");
						}
					}	
			}
			System.out.println();
		}
		System.out.println(this.getWinner());
		scoreOfBoard(p1);
		scoreOfBoard(p2);
	}
}