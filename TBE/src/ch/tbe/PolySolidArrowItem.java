package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;

public class PolySolidArrowItem extends ArrowItem
{
	public PolySolidArrowItem(List<Point2D> points){
		setPoints(points);
	}

	public String getType(){
		return "PolySolidArrowTool";
	}
}