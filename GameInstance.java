import java.util.Collection;
import java.util.HashMap;


public class GameInstance {
	private final static int ERROR = -1;
	
	private Board board = null; // game board
	private HashMap<Integer, Participant> players = null; // <id, participant> first participant has id 1
	private int num_part = ERROR; // number of participants
	
	public GameInstance(int rows, int columns, int num_player, int num_ai) {
		board = new Board(rows, columns);
		players = new HashMap<Integer, Participant>();
		num_part = 0;
		for (int i = 0; i < num_player; i++) {
			players.put(num_part, new Player(i));
			num_part++;
		}
		for (int i = num_part; i < num_ai; i++) { 
			players.put(num_part, new AI(i));
			num_part++;
		}
	}

	public Board getBoard() {
		return this.board;
	}

	public Collection<Participant> getPlayers() {
		return this.players.values();
	}
	
	public Participant getCurrentParticipant() {
		Participant part = players.get((board.getTurnNum()%num_part));
		return part;
	}

	public void makeMove(Move mv, Participant p) {
		int row = board.getColumnSpace(mv.getCol());
		if (row != ERROR) {
			mv.setRow(row);
			board.addMove(mv, p);
		} else {
			System.out.println("Invalid move. Column is full!");
		}
	}
	
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
}