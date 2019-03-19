package brick.model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.sun.org.glassfish.external.statistics.BoundaryStatistic;

import brick.controller.Controller;
import brick.controller.KeyInput;
import brick.utils.ID;
import brick.view.Ball;
import brick.view.Bonus;
import brick.view.BonusLargerPaddle;
import brick.view.Brick;
import brick.view.Fenetre;
import brick.view.Hud;
import brick.view.Menu;
import brick.view.Paddle;

/**
 * @author BigRam
 *
 */
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -3291259578636120204L;

	private static final int NB_COL = 10;

	private static final int NB_LINES = 2;

	Thread thread;
	Fenetre fenetre;

	public static int PADDLE_Y;

	private boolean running = false;
	private Controller controller;
	private int gridStartX, gridStartY, space;
	private boolean isGameStarted;
	private int paddleStepX, paddleStepY;
	int pause = 10;
	private boolean lifeLost = false;
	private int life;
	private int score;
	private boolean readyToPlay;
	public static Paddle paddle;
	private Ball lastBall;
	public Hud hud;
	private int nbBricks;
	private boolean isGameOver = false;
	private int currentLevel;
	private boolean levelWon;
	private Menu menu;
	private STATE state = STATE.menu;
	private ArrayList<Integer> brickWithBonuses =  new ArrayList<>();
	
	Random r = new Random();

	private int nbOfBonuses;

	public Game() {
		fenetre = new Fenetre();
		controller = new Controller(this, fenetre);
		fenetre.getFrame().add(this);
		PADDLE_Y = fenetre.getFrame().getHeight() - 100;
		this.addKeyListener(new KeyInput(controller));
		this.menu = new Menu(this);
		this.addMouseListener(menu);
		this.start();
//		if (state == STATE.game) {
//			initGame();
//			createBricks();
//			createPaddle();
//			createBall();
//			createHud();
//		}
	}

	public void initGame() {
		gridStartX = 100;
		gridStartY = 100;
		space = 5;
		isGameStarted = false;
		paddleStepX = 20;
		paddleStepY = 0;
		life = 1;
		currentLevel = 1;
		levelWon = false;
		isGameOver = false;
	}

	public void createPaddle() {

		// calculate center X
		paddle = new Paddle(0, PADDLE_Y, ID.Paddle, Color.gray, controller);
		int centerX = fenetre.getFrame().getWidth() / 2 - paddle.getWidth() / 2;
		paddle.setX(centerX);
		paddle.setY(paddle.getY());
		controller.addObject(paddle);

	}

	public void createBall() {
		Ball ball = new Ball(0, 0, ID.Ball, Color.WHITE);
		lastBall = ball;
		centerBall(ball);
		controller.addObject(ball);
	}

	public void createHud() {
		hud = new Hud(0, 0, ID.Hud, controller);
		hud.setLife(life);
		hud.setScore(score);
		controller.addObject(hud);
	}

	public void createBricks() {
		
		generateBonuses(currentLevel);
		int k = 0;
		Brick br;
		for (int i = 0; i < NB_LINES; i++) {
			// line
			gridStartY += Brick.HEIGHT + space;
			for (int j = 0; j < NB_COL; j++) {
				if (((i +1) * j) == brickWithBonuses.get(k)) {
					Bonus bonus = new BonusLargerPaddle(gridStartX + 10, gridStartY + 5, ID.BonusLargerPaddle);
					controller.addObject(bonus);
					br = new Brick(gridStartX, gridStartY, ID.Brick, 1, bonus);	
					if (k < 2)
						k++;
				}else
					br = new Brick(gridStartX, gridStartY, ID.Brick, 1, null);	
				gridStartX += Brick.WIDTH + space;
				controller.addObject(br);
				nbBricks++;
			}
			gridStartX = 100;
		}
	}

	public void centerBall(Ball b) {
		int posX = paddle.getX() + (paddle.getWidth() / 2) - b.getWidth() / 2;
		int posY = paddle.getY() - b.getHeight();
		b.setX(posX);
		b.setY(posY);

	}

	public void setIsGameStarted(boolean state) {
		isGameStarted = state;
		if (isGameStarted == true) {
			// ball has been launched
			// increase ball velocity
			moveBall();
		}
	}

	/**
	 * @return
	 */
	public boolean isGameStarted() {
		return isGameStarted;
	}

	/**
	 * @return the nbBricks
	 */
	public int getNbBricks() {
		return nbBricks;
	}

	/**
	 * @param nbBricks the nbBricks to set
	 */
	public void setNbBricks(int nbBricks) {
		this.nbBricks = nbBricks;
	}

	/**
	 * @return the paddleStepX
	 */
	public int getPaddleStepX() {
		return paddleStepX;
	}

	/**
	 * @return the paddleStepY
	 */
	public int getPaddleStepY() {
		return paddleStepY;
	}

	/**
	 * @param paddleStepX the paddleStepX to set
	 */
	public void setPaddleStepX(int paddleStepX) {
		this.paddleStepX = paddleStepX;
	}

	/**
	 * @param paddleStepY the paddleStepY to set
	 */
	public void setPaddleStepY(int paddleStepY) {
		this.paddleStepY = paddleStepY;
	}

	/**
	 * @return the endPaddleX
	 */
	public float getEndPaddleX() {
		return paddle.getX() + paddle.getWidth();
	}

	/**
	 * 
	 */
	public void looseLife() {
		System.out.println("You lose a life");
		life--;
		controller.setLifeLost(true);
	}

	public boolean isReadyToPlay() {
		return readyToPlay;
	}

	public void setReadyToPlay(boolean readyToPlay) {
		this.readyToPlay = readyToPlay;
	}

	public int getLife() {
		return life;
	}

	/**
	 * @param life the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	public void gameOver() {
		stopThread();
	}

	public synchronized void start() {

		thread = new Thread(this);
		thread.start();
		running = true;

	}

	public synchronized void stopThread() {

		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0.0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stopThread();
	}

	private void tick() {
		if (getState() == STATE.game) {
			controller.tick();

		} else if (getState() == STATE.menu) {
			menu.tick();
		}

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		// background color
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, fenetre.getFrame().getWidth(), fenetre.getFrame().getHeight());

		controller.render(g);

		g.dispose();
		bs.show();
	}

	public Paddle getPaddle() {
		return paddle;
	}

	/**
	 * @return the hud
	 */
	public Hud getHud() {
		return hud;
	}

	public void moveBall() {
		for (GameObject object : controller.getObjects())
			if (object.getId() == ID.Ball) {
				object.setVitX(((Ball) object).getXSpeed());
				object.setVitY(((Ball) object).getYSpeed());
			}
	}

	/**
	 * @return
	 */
	public Ball getLastBall() {
		return lastBall;
	}

	/**
	 * 
	 */
	public void newTry() {
		for (GameObject object : controller.objects) {
			if (object.getId() == ID.Ball || object.getId() == ID.Paddle || object.getId() == ID.Hud) {
				// remove
				controller.toRemove.add(object);
				setIsGameStarted(false);
			}
		}
		controller.objects.removeAll(controller.toRemove);
		// now prepare the game for a new try
		// new paddle
		createPaddle();
		createBall();
		createHud();
	}

	/**
	 * 
	 */
	public void addScore() {
		score += 10;
	}

	public int clamp(int x, int min, int max) {
		if (x < min)
			x = min;
		else if (x > max)
			x = max;

		return x;
	}

	/**
	 * @return the isGameOver
	 */
	public boolean isGameOver() {
		return isGameOver;
	}

	/**
	 * @param isGameOver the isGameOver to set
	 */
	public void setGameOver(boolean isGameOver) {

		this.isGameOver = isGameOver;
	}

	/**
	 * @param levelWon the levelWon to set
	 */
	public void setLevelWon(boolean levelWon) {
		this.levelWon = levelWon;
		currentLevel++;
	}

	/**
	 * @return the levelWon
	 */
	public boolean isLevelWon() {
		return levelWon;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(STATE state) {
		this.state = state;
	}

	/**
	 * @return the state
	 */
	public STATE getState() {
		return state;
	}

	public enum STATE {
		game, menu;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}
	
	private void generateBonuses(int currentLevel){
		
		if (currentLevel == 1) {
			// 3 bonuses to generate
			nbOfBonuses = 3;
			int value;
			for (int i = 0; i < nbOfBonuses; i++) {
				do {
					value = r.nextInt(NB_LINES * NB_COL +1);
				}while (brickWithBonuses.contains(value));
				brickWithBonuses.add(value);
				System.out.println(brickWithBonuses.get(i));
			}
			Collections.sort(brickWithBonuses);
			System.out.println(Arrays.asList(brickWithBonuses));
		}
		
	}
}
