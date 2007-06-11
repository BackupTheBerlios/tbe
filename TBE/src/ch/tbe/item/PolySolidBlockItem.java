package ch.tbe.item;

import java.awt.geom.Point2D;
import java.util.List;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.TBEGraphConstants;


public class PolySolidBlockItem extends ArrowItem
{

	public PolySolidBlockItem(List<Point2D> points, ItemType itemType){
		super(itemType);

		TBEGraphConstants.setLineEnd(this.getAttributes(), TBEGraphConstants.ARROW_BLOCK);
		TBEGraphConstants.setEndFill(this.getAttributes(), false);
		setPoints(points);
	}
	
	public String getType(){
		return "PolySolidBlockTool";
	}

}