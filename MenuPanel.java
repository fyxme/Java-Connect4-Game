import java.awt.Color;
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
		logo = new JLabel(new ImageIcon("src/logo.png"));
		logo.setAlignmentX(CENTER_ALIGNMENT);
		add(logo);
		
		onePlayerButton = new JButton("1 Player");
		onePlayerButton.addActionListener(this);
		onePlayerButton.setAlignmentX(CENTER_ALIGNMENT);
		add(onePlayerButton);
		
		twoPlayerButton = new JButton("2 Player");
		twoPlayerButton.addActionListener(this);
		twoPlayerButton.setAlignmentX(CENTER_ALIGNMENT);
		add(twoPlayerButton);
		
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