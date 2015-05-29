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
	private final static int DEFAULT_DIFFICULTY = 0;
	private static final int EASY = 0;
	private static final int MEDIUM = 1;
	private static final int HARD = 2;
	
	public static final String MENU = "menu";
	public static final String OPTIONS = "opti";
	public static final String INSTRUCTIONS = "instru";
	public static final String GAME = "game";
	
	private GameInstance gi;
	
	//basic idea is the main frame contains a card layout with two panes, one pane being main menu other pane being game screen.	
	private JPanel cards;
	private JPanel gameCard;
	private JPanel menuCard;
	
	private JButton onePlayer;
	private JButton twoPlayer;
	
	// components:
	private GamePanel gamePanel;
	private UIPanel uiPanel;
	private MenuPanel menuPanel;
	// add buttons and stuff here
	
	MainFrame()
	{
		super();
		initUI();
//		newGame(new GameInstance(DEFAULT_ROW_NUM, DEFAULT_COLUMN_NUM,
//				   DEFAULT_PLAYER_NUM, DEFAULT_AI_NUM, DEFAULT_DIFFICULTY));
	}

	private void initUI()
	{
		gamePanel = new GamePanel();
		uiPanel = new UIPanel();
		menuPanel = new MenuPanel(this);
		uiPanel.getMainMenuButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards, "menu" );
			}
		});
		
		gameCard = new JPanel(new BorderLayout());
		gameCard.add(gamePanel,BorderLayout.CENTER);
		gameCard.add(uiPanel,BorderLayout.PAGE_END);
		
		BoxLayout bl = new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS);
		
		menuPanel.setLayout(bl);
		menuCard = new JPanel();
		menuCard.setBackground(new Color(210,210,210));
		menuCard.setLayout(new BorderLayout());
		menuCard.add(menuPanel, BorderLayout.CENTER);
		
		
		
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
	
	public void newGame(GameInstance gi)
	{
		//maybe give up on the idea of instantiating our game instance more than once.
		this.gi = gi;
		gamePanel.setGameInstance(gi);
		uiPanel.setGameInstance(gi);
		 CardLayout cl = (CardLayout)(cards.getLayout());
		 cl.show(cards, "game" );
	}
	
	public void changeCard(String token){
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == onePlayer)
//		{
//			newGame(new GameInstance(DEFAULT_ROW_NUM, DEFAULT_COLUMN_NUM,1, 1));
//	        CardLayout cl = (CardLayout)(cards.getLayout());
//	        cl.show(cards, "game" );
//		}
//		else if (e.getSource() == twoPlayer)
//		{
//	        CardLayout cl = (CardLayout)(cards.getLayout());
//	        cl.show(cards, "game" );
//		}
	}
}