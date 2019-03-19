
package brick.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import brick.model.Game;
import brick.model.Game.STATE;
import brick.model.GameObject;
import brick.utils.ID;
import brick.view.Ball;
import brick.view.Bonus;
import brick.view.BonusLargerPaddle;
import brick.view.Brick;
import brick.view.Fenetre;

/**
 * @author BigRam
 *
 */
public class Controller {

	private boolean lifeLost = false;
	private Fenetre fenetre;
	private Game game;
	private boolean isGameOver = false;

	public static ArrayList<GameObject> objects = new ArrayList<>();
	public static ArrayList<GameObject> toRemove = new ArrayList<>();

	public Controller(Game game, Fenetre fenetre) {
		this.game = game;
		this.fenetre = fenetre;

	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @return the fenetre
	 */
	public Fenetre getFenetre() {
		return fenetre;
	}

	private void gameLost() {
		System.out.println("Game over");
		game.setIsGameStarted(false);
		clearObjects();
		fenetre.repaint();
		isGameOver = true;
	}

	/**
	 * @param b
	 */
	public void setLifeLost(boolean b) {
		lifeLost = b;
		if (game.getLife() >= 0) {

			// remove the balls and the paddle from the GameObject array and create them for
			// a new try
			game.newTry();
		} else {
			// GameOver
			gameLost();
		}
	}

	public void tick() {

		for (GameObject object : objects) {
			object.tick();
			// check if ball is lost
			if (object.getId() == ID.Ball) {
				if (((Ball) object).isLost()) {
					looseLife();
					return;
				} else {
					collisionWithBrick((Ball) object);
				}
			}
			
			// check collision between paddle and bonus
			if (object.getId() == ID.BonusLargerPaddle) {
				Bonus bonus = (BonusLargerPaddle)object;
				if ((game.getPaddle().getBounds().toRectBounds().intersects((bonus.getBounds().toRectBounds())))){
					game.getPaddle().setWidth(game.getPaddle().getWidth() + 20);
					game.getPaddle().setX(game.getPaddle().getX() - 10);
					toRemove.add(bonus);
					System.out.println("bonus removed : "+ bonus.toString());
				}
			}
		}
		objects.removeAll(toRemove);
		// check if total number of bricks equals zero
		if (game.getNbBricks() == 0 && game.getScore() > 0) {
			// Level won
			setLevelWon();
		}

	}

	/**
	 * 
	 */
	private void setLevelWon() {
		game.setIsGameStarted(false);
		game.setLevelWon(true);
		clearObjects();
		fenetre.repaint();
	}

	/**
	 * 
	 */
	private void looseLife() {
		game.looseLife();
		game.getHud().setLife(game.getLife());
	}

	/**
	 * @param object
	 * @return
	 */
	private void collisionWithBrick(Ball ball) {
		for (GameObject object : objects) {
			if (object.getId() == ID.Brick) {
				Brick brick = (Brick) object;
				if (ball.getX() >= brick.getX() && ball.getX() < brick.getX() + Brick.WIDTH
						&& ball.getY() + ball.HEIGHT >= brick.getY() && ball.getY() < brick.getY() + Brick.HEIGHT) {
					/*
					 * ball has touched a brick
					 * 
					 * check which direction the ball should bounce to
					 */
					if (ball.getX() <= brick.getX()) {
						// touched the left side of the brick
						ball.setVitX(ball.getVitX() * -1);
					} else if (ball.getX() >= brick.getX() + Brick.WIDTH) {
						// touched the right side of the brick
						ball.setVitX(ball.getVitX() * -1);
					} else if (ball.getY() + ball.HEIGHT >= brick.getY()) {
						// touched the top side of the brick
						ball.setVitY(ball.getVitY() * -1);
					} else if (ball.getY() <= brick.getY() + Brick.WIDTH) {
						// touched the bottom side of the brick
						ball.setVitY(ball.getVitY() * -1);
					}
					// increment score
					score();

					// if brick contains a bonus, let the bonus fall
					if (brick.getBonus() != null) {
						brick.getBonus().setDiscovered(true);
					}

					((Brick) object).decreaseLife();
					if (((Brick) object).getLife() == 0) {
						// remove the brick
						toRemove.add(object);
						// decrease total number of bricks
						game.setNbBricks(game.getNbBricks() - 1);
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	private void score() {
		// update the variable score for the whole game
		game.addScore();
		// update the hud
		game.getHud().setScore(game.getScore());

	}

	public void render(Graphics g) {

		if (game.getState() == STATE.game) {
			for (GameObject object : objects) {
				object.render(g);
			}
			if (game.isLevelWon()) {
				g.setColor(Color.WHITE);
				Font font = new Font("Arial", Font.BOLD, 82);
				g.setFont(font);
				g.drawString("LEVEL WON", 160, 200);
			}
			if (isGameOver) {
				g.setColor(Color.WHITE);
				Font font = new Font("Arial", Font.BOLD, 82);
				g.setFont(font);
				g.drawString("GAME OVER", 160, 200);
			}

		} else if (game.getState() == STATE.menu) {
			game.getMenu().render(g);
		}

	}

	public void addObject(GameObject o) {
		this.objects.add(o);
	}

	public void removeObject(GameObject o) {
		this.objects.remove(o);
	}

	/**
	 * @return
	 */
	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	/**
	 * 
	 */
	public void clearObjects() {
		objects.clear();

	}

}
