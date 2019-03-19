/**
 * 
 */
package brick.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sun.javafx.geom.Rectangle;

import brick.utils.ID;

/**
 * @author BigRam
 *
 */
public class BonusLargerPaddle extends Bonus {

	
	public BonusLargerPaddle(int x, int y, ID id) {
		super(x, y, id);
		
	}

	@Override
	public void tick() {	
		if (isDiscovered()) {
				y += vitY;
			}
		
	}

	@Override
	public void render(Graphics g) {
		if (isDiscovered()) {
			g.setColor(new Color(187, 100, 36));
			g.setFont(new Font("arial", Font.BOLD, 22));
			g.drawString("<====>", x, y);
		}
		
	}


	@Override
	public Rectangle getBounds() {
		
		return new Rectangle(x, y, width, height);
	}
	

}
