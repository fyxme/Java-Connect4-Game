
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
		
        
        // for testing
		board[5][1].setOccupation(1);
		getWinner(board,5,1);
		System.out.println(checkVertical(board,5,1));
		board[4][1].setOccupation(1);
		getWinner(board,4,1);
		System.out.println(checkVertical(board,4,1));
		board[3][1].setOccupation(1);
		getWinner(board,3,1);
		System.out.println(checkVertical(board,3,1));
		board[2][1].setOccupation(1);
		getWinner(board,2,1);
		System.out.println(checkVertical(board,2,1));
		
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
	
			for (int row = rowOfLastPlaced + 1; row < 5; row++) {
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
	 * If someone won, prints the details
	 * 
	 * @param board					The board instance
	 * @param rowOfLastPlaced		The row of the last placed counter
	 * @param colOfLastPlaced		The column of the last placed counter
	 * @return						A number corresponding to the player that has four in a row, or 0 if neither
	 * 								ie. 1 for player1, 2 for player2, 0 for no-one.
	 */
	public static void getWinner(Tile[][] board, int rowOfLastPlaced, int colOfLastPlaced) {
		
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
		}
	}

}
