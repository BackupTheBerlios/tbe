package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.TBEGraphConstants;

public class BezierDashedArrowItem extends ArrowItem
{

	private final float[] DASH = new float[] { 6, 6 };

	public BezierDashedArrowItem(List<Point2D> points)
	{
		super();
		TBEGraphConstants.setLineStyle(this.getAttributes(),
		TBEGraphConstants.STYLE_BEZIER);
		TBEGraphConstants.setDashPattern(this.getAttributes(), DASH);
		setPoints(points);
	}

}
