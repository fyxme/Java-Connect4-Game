import javax.swing.*;

/**
 * Connect4Game Class contains the main method which initiates the whole program
 */
public class Connect4Game {
	static Connect4Game cg = null;


	public static void main(String[] args) {
		cg = new Connect4Game();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame jframe = new MainFrame();
				jframe.setVisible(true);
			}
		});
	}
}
