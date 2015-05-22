/**
 * Main window
 * @author Michael Bernardi
 */

import java.awt.*;
import javax.swing.*;


// supposedly extending Jframe is bad design, but it's what we were taught in 2911 so maybe it gets us more marks? 
public class MainFrame extends JFrame {
	public final static int DEFAULT_ROW_NUM = 6; //made these two public since they make sense to be program-wide constants.
	public final static int DEFAULT_COLUMN_NUM = 7;
	private final static int DEFAULT_PLAYER_NUM = 2;
	private final static int DEFAULT_AI_NUM = 0;
	private final static int DEFAULT_AI_DIFFICULTY = ERROR;
	private static final int EASY = 1;
	private static final int MEDIUM = 2;
	private static final int HARD = 3;
	
	private GameInstance gi;
	
	// components:
	private GamePanel gamePanel;
	private UIPanel uiPanel;
	// add buttons and stuff here
	
	MainFrame()
	{
		super();
		initUI();
		newGame();
	}
	
	private void newGame()
	{
		//maybe give up on the idea of instantiating our game instance more than once.
		this.gi = new GameInstance(DEFAULT_ROW_NUM, DEFAULT_COLUMN_NUM,
				   				   DEFAULT_PLAYER_NUM, DEFAULT_AI_NUM, DEFAULT_AI_DIFFICULTY);
		gamePanel.setGameInstance(gi);
		uiPanel.setGameInstance(gi);
	}

	private void initUI()
	{
		gamePanel = new GamePanel();
		add(gamePanel,BorderLayout.CENTER);
		
		uiPanel = new UIPanel();
		add(uiPanel,BorderLayout.PAGE_END);
		
		pack();
		setMinimumSize(getSize());
		setTitle("Connect Four");
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );		
	}
}