import java.awt.BorderLayout;
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

	
	/**
	 * A method to draw the instructions page. Adds the instructions image
	 * and a back button.
	 */
	private void initUI() {
		setLayout(new BorderLayout());
		
		insImg = new JLabel(new ImageIcon("src/instructions.png"));
		add(insImg, BorderLayout.CENTER);
		
		backButton = new JButton("Back");
		backButton.addActionListener(this);
		add(backButton, BorderLayout.PAGE_END);
		
		setBackground(Color.white);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton){
			main.changeCard(main.MENU); //goes back to the menu
		}
	}
	
}