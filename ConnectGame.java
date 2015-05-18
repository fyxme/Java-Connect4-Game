import java.util.List;

import javax.swing.SwingUtilities;


public class ConnectGame {
	ConnectGame cg = null;
	Board board = null;
	List<Participant> prts = null;
	
	public static void main(String[] args) {		
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	            	GameInstance gi = new GameInstance();
	    			MainFrame jframe = new MainFrame(gi);
	    			jframe.setVisible(true);
	            }
	        });
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
