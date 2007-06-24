package ch.tbe.item;

import java.awt.geom.Point2D;
import java.util.List;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowItem;

public class PolySolidArrowItem extends ArrowItem {
    private static final long serialVersionUID = 1L;

    public PolySolidArrowItem(List<Point2D> points, ItemType itemType) {
	super(itemType);
	setPoints(points);
    }

    public String getType() {
	return "PolySolidArrowTool";
    }
}