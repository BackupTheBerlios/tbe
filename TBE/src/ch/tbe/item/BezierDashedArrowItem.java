package ch.tbe.item;

import java.awt.geom.Point2D;
import java.util.List;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.TBEGraphConstants;

/**
 * Tactic Board Editor
 * **********************
 * BezierDashedArrowItem 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
 */

public class BezierDashedArrowItem extends ArrowItem {
	private static final long serialVersionUID = 1L;

	private final float[] DASH = new float[] { 6, 6 };

	public BezierDashedArrowItem(List<Point2D> points, ItemType itemType) {
		super(itemType);
		TBEGraphConstants.setLineStyle(this.getAttributes(), TBEGraphConstants.STYLE_BEZIER);
		TBEGraphConstants.setDashPattern(this.getAttributes(), DASH);
		setPoints(points);
	}

	public String getType() {
		return "BezierDashedArrowTool";
	}

}