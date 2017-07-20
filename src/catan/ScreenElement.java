package catan;

import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class ScreenElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1154523057914430260L;
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
	private ArrayList<SerializableActionListener> listeners = new ArrayList<SerializableActionListener>();
	public void addActionListener(SerializableActionListener l){
		listeners.add(l);
	}
	public void click(int playerID){
		for(ActionListener l: listeners){
			l.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED, "click",playerID));
		}
		System.out.println("click" + this);
	}
	public abstract int priority();
}
