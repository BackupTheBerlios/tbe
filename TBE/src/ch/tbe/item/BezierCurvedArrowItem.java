package ch.tbe.item;

import java.awt.geom.Point2D;
import java.util.List;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowItem;

public class BezierCurvedArrowItem extends ArrowItem{
 
	public BezierCurvedArrowItem(List<Point2D> points, ItemType itemType){
		super(itemType);
	}

	public String getType()
	{
		return "BezierCurvedArrowTool";
	}
}
 
