import java.util.List;
import java.util.*;
import javax.swing.*;

/**
 * Connect4Game Class contains the main method which initiates the whole program
 */
public class Connect4Game {
	static Connect4Game cg = null;

	// DEBUGGING //
	private final static int DEFAULT_ROW_NUM = 6;
	private final static int DEFAULT_COLUMN_NUM = 7;
	private final static int DEFAULT_PLAYER_NUM = 2;
	private final static int DEFAULT_AI_NUM = 0;
	private final static int DEFAULT_AI_DIFFICULTY = -1;
	GameInstance gi = null;

	public void testAI() {
		this.gi = new GameInstance(DEFAULT_ROW_NUM, DEFAULT_COLUMN_NUM,
				DEFAULT_PLAYER_NUM, DEFAULT_AI_NUM, DEFAULT_AI_DIFFICULTY);	
		Participant p1 = gi.getCurrentParticipant();
		Participant p2 = gi.getOtherParticipant(p1);
		
		List<Move> moves = new LinkedList<Move>();
		moves.add(new Move(0,p2));
		moves.add(new Move(1,p2));
		moves.add(new Move(3,p2));
		moves.add(new Move(4,p2));
		moves.add(new Move(2,p1));
		moves.add(new Move(5,p1));
		moves.add(new Move(2,p2));
		moves.add(new Move(4,p2));
		moves.add(new Move(2,p1));
		moves.add(new Move(3,p1));
		moves.add(new Move(4,p1));
		
		gi.makeMoves(moves);
		
		printBoard(p1,p2);
		
	}
	
	public void printBoard(Participant p1, Participant p2) {
		gi.getBoard().printBoard(p1, p2);
	}
	
	private boolean testing_AI = false;
	// END OF DEBUGGING //

	public static void main(String[] args) {
		cg = new Connect4Game();
		
		if (cg.testing_AI) {
			cg.testAI();// DEBUGGING
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					MainFrame jframe = new MainFrame();
					jframe.setVisible(true);
				}
			});
		}
	}

	// to be used later when working on the design
	// generate assets maybe?
	/**
	 * Generates all colors by checking the assets/colors folder
	 * and adding all the colors that are in there.
	 * This makes sure that if a color's asset is missing 
	 * it will not be added to the game
	 */
	//	private void generateColours() {
	//		
	//	}
}
