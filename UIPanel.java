import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UIPanel extends JPanel implements ActionListener{
	private JButton restartButton;
	private JButton undoButton;
	private JButton redoButton;
	private JButton mainMenuButton;
	private GameInstance gi;
	private MainFrame main;
	
	/**
	 * A method to intialise the drawing of the UI
	 * @param mf	The mainframe to intialise for
	 */
	public UIPanel(MainFrame mf)
	{
		super();
		main = mf;
		initUI();
	}
	
	public void setGameInstance(GameInstance gi) {
		this.gi = gi;
	}

	/**
	 * A method to draw and set up all the required buttons
	 * at the bottom of the screen 
	 */
	private void initUI() {
		restartButton = new JButton("Restart");
		restartButton.setBounds(10, 11, 89, 23);
		restartButton.addActionListener(this);
		add(restartButton);
		
		undoButton = new JButton("Undo");
		undoButton.setBounds(109, 11, 89, 23);
		undoButton.addActionListener(this);
		add(undoButton);
		
		redoButton = new JButton("Redo");
		redoButton.setBounds(208, 11, 89, 23);
		redoButton.addActionListener(this);
		add(redoButton);
		
		mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds(307, 11, 89, 23);
		mainMenuButton.addActionListener(this);
		add(mainMenuButton);
		setBackground(new Color(210,210,210));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == restartButton) gi.restartGame();
		else if(e.getSource() == undoButton) {
			if (gi.getNumAI() == 1) {
				gi.getBoard().undoLastMove();
			}
			gi.undoMove();
		}
		else if(e.getSource() == redoButton) gi.redoMove();
		else if(e.getSource() == mainMenuButton) main.changeCard(main.MENU);
	}
	
}