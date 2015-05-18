/**
 * Main window
 * @author Michael Bernardi
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GamePanel extends JPanel implements MouseMotionListener,MouseListener{
	public int winner = 0;
	
	private GameInstance gi = null;

	public void setGameInstance(GameInstance gi) {
		this.gi = gi;
	}
	
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
		
		if(mouseIsInPanel) //draw selection rectangle code
		{
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
				
				
				
				Occupant oc = board[y][x].getOccupant().getId();
				
				
				if (oc == null) {
					g.setColor(slotColours[0]);	
				} else {
					g.setColor(slotColours[board[y][x].getOccupant().getId()]);//change this depending on what's in the slot
				}
				
				
				
				
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
		selectedColumn = mousex /  (Game.CIRCLE_SPACE);
		repaint();
		// set x and y positions of mouse then repaint.		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//do something with selectedColumn
		repaint();
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