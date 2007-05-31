package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.TBEGraphConstants;

public class PolyCurvedArrowItem extends ArrowItem
{

	public PolyCurvedArrowItem(List<Point2D> points)
	
	{

		TBEGraphConstants.setLineStyle(this.getAttributes(),
				TBEGraphConstants.STYLE_CURVED);
		setPoints(points);
	}
	
	public String getType(){
		return "PolyCurvedArrowTool";
	}

}