import java.awt.Color;

/**
 * Player Class containing the name of a Player and the Player ID.
 * A player is someone participating in the game.
 * @version 0.1
 */
public class Player implements Participant {
	private static final int ERROR = -1;
	
	private String name = null;
	private int id = ERROR;
	private Color color = null;
	
	/**
	 * Constructor method for the Player class
	 * @param id ID assigned to this player
	 */
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
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Move makeMove(int col) {
		Move ret = new Move(col, this);
		return ret;
	}
	
	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setColor(Color color){
		this.color = color;
	}
}