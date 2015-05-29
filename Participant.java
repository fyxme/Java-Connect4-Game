import java.awt.Color;

/**
 * Interface for the occupants.
 * The participant is considered to be either a player or an AI.
 * @version 0.1
 */
public interface Participant {
	/**
	 * @return Returns the name of the participant
	 */
	public String getName();
	
	/**
	 * Set the name of the participant
	 * @param name Name of the Participant
	 */
	public void setName(String name);
	
	/**
	 * Asks the player or the AI to make a move
	 * @return The Move made
	 * @param col Column to make the Move in
	 */
	public Move makeMove(int col);
	
	/**
	 * @return Returns the Player ID
	 */
	public int getPid();

	/**
	 * @return Returns the color of this participants tiles
	 */
	public Color getColor();
	
	/**
	 * Set Color
	 */
	public void setColor(Color color);

}
