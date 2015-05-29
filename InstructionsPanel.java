import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class InstructionsPanel extends JPanel implements ActionListener{
	private JLabel insImg;
	private JButton backButton;
	private MainFrame main;
	
	public InstructionsPanel(MainFrame mf)
	{
		super();
		main = mf;
		initUI();
	}

	private void initUI() {
		insImg = new JLabel(new ImageIcon("src/logo.jpg"));
		insImg.setAlignmentX(CENTER_ALIGNMENT);
		add(insImg);
		
		backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setAlignmentX(CENTER_ALIGNMENT);
		add(backButton);
		
		setBackground(Color.white);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton){
			main.changeCard(main.MENU); //goes back to the menu
		}
	}
	
}