package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.TBEGraphConstants;

public class BezierDoubleArrowItem extends ArrowItem
{

	public BezierDoubleArrowItem(List<Point2D> points)
	{
		
		TBEGraphConstants.setLineStyle(this.getAttributes(),
				TBEGraphConstants.STYLE_DOUBLEBEZIER);
	}
	
	public String getType(){
		return "BezierDoubleArrowItem";
	}

}