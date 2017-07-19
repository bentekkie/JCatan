package catan;

import java.awt.Shape;

public abstract class ScreenElement {
	private Shape area;

	/**
	 * @return the area
	 */
	public Shape getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Shape area) {
		this.area = area;
	}
	
	public abstract void click(int playerID);
	public abstract int priority();
}
