/**
 * Player Class containing the name of a Player and the Player ID.
 * A player is someone participating in the game.
 * @author Louis RIGAUDIE z5016776
 * @version 0.1
 */
public class Player implements Participant {
	private static final int ERROR = -1;
	
	private String name = null;
	private int id = ERROR;
	
	public Player(int id) {
		this.id = id;
	}
	
	@Override
	public int getPid() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Move makeMove(int col) {
		Move ret = new Move(col, this);
		return ret;
	}
}