package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.*;

public final class ItemFactory
{
	private ItemFactory(){};

	public static ArrowItem getArrowItem(Sport sport, String itemName, List<Point2D> points){
		ArrowItem item = null;
		
		for (ShapeType shapeType: sport.getArrowTypes()){
			if (shapeType.getName().equals(itemName)){
				if(shapeType.getName().equals("BezierCurvedArrowTool"))item = new BezierCurvedArrowItem(points);
				if(shapeType.getName().equals("BezierDashedArrowTool")) item = new BezierDashedArrowItem(points);
				if(shapeType.getName().equals("BezierDoubleArrowTool")) item = new BezierDoubleArrowItem(points);
				if(shapeType.getName().equals("BezierSolidArrowTool")) item = new BezierSolidArrowItem(points);
				if(shapeType.getName().equals("PolyCurvedArrowTool")) item = new PolyCurvedArrowItem(points);
				if(shapeType.getName().equals("PolyCurvedBlockTool")) item = new PolyCurvedBlockItem(points);
				if(shapeType.getName().equals("PolyDashedArrowTool")) item = new PolyDashedArrowItem(points);
				if(shapeType.getName().equals("PolyDashedBlockTool")) item = new PolyDashedBlockItem(points);
				if(shapeType.getName().equals("PolyDoubleArrowTool")) item = new PolyDoubleArrowItem(points);
				if(shapeType.getName().equals("PolySolidArrowTool")) item = new PolySolidArrowItem(points);
				if(shapeType.getName().equals("PolySolidBlockTool")) item = new PolySolidBlockItem(points);
			}
		}
		return item;
	}
	
	public static ShapeItem getShapeItem(Sport sport, String itemName,  Point2D point){
	
		ShapeItem item = null;
		
		for (ShapeType shapeType: sport.getShapeTypes()){
			if (shapeType.getName().equals(itemName)){
				item = new ShapeItem(shapeType, point);
			}
		}
		
		return item;
	}
}
