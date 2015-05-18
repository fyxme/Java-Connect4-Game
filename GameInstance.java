import java.util.List;


public class GameInstance {
	private Board board = null;
	private List<Participant> players = null;
	
	public GameInstance(int rows, int columns, int number_players) {
		
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public List<Participant> getPlayers() {
		return this.players;
	}
}