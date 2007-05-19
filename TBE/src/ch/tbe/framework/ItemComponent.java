package ch.tbe.framework;

import ch.tbe.jgraph.graph.DefaultEdge;



public abstract class ItemComponent extends DefaultEdge{
	private int x,y;
 
	public void ItemComponent(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
 
