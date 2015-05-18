import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.*;

import javax.print.DocFlavor.URL;
import javax.swing.*;

public class DrawPanel extends JButton {
	public DrawPanel(String label, int sizeInput){
		super(label);

		// These statements enlarge the button so that it 
		// becomes a circle rather than an oval.
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(sizeInput, 
				sizeInput);
		setPreferredSize(size);



		// This call causes the JButton not to paint 
		// the background.
		// This allows us to paint a round background.
		setContentAreaFilled(false);

	}

	// Paint the round background and label.
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			// You might want to make the highlight color 
			// a property of the RoundButton class.
			// g.setColor(Color.red);
		} else {
			g.setColor(getBackground());
		}
		g.fillOval(0, 0, getSize().width-1, 
				getSize().height-1);

		// This call will paint the label and the 
		// focus rectangle.
		super.paintComponent(g);
	}

	// Paint the border of the button using a simple stroke.
	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawOval(0, 0, getSize().width-1, 
				getSize().height-1);
	}

	// Hit detection.
	/*Shape shape;
public boolean contains(int x, int y) {
// If the button has changed size, 
   // make a new shape object.
   if (shape == null || 
      !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0, 
        getWidth(), getHeight());
    } 
    return shape.contains(x, y);
  }*/

	// Test routine.
	public static void main(String[] args) {
		// Create a button with the label "Jackpot".
		JFrame frame = new JFrame();
		//frame.getContentPane().setBackground(Color.blue);
		int col = 7;
		int row = 7;
		frame.getContentPane().setLayout(new GridLayout(col,row));
		frame.setLayout(null);
		// frame.setBounds(50, 50, 80, 25);
		frame.setVisible(true);
		frame.setSize(new Dimension(51*row, 55*col));
		frame.setTitle("Four in a row");	
		frame.setResizable(false);
		ImageIcon icon = new ImageIcon("smiley.png");
		frame.setIconImage(icon.getImage());
		for(int i = 0; i!= row ;i++){
			for(int j = 0; j!= col; j++){
				JButton button = new RoundButton("",100);
				button.setBounds(i*50,j*50,50,50);
				button.setBackground(Color.white);
				frame.getContentPane().add(button);
				button.setVisible(true);
				System.out.println(button.getBounds().y/button.getBounds().width);



			}
		}

		// Create a frame in which to show the button.

	}
}