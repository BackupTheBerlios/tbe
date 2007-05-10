package ch.tbe.schrott;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Vector;

import ch.tbe.jgraph.graph.*;

public class Kurvaegraf extends DefaultEdge
{
	private int style = GraphConstants.STYLE_BEZIER;
	static final int ARROW = GraphConstants.ARROW_CLASSIC;
	
	List<Point2D> myPoints2 = new Vector();
	
	public Kurvaegraf(List<Point2D> points){
		GraphConstants.setLineStyle(this.getAttributes(), style);
		
		myPoints2.add(points.get(0));
		myPoints2.add(points.get(1));
		
		
		
		GraphConstants.setPoints(this.getAttributes(), myPoints2);
		GraphConstants.setLineEnd(this.getAttributes(), ARROW);
		GraphConstants.setEndFill(this.getAttributes(), true);
	}
}
