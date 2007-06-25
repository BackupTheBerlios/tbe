package ch.tbe.item;

import java.awt.geom.Point2D;
import java.util.List;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.TBEGraphConstants;

public class BezierDoubleArrowItem extends ArrowItem {
	private static final long serialVersionUID = 1L;

	public BezierDoubleArrowItem(List<Point2D> points, ItemType itemType) {
		super(itemType);
		TBEGraphConstants.setLineStyle(this.getAttributes(), TBEGraphConstants.STYLE_DOUBLEBEZIER);
		setPoints(points);
	}

	public String getType() {
		return "BezierDoubleArrowTool";
	}

}