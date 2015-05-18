import java.util.Collection;
import java.util.HashMap;


public class GameInstance {
	private final static int ERROR = -1;
	
	private Board board = null; // game board
	private HashMap<Integer, Participant> players = null; // <id, participant> first participant has id 1
	private int num_part = ERROR; // number of participants

	private int turn = ERROR; // turn counts
	
	public GameInstance(int rows, int columns, int num_player, int num_ai) {
		board = new Board(rows, columns);
		players = new HashMap<Integer, Participant>();
		turn = 0;
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
		System.out.println("turn = " + turn + " || num = " + num_part);
		Participant part = players.get((turn%num_part));
		return part;
	}

	public void makeMove(Move mv, Participant p) {
		int row = board.getColumnSpace(mv.getCol());
		if (row != ERROR) {
			mv.setRow(row);
			board.addMove(mv, p);
			turn++;
		} else {
			System.out.println("Invalid move. Column is full!");
		}
	}
}