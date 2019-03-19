/**
 * 
 */
package brick.view;

import java.awt.Color;
import java.awt.Graphics;

import com.sun.javafx.geom.Rectangle;

import brick.model.GameObject;
import brick.utils.ID;

public class Brick extends GameObject {

	private Color color;
	public static int WIDTH = 50;
	public static int HEIGHT = 20;
	private int life;
	Bonus bonus;
	

	/**
	 * 
	 * @param posX position in X
	 * @param posY position in Y
	 */
	public Brick(int posX, int posY, ID id, int life, Bonus bonus) {
		super(posX, posY, id);
		setHeight(HEIGHT);
		setWidth(WIDTH);
		this.bonus = bonus;
		this.life = life;
		changeColor(life);
	}

	/**
	 * 
	 */
	private void changeColor(int life) {
		// if brick color is red (#be191c), life = 1 | if blue (#4a6582 ) life = 2| if
		// light grey (#e5e2c2 ) life = 3
		switch (life) {
		case 1:
			color = Color.decode("#be191c");
			break;

		case 2:
			color = Color.decode("#4a6582");
			break;

		case 3:
			color = Color.decode("#e5e2c2");
			break;
		default:
			color = Color.decode("#000");
			break;
		}
	}

	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}
	
	/**
	 * @param life the life to update
	 */
	public void decreaseLife() {
		this.life--;
		changeColor(life);
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, WIDTH, HEIGHT);

	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}
	
	@Override
	public Rectangle getBounds() {
		
		return new Rectangle(x,y,getWidth(),getHeight());
	}
	

	public Bonus getBonus() {
		return bonus;
	}
	
	
}
