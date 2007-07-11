package ch.tbe.item;

import java.awt.geom.Point2D;
import java.util.List;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.TBEGraphConstants;

/**
 * Tactic Board Editor
 * **********************
 * PolyDashedArrowItem 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class PolyDashedArrowItem extends ArrowItem {
	private static final long serialVersionUID = 1L;

	private final float[] DASH = new float[] { 6, 6 };

	/**
	 * @param points as List of Point2D
	 * @param itemType ItemType
	 */
	public PolyDashedArrowItem(List<Point2D> points, ItemType itemType) {
		super(itemType);

		TBEGraphConstants.setDashPattern(this.getAttributes(), DASH);
		setPoints(points);
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.ItemComponent#getType()
	 */
	public String getType() {
		return "PolyDashedArrowTool";
	}
}
