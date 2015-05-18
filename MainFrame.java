/**
 * Main window
 * @author Michael Bernardi
 */

import java.awt.*;

import javax.swing.*;


// supposedly extending Jframe is bad design 
public class MainFrame extends JFrame {
	private final static int DEFAULT_ROW_NUM = 6;
	private final static int DEFAULT_COLUMN_NUM = 7;
	private final static int DEFAULT_PLAYER_NUM = 2;
	private final static int DEFAULT_AI_NUM = 0;
	
	public static final int CIRCLE_WIDTH = 50; // padding of circles;
	public static final int CIRCLE_PADDING = 10; // padding in pixels between two drawn slots.
	public static final int LINE_THICKNESS = 3;
	public static final int CIRCLE_SPACE = CIRCLE_WIDTH + CIRCLE_PADDING;
	
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
		this.gi = new GameInstance(DEFAULT_ROW_NUM, DEFAULT_COLUMN_NUM,
				   				   DEFAULT_PLAYER_NUM, DEFAULT_AI_NUM);
		gamePanel.setGameInstance(gi);
	}

	private void initUI()
	{
		gamePanel = new GamePanel();
		gamePanel.setLayout(new BorderLayout());
		gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		gamePanel.setPreferredSize(new Dimension(
				MainFrame.DEFAULT_COLUMN_NUM * (MainFrame.CIRCLE_WIDTH + MainFrame.CIRCLE_PADDING),
				MainFrame.DEFAULT_ROW_NUM * (MainFrame.CIRCLE_WIDTH + MainFrame.CIRCLE_PADDING)));	

		add(gamePanel);
		
		pack();
		setTitle("Connect Four");
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );		
	}
}