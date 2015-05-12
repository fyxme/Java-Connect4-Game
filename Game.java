
public class Game {

	public static void main(String[] args) {
		
		
		// Set up the board
		Tile[][] board = new Tile[6][7];
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				board[i][j] = new Tile();
			}
		}
		
		/*
		 *		Col	0	1	2	3	4	5	6
		 *	Row
		 *	0 		0	0	0	0	0	0	0
		 * 	1		0	0	0	0	0	0	0
		 * 	2		0	0	0	0	0	0	0 
		 * 	3		0	0	0	0	0	0	0
		 * 	4		0	0	0	0	0	0	0 
		 * 	5		0	0	0	0	0	0	0 
		 * 
		 * The zeroes in the array are the occupations
		 */
		
        
        Scanner sc = new Scanner(System.in);
		
		int row = 0;
		int col = 0;
		int playerIdTurn = 1;
		while (getWinner(board, row, col) == false) {
			System.out.print("Player " + playerIdTurn + ", enter a col number > ");
			col = sc.nextInt();
					
			row = placeTile(board, playerIdTurn, col);
			col = col - 1;
		
			// updates the players turn		
			if (playerIdTurn == 1) {
				playerIdTurn = 2;
			} else {
				playerIdTurn = 1;
			}
		}
	}
	
	
	
	/**
	 * A method to be called by checkWinner, checks if any player has four of their counters placed
	 * in a column one after the other
	 * @param board					The board instance
	 * @param rowOfLastPlaced		The row of the last placed counter
	 * @param colOfLastPlaced		The column of the last placed counter
	 * @return						A number corresponding to the player that has four in a row, or 0 if neither
	 * 								ie. 1 for player1, 2 for player2, 0 for no-one.
	 */
	public static int checkVertical(Tile[][] board, int rowOfLastPlaced, int colOfLastPlaced) {
		int sameInARow = 1;
		
		if (rowOfLastPlaced <= 2) {		// Can only win in a vertical line if you have placed your
										// last counter on or above the fourth highest row
			int lastPlayer = board[rowOfLastPlaced][colOfLastPlaced].getOccupation();
	
			for (int row = rowOfLastPlaced + 1; row <= 5; row++) {
				if (board[row][colOfLastPlaced].getOccupation() == lastPlayer) {
					sameInARow++;
					if (sameInARow == 4) {
						return lastPlayer;
					}
				} else {
					return 0;
				}
			}
		}
		
		return 0;
	}
	
	
	
	/**
	 * A method to be called by checkWinner, checks if any player has four of their counters placed
	 * in a row one after the other
	 * @param board					The board instance
	 * @param rowOfLastPlaced		The row of the last placed counter
	 * @param colOfLastPlaced		The column of the last placed counter
	 * @return						A number corresponding to the player that has four in a row, or 0 if neither
	 * 								ie. 1 for player1, 2 for player2, 0 for no-one.
	 */
	public static int checkHorizontal(Tile[][] board, int rowOfLastPlaced, int colOfLastPlaced) {
		int sameInARow = 0;
		int lastPlayer = board[rowOfLastPlaced][colOfLastPlaced].getOccupation();

		for (int col = 0; col < 7; col++) {
			if (board[rowOfLastPlaced][col].getOccupation() == lastPlayer) {
				sameInARow++;
				if (sameInARow == 4) {
					return lastPlayer;
				}
			} else {
				sameInARow = 0;
			}
		}
		
		return 0;
	}
	
	
	
//	public int checkDiagonals(Tile[][] board, int rowOfLastPlaced, int colOfLastPlaced) {
//		int sameInARow = 0;
//		int lastPlayer = board[rowOfLastPlaced][colOfLastPlaced].getOccupation();
//
//		// CHECKS THE DIAGONAL GOING FROM BOTTOM LEFT TO TOP RIGHT FIRST
//		for (int row = ) {
//			
//		}
//		
//		
//		for (int row = rowOfLastPlaced; row > 0; row--) {
//			if (board[row][colOfLastPlaced].getOccupation() == lastPlayer) {
//				sameInARow++;
//				if (sameInARow == 4) {
//					return lastPlayer;
//				}
//			} else {
//				return 0;
//			}
//		}
//		
//		// CHECKS THE DIAGONAL GOING FROM BOTTOM RIGHT TO TOP LEFT SECOND
//		for (int row = rowOfLastPlaced; row > 0; row--) {
//			if (board[row][colOfLastPlaced].getOccupation() == lastPlayer) {
//				sameInARow++;
//				if (sameInARow == 4) {
//					return lastPlayer;
//				}
//			} else {
//				return 0;
//			}
//		}
//		
//		return 0;
//	}
	
	
	
	/**
	 * A method to be called at the end of each turn to check if the last piece played resulted in 
	 * one player winning by having four counters in a row, column or diagonally one after the other.
	 * 
	 * If someone won, prints the details and returns true, otherwise returns false
	 * 
	 * @param board					The board instance
	 * @param rowOfLastPlaced		The row of the last placed counter
	 * @param colOfLastPlaced		The column of the last placed counter
	 * @return						A number corresponding to the player that has four in a row, or 0 if neither
	 * 								ie. 1 for player1, 2 for player2, 0 for no-one.
	 */
	public static boolean getWinner(Tile[][] board, int rowOfLastPlaced, int colOfLastPlaced) {
		// Checks vertical first
		int winnerId = checkVertical(board, rowOfLastPlaced, colOfLastPlaced);
		
		// Checks the horizontal second
		if (winnerId == 0) {
			winnerId = checkHorizontal(board, rowOfLastPlaced, colOfLastPlaced);
		}
		
		// Checks the two diagonals last
//		if (winnerId == 0) {
//			winnerId = checkDiagonals(board, rowOfLastPlaced, colOfLastPlaced);
//		}
		
		
		
		// Prints out the winners details if someone won
		if (winnerId != 0) {
			System.out.println("PLAYER " + winnerId + " WON!");
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * A method which takes in a column number (1 - 7 = left - right) and places a counter where it can
	 * according to the rules of connect four.
	 * Preconditions:	Column number is between 1 and 7 and column choice is not full already
	 * Postconditions:	Places a counter in that column
	 * @param board		The board instance
	 * @param col		The column that you would like to drop a counter in (1 - 7)
	 * @param playerId	The playerId (1 or 2)
	 * @return			Returns the row that the tile was placed
	 */
	public static int placeTile(Tile[][] board, int playerId, int col) {
		for (int row = 5; row >= 0; row--) {
			if (board[row][col - 1].getOccupation() == 0) {
				board[row][col - 1].setOccupation(playerId);
				return row;
			}
		}
		return 0;
	}

}
