import javax.swing.SwingUtilities;

public class Connect4Game {
	Connect4Game cg = null;
	
	public static void main(String[] args) {		
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	    			MainFrame jframe = new MainFrame();
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
