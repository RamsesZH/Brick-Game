/**
 * 
 */
package brick.view;

import java.awt.Color;
import java.awt.Graphics;

import com.sun.javafx.geom.Rectangle;
import com.sun.org.apache.bcel.internal.generic.IXOR;

import brick.controller.Controller;
import brick.model.GameObject;
import brick.utils.ID;

/**
 * @author BigRam
 *
 */
public class Paddle extends GameObject{

	private Color color;
	public static int WIDTH = 80;
	public static int HEIGHT = 8;
	public static int STEP = 8;
	
	private Controller controller;
	/**
	 * 
	 * @param posX  position in X
	 * @param posY  position in Y
	 */
	public Paddle(int posX, int posY, ID id, Color col, Controller controller) {
		super(posX, posY, id);
		this.color = col;
		setWidth(WIDTH);
		setHeight(HEIGHT);
		this.controller = controller;
		vitX = 0;
		vitY = 0;
	}
	
	public Color getColor() {
		return color;
	}

	@Override
	public void tick() {
		x += vitX;
		y += vitY;
		int x = controller.getGame().clamp(getX(), 0, Fenetre.WINDOW_WIDTH - getWidth());
		setX(x);	
		if (!controller.getGame().isGameStarted()) {
			// stick the ball centered on the paddle
			controller.getGame().centerBall(controller.getGame().getLastBall());
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, getWidth(), getHeight());
	}

	/* (non-Javadoc)
	 * @see brick.model.GameObject#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		
		return new Rectangle(x,y,getWidth(),getHeight());
	}
	
	
}
