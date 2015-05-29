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
	
	private MainFrame main;
	
	public MenuPanel(MainFrame mf)
	{
		super();
		main = mf;
		initUI();
	}

	private void initUI() {
		Dimension preferredSize = new Dimension(300, 100);
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(50,50,55,55);//set padding here
		c.anchor = c.CENTER;
		c.fill = c.HORIZONTAL;
		c.weighty = 1;
		c.weightx = 1;
		
		c.gridx = 0;
		c.gridy = 0;
		logo = new JLabel(new ImageIcon("src/logo.png"));
		//logo.setAlignmentX(CENTER_ALIGNMENT);
		add(logo,c);
		c.gridy++;
		
		onePlayerButton = new JButton("1 Player");
		onePlayerButton.addActionListener(this);
		//onePlayerButton.setAlignmentX(CENTER_ALIGNMENT);
		onePlayerButton.setPreferredSize(preferredSize);
		add(onePlayerButton,c);
		c.gridy++;
		
		twoPlayerButton = new JButton("2 Player");
		twoPlayerButton.addActionListener(this);
		//twoPlayerButton.setAlignmentX(CENTER_ALIGNMENT);
		add(twoPlayerButton,c);
		c.gridy++;
		
		instructionsButton = new JButton("Instructions");
		instructionsButton.addActionListener(this);
		//instructionsButton.setAlignmentX(CENTER_ALIGNMENT);
		add(instructionsButton,c);
		c.gridy++;
		
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