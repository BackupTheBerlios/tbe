package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.*;
import ch.tbe.item.*;

/**
 * Tactic Board Editor
 * **********************
 * ItemFactory 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public final class ItemFactory {
	private ItemFactory() {};

	public static ArrowItem getArrowItem(Sport sport, String itemName, List<Point2D> points) {
		ArrowItem item = null;

		for (ItemType itemType : sport.getArrowTypes()) {
			if (itemType.getName().equals(itemName)) {
				if (itemType.getName().equals("BezierCurvedArrowTool"))
					item = new BezierCurvedArrowItem(points, itemType);
				if (itemType.getName().equals("BezierDashedArrowTool"))
					item = new BezierDashedArrowItem(points, itemType);
				if (itemType.getName().equals("BezierDoubleArrowTool"))
					item = new BezierDoubleArrowItem(points, itemType);
				if (itemType.getName().equals("BezierSolidArrowTool"))
					item = new BezierSolidArrowItem(points, itemType);
				if (itemType.getName().equals("PolyCurvedArrowTool"))
					item = new PolyCurvedArrowItem(points, itemType);
				if (itemType.getName().equals("PolyCurvedBlockTool"))
					item = new PolyCurvedBlockItem(points, itemType);
				if (itemType.getName().equals("PolyDashedArrowTool"))
					item = new PolyDashedArrowItem(points, itemType);
				if (itemType.getName().equals("PolyDashedBlockTool"))
					item = new PolyDashedBlockItem(points, itemType);
				if (itemType.getName().equals("PolyDoubleArrowTool"))
					item = new PolyDoubleArrowItem(points, itemType);
				if (itemType.getName().equals("PolySolidArrowTool"))
					item = new PolySolidArrowItem(points, itemType);
				if (itemType.getName().equals("PolySolidBlockTool"))
					item = new PolySolidBlockItem(points, itemType);
			}
		}
		return item;
	}

	public static ShapeItem getShapeItem(Sport sport, String itemName, Point2D point) {

		ShapeItem item = null;

		for (ItemType shapeType : sport.getShapeTypes()) {
			if (shapeType.getName().equals(itemName)) {
				item = new ShapeItem(shapeType, point);
			}
		}

		return item;
	}
}
