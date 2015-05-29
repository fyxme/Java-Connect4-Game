import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class OptionsPanel extends JPanel implements ActionListener{
	private JPanel cards;
	private JPanel aiCard;
	private JPanel twoPlayerCard;
	
	//AI screen components
	private JLabel youLabel;
	private JTextField youName;
	
	private ButtonGroup plColourGroup;
	private JRadioButton plColour1Rad;
	private JRadioButton plColour2Rad;
	private JRadioButton plColour3Rad;
	private JRadioButton plColour4Rad;
	
	private JLabel aiLabel;
	private ButtonGroup aiColourGroup;
	private JRadioButton aiColour1Rad;
	private JRadioButton aiColour2Rad;
	private JRadioButton aiColour3Rad;
	private JRadioButton aiColour4Rad;
	
	private JLabel difficultyLabel;
	private ButtonGroup diffButtonGroup;
	private JRadioButton diffEasyRad;
	private JRadioButton diffMedRad;
	private JRadioButton diffHardRad;
	
	private JButton backAIButton;
	private JButton playAIButton;
	
	//Two Player screen components
	private JLabel playerOneLabel;
	private JTextField playerOneName;
	private JLabel playerTwoLabel;
	private JTextField playerTwoName;
	
	private JButton backTPButton;
	private JButton playTPButton;
	
	//other variables
	private MainFrame main;
	
	private final static String AI = "ai";
	private final static String TP = "twoplayer";
	
	public OptionsPanel(MainFrame mf)
	{
		super();
		main = mf;
		initUI();
		createLayout();
	}

	private void initUI() {
		//AI screen
		youLabel = new JLabel("You:");
		youName = new JTextField("Player 1");
		
		Dimension preferredRadSize = new Dimension(80,20);
		
		plColour1Rad = new JRadioButton();
		plColour1Rad.setPreferredSize(preferredRadSize);
		plColour1Rad.setBackground(GameInstance.slotColours[0]);
		plColour1Rad.addActionListener(this);
		
		plColour2Rad = new JRadioButton();
		plColour2Rad.setPreferredSize(preferredRadSize);
		plColour2Rad.setBackground(GameInstance.slotColours[1]);
		plColour2Rad.addActionListener(this);
		
		plColour3Rad = new JRadioButton();
		plColour3Rad.setPreferredSize(preferredRadSize);
		plColour3Rad.setBackground(GameInstance.slotColours[2]);
		plColour3Rad.addActionListener(this);
		
		plColour4Rad = new JRadioButton();
		plColour4Rad.setPreferredSize(preferredRadSize);
		plColour4Rad.setBackground(GameInstance.slotColours[3]);
		plColour4Rad.addActionListener(this);
		
		
		plColourGroup = new ButtonGroup();
		plColourGroup.add(plColour1Rad);
		plColourGroup.add(plColour2Rad);
		plColourGroup.add(plColour3Rad);
		plColourGroup.add(plColour4Rad);
		
		plColourGroup.setSelected(plColour1Rad.getModel(), true);
		
		aiLabel = new JLabel("Computer:");
		
		aiColour1Rad = new JRadioButton();
		aiColour1Rad.setPreferredSize(preferredRadSize);
		aiColour1Rad.setBackground(GameInstance.slotColours[0]);
		aiColour1Rad.addActionListener(this);
		
		aiColour2Rad = new JRadioButton();
		aiColour2Rad.setPreferredSize(preferredRadSize);
		aiColour2Rad.setBackground(GameInstance.slotColours[1]);
		aiColour2Rad.addActionListener(this);
		
		aiColour3Rad = new JRadioButton();
		aiColour3Rad.setPreferredSize(preferredRadSize);
		aiColour3Rad.setBackground(GameInstance.slotColours[2]);
		aiColour3Rad.addActionListener(this);
		
		aiColour4Rad = new JRadioButton();
		aiColour4Rad.setPreferredSize(preferredRadSize);
		aiColour4Rad.setBackground(GameInstance.slotColours[3]);
		aiColour4Rad.addActionListener(this);
		
		aiColourGroup = new ButtonGroup();
		aiColourGroup.add(aiColour1Rad);
		aiColourGroup.add(aiColour2Rad);
		aiColourGroup.add(aiColour3Rad);
		aiColourGroup.add(aiColour4Rad);
		
		aiColourGroup.setSelected(aiColour2Rad.getModel(), true);
		
		difficultyLabel = new JLabel("Difficulty:");

		diffEasyRad = new JRadioButton("Easy");
		diffMedRad = new JRadioButton("Medium");
		diffHardRad = new JRadioButton("Hard");
		
		diffButtonGroup = new ButtonGroup();
		diffButtonGroup.add(diffEasyRad);
		diffButtonGroup.add(diffMedRad);
		diffButtonGroup.add(diffHardRad);
		
		diffButtonGroup.setSelected(diffMedRad.getModel(), true);
		
		playAIButton = new JButton("Play");
		playAIButton.addActionListener(this);
		backAIButton = new JButton("Back");
		backAIButton.addActionListener(this);
		
		//TP screen
		playerOneLabel = new JLabel("Player 1");
		playerOneName = new JTextField("Player 1");
		playerTwoLabel = new JLabel("Player 2");
		playerOneName = new JTextField("Player 2");
		
		playTPButton = new JButton("Play");
		playTPButton.addActionListener(this);
		backTPButton = new JButton("Back");
		backTPButton.addActionListener(this);
		
		aiCard = new JPanel();
		//aiCard.setBackground(Color.blue);
		
		twoPlayerCard = new JPanel();
		//twoPlayerCard.setBackground(Color.green);
		
		cards = new JPanel(new CardLayout());
		cards.add(aiCard,AI);
		cards.add(twoPlayerCard,TP);
		add(cards);
		
		setBackground(Color.white);
		
	}
	
	private void createLayout()
	{	
		//ai screen
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);//set padding here
		c.anchor = c.CENTER;
		
		aiCard.setLayout(new GridBagLayout());
		
		//player info
		c.gridx = 0; c.gridy = 0;
		aiCard.add(youLabel,c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.anchor = c.WEST;
		c.fill = c.HORIZONTAL;
		aiCard.add(youName,c);
		c.anchor = c.CENTER;
		c.gridwidth = 1;
		//colour buttons
		c.gridy++;
		c.gridx = 0;
		aiCard.add(plColour1Rad,c);
		c.gridx = 1;
		aiCard.add(plColour2Rad,c);
		c.gridx = 2;
		aiCard.add(plColour3Rad,c);
		c.gridx = 3;
		aiCard.add(plColour4Rad,c);
		
		//ai info
		c.gridy++;
		c.gridx = 0;
		aiCard.add(aiLabel, c);
		
		c.gridy++;
		c.gridx = 0;
		aiCard.add(aiColour1Rad,c);
		c.gridx = 1;
		aiCard.add(aiColour2Rad,c);
		c.gridx = 2;
		aiCard.add(aiColour3Rad,c);
		c.gridx = 3;
		aiCard.add(aiColour4Rad,c);
		
		//ai difficulty
		c.gridy++;
		c.gridx = 0;
		aiCard.add(difficultyLabel,c);
		
		c.gridy++;
		c.gridx = 0;
		aiCard.add(diffEasyRad,c);
		c.gridx = 1;
		aiCard.add(diffMedRad,c);
		c.gridx = 2;
		aiCard.add(diffHardRad,c);
		
		c.gridy++;
		c.gridx = 0;
		aiCard.add(backAIButton,c);
		c.gridx = 3;
		aiCard.add(playAIButton,c);
		
		//two player screen
		c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);//set padding here
		//c.weightx = c.weighty = 1;
		
		twoPlayerCard.setLayout(new GridBagLayout());
		c.gridx = c.gridy = 0;
		twoPlayerCard.add(backTPButton,c);
		c.gridx = 1;
		twoPlayerCard.add(playTPButton,c);
	}
	
	public void setAIScreen(boolean aiScreen)
	{
		 CardLayout cl = (CardLayout)(cards.getLayout());
		 cl.show(cards, aiScreen ? AI : TP );
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backAIButton || e.getSource() == backTPButton){
			main.changeCard(main.MENU); //goes back to the menu
		}
		else if(e.getSource() == playAIButton){
			GameInstance gi = new GameInstance(main.DEFAULT_ROW_NUM,
					main.DEFAULT_COLUMN_NUM, 1, 1, 2);
			main.newGame(gi);
		}
		else if(e.getSource() == playTPButton){
			GameInstance gi = new GameInstance(main.DEFAULT_ROW_NUM,
					main.DEFAULT_COLUMN_NUM, 2, 0, 0);
			main.newGame(gi);
		}
		else if(e.getSource() == plColour1Rad)
		{
		}
	}
	
}