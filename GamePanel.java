/**
 * Game screen.
 * @author Michael Bernardi
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, MouseMotionListener,MouseListener,ComponentListener,GameEventListener{
	private GameInstance gi = null;
	
	
	private static final float _CIRCLE_WIDTH = 50; // padding of circles;
	private static final float _CIRCLE_PADDING = 10; // padding in pixels between two drawn slots.
	private static final float _LINE_THICKNESS = 3;
	private static final float _CIRCLE_SPACE = _CIRCLE_WIDTH + _CIRCLE_PADDING;
	
	private static final int _ANIM_FPS = 30; //frames per second
	private static final float _ANIM_SPEED = 10; //slots per second
	
	private static final int _FONT_SIZE = 24;
	private static final int _FONT_SHIFT = 1;
	
	private float CIRCLE_WIDTH = _CIRCLE_WIDTH; // padding of circles;
	private float CIRCLE_PADDING = _CIRCLE_PADDING; // padding in pixels between two drawn slots.
	private float LINE_THICKNESS = _LINE_THICKNESS;
	private float CIRCLE_SPACE = _CIRCLE_WIDTH + _CIRCLE_PADDING;
	
	private int FONT_SHIFT = _FONT_SHIFT;
	
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
		
		//TODO: proper fonts for end screen.
		//the next line needs to be looked at if we want proper fonts.
		this.setFont(this.getFont().deriveFont(Font.PLAIN, _FONT_SIZE));
	}
	
	// all the private variables below are for demo purposes only.
	private Color[] slotColours = new Color[]
			{
				Color.WHITE, // empty cell
				Color.ORANGE, // player one color
				new Color(204,0,0) // player two color
				
				/*
				 * All Colors:
				 * BLUE 	: new Color(0, 76, 153)
				 * RED		: new Color(204, 0, 0)
				 * GREEN	: new Color(0, 153, 76)
				 * ORANGE	: Color.ORANGE
				 */
			};
	
	//*
	private int mousex, mousey;
	private boolean mouseIsInPanel = false;
	
	private int selectedColumn; // -1 for invalid columns
	private int screenLeft;
	
	private int animX;
	private int animY;
	private float animYCur;
	private Color animColour;
	private boolean animating = false;
	
	private Timer animTimer = new Timer(1000/_ANIM_FPS, this); //30fps timer.
	
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
		
		for( int y = 0; y < gi.getBoard().numRow(); y++ ) //draw circles on board.
		{
			for( int x = 0; x < gi.getBoard().numCol(); x++ )
			{
				int xpos = (int) ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * x + screenLeft);
				int ypos = (int) ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * y);
				
				Participant oc = gi.getBoard().getTile(y,x).getOccupant();
				
				g.setColor(slotColours[0]);
				
				if (oc != null && !(animating && x == animX && y == animY))
					g.setColor(slotColours[oc.getPid()+1]); // added +1 since pid starts at 0 and colors start at 1
				
				g.fillOval(xpos, ypos, (int)CIRCLE_WIDTH, (int)CIRCLE_WIDTH);
				
			}
		}
		if(animating) // draw animation code
		{// repainting the whole board just to animate is inefficient.
		 // but it shouldn't matter since it's connect 4, not connect of duty 4: modern fourfare.
			int xpos = (int) ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * animX + screenLeft);
			int ypos = (int) ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * animYCur);
		
			g.setColor(animColour);
			
			Shape s = g.getClip();

			float yClipPos = ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * (int)animYCur);
			g.setClip(new Ellipse2D.Float(xpos, yClipPos, CIRCLE_WIDTH, CIRCLE_WIDTH));
			g.fillOval(xpos, ypos, (int)CIRCLE_WIDTH, (int)CIRCLE_WIDTH);
			g.setClip(new Ellipse2D.Float(xpos, yClipPos + CIRCLE_SPACE, CIRCLE_WIDTH, CIRCLE_WIDTH));
			g.fillOval(xpos, ypos, (int)CIRCLE_WIDTH, (int)CIRCLE_WIDTH);
			
			g.setClip(s);
		}
		
		for( int y = 0; y < gi.getBoard().numRow(); y++ ) //draw circle outlines
		{
			for( int x = 0; x < gi.getBoard().numCol(); x++ )
			{
				int xpos = (int) ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * x + screenLeft);
				int ypos = (int) ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * y);
				//outline
				g.setColor(Color.black);
				if(gi.getWinner() != null && !animating) // code for blue highlighting of winning tiles.
				{
					int[][] winningTiles = gi.getBoard().getWinningTiles();
					for(int i = 0; i < winningTiles.length; i++)
					{
						if(winningTiles[i][0] == y && winningTiles[i][1] == x)
						{
							g.setColor(Color.WHITE);
						}
					}
				}
				g.drawOval(xpos, ypos, (int)CIRCLE_WIDTH, (int)CIRCLE_WIDTH);
			}
		}
		
		if(gi.getWinner() == null && !animating) //draw selection rectangle code
		{
			if(mouseIsInPanel && gi.getBoard().hasEmptySlot() && selectedColumn != -1) 
			{
				if(selectedColumn < gi.getBoard().numCol())
				{
					Participant oc = gi.getCurrentParticipant();
					g.setColor(slotColours[oc.getPid()+1]);
					g.drawRect((int)(selectedColumn * CIRCLE_SPACE + screenLeft), 0, (int)CIRCLE_SPACE, (int)(gi.getBoard().numRow() * CIRCLE_SPACE));
				}
			}
		}
		else if(!animating) //draw winner screen
		{			
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
			drawOutlineString(g,str, ((int)getSize().getWidth() - strWidth)/2, ((int)getSize().getHeight() - strHeight)/2);
		}
	}
	
	private void drawOutlineString(Graphics g, String str, int x, int y)
	{
		g.setColor(Color.black);
		g.drawString(str, x-FONT_SHIFT,y-FONT_SHIFT);
		g.drawString(str, x+FONT_SHIFT,y-FONT_SHIFT);
		g.drawString(str, x-FONT_SHIFT,y+FONT_SHIFT);
		g.drawString(str, x+FONT_SHIFT,y+FONT_SHIFT);
		
		g.drawString(str, x+FONT_SHIFT,y);
		g.drawString(str, x-FONT_SHIFT,y);
		g.drawString(str, x,y+FONT_SHIFT);
		g.drawString(str, x,y-FONT_SHIFT);
		g.setColor(Color.white);
		g.drawString(str, x,y);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		//do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		updateMouse(e);
		//might be worth moving the code in the mouse clicked event to here.
		//this is because mouse click doesn't fire if the mouse moves at all when
		//the button is released (can be annoying, also could be an accessibility issue)
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		updateMouse(e);
		// set x and y positions of mouse then repaint.		
	}
	
	private void updateMouse(MouseEvent e)
	{
		if(!animating)
		{
			mousex = e.getX();
			mousey = e.getY();
			selectedColumn = (int)((mousex-screenLeft) /  (CIRCLE_SPACE));
			if(selectedColumn < 0 || selectedColumn >= gi.getBoard().getNumberOfColumns() || mousex < screenLeft)
			{
				selectedColumn = -1;
			}
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
<<<<<<< HEAD
		if (gi.getWinner() == null && gi.getBoard().hasEmptySlot() && selectedColumn != -1) {
=======
		if (gi.getCurrentParticipant().getClass().getName()=="Player"&&gi.getWinner() == null && gi.getBoard().hasEmptySlot() && selectedColumn != -1) {
			Participant curr = gi.getCurrentParticipant();
>>>>>>> 8fa87abe94dc7da65a96184d6ca64e51365d9dcb
			
			animY = gi.getBoard().getColumnSpace(selectedColumn);
			animColour = slotColours[gi.getCurrentParticipant().getPid() + 1];
			// curr.makeMove(selectedColumn)
			if(gi.makeMove(selectedColumn));
			{
				animX = selectedColumn;
				animYCur = -1;
				selectedColumn = -1;
				
				animating = true;
				animTimer.start();
			}
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
	public void onGameEvent(int arg) {
		animating = false;
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
		
		//adjust winning screen font size
		this.setFont(this.getFont().deriveFont(Font.PLAIN, sizeRatio * _FONT_SIZE));
		FONT_SHIFT = (int) (sizeRatio *_FONT_SHIFT);
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == animTimer)
		{
			if(!animating)
			{
				animTimer.stop();
			}
			else
			{
				animYCur += _ANIM_SPEED / _ANIM_FPS;
				if(animYCur >= animY)
				{
					animating = false;
					animTimer.stop();
					//need to run update mouse to update the selected column.
					Point mousePoint = MouseInfo.getPointerInfo().getLocation();
					updateMouse(new MouseEvent(this, 0, 0, 0, mousePoint.x, mousePoint.y, 0, false));
				}
				repaint();
			}

		}
	}
}