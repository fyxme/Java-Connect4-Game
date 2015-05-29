import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class OptionsPanel extends JPanel implements ActionListener{
	private JPanel cards;
	private JPanel aiCard;
	private JPanel twoPlayerCard;
	
	private MainFrame main;
	
	private final static String AI = "ai";
	
	public OptionsPanel(MainFrame mf)
	{
		super();
		main = mf;
		initUI();
	}

	private void initUI() {
		
		aiCard = new JPanel();
		twoPlayerCard = new JPanel();
		cards = new JPanel(new CardLayout());
		cards.add(aiCard,"menu");
		cards.add(twoPlayerCard,"game");
		
		add(cards);
		
		setBackground(Color.white);
		
	}
	
	public void setAIScreen(boolean aiScreen)
	{//change cards here.
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//if(e.getSource() == backButton){
		//	main.changeCard(main.MENU); //goes back to the menu
		//}
	}
	
}