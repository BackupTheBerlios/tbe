package ch.tbe;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.graph.GraphConstants;

public class BezierDashedArrowItem extends ArrowItem
{

	private final float[] DASH = new float[] { 6, 6 };

	public BezierDashedArrowItem(Point2D.Double p)
	{
		super();
		GraphConstants.setLineStyle(this.getAttributes(),
		GraphConstants.STYLE_BEZIER);
		GraphConstants.setDashPattern(this.getAttributes(), DASH);
		List<Point2D.Double> points = new ArrayList<Point2D.Double>();
		points.add(p);
		points.add(new Point2D.Double(p.getX()+DEFAULTLENGTH, p.getY()));
		setPoints(points);
	}

}
