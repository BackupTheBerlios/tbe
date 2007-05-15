package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.graph.GraphConstants;

public class PolyCurvedBlockItem extends ArrowItem
{

	public PolyCurvedBlockItem(List<Point2D> points)
	
	{
		super(points);
		GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_BLOCK);
		GraphConstants.setLineStyle(edge.getAttributes(),
				GraphConstants.STYLE_CURVED);
	}

}