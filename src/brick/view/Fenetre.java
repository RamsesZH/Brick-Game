/**
 * 
 */
package brick.view;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Fenetre extends Canvas {
	
	private static final long serialVersionUID = -991468034398577403L;

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	private JFrame frame;
	
	public Fenetre() {
		frame = new JFrame("Brick Breaker");
		frame.setResizable(false);
		frame.setMaximumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
		
	}
	public JFrame getFrame() {
		return frame;
	}
}
