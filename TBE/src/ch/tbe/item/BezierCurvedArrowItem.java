package ch.tbe.item;

import java.awt.geom.Point2D;
import java.util.List;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowItem;

/**
 * Tactic Board Editor
 * **********************
 * BezierCurvedArrowItem 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class BezierCurvedArrowItem extends ArrowItem {
	private static final long serialVersionUID = 1L;

	public BezierCurvedArrowItem(List<Point2D> points, ItemType itemType) {
		super(itemType);
	}

	public String getType() {
		return "BezierCurvedArrowTool";
	}
}
