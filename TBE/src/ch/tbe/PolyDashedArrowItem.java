package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.graph.GraphConstants;

public class PolyDashedArrowItem extends ArrowItem
{

	private final float[] DASH = new float[] { 6, 6 };

	public PolyDashedArrowItem(List<Point2D> points)
	{

		GraphConstants.setDashPattern(this.getAttributes(), DASH);
		setPoints(points);
	}

}
