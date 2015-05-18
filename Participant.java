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
	public Move makeMove(int row, int column);
	
	/**
	 * Method mostly called by the makeMove method.
	 * Allows the player to choose his move 
	 * and the AI to calculate the best move possible
	 * and choose that move.
	 * 
	 * This method also allows the AI to help a player
	 * if a Hint is requested 
	 * 
	 * @return The chosen Move
	 */
	public Move chooseMove();
	
	/**
	 * @return Returns the Player ID
	 */
	public int getPid();

	Move chooseMove(Board bd);
}
