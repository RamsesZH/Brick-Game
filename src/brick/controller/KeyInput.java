/**
 * 
 */
package brick.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import brick.model.GameObject;
import brick.utils.ID;
import brick.view.Paddle;

/**
 * @author BigRam
 *
 */
public class KeyInput extends KeyAdapter {

	private Controller controller;
	private boolean rightPressed;
	private boolean leftPressed;

	public KeyInput(Controller controller) {
		this.controller = controller;
		rightPressed = false;
		leftPressed = false;

	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for (GameObject object : controller.objects) {
			if (object.getId() == ID.Paddle) {

				if (key == KeyEvent.VK_RIGHT) {
					object.setVitX(Paddle.STEP);
					rightPressed = true;
				}
				if (key == KeyEvent.VK_LEFT) {
					object.setVitX(-Paddle.STEP);
					leftPressed = true;
				}
				if (key == KeyEvent.VK_SPACE) {
					if (!controller.getGame().isGameStarted()) {
						controller.getGame().setIsGameStarted(true);
						System.out.println("touche Space");
					}
				}
				if (!controller.getGame().isGameStarted()) {
					// stick the ball centered on the paddle
					controller.getGame().centerBall(controller.getGame().getLastBall());
				}
			}
		}

		if (key == KeyEvent.VK_ESCAPE)
			System.exit(1);
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for (GameObject object : controller.objects) {
			if (object.getId() == ID.Paddle) {
				if (key == KeyEvent.VK_RIGHT)
					rightPressed = false;
				if (key == KeyEvent.VK_LEFT)
					leftPressed = false;

				if (rightPressed == false && leftPressed == false)
					object.setVitX(0);
			}
		}
	}
}