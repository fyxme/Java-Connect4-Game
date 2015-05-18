/**
 * Main window
 * @author Michael Bernardi
 */

import java.awt.*;
import javax.swing.*;


// supposedly extending Jframe is bad design 
public class MainFrame extends JFrame{
	
	private GameInstance gi;
	
	// components:
	private GamePanel gamePanel;
	// add buttons and stuff here
	
	MainFrame()
	{
		super();
		initUI();
		newGame();
	}
	
	private void newGame()
	{
		this.gi = new GameInstance();
		gamePanel.setGameInstance(gi);
	}

	private void initUI()
	{
		gamePanel = new GamePanel();
		gamePanel.setLayout(new BorderLayout());
		gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		gamePanel.setPreferredSize(new Dimension(
				Game.COLUMNS * (Game.CIRCLE_WIDTH + Game.CIRCLE_PADDING),
				Game.ROWS * (Game.CIRCLE_WIDTH + Game.CIRCLE_PADDING)));	

		add(gamePanel);
		
		pack();
		setTitle("Connect Four");
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );		
	}
}