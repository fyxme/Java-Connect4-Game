import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuPanel extends JPanel implements ActionListener{
	private JLabel logo;
	private JButton onePlayerButton;
	private JButton twoPlayerButton;
	private JButton instructionsButton;
	private JButton glassPanel1;
	private JButton glassPanel2;

	
	private MainFrame main;
	
	
	/**
	 * A method to initiate a main menu UI.
	 * @param mf 	the mainframe for which we want to draw a menu
	 */
	public MenuPanel(MainFrame mf)
	{
		super();
		main = mf;
		initUI();
	}

	
	/**
	 * Draws the necessary images and buttons
	 */
	private void initUI() {
		
		// The logo
		logo = new JLabel(new ImageIcon("src/logo.png"));
		logo.setAlignmentX(CENTER_ALIGNMENT);
		add(logo);
		
		// The one player button
		onePlayerButton = new JButton("1 Player");
		onePlayerButton.addActionListener(this);
		onePlayerButton.setAlignmentX(CENTER_ALIGNMENT);
		add(onePlayerButton);
		
		// To add a little blank space under the first button
		glassPanel1 = new JButton("");
		glassPanel1.setAlignmentX(CENTER_ALIGNMENT);
		glassPanel1.setOpaque(false);
		glassPanel1.setContentAreaFilled(false);
		glassPanel1.setBorderPainted(false);
		add(glassPanel1);


		// The two player button
		twoPlayerButton = new JButton("2 Player");
		twoPlayerButton.addActionListener(this);
		twoPlayerButton.setAlignmentX(CENTER_ALIGNMENT);
		add(twoPlayerButton);
		
		// To add a little blank space under the first button
		glassPanel2 = new JButton("");
		glassPanel2.setAlignmentX(CENTER_ALIGNMENT);
		glassPanel2.setOpaque(false);
		glassPanel2.setContentAreaFilled(false);
		glassPanel2.setBorderPainted(false);
		add(glassPanel2);
		
		// The instruction button
		instructionsButton = new JButton("Instructions");
		instructionsButton.addActionListener(this);
		instructionsButton.setAlignmentX(CENTER_ALIGNMENT);
		add(instructionsButton);
		
		setBackground(Color.white);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == onePlayerButton){
			main.changeOptiCard(true);
		}
		else if(e.getSource() == twoPlayerButton){
			main.changeOptiCard(false);
		}
		else if(e.getSource() == instructionsButton){
			main.changeCard(main.INSTRUCTIONS);			
		}
	}
	
}