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
	private JPanel tpCard;
	
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
	private JLabel p1Label;
	private JTextField p1Name;
	private JLabel p2Label;
	private JTextField p2Name;
	
	private JButton backTPButton;
	private JButton playTPButton;
	
	private ButtonGroup p1ColourGroup;
	private JRadioButton p1Colour1Rad;
	private JRadioButton p1Colour2Rad;
	private JRadioButton p1Colour3Rad;
	private JRadioButton p1Colour4Rad;
	
	private ButtonGroup p2ColourGroup;
	private JRadioButton p2Colour1Rad;
	private JRadioButton p2Colour2Rad;
	private JRadioButton p2Colour3Rad;
	private JRadioButton p2Colour4Rad;
	
	//other variables
	private MainFrame main;
	
	private final static String AI = "ai";
	private final static String TP = "twoplayer";
	
	
	
	/**
	 * A method to intiate the drawing of an options panel
	 * @param mf	The mainframe for which we want to draw the options panel
	 */
	public OptionsPanel(MainFrame mf)
	{
		super();
		main = mf;
		initUI();
		createLayout();
	}

	
	
	/**
	 * Draws the necessary buttons for the options screens.
	 * There are essentially two parts to this method, one of which draws the option
	 * screen for a game between two players, and the other draws the option screen for
	 * a game between a player and an AI.
	 */
	private void initUI() {
		Dimension preferredRadSize = new Dimension(80,20);
			
		//1 vs 1 screen
		p1Label = new JLabel("Player 1:");
		p1Name = new JTextField("player 1");	
		p2Label = new JLabel("Player 2:");
		p2Name = new JTextField("player 2");
		
		p1Colour1Rad = new JRadioButton();
		p1Colour1Rad.setPreferredSize(preferredRadSize);
		p1Colour1Rad.setBackground(GameInstance.slotColours[0]);
		p1Colour1Rad.addActionListener(this);
		
		p1Colour2Rad = new JRadioButton();
		p1Colour2Rad.setPreferredSize(preferredRadSize);
		p1Colour2Rad.setBackground(GameInstance.slotColours[1]);
		p1Colour2Rad.addActionListener(this);
		
		p1Colour3Rad = new JRadioButton();
		p1Colour3Rad.setPreferredSize(preferredRadSize);
		p1Colour3Rad.setBackground(GameInstance.slotColours[2]);
		p1Colour3Rad.addActionListener(this);
		
		p1Colour4Rad = new JRadioButton();
		p1Colour4Rad.setPreferredSize(preferredRadSize);
		p1Colour4Rad.setBackground(GameInstance.slotColours[3]);
		p1Colour4Rad.addActionListener(this);
		
		
		p1ColourGroup = new ButtonGroup();
		p1ColourGroup.add(p1Colour1Rad);
		p1ColourGroup.add(p1Colour2Rad);
		p1ColourGroup.add(p1Colour3Rad);
		p1ColourGroup.add(p1Colour4Rad);
		
		p1ColourGroup.setSelected(p1Colour1Rad.getModel(), true);
		
		
		p2Colour1Rad = new JRadioButton();
		p2Colour1Rad.setPreferredSize(preferredRadSize);
		p2Colour1Rad.setBackground(GameInstance.slotColours[0]);
		p2Colour1Rad.addActionListener(this);
		
		p2Colour2Rad = new JRadioButton();
		p2Colour2Rad.setPreferredSize(preferredRadSize);
		p2Colour2Rad.setBackground(GameInstance.slotColours[1]);
		p2Colour2Rad.addActionListener(this);
		
		p2Colour3Rad = new JRadioButton();
		p2Colour3Rad.setPreferredSize(preferredRadSize);
		p2Colour3Rad.setBackground(GameInstance.slotColours[2]);
		p2Colour3Rad.addActionListener(this);
		
		p2Colour4Rad = new JRadioButton();
		p2Colour4Rad.setPreferredSize(preferredRadSize);
		p2Colour4Rad.setBackground(GameInstance.slotColours[3]);
		p2Colour4Rad.addActionListener(this);
		
		p2ColourGroup = new ButtonGroup();
		p2ColourGroup.add(p2Colour1Rad);
		p2ColourGroup.add(p2Colour2Rad);
		p2ColourGroup.add(p2Colour3Rad);
		p2ColourGroup.add(p2Colour4Rad);
		
		p2ColourGroup.setSelected(p2Colour2Rad.getModel(), true);
		
		playTPButton = new JButton("Play");
		playTPButton.addActionListener(this);
		backTPButton = new JButton("Back");
		backTPButton.addActionListener(this);
		
		
		
		
		
		
		//AI screen
		youLabel = new JLabel("You:");
		youName = new JTextField("Player 1");
		
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
		
		
		
		
		
		// Cards
		
		aiCard = new JPanel();
		//aiCard.setBackground(Color.blue);
		
		tpCard = new JPanel();
		//twoPlayerCard.setBackground(Color.green);
		
		cards = new JPanel(new CardLayout());
		cards.add(aiCard,AI);
		cards.add(tpCard,TP);
		add(cards);
		
		setBackground(Color.white);
		
	}
	
	
	/**
	 * Draws all the necessary buttons using a grid
	 */
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
		c.anchor = c.CENTER;
		
		tpCard.setLayout(new GridBagLayout());
		
		// PLAYER 1 labels
		c.gridx = 0;
		c.gridy = 0;
		tpCard.add(p1Label,c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.anchor = c.WEST;
		c.fill = c.HORIZONTAL;
		tpCard.add(p1Name,c);
		c.anchor = c.CENTER;
		c.gridwidth = 1;
		//colour buttons
		c.gridy++;
		c.gridx = 0;
		tpCard.add(p1Colour1Rad,c);
		c.gridx = 1;
		tpCard.add(p1Colour2Rad,c);
		c.gridx = 2;
		tpCard.add(p1Colour3Rad,c);
		c.gridx = 3;
		tpCard.add(p1Colour4Rad,c);
		
		c.gridy++;
		c.gridx = 0;

		// PLAYER 2 labels
		tpCard.add(p2Label,c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.anchor = c.WEST;
		c.fill = c.HORIZONTAL;
		tpCard.add(p2Name,c);
		c.anchor = c.CENTER;
		c.gridwidth = 1;
		//colour buttons
		c.gridy++;
		c.gridx = 0;
		tpCard.add(p2Colour1Rad,c);
		c.gridx = 1;
		tpCard.add(p2Colour2Rad,c);
		c.gridx = 2;
		tpCard.add(p2Colour3Rad,c);
		c.gridx = 3;
		tpCard.add(p2Colour4Rad,c);
		
		c.gridy++;
		c.gridx = 0;
		tpCard.add(backTPButton,c);
		c.gridx = 3;
		tpCard.add(playTPButton,c);
	}
	
	
	/**
	 * A method to intiate the showing of either the 2 player screen, or 1 player screen
	 * @param aiScreen	A boolean, true if one player, false if two player
	 */
	public void setAIScreen(boolean aiScreen)
	{
		 CardLayout cl = (CardLayout)(cards.getLayout());
		 cl.show(cards, aiScreen ? AI : TP );
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == backAIButton || e.getSource() == backTPButton){
			main.changeCard(main.MENU); //goes back to the menu
		} else if(e.getSource() == playAIButton){
			// Get the difficulty from the check boxes
			int difficulty = 3;
			if (diffHardRad.isSelected()) {
				difficulty = 9;
			} else if (diffMedRad.isSelected()) {
				difficulty = 6;
			}
			
			// Set up the game instance with one player and one AI of specified difficulty
			GameInstance gi = new GameInstance(main.DEFAULT_ROW_NUM,
					main.DEFAULT_COLUMN_NUM, 1, 1, difficulty);
			
			Player pl = null;
			AI ai = null;
			
			if (gi.getCurrentParticipant() instanceof Player) {
				pl = (Player) gi.getCurrentParticipant();
				ai = (AI) gi.getOtherParticipant(gi.getCurrentParticipant());
			} else {
				ai = (AI) gi.getCurrentParticipant();
				pl = (Player) gi.getOtherParticipant(gi.getCurrentParticipant());
			}
			
			pl.setName(youName.getText());
			
			if (aiColour1Rad.isSelected()) {
				ai.setColor(aiColour1Rad.getBackground());
			} else if (aiColour2Rad.isSelected()) {
				ai.setColor(aiColour2Rad.getBackground());
			} else if (aiColour3Rad.isSelected()) {
				ai.setColor(aiColour3Rad.getBackground());
			} else {
				ai.setColor(aiColour4Rad.getBackground());
			}
			
			if (plColour1Rad.isSelected()) {
				pl.setColor(plColour1Rad.getBackground());
			} else if (plColour2Rad.isSelected()) {
				pl.setColor(plColour2Rad.getBackground());
			} else if (plColour3Rad.isSelected()) {
				pl.setColor(plColour3Rad.getBackground());
			} else {
				pl.setColor(plColour4Rad.getBackground());
			}

			main.newGame(gi);
		} else if(e.getSource() == playTPButton){
			GameInstance gi = new GameInstance(main.DEFAULT_ROW_NUM,
					main.DEFAULT_COLUMN_NUM, 2, 0, 0);
			
			Player p1 = (Player) gi.getCurrentParticipant();
			Player p2 = (Player) gi.getOtherParticipant(gi.getCurrentParticipant());
			
			// Set the names
			p1.setName(p1Name.getText());
			p2.setName(p2Name.getText());

			// Sest the colours
			if (p1Colour1Rad.isSelected()) {
				p1.setColor(p1Colour1Rad.getBackground());
			} else if (p1Colour2Rad.isSelected()) {
				p1.setColor(p1Colour2Rad.getBackground());
			} else if (p1Colour3Rad.isSelected()) {
				p1.setColor(p1Colour3Rad.getBackground());
			} else {
				p1.setColor(p1Colour4Rad.getBackground());
			}
			
			if (p2Colour1Rad.isSelected()) {
				p2.setColor(p2Colour1Rad.getBackground());
			} else if (p2Colour2Rad.isSelected()) {
				p2.setColor(p2Colour2Rad.getBackground());
			} else if (p2Colour3Rad.isSelected()) {
				p2.setColor(p2Colour3Rad.getBackground());
			} else {
				p2.setColor(p2Colour4Rad.getBackground());
			}
			
			main.newGame(gi);
		}
	}
	
}