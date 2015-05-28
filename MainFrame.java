/**
 * Main window
 * @author Michael Bernardi
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;


// supposedly extending Jframe is bad design, but it's what we were taught in 2911 so maybe it gets us more marks? 
public class MainFrame extends JFrame implements ActionListener {
	public final static int DEFAULT_ROW_NUM = 6; //made these two public since they make sense to be program-wide constants.
	public final static int DEFAULT_COLUMN_NUM = 7;
	private final static int DEFAULT_PLAYER_NUM = 2;
	private final static int DEFAULT_AI_NUM = 0;
	private static final int EASY = 0;
	private static final int MEDIUM = 1;
	private static final int HARD = 2;
	
	private GameInstance gi;
	
	//basic idea is the main frame contains a card layout with two panes, one pane being main menu other pane being game screen.	
	JPanel cards;
	JPanel gameCard;
	JPanel menuCard;
	
	JButton onePlayer;
	JButton twoPlayer;
	
	// components:
	private GamePanel gamePanel;
	private UIPanel uiPanel;
	// add buttons and stuff here
	
	MainFrame()
	{
		super();
		initUI();
		newGame(new GameInstance(DEFAULT_ROW_NUM, DEFAULT_COLUMN_NUM,
				   DEFAULT_PLAYER_NUM, DEFAULT_AI_NUM));
	}

	private void initUI()
	{
		gamePanel = new GamePanel();
		uiPanel = new UIPanel();
		uiPanel.getMainMenuButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards, "menu" );
			}
		});
		
		gameCard = new JPanel(new BorderLayout());
		gameCard.add(gamePanel,BorderLayout.CENTER);
		gameCard.add(uiPanel,BorderLayout.PAGE_END);
		
		menuCard = new JPanel();
		menuCard.setBackground(Color.BLACK);
		
		onePlayer = new JButton("1 Player");
		onePlayer.addActionListener(this);
		onePlayer.setBounds(86, 48, 89, 23);
		menuCard.add(onePlayer);
		
		twoPlayer = new JButton("2 Player");
		twoPlayer.addActionListener(this);
		twoPlayer.setBounds(86, 74, 89, 23);
		menuCard.add(twoPlayer);
		
		menuCard.add(new JButton("play game"));
		
		cards = new JPanel(new CardLayout());
		cards.add(menuCard,"menu");
		cards.add(gameCard,"game");
		
		add(cards);
		
		pack();
		setMinimumSize(getSize());
		setTitle("Connect Four");
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );		
		setLocationByPlatform(true);//makes the frame be displayed where the OS wants it to be displayed
	}
	
	private void newGame(GameInstance gi)
	{
		//maybe give up on the idea of instantiating our game instance more than once.
		this.gi = gi;
		gamePanel.setGameInstance(gi);
		uiPanel.setGameInstance(gi);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == onePlayer)
		{
			newGame(new GameInstance(DEFAULT_ROW_NUM, DEFAULT_COLUMN_NUM,1, 1));
	        CardLayout cl = (CardLayout)(cards.getLayout());
	        cl.show(cards, "game" );
		}
		else if (e.getSource() == twoPlayer)
		{
	        CardLayout cl = (CardLayout)(cards.getLayout());
	        cl.show(cards, "game" );
		}
	}
}