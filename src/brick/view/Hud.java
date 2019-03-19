/**
 * 
 */
package brick.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sun.javafx.geom.Rectangle;

import brick.controller.Controller;
import brick.model.GameObject;
import brick.utils.ID;

/**
 * @author BigRam
 *
 */
public class Hud extends GameObject {

	private int life;
	private int score;
	private Controller controller;

	/**
	 * @param x
	 * @param y
	 * @param id
	 */
	public Hud(int x, int y, ID id, Controller controller) {
		super(x, y, id);
		this.controller = controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see brick.model.GameObject#tick()
	 */
	@Override
	public void tick() {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see brick.model.GameObject#render(java.awt.Graphics)
	 */
	@Override
	public void render(Graphics g) {
		String strLife = "Life: " + life;
		String strScore = "Score:" + score;
		g.setColor(Color.BLACK);
		g.setFont(new Font("Sans Serif", Font.BOLD, 28));
		g.drawString(strLife, 10, 560);
		g.drawString(strScore, 600, 560);
		g.setColor(Color.decode("#a93e5a"));
		g.setFont(new Font("Sans Serif", Font.BOLD, 28));
		g.drawString(strLife, 10, 560);
		g.drawString(strScore, 600, 560);

	}

	/**
	 * @param life the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/* (non-Javadoc)
	 * @see brick.model.GameObject#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated constructor stub
		return null;
	}

}
