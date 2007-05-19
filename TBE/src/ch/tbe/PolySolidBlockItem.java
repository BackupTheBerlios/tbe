package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.graph.GraphConstants;

public class PolySolidBlockItem extends ArrowItem
{

	public PolySolidBlockItem(List<Point2D> points)
	{
		super(points);
		GraphConstants.setLineEnd(this.getAttributes(), GraphConstants.ARROW_BLOCK);

	}

}