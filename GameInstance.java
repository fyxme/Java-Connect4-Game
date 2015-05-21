import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Instance of the current Game 
 * containing the Players
 * and the Board
 */
public class GameInstance {
	private final static int ERROR = -1;
	
	/**
	 * Game Board
	 */
	private Board board = null;
	
	/**
	 * HashMap of Participants with Id as Key.
	 * >> (id, participant) first participant has id 0
	 */
	private HashMap<Integer, Participant> players = null;
	
	/**
	 * Number of Participants
	 */
	private int num_part = ERROR;
	
	/**
	 * Constructor Method for GameInstance Class
	 * @param rows Number of Rows
	 * @param columns Number of Columns
	 * @param num_player Number of Human Players
	 * @param num_ai Number of AI's
	 * @param difficulty Difficulty of AI
	 */
	public GameInstance(int rows, int columns, int num_player, int num_ai, int difficulty) {
		board = new Board(rows, columns);
		players = new HashMap<Integer, Participant>();
		num_part = 0;
		for (int i = 0; i < num_player; i++) {
			players.put(num_part, new Player(i));
			num_part++;
		}
		for (int i = 0; i < num_ai; i++) { 
			players.put(num_part, new AI(i, difficulty));
			num_part++;
		}
	}

	/**
	 * Clears the Board Completely so the game 
	 * can be Restarted with the same settings
	 */
	public void restartGame() {
		this.board = new Board(this.board.getNumberOfRows(), this.board.getNumberOfColumns());
	}
	
	/**
	 * Get the board of this Game Instance
	 * @return The board itself
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Get a Collection of all the Players participating in the game
	 * This contains both the AI and Human Players
	 * @return A Collection of the Participants
	 */
	public Collection<Participant> getPlayers() {
		return this.players.values();
	}
	
	/**
	 * Get the Current Participant
	 * ie. who's turn is it?
	 * @return The Current Participant Playing
	 */
	public Participant getCurrentParticipant() {
		Participant part = players.get((board.getTurnNum()%num_part));
		return part;
	}
	
	/**
	 * Returns the second participant based on the given participant
	 * @param ptc The first Participant
	 * @return The second Participant or null if no other participants
	 */
	public Participant getOtherParticipant(Participant ptc) {
		for (Participant p : getPlayers())
			if (p != ptc)
				return p;
		return null;
	}

	/**
	 * Tries to make a Move on the board based on the input column
	 * @param mv Move made
	 * @return True if the Move has been Made else returns False if the move can't be made.
	 */
	public boolean makeMove(Move mv) {
		int row = board.getColumnSpace(mv.getCol());
		if (row != ERROR) {
			mv.setRow(row);
			board.addMove(mv);
			board.clearUndoneMoves(); // clears the stack of undone Moves
			return true;
		} else {
			System.out.println("Invalid move. Column is full!");
			return false;
		}
	}
	
	/**
	 * Get the Winner of the game if there is one
	 * @return The Winner if there is one, else returns no
	 */
	public Participant getWinner() {
		Participant winner = board.getWinner();
		if (winner == null && !board.hasEmptySlot()) {
			System.out.println("\n*****DRAW!! NO ONE WINS*****");
		// Prints out the winners details if someone won
		} else if (winner != null) {
			System.out.println("\nPLAYER " + winner.getPid() + " WON!");
		}
		return winner;
	}

	/**
	 * Used to make multiple moves at once
	 * THIS IS MOSTLY USED FOR TESTING PURPOSES
	 * @param moves List of Moves to make
	 */
	public void makeMoves(List<Move> moves) {
		for (Move mv : moves) {
			makeMove(mv);
		}
	}
}