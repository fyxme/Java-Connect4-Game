import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Instance of the current Game 
 * containing the Players
 * and the Board
 */
public class GameInstance {
	private final static int ERROR = -1;
	
	private List<GameEventListener> eventListeners = new LinkedList<GameEventListener>();
	
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
	 * CODE MAY NEED TO BE DELETED WHEN MAIN MENU IS IMPLEMENTED.
	 */
	private int difficulty = 0;
	

	private boolean p2 = true;
	/**
	 * END MAYBE DELETABLE CODE.
	 */
	
	/**
	 * Constructor Method for GameInstance Class
	 * @param rows Number of Rows
	 * @param columns Number of Columns
	 * @param num_player Number of Human Players
	 * @param num_ai Number of AI's
	 * @param difficulty Difficulty of AI
	 */
	public GameInstance(int rows, int columns, int num_player, int num_ai) {
		board = new Board(rows, columns);
		players = new HashMap<Integer, Participant>();
		num_part = 0;
		for (int i = 0; i < num_player; i++, num_part++) {
			players.put(num_part, new Player(i));
		}
		for (int i = 0; i < num_ai; i++, num_part++) { 
			players.put(num_part, new AI(i, difficulty));
		}
	}
	
	public void addListener(GameEventListener l)
	{
		eventListeners.add(l);
	}
	public void removeListener(GameEventListener l)
	{
		eventListeners.remove(l);
	}
	private void fireGameEvent()
	{
		for(GameEventListener l : eventListeners)
		{
			l.onGameEvent(0);
		}
	}

	/**
	 * Clears the Board Completely so the game 
	 * can be Restarted with the same settings
	 */
	public void restartGame() {
		this.board = new Board(this.board.getNumberOfRows(), this.board.getNumberOfColumns());
		fireGameEvent();
		
		/**
		 * TEMPORARY CODE. DELETE WHEN MAIN MENU IS IMPLEMENTED.
		 */

		players.remove(1);
		if(p2){
			players.put(1, new Player(1));
		}else{
			players.put(1, new AI(1, difficulty));
		}
		
		for(int i=0;i<players.size();i++){//debugging
			System.out.println(players.get(i).getClass().getName());
		}
		
		/**
		 * END TEMPORARY CODE.
		 */
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
	public boolean makeMove(int col) {
		Participant curr = getCurrentParticipant();
		Move mv = new Move(col, curr);
		int row = board.getColumnSpace(col);
		if (row != ERROR) {
			mv.setRow(row);
			board.addMove(mv);
			board.clearUndoneMoves(); // clears the stack of undone Moves
			fireGameEvent();
			board.scoreOfBoard(this.getCurrentParticipant()); // DEBUG
			return true;
		} else { // column is full invalid column
			return false;
		}
	}
	
	/**
	 * Get the Winner of the game if there is one
	 * @return The Winner if there is one, else returns no
	 */
	public Participant getWinner() {
		Participant winner = board.getWinner();
//		if (winner == null && !board.hasEmptySlot()) {
//			System.out.println("\n*****DRAW!! NO ONE WINS*****");
//		// Prints out the winners details if someone won
//		} else if (winner != null) {
//			System.out.println("\nPLAYER " + winner.getPid() + " WON!");
//		}
		return winner;
	}

	/**
	 * Used to make multiple moves at once
	 * THIS IS MOSTLY USED FOR TESTING PURPOSES
	 * @param moves List of Moves to make
	 */
	public void makeMoves(List<Move> moves) {
		for (Move mv : moves) {
			makeMove(mv.getCol());
		}
	}

	public void undoMove() {
		board.undoLastMove();
		fireGameEvent();
	}
	public void redoMove() {
		board.redoLastMove();
		fireGameEvent();
	}

	
	/**
	 * TEMPORARY CODE. DELETE WHEN MAIN MENU IS IMPLEMENTED.
	 */
	public void changeDifficulty(int i){
		difficulty = i;
	}
	

	public void changeNumAI(boolean b){
		p2 = b;
	}
	
	/**
	 * END TEMPORARY CODE.
	 */
}