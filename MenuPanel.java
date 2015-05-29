import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuPanel extends JPanel implements ActionListener{
	private JLabel logo;
	private JButton easyButton;
	private JButton medButton;
	private JButton hardButton;
	private JButton multiButton;
	private MainFrame main;
	
	public MenuPanel(MainFrame mf)
	{
		super();
		main = mf;
		initUI();
	}

	private void initUI() {
		logo = new JLabel(new ImageIcon("src/logo.jpg"));
		logo.setAlignmentX(CENTER_ALIGNMENT);
		add(logo);
		
		easyButton = new JButton("Easy");
		easyButton.setBounds(10, 11, 89, 23);
		easyButton.addActionListener(this);
		easyButton.setAlignmentX(CENTER_ALIGNMENT);
		add(easyButton);
		
		medButton = new JButton("Medium");
		medButton.setBounds(10, 41, 89, 23);
		medButton.addActionListener(this);
		medButton.setAlignmentX(CENTER_ALIGNMENT);
		add(medButton);
		
		hardButton = new JButton("Hard");
		hardButton.setBounds(10, 71, 89, 23);
		hardButton.addActionListener(this);
		hardButton.setAlignmentX(CENTER_ALIGNMENT);
		add(hardButton);
		
		multiButton = new JButton("Multiplayer");
		multiButton.setBounds(10, 101, 89, 23);
		multiButton.addActionListener(this);
		multiButton.setAlignmentX(CENTER_ALIGNMENT);
		add(multiButton);
		setBackground(Color.white);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == easyButton){
			GameInstance gi = new GameInstance(main.DEFAULT_ROW_NUM,
					main.DEFAULT_COLUMN_NUM, 1, 1, 0);
			main.newGame(gi);
		}
		else if(e.getSource() == medButton){
			GameInstance gi = new GameInstance(main.DEFAULT_ROW_NUM,
					main.DEFAULT_COLUMN_NUM, 1, 1, 1);
			main.newGame(gi);
			
		}
		else if(e.getSource() == hardButton){
			GameInstance gi = new GameInstance(main.DEFAULT_ROW_NUM,
					main.DEFAULT_COLUMN_NUM, 1, 1, 2);
			main.newGame(gi);
			
		}
		else if(e.getSource() == multiButton){
			GameInstance gi = new GameInstance(main.DEFAULT_ROW_NUM,
					main.DEFAULT_COLUMN_NUM, 2, 0, 0);
			main.newGame(gi);
			
		}
	}
	
}