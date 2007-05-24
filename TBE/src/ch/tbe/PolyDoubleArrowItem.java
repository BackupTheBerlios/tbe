package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.graph.GraphConstants;

public class PolyDoubleArrowItem extends ArrowItem
{

	public PolyDoubleArrowItem(List<Point2D> points)
	{

		GraphConstants.setLineStyle(this.getAttributes(),
				GraphConstants.STYLE_DOUBLELINE);
		setPoints(points);
	}

}