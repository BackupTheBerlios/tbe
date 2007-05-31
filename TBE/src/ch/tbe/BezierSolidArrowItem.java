package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.TBEGraphConstants;

public class BezierSolidArrowItem extends ArrowItem
{

	public BezierSolidArrowItem(List<Point2D> points)
	{
		TBEGraphConstants.setLineStyle(this.getAttributes(),
				TBEGraphConstants.STYLE_BEZIER);
		setPoints(points);

	}

	public String getType(){
		return "BezierSolidArrowItem";
	}
}