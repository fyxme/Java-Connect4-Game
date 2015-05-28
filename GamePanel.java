/**
 * Game screen.
 * @author Michael Bernardi
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;
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
	private static final int _FONT_SHIFT = 4;

	private float CIRCLE_WIDTH = _CIRCLE_WIDTH; // padding of circles;
	private float CIRCLE_PADDING = _CIRCLE_PADDING; // padding in pixels between two drawn slots.
	private float LINE_THICKNESS = _LINE_THICKNESS;
	private float CIRCLE_SPACE = _CIRCLE_WIDTH + _CIRCLE_PADDING;

	private int FONT_SHIFT = _FONT_SHIFT;

	private Dimension initialSize;

	private Color empty_cell = Color.WHITE;
	
	public GamePanel()
	{
		super();
		initUI();
	}
	
	/**
	 * Sets the GameInstance object that this panel retrieves game information from.
	 * @param gi The game instance object.
	 */
	public void setGameInstance(GameInstance gi) {
		this.gi = gi;
		gi.addListener(this);
	}

	/**
	 * Initialises the UI. This involves setting up the layout and size, as well as adding the relevant listeners.
	 */
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

		//the next line needs to be looked at if we want non-default fonts. But I think the default one is portable and looks fine.
		this.setFont(this.getFont().deriveFont(Font.PLAIN, _FONT_SIZE));
	}

	//*
	private int mousex, mousey;
	private boolean mouseIsInPanel = false;

	private int selectedColumn; // -1 for invalid columns
	private int screenLeft;
	private int screenTop;

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
		screenTop = (int) ((getSize().getHeight() - gi.getBoard().numRow()*CIRCLE_SPACE)/2);//better to just do it here
		
		// drawing code in general is a bit messy because of the drawing orders i need to keep to in order to make sure 
		// stuff is drawn right and prevent artifacts. 
		for( int y = 0; y < gi.getBoard().numRow(); y++ ) //draw circles on board.
		{
			for( int x = 0; x < gi.getBoard().numCol(); x++ )
			{
				Point2D.Float screenCoord = boardToScreenSpace(new Point2D.Float(x,y));

				Participant oc = gi.getBoard().getTile(y,x).getOccupant();

				g.setColor(empty_cell);

				if (oc != null && !(animating && x == animX && y == animY))
					g.setColor(oc.getColor()); // added +1 since pid starts at 0 and colors start at 1

				g.fillOval((int)screenCoord.getX(), (int)screenCoord.getY(), (int)CIRCLE_WIDTH, (int)CIRCLE_WIDTH);

			}
		}
		if(animating) // draw animation code
		{// repainting the whole board just to animate is inefficient.
			// but it shouldn't matter since it's connect 4, not connect of duty 4: modern fourfare.
			Point2D.Float screenCoord = boardToScreenSpace(new Point2D.Float(animX,animYCur));
			float yClipPos = (float) boardToScreenSpace(new Point2D.Float(animX,(int)animYCur)).getY();

			Shape s = g.getClip(); //save clip
			
			g.setColor(animColour);
			if(animYCur > -1)
			{// stops the animation rendering above the board.
				g.setClip(new Ellipse2D.Float((int)screenCoord.getX(), yClipPos, CIRCLE_WIDTH, CIRCLE_WIDTH));
				g.fillOval((int)screenCoord.getX(), (int)screenCoord.getY(), (int)CIRCLE_WIDTH, (int)CIRCLE_WIDTH);
			}
			g.setClip(new Ellipse2D.Float((int)screenCoord.getX(), yClipPos + CIRCLE_SPACE, CIRCLE_WIDTH, CIRCLE_WIDTH));
			g.fillOval((int)screenCoord.getX(), (int)screenCoord.getY(), (int)CIRCLE_WIDTH, (int)CIRCLE_WIDTH);

			g.setClip(s); //restore clip
		}
		for( int y = 0; y < gi.getBoard().numRow(); y++ ) // draw circle outlines
		{
			for( int x = 0; x < gi.getBoard().numCol(); x++ )
			{
				Point2D.Float screenCoord = boardToScreenSpace(new Point2D.Float(x,y));
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
				g.drawOval((int)screenCoord.getX(), (int)screenCoord.getY(), (int)CIRCLE_WIDTH, (int)CIRCLE_WIDTH);
			}
		}
		if(gi.getWinner() == null && !animating) //draw selection rectangle code
		{
			if(mouseIsInPanel && gi.getBoard().hasEmptySlot() && selectedColumn != -1) 
			{
				if(selectedColumn < gi.getBoard().numCol())
				{
					Point2D.Float screenCoord = boardToScreenSpace(new Point2D.Float(selectedColumn,0)); 
					Participant oc = gi.getCurrentParticipant();
					g.setColor(oc.getColor());
					g.drawRect((int)(screenCoord.getX() - CIRCLE_PADDING/2), (int)(screenCoord.getY() - CIRCLE_PADDING/2), (int)CIRCLE_SPACE, (int)(gi.getBoard().numRow() * CIRCLE_SPACE));
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
			drawOutlineString((Graphics2D) g, getFont(), str, (getSize().getWidth() - strWidth)/2d, (getSize().getHeight() - strHeight)/2d);
		}
	}
	
	/**
	 * Converts a board space coordinate to a screen space coordinate.
	 * @param boardSpace The board space coordinate.
	 * @return The resulting screens space coordinate.
	 */
	private Point2D.Float boardToScreenSpace(Point2D.Float boardSpace)
	{
		int x = (int) ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * boardSpace.getX() + screenLeft);
		int y = (int) ((CIRCLE_PADDING/2) + (CIRCLE_SPACE)  * boardSpace.getY() + screenTop);
		return new Point2D.Float(x,y);
	}

	/**
	 * Draws a s
	 * @param g2d The Graphics2D object used to draw.
	 * @param f The font used to draw.
	 * @param str The string to draw.
	 * @param x The x position on the screen to draw.
	 * @param y The y position on the screen to daw.
	 */
	private void drawOutlineString(Graphics2D g2d, Font f, String str, double x, double y)
	{
		Stroke oldStroke = g2d.getStroke();
		
		g2d.setStroke(new BasicStroke(FONT_SHIFT,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		TextLayout t = new TextLayout(str,f, g2d.getFontRenderContext());
		g2d.setColor(Color.black);
		g2d.draw(t.getOutline(AffineTransform.getTranslateInstance(x, y)));
		g2d.setColor(Color.white);
		g2d.drawString(str, (int)x,(int)y);
		
		g2d.setStroke(oldStroke);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		updateMouse(e);
	}
	
	int pressedColumn = -1;
	@Override
	public void mousePressed(MouseEvent e) {
		updateMouse(e);
		pressedColumn = selectedColumn;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		updateMouse(e);
		if(selectedColumn == pressedColumn)
		{
			//below this line is the code for handling mouse clicks.
			if (gi.getWinner() == null && gi.getBoard().hasEmptySlot() && selectedColumn != -1) {
				if (gi.getCurrentParticipant().getClass().getName()=="Player"&&gi.getWinner() == null && gi.getBoard().hasEmptySlot() && selectedColumn != -1) {
					
					animY = gi.getBoard().getColumnSpace(selectedColumn);
					animColour = gi.getCurrentParticipant().getColor();
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
		}
		pressedColumn = -1;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		updateMouse(e);
		// set x and y positions of mouse then repaint.		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// do nothing.
	}
	
	private void updateMouse(MouseEvent e)
	{
		if(!animating)
		{
			mousex = e.getX();
			mousey = e.getY();
			selectedColumn = (int)((mousex-screenLeft) /  (CIRCLE_SPACE));
			
			if(selectedColumn >= gi.getBoard().getNumberOfColumns() || //detects if mouse is right of board.
					selectedColumn < 0 || // makes sure negative numbers result in selected column = -1 (required even with next line because of floating point issues)
					mousex < screenLeft ||// detects if mouse is left of board
					mousey > screenTop + CIRCLE_SPACE * gi.getBoard().getNumberOfRows() || // detects if mouse is below board
					mousey < screenTop - CIRCLE_PADDING/2 + LINE_THICKNESS || // detects if mouse is above board
					(pressedColumn != -1 && selectedColumn != pressedColumn))//stops highlighting adjacent columns when you click and drag.
			{
				selectedColumn = -1;
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
		{// this stops repaint being called right when window opens which causes a flicker on the screen.
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
					Point screenLoc = getLocationOnScreen();
					updateMouse(new MouseEvent(this, 0, 0, 0, mousePoint.x - screenLoc.x, mousePoint.y - screenLoc.y, 0, false));
				}
				repaint();
			}
		}
	}
}