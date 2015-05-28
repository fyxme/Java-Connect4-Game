import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UIPanel extends JPanel implements ActionListener{
	private JButton restartButton;
	private JButton undoButton;
	private JButton redoButton;
	private JButton mainMenuButton;
	private JComboBox<String> difficultyMenu;
	private JCheckBox twoPlayer;
	private GameInstance gi;
	
	public UIPanel()
	{
		super();
		initUI();
	}
	
	public void setGameInstance(GameInstance gi) {
		this.gi = gi;
	}

	private void initUI() {
		restartButton = new JButton("Restart");
		restartButton.setBounds(10, 11, 89, 23);
		restartButton.addActionListener(this);
		add(restartButton);
		
		undoButton = new JButton("Undo");
		undoButton.setBounds(109, 11, 89, 23);
		undoButton.addActionListener(this);
		add(undoButton);
		
		redoButton = new JButton("Redo");
		redoButton.setBounds(208, 11, 89, 23);
		redoButton.addActionListener(this);
		add(redoButton);
		
		mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds(307, 11, 89, 23);
		mainMenuButton.addActionListener(this);
		add(mainMenuButton);
		setBackground(new Color(210,210,210));
		

		
		/**
		 * TEMPORARY CODE. DELETE WHEN MAIN MENU IS IMPLEMENTED.
		 */

		String[] difficulties = { "Easy", "Medium", "Hard"};
		difficultyMenu = new JComboBox<String>(difficulties);
		difficultyMenu.setBounds(406, 11, 89, 23);
		difficultyMenu.setSelectedIndex(0); //0 makes the default difficulty easy.
		difficultyMenu.addActionListener(this);
		add(difficultyMenu);

		
		twoPlayer = new JCheckBox("Two Player");
		twoPlayer.setSelected(true);
		twoPlayer.setBounds(505, 11, 89, 23);
		twoPlayer.addActionListener(this);
		twoPlayer.setBackground(new Color(210,210,210));
		add(twoPlayer);
		

		
		/**
		 * END TEMPORARY CODE.
		 */
		
		
		
		/*
		JButton onePlayer = new JButton("1 Player");
		onePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		onePlayer.setBounds(86, 48, 89, 23);
		frame.getContentPane().add(onePlayer);
		
		JButton twoPlayer = new JButton("2 Player");
		twoPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		twoPlayer.setBounds(86, 74, 89, 23);
		frame.getContentPane().add(twoPlayer);
		
		JButton help = new JButton("Help");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		help.setBounds(86, 100, 89, 23);
		frame.getContentPane().add(help);
		
		JButton easyAI = new JButton("Easy AI");
		easyAI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		easyAI.setBounds(86, 134, 89, 23);
		frame.getContentPane().add(easyAI);
		
		JButton mediumAI = new JButton("Medium AI");
		mediumAI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mediumAI.setBounds(86, 163, 89, 23);
		frame.getContentPane().add(mediumAI);
		
		JButton hardAI = new JButton("Hard AI");
		hardAI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		hardAI.setBounds(86, 198, 89, 23);
		frame.getContentPane().add(hardAI);
		*/
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == restartButton)
		{
			gi.restartGame();
		}
		else if(e.getSource() == undoButton)
		{
			gi.undoMove();
		}
		else if(e.getSource() == redoButton)
		{
			gi.redoMove();
		}
		else if(e.getSource() == mainMenuButton)
		{
			JOptionPane.showMessageDialog(null, "mainMenuButton not implemented");
		}
		/**
		 * TEMPORARY CODE. DELETE WHEN MAIN MENU IS IMPLEMENTED.
		 */
		else if(e.getSource() == difficultyMenu)
		{
			gi.changeDifficulty(difficultyMenu.getSelectedIndex());
		}
		else if(e.getSource() == twoPlayer)
		{
			gi.changeNumAI(twoPlayer.isSelected());
		}
		/**
		 * END TEMPORARY CODE.
		 */
	}
	
}