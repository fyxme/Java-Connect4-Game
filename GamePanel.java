/**
 * Game screen.
 * @author Michael Bernardi
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements MouseMotionListener,MouseListener,GameEventListener{
	private GameInstance gi = null;
	
	public void setGameInstance(GameInstance gi) {
		this.gi = gi;
		gi.addListener(this);
	}
	
	public GamePanel()
	{
		super();
		initUI();
	}
	
	private void initUI()
	{
		setLayout(new BorderLayout());//might be a better way to do this.
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setPreferredSize(new Dimension(
				MainFrame.DEFAULT_COLUMN_NUM * (MainFrame.CIRCLE_WIDTH + MainFrame.CIRCLE_PADDING),
				MainFrame.DEFAULT_ROW_NUM * (MainFrame.CIRCLE_WIDTH + MainFrame.CIRCLE_PADDING)));	
		setBackground(new Color(210,210,210));
		addMouseMotionListener(this);
		addMouseListener(this);
		
		//the next line needs to be looked at if we want proper fonts.
		this.setFont(this.getFont().deriveFont(Font.PLAIN, 24));
	}
	
	// all the private variables below are for demo purposes only.
	private Color[] slotColours = new Color[]
			{
				Color.WHITE, // empty cell
				Color.RED, // player one color
				Color.YELLOW // player two color
			};
	
	//*
	private int mousex, mousey;
	private boolean mouseIsInPanel = false;
	
	private int selectedColumn;
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// maybe there's a way to only run the next 3 lines once, as opposed to on every paint call.
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(MainFrame.LINE_THICKNESS));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//makes things look nice
		
		for( int x = 0; x < gi.getBoard().numRow(); x++ )
		{
			for( int y = 0; y < gi.getBoard().numCol(); y++ )
			{
				int xpos = (MainFrame.CIRCLE_PADDING/2) + (MainFrame.CIRCLE_SPACE)  * x;
				int ypos = (MainFrame.CIRCLE_PADDING/2) + (MainFrame.CIRCLE_SPACE)  * y;
				
				Participant oc = gi.getBoard().getTile(x,y).getOccupant();
				
				g.setColor(slotColours[0]);
				
				if (oc != null)
					g.setColor(slotColours[oc.getPid()+1]); // added +1 since pid starts at 0 and colors start at 1
				
				g.fillOval(ypos, xpos, MainFrame.CIRCLE_WIDTH, MainFrame.CIRCLE_WIDTH);
				g.setColor(Color.black);
				g.drawOval(ypos, xpos, MainFrame.CIRCLE_WIDTH, MainFrame.CIRCLE_WIDTH);
			}
		}
		if(gi.getWinner() == null)
		{
			if(mouseIsInPanel && gi.getBoard().hasEmptySlot()) //draw selection rectangle code
			{
				if(selectedColumn < gi.getBoard().numCol())
				{
					g.setColor(new Color(100,100,255));
					g.drawRect(selectedColumn * MainFrame.CIRCLE_SPACE, 0, MainFrame.CIRCLE_SPACE, (int)getSize().getHeight() - 3);
				}
			}
		}
		else //draw winner screen
		{
			//might be worth highlighting the winning four pieces here...
			
			//dark screen effect
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			g.setColor(Color.black);
			g.fillRect(0, 0, (int)getSize().getWidth(), (int)getSize().getHeight());
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
			
			//winner text
			FontMetrics fm = getFontMetrics(getFont());
			String str = "Player " + (gi.getWinner().getPid()+1) + " wins!";
			int strWidth = fm.stringWidth(str);
			int strHeight = (int) fm.getLineMetrics(str, g).getHeight();
			g.setColor(Color.white);
			g.drawString(str, ((int)getSize().getWidth() - strWidth)/2, ((int)getSize().getHeight() - strHeight)/2);
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
		if (gi.getWinner() == null && gi.getBoard().hasEmptySlot()) {
			int x = e.getX() /  (MainFrame.CIRCLE_SPACE); // column number
			
			Participant curr = gi.getCurrentParticipant();
			gi.makeMove(curr.makeMove(x));
//			if (gi.getWinner() != null || !(gi.getBoard().hasEmptySlot())) {
//				isOver = true;
//			}
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
		//might be worth moving the code in the mouse clicked event to here.
		//this is because mouse click doesn't fire if the mouse moves at all when
		//the button is released (can be annoying, also could be an accessibility issue)
	}

	@Override
	public void onGameEvent(int arg) {
		repaint();
	}
}