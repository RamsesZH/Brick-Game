/**
 * 
 */
package brick.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.sun.javafx.geom.Rectangle;

import brick.model.Game;
import brick.model.GameObject;
import brick.utils.ID;

/**
 * @author BigRam
 *
 */
public class Ball extends GameObject {

	private Color color;
	public static int WIDTH = 10;
	public static int HEIGHT = 10;
	private boolean isLost = false;

	private float xSpeed, ySpeed;
	Random random= new Random();

	/**
	 * 
	 * @param posX position in X
	 * @param posY position in Y
	 */
	public Ball(int posX, int posY, ID id, Color color) {
		super(posX, posY, id);
		setWidth(WIDTH);
		setHeight(HEIGHT);
		this.color = color;
		vitX = 0;
		vitY = 0;
		xSpeed = 4;
		ySpeed = -4;

	}

	public Color getColor() {
		return color;
	}

	/**
	 * @return the isLost
	 */
	public boolean isLost() {
		return isLost;
	}

	/**
	 * @param isLost the isLost to set
	 */
	public void setLost(boolean isLost) {
		this.isLost = isLost;
	}

	@Override
	public void tick() {
//		Random random = new Random();
//		float angle = (random.nextFloat() * 3.14159f * 2.0f);
//		System.out.println("angle:" + angle);
//		angle = .6f;
//		double ballDX = Math.cos(angle);
//		double ballDY = Math.sin(angle);
		
		
		x += vitX;
		y += vitY;
		// if wall is touched, then bounce
		// x coordinates
		if (x < 0 || x > Fenetre.WINDOW_WIDTH - width - width / 2)
			vitX *= -1;
		// top wall
		if (y < 0)
			vitY *= -1;
		
		// for bottom, it's different, ball can only bounce on paddle, otherwise, loose
		// a life
		if (y + height >= Game.PADDLE_Y && y + height < Game.PADDLE_Y + Game.paddle.getHeight() - 5
				&& x + width > Game.paddle.getX() && (x < Game.paddle.getX() + Game.paddle.getWidth())) {
			
			// // we are sure the ball hit the paddle
	
			vitY *= -1;
		}
		if (y + height > Game.PADDLE_Y + 100) {
			// loose a life
			isLost = true;
			System.out.println("Ball Lost");

		}

	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, getWidth(), getHeight());

	}

	@Override
	public Rectangle getBounds() {

		return new Rectangle(x, y, getWidth(), getHeight());
	}

	public float getXSpeed() {

		return xSpeed;
	}

	/**
	 * @return the ySpeed
	 */
	public float getYSpeed() {
		return ySpeed;
	}

	/**
	 * @param xSpeed the xSpeed to set
	 */
	public void setXSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * @param ySpeed the ySpeed to set
	 */
	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}

}
