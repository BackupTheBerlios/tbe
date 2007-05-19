package ch.tbe.framework;

import ch.tbe.jgraph.graph.DefaultGraphCell;

public abstract class ItemComponent extends DefaultGraphCell {
	private int x,y;
 
	public void ItemComponent(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
 
