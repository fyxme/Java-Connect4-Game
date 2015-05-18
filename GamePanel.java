/**
 * Main window
 * @author Michael Bernardi
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
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
		g2d.setStroke(new BasicStroke(MainFrame.LINE_THICKNESS));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//makes things look nice
		
		if(mouseIsInPanel) //draw selection rectangle code
		{
			if(selectedColumn < gi.getBoard().numCol())
			{
				g.setColor(new Color(100,100,255));
				g.drawRect(selectedColumn * MainFrame.CIRCLE_SPACE, 0, MainFrame.CIRCLE_SPACE, (int)getSize().getHeight() - 3);
			}
		}
		
		for( int x = 0; x < gi.getBoard().numRow(); x++ )
		{
			for( int y = 0; y < gi.getBoard().numCol(); y++ )
			{
				int xpos = (MainFrame.CIRCLE_PADDING/2) + (MainFrame.CIRCLE_SPACE)  * x;
				int ypos = (MainFrame.CIRCLE_PADDING/2) + (MainFrame.CIRCLE_SPACE)  * y;
						
				Participant oc = gi.getBoard().getTile(x,y).getOccupant();
				
				g.setColor(slotColours[0]);
				
				if (oc != null)
					g.setColor(slotColours[oc.getPid()]); //change this depending on what's in the slot
				
				g.fillOval(xpos, ypos, MainFrame.CIRCLE_WIDTH, MainFrame.CIRCLE_WIDTH);
				g.setColor(Color.black);
				g.drawOval(xpos, ypos, MainFrame.CIRCLE_WIDTH, MainFrame.CIRCLE_WIDTH);
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
		selectedColumn = mousex /  (MainFrame.CIRCLE_SPACE);
		repaint();
		// set x and y positions of mouse then repaint.		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//do something with selectedColumn
		
		System.out.println(mousex);
		
		Participant curr = gi.getCurrentParticipant();
		gi.makeMove(curr.makeMove(mousey,mousex), curr);
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