package ch.tbe.item;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;

import ch.tbe.ItemType;
import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.TBEGraphConstants;

public class ShapeItem extends DefaultGraphCell implements ItemComponent {
	private static final long serialVersionUID = 1L;
	private ItemType itemType;

	public ShapeItem(ItemType shapeType, Point2D p) {

		this.itemType = shapeType;
		double maxSideWidth = this.itemType.getMaxSideWidth();

		int max = Math.max(shapeType.getPicture().getIconWidth(), shapeType.getPicture().getIconHeight());
		if (maxSideWidth > max) {
			maxSideWidth = max;
		}
		TBEGraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(p.getX(), p.getY(), maxSideWidth, maxSideWidth));
		TBEGraphConstants.setIcon(this.getAttributes(), shapeType.getPicture());
		TBEGraphConstants.setEditable(this.getAttributes(), false);

	}

	public Icon getIcon() {
		return itemType.getIcon();
	}

	public ShapeItem clone() {
		ShapeItem s = (ShapeItem) super.clone();
		s.attributes = (AttributeMap) attributes.clone();
		s.itemType = this.itemType;
		return s;
	}

	public void setRotation(int degree) {
		TBEGraphConstants.setRotation(this.getAttributes(), degree);
	}

	public int getRotation() {
		return TBEGraphConstants.getRotation(this.getAttributes());
	}

	public String getType() {
		return itemType.getName();
	}

}
