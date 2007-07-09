package ch.tbe.item;

import java.awt.geom.Point2D;
import java.util.List;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.TBEGraphConstants;

/**
 * Tactic Board Editor
 * **********************
 * PolyCurvedBlockItem 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
 */

public class PolyCurvedBlockItem extends ArrowItem {
	private static final long serialVersionUID = 1L;

	public PolyCurvedBlockItem(List<Point2D> points, ItemType itemType) {
		super(itemType);

		TBEGraphConstants.setLineEnd(this.getAttributes(), TBEGraphConstants.ARROW_BLOCK);
		TBEGraphConstants.setEndFill(this.getAttributes(), false);
		TBEGraphConstants.setLineStyle(this.getAttributes(), TBEGraphConstants.STYLE_CURVED);
		setPoints(points);
	}

	public String getType() {
		return "PolyCurvedBlockTool";
	}
}