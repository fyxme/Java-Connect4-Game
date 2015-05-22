/**
 * Game screen.
 * @author Michael Bernardi
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements MouseMotionListener,MouseListener,ComponentListener,GameEventListener{
	private GameInstance gi = null;
	
	
	private static final float _CIRCLE_WIDTH = 50; // padding of circles;
	private static final float _CIRCLE_PADDING = 10; // padding in pixels between two drawn slots.
	private static final float _LINE_THICKNESS = 3;
	private static final float _CIRCLE_SPACE = _CIRCLE_WIDTH + _CIRCLE_PADDING;
	
	private static float CIRCLE_WIDTH = 50; // padding of circles;
	private static float CIRCLE_PADDING = 10; // padding in pixels between two drawn slots.
	private static float LINE_THICKNESS = 3;
	private static float CIRCLE_SPACE = CIRCLE_WIDTH + CIRCLE_PADDING;
	
	private Dimension initialSize;
	
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
		initialSize = new Dimension(
				(int)(MainFrame.DEFAULT_COLUMN_NUM * (CIRCLE_SPACE)),
				(int)(MainFrame.DEFAULT_ROW_NUM * (CIRCLE_SPACE) + 1));
		setPreferredSize(initialSize);	
		setBackground(new Color(150,150,150));
		addMouseMotionListener(this);
		addMouseListener(this);
		addComponentListener(this);
		
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
	private int screenLeft;
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		// maybe there's a way to only run the next 3 lines once, as opposed to on every paint call.
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(LINE_THICKNESS));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//makes things look nice
		
		//for some reason calculating this elsewhere results in inconsistencies if its not set properly before every paint call
		screenLeft = (int) ((getSize().getWidth() - gi.getBoard().numCol()*CIRCLE_SPACE)/2);//better to just do it here
		
		for( int y = 0; y < gi.getBoard().numRow(); y++ )
		{
			for( int x = 0; x < gi.getBoard().numCol(); x++ )
			{
				int xpos = (int) ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * x + screenLeft);
				int ypos = (int) ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * y);
				
				Participant oc = gi.getBoard().getTile(y,x).getOccupant();
				
				g.setColor(slotColours[0]);
				
				if (oc != null)
					g.setColor(slotColours[oc.getPid()+1]); // added +1 since pid starts at 0 and colors start at 1
				
				g.fillOval(xpos, ypos, (int)CIRCLE_WIDTH, (int)CIRCLE_WIDTH);
				g.setColor(Color.black);
				g.drawOval(xpos, ypos, (int)CIRCLE_WIDTH, (int)CIRCLE_WIDTH);
			}
		}
		if(gi.getWinner() == null)
		{
			if(mouseIsInPanel && gi.getBoard().hasEmptySlot() && selectedColumn != -1) //draw selection rectangle code
			{
				if(selectedColumn < gi.getBoard().numCol())
				{
					Participant oc = gi.getCurrentParticipant();
					g.setColor(slotColours[oc.getPid()+1]);
					g.drawRect((int)(selectedColumn * CIRCLE_SPACE + screenLeft), 0, (int)CIRCLE_SPACE, (int)(gi.getBoard().numRow() * CIRCLE_SPACE));
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
		selectedColumn = (int)((mousex-screenLeft) /  (CIRCLE_SPACE));
		if(selectedColumn < 0 || selectedColumn >= gi.getBoard().getNumberOfColumns() || mousex < screenLeft)
		{
			selectedColumn = -1;
		}
		repaint();
		// set x and y positions of mouse then repaint.		
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
		if (gi.getWinner() == null && gi.getBoard().hasEmptySlot() && selectedColumn != -1) {
			Participant curr = gi.getCurrentParticipant();
			gi.makeMove(curr.makeMove(selectedColumn));
			
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

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// do nothing		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// do nothing
		
	}
	
	boolean firstResize = true;
	@Override
	public void componentResized(ComponentEvent arg0) {
		Dimension newSize = getSize();
		float xratio = (float)newSize.getWidth() / (float)initialSize.getWidth();
		float yratio = (float)newSize.getHeight() / (float)initialSize.getHeight();
		float sizeRatio = xratio < yratio ? xratio : yratio; //min of two ratios.
		
		CIRCLE_WIDTH = sizeRatio * _CIRCLE_WIDTH; // padding of circles;
		CIRCLE_PADDING = sizeRatio * _CIRCLE_PADDING; // padding in pixels between two drawn slots.
		LINE_THICKNESS = sizeRatio * _LINE_THICKNESS;
		CIRCLE_SPACE = sizeRatio * _CIRCLE_SPACE;
		if(!firstResize)
		{
				repaint();
		}
		firstResize = false;
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// do nothing
		
	}
}