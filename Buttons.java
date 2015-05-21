import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTextField;
import java.awt.Canvas;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Buttons {

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buttons window = new Buttons();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Buttons() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JButton btnNewButton = new JButton("New Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton.setBackground(new Color(255, 255, 255));
		buttonGroup.add(btnNewButton);
		mnNewMenu.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_1.setMinimumSize(new Dimension(83, 23));
		btnNewButton_1.setMaximumSize(new Dimension(83, 23));
		btnNewButton_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setForeground(Color.BLACK);
		mnNewMenu.add(btnNewButton_1);
		
		Canvas canvas = new Canvas();
		frame.getContentPane().add(canvas, BorderLayout.WEST);
	}

}