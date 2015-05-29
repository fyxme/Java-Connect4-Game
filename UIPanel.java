package asdasd;

import java.awt.BorderLayout;

public class asdas {

	private JFrame frame;
	private JPanel panelHolder[][];
	private Map<String, JButton> buttonMap = new HashMap<String, JButton>();
	private Map<String, ImageIcon> imageMap = new HashMap<String, ImageIcon>();
	
	private void changeStyle(JButton button){
		button.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		button.setBackground(Color.LIGHT_GRAY);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					asdas window = new asdas();
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
	
	
	public asdas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setTitle("Connect 4");
		frame.setBounds(100, 100, 600, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		int i  = 7;
		int j = 5;
		panelHolder = new JPanel[i][j];
		
		JPanel centrePanel = new JPanel();
		frame.getContentPane().add(centrePanel, BorderLayout.CENTER);
		centrePanel.setLayout(new GridLayout(i, j, i, j));
		
		
		for(int m = 0; m < i; m++) {
		   for(int n = 0; n < j; n++) {
		      panelHolder[m][n] = new JPanel(new GridLayout());
		      centrePanel.add(panelHolder[m][n]);
		   }
		}
		JButton onePlayer = new JButton("1 Player");
		onePlayer.setToolTipText("play against AI player");
		buttonMap.put("onePlayer",onePlayer);
		JButton twoPlayer = new JButton("2 Player");
		twoPlayer.setToolTipText("play against human player");
		buttonMap.put("twoPlayer",twoPlayer);
		JButton help = new JButton("Help");
		buttonMap.put("help", help);
		help.setToolTipText("how to play connect 4");
		JButton easyAi = new JButton("Easy Ai");
		buttonMap.put("easyAi", easyAi);
		JButton mediumAi = new JButton("Medium Ai");
		buttonMap.put("mediumAi", mediumAi);
		JButton hardAi = new JButton("Hard Ai");
		buttonMap.put("hardAi", hardAi);
		JButton mainMenu = new JButton("Main menu");
		buttonMap.put("mainMenu", mainMenu);
		createMainMenu(returnPanelHolder());
		ImageIcon title = new ImageIcon("4.PNG");
		JPanel topPanel = new JPanel();
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(i, j, i, j));
		frame.setIconImage(title.getImage());
		changeStyle(onePlayer);
	}
	
	private void clearPanelHolder(JPanel panelHolder[][]){
		int i = 7;
		int j = 5;
		for(int m = 0; m < i; m++) {
			   for(int n = 0; n < j; n++) {
			      panelHolder[m][n].removeAll();;
			   }
		}
	}
	
	private void createMainMenu(JPanel panelHolder[][]){
		clearPanelHolder(returnPanelHolder());
		panelHolder[1][2].add(buttonMap.get("onePlayer"));
		panelHolder[2][2].add(buttonMap.get("twoPlayer"));
		panelHolder[3][2].add(buttonMap.get("help"));
	}
	
	private void createOnePlayerMenu(JPanel panelHolder[][]){
		clearPanelHolder(returnPanelHolder());
		panelHolder[1][2].add(buttonMap.get("easyAi"));
		panelHolder[2][2].add(buttonMap.get("mediumAi"));
		panelHolder[3][2].add(buttonMap.get("hardAi"));
		panelHolder[4][2].add(buttonMap.get("mainMenu"));
	}
	
	
	
	private void createHelpMenu(JPanel panelHolder[][]){
		clearPanelHolder(returnPanelHolder());
		
	}
	
	
	
	
	private JPanel[][] returnPanelHolder(){
		return panelHolder;
	}
	
	private JButton returnButton(String string){
		return buttonMap.get(string);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttonMap.get("twoPlayer")){
			buttonMap.get("onePlayer").setVisible(true);
		}
	
	}
	
	
	
		
	
	

}
