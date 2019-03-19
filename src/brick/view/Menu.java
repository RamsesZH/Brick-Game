/**
 * 
 */
package brick.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import brick.controller.Controller;
import brick.model.Game;
import brick.model.Game.STATE;

/**
 * @author BigRam
 *
 */
public class Menu extends MouseAdapter{
	
	private Game game;

	/**
	 * 
	 */
	public Menu(Game game) {
		
		this.game = game;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (mouseOver(mx, my, 300, 200, 200, 80)) {
			// clicked on PLay
			game.setState(STATE.game);
			game.setIsGameStarted(true);
			System.out.print("Dans le bouton Play");
			game.initGame();
			game.createBricks();
			game.createPaddle();
			game.createBall();
			game.createHud();
			
			
		}else if (mouseOver(mx, my, 300, 300, 200, 80)) {
			// click on Help
			
			
		}else if (mouseOver(mx, my, 300, 400, 200, 80)) {
			// click on Quit
			System.exit(1);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	
	public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			}
		}
		return false;		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		Font font = new Font("Arial", Font.BOLD, 62);
		g.setFont(font);
		g.drawString("MENU", 300, 120);
		// draw buttons
		
		Font font2 = new Font("Arial", Font.BOLD, 40);
		g.setFont(font2);
		
		g.drawRect(300, 200, 200, 80);
		g.drawString("Play", 360, 250);
		
		g.drawRect(300, 300, 200, 80);
		g.drawString("Help", 360, 350);
		
		g.drawRect(300, 400, 200, 80);
		g.drawString("Quit", 360, 450);
	
	}
	/**
	 * 
	 */
	public void tick() {
		// TODO Auto-generated constructor stub
		
	}
}
