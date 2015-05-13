package kk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

class DrawPanel extends JPanel {

    private void doDrawing(Graphics g) {
    

        Graphics2D g2d = (Graphics2D) g;
        int column = 5;
        int row = 5;
        g2d.setColor(new Color(218, 218, 218));
        for(int j = 0; j != row; j++){
           	for(int i = 0; i != column; i++){
        		g2d.drawOval(35 * i, 35 * j, 30, 30);
        		g2d.setColor(new Color(0, 0, 0));
        		g2d.fillOval(35*i, 35*j, 30, 30);
        	}
        }
}
    private void createLayout(JComponent... arg) {

        Container pane = getRootPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );
    }
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        doDrawing(g);
    }
}
