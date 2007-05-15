package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.graph.GraphConstants;

public class PolyCurvedArrowItem extends ArrowItem
{

	public PolyCurvedArrowItem(List<Point2D> points)
	
	{
		super(points);
		GraphConstants.setLineStyle(edge.getAttributes(),
				GraphConstants.STYLE_CURVED);
	}

}