package ch.tbe.framework;

import java.awt.geom.Point2D;
import java.util.List;

import ch.tbe.jgraph.graph.DefaultEdge;
import ch.tbe.jgraph.graph.GraphConstants;

public abstract class ArrowItem extends ItemComponent
{
	protected DefaultEdge edge = new DefaultEdge();

	public ArrowItem(List<Point2D> points)
	{
		GraphConstants.setPoints(edge.getAttributes(), points);
		GraphConstants.setLineEnd(edge.getAttributes(),
				GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(edge.getAttributes(), true);
	}

	public void addPoint()
	{
		int size = GraphConstants.getPoints(edge.getAttributes()).size();
		double lastX = ((Point2D) GraphConstants
				.getPoints(edge.getAttributes()).get(size - 1)).getX();
		double lastY = ((Point2D) GraphConstants
				.getPoints(edge.getAttributes()).get(size - 1)).getY();
		GraphConstants.getPoints(edge.getAttributes()).add(
				new Point2D.Double(lastX + 10, lastY));

	}

	public void removePoint()
	{
		if (GraphConstants.getPoints(edge.getAttributes()).size() > 2)
		{
			int size = GraphConstants.getPoints(edge.getAttributes()).size();
			Point2D lastP = ((Point2D) GraphConstants.getPoints(
					edge.getAttributes()).get(size - 1));
			GraphConstants.getPoints(edge.getAttributes()).remove(lastP);

		}
	}

	public DefaultEdge getArrow()
	{
		return edge;
	}
}
