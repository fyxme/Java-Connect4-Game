/**
 * Interface for the occupants.
 * The participant is considered to be either a player or an AI.
 * 
 * @author Louis RIGAUDIE z5016776
 * @version 0.1
 */
public interface Participant {
	/**
	 * @return Returns the name of the participant
	 */
	public String getName();
	
	/**
	 * Asks the player or the AI to make a move
	 * @return The Move made
	 */
	public Move makeMove(int col);
	
	/**
	 * @return Returns the Player ID
	 */
	public int getPid();

}
