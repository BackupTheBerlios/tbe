package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.graph.GraphConstants;

public class BezierDashedArrowItem extends ArrowItem
{

	private final float[] DASH = new float[] { 6, 6 };

	public BezierDashedArrowItem(List<Point2D> points)
	{
		super(points);
		GraphConstants.setLineStyle(edge.getAttributes(),
				GraphConstants.STYLE_BEZIER);
		GraphConstants.setDashPattern(edge.getAttributes(), DASH);
	}

}
