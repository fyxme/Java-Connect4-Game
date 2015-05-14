/**
 * Main window
 * @author Michael Bernardi
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GamePanel extends JPanel implements MouseMotionListener,MouseListener{
	public GamePanel()
	{
		super();
		initUI();
	}
	
	private void initUI()
	{
		setBackground(new Color(210,210,210));
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	// all the private variables below are for demo purposes only.
	private Color[] slotColours = new Color[]
			{
				Color.WHITE, //empty cell
				Color.RED, //player one colour
				Color.YELLOW //player two colour
			};
	
	//*
	private int[][] demoArray = new int[][]
			{
				{0, 0, 0, 0, 0, 0, 0 },
				{0, 0, 1, 2, 0, 0, 0 },
				{0, 0, 2, 2, 2, 1, 0 },
				{0, 0, 1, 1, 2, 2, 0 },
				{0, 0, 1, 2, 1, 2, 0 },
				{0, 2, 1, 1, 1, 2, 1 }
			};
	//*/
	/*
	private int[][] demoArray = new int[][]
			{
				{0, 0, 0, 0, 0, 0, 0 },
				{0, 0, 0, 0, 0, 0, 0 },
				{0, 0, 0, 0, 0, 0, 0 },
				{0, 0, 0, 0, 0, 0, 0 },
				{0, 0, 0, 0, 0, 0, 0 },
				{0, 0, 0, 0, 0, 0, 0 },
			};
	//*/
	private boolean player1Turn = true;
	private int mousex, mousey;
	private boolean mouseIsInPanel = false;
	
	private int selectedColumn;
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//maybe theres a way to only run the next 3 lines once, as opposed to on every paint call.
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(Game.LINE_THICKNESS));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//makes things look nice
		
		if(mouseIsInPanel)
		{
			selectedColumn = mousex / (Game.CIRCLE_SPACE);
			if(selectedColumn < Game.COLUMNS)
			{
				g.setColor(new Color(100,100,255));
				g.drawRect(selectedColumn * Game.CIRCLE_SPACE, 0, Game.CIRCLE_SPACE, (int)getSize().getHeight() - 3);
			}
		}
		
		for( int x = 0; x < Game.COLUMNS; x++ )
		{
			for( int y = 0; y < Game.ROWS; y++ )
			{
				int xpos = (Game.CIRCLE_PADDING/2) + (Game.CIRCLE_SPACE)  * x;
				int ypos = (Game.CIRCLE_PADDING/2) + (Game.CIRCLE_SPACE)  * y;
				
				g.setColor(slotColours[demoArray[y][x]]);//change this depending on what's in the slot
				
				g.fillOval(xpos, ypos, Game.CIRCLE_WIDTH, Game.CIRCLE_WIDTH);
				g.setColor(Color.black);
				g.drawOval(xpos, ypos, Game.CIRCLE_WIDTH, Game.CIRCLE_WIDTH);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		//do nothing
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mousex = arg0.getX();
		mousey = arg0.getY();
		repaint();
		// set x and y positions of mouse then repaint.		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int memes = 14;
		if(selectedColumn >= 0 && selectedColumn < Game.COLUMNS)
		{
			for( int y = 0; y < Game.ROWS; y++)
			{
				if(demoArray[y][selectedColumn] != 0)
				{
					if(y > 0)
					{
						if(player1Turn)
							demoArray[y-1][selectedColumn] = 1;
						else
							demoArray[y-1][selectedColumn] = 2;
						player1Turn = !player1Turn;
					}
					break;
				}
				if(y == Game.ROWS - 1)
				{
					if(player1Turn)
						demoArray[y][selectedColumn] = 1;
					else
						demoArray[y][selectedColumn] = 2;
					player1Turn = !player1Turn;
				}
			}
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseIsInPanel = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseIsInPanel = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//do nothing
	}
}
