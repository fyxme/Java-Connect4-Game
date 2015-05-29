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
	public static final String INSTRUCTIONS = "inst";
	public static final String GAME = "game";
	
	private GameInstance gi;
	
	//basic idea is the main frame contains a card layout with two panes, one pane being main menu other pane being game screen.	
	private JPanel cards;
	private JPanel gameCard;
	private JPanel menuCard;
	private JPanel instCard;
	private JPanel optiCard;
	
	private JButton onePlayer;
	private JButton twoPlayer;
	
	// components:
	private GamePanel gamePanel;
	private UIPanel uiPanel;
	private MenuPanel menuPanel;
	private InstructionsPanel instPanel;
	private OptionsPanel optiPanel;
	// add buttons and stuff here
	
	MainFrame()
	{
		super();
		initUI();
	}

	private void initUI()
	{
		gamePanel = new GamePanel();
		uiPanel = new UIPanel(this);
		menuPanel = new MenuPanel(this);
		instPanel = new InstructionsPanel(this);
		optiPanel = new OptionsPanel(this);
		
		gameCard = new JPanel(new BorderLayout());
		gameCard.add(gamePanel,BorderLayout.CENTER);
		gameCard.add(uiPanel,BorderLayout.PAGE_END);
		
		BoxLayout bl = new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS);
		menuPanel.setLayout(bl);
		menuCard = new JPanel();
		menuCard.setBackground(new Color(210,210,210));
		menuCard.setLayout(new BorderLayout());
		menuCard.add(menuPanel, BorderLayout.CENTER);
		
		instPanel.setLayout(new BorderLayout());
		instCard = new JPanel();
		instCard.setBackground(new Color(210,210,210));
		instCard.setLayout(new BorderLayout());
		instCard.add(instPanel, BorderLayout.CENTER);
		
		optiPanel.setLayout(new CardLayout());
		optiCard = new JPanel();
		optiCard.setBackground(new Color(210,210,210));
		optiCard.setLayout(new BorderLayout());
		optiCard.add(optiPanel, BorderLayout.CENTER);
		
		cards = new JPanel(new CardLayout());
		cards.add(menuCard,MENU);
		cards.add(gameCard,GAME);
		cards.add(optiCard,OPTIONS);
		cards.add(instCard,INSTRUCTIONS);
		
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
		this.changeCard(GAME);
	}
	
	public void changeCard(String token){
		 CardLayout cl = (CardLayout)(cards.getLayout());
		 cl.show(cards, token );
	}
	
	public void changeOptiCard(boolean isAI){
		this.changeCard(OPTIONS);
		optiPanel.setAIScreen(isAI);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}