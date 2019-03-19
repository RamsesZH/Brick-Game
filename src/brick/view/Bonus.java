/**
 * 
 */
package brick.view;

import com.sun.javafx.geom.Rectangle;

import brick.model.GameObject;
import brick.utils.ID;

/**
 * @author BigRam
 *
 */
public abstract class Bonus extends GameObject {

	private boolean isDiscovered = false;
	
	public Bonus(int x, int y, ID id) {
		super(x, y, id);
		vitY = 3.0f;
	}
	
	public boolean isDiscovered() {
		return isDiscovered;
	}
	
	public void setDiscovered(boolean isDiscovered) {
		this.isDiscovered = isDiscovered;
	}
	
//	@Override
//	public Rectangle getBounds() {
//		return new Rectangle(x, y, width, height);
//	}

}
