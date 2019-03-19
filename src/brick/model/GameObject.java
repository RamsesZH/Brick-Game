/**
 * 
 */
package brick.model;

import java.awt.Graphics;

import com.sun.javafx.geom.Rectangle;

import brick.utils.ID;

/**
 * @author BigRam
 *
 */
public abstract class GameObject{
	
	protected int x, y;
	protected ID id;
	protected float vitX, vitY;
	protected int width, height;
	
	/**
	 * 
	 */
	public GameObject(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public ID getId() {
		return id;
	}

	public float getVitX() {
		return vitX;
	}

	public float getVitY() {
		return vitY;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public void setVitX(float vitX) {
		this.vitX = vitX;
	}

	public void setVitY(float f) {
		this.vitY = f;
	}

	
}
