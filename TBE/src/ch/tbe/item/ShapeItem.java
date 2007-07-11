package ch.tbe.item;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;

import ch.tbe.ItemType;
import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.TBEGraphConstants;

/**
 * Tactic Board Editor
 * **********************
 * ShapeItem 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class ShapeItem extends DefaultGraphCell implements ItemComponent {
	private static final long serialVersionUID = 1L;
	private ItemType itemType;

	/**
	 * @param itemType as ItemType
	 * @param point as Point
	 */
	public ShapeItem(ItemType itemType, Point2D point) {

		this.itemType = itemType;
		double maxSideWidth = this.itemType.getMaxSideWidth();

		int max = Math.max(itemType.getPicture().getIconWidth(), itemType.getPicture().getIconHeight());
		if (maxSideWidth > max) {
			maxSideWidth = max;
		}
		TBEGraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(point.getX(), point.getY(), maxSideWidth, maxSideWidth));
		TBEGraphConstants.setIcon(this.getAttributes(), itemType.getPicture());
		TBEGraphConstants.setEditable(this.getAttributes(), false);

	}

	/**
	 * Returns the Icon
	 * @return Icon
	 */
	public Icon getIcon() {
		return itemType.getIcon();
	}

	/* (non-Javadoc)
	 * @see org.jgraph.graph.DefaultGraphCell#clone()
	 */
	public ShapeItem clone() {
		ShapeItem s = (ShapeItem) super.clone();
		s.attributes = (AttributeMap) attributes.clone();
		s.itemType = this.itemType;
		return s;
	}

	/**
	 * Sets the rotation in degree
	 * @param degree as int
	 */
	public void setRotation(int degree) {
		TBEGraphConstants.setRotation(this.getAttributes(), degree);
	}

	/**
	 * Returns the rotation in degree
	 * @return rotation as int
	 */
	public int getRotation() {
		return TBEGraphConstants.getRotation(this.getAttributes());
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.ItemComponent#getType()
	 */
	public String getType() {
		return itemType.getName();
	}

}
