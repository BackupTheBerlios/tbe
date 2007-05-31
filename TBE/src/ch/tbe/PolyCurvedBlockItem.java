package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.TBEGraphConstants;

public class PolyCurvedBlockItem extends ArrowItem
{

	public PolyCurvedBlockItem(List<Point2D> points)
	
	{

		TBEGraphConstants.setLineEnd(this.getAttributes(), TBEGraphConstants.ARROW_BLOCK);
		TBEGraphConstants.setEndFill(this.getAttributes(), false);
		TBEGraphConstants.setLineStyle(this.getAttributes(),
				TBEGraphConstants.STYLE_CURVED);
		setPoints(points);
	}

	public String getType(){
		return "PolyCurvedBlockTool";
	}
}