package ch.tbe.framework;

import java.awt.geom.Point2D;
import java.util.List;

import ch.tbe.jgraph.graph.DefaultEdge;
import ch.tbe.jgraph.graph.GraphConstants;

public abstract class ArrowItem extends ItemComponent
{

	protected ArrowItem(List<Point2D> points)
	{
		GraphConstants.setPoints(this.getAttributes(), points);
		GraphConstants.setLineEnd(this.getAttributes(),
				GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(this.getAttributes(), true);
		
	}

	public void addPoint()
	{
		int size = GraphConstants.getPoints(this.getAttributes()).size();
		double lastX = ((Point2D) GraphConstants
				.getPoints(this.getAttributes()).get(size - 1)).getX();
		double lastY = ((Point2D) GraphConstants
				.getPoints(this.getAttributes()).get(size - 1)).getY();
		GraphConstants.getPoints(this.getAttributes()).add(
				new Point2D.Double(lastX + 10, lastY));

	}

	public void removePoint()
	{
		if (GraphConstants.getPoints(this.getAttributes()).size() > 2)
		{
			int size = GraphConstants.getPoints(this.getAttributes()).size();
			Point2D lastP = ((Point2D) GraphConstants.getPoints(
					this.getAttributes()).get(size - 1));
			GraphConstants.getPoints(this.getAttributes()).remove(lastP);

		}
	}

}
