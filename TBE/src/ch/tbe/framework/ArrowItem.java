package ch.tbe.framework;

import java.awt.geom.Point2D;
import java.util.List;
import javax.swing.Icon;
import org.jgraph.graph.DefaultEdge;
import ch.tbe.ItemType;
import ch.tbe.jgraph.TBEGraphConstants;

/**
 * Tactic Board Editor
 * **********************
 * ArrowItem 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public abstract class ArrowItem extends DefaultEdge implements ItemComponent {
	private ItemType itemType;

	protected final int DEFAULTLENGTH = 20;

	protected ArrowItem(ItemType itemType) {
		this.itemType = itemType;
		TBEGraphConstants.setLineEnd(this.getAttributes(), TBEGraphConstants.ARROW_CLASSIC);
		TBEGraphConstants.setEndFill(this.getAttributes(), true);

	}

	public void setPoints(List<Point2D> points) {
		TBEGraphConstants.setPoints(this.getAttributes(), points);
	}

	@SuppressWarnings("unchecked")
	public List<Point2D> getPoints() {
		return TBEGraphConstants.getPoints(this.getAttributes());
	}

	@SuppressWarnings("unchecked")
	public void addPoint() {
		int size = TBEGraphConstants.getPoints(this.getAttributes()).size();
		double lastX = ((Point2D) TBEGraphConstants.getPoints(this.getAttributes()).get(size - 1)).getX();
		double lastY = ((Point2D) TBEGraphConstants.getPoints(this.getAttributes()).get(size - 1)).getY();

		Point2D point = new Point2D.Double(lastX + DEFAULTLENGTH, lastY);
		TBEGraphConstants.getPoints(this.getAttributes()).add(point);
	}

	public void removePoint() {
		if (TBEGraphConstants.getPoints(this.getAttributes()).size() > 2) {
			int size = TBEGraphConstants.getPoints(this.getAttributes()).size();
			Point2D lastP = ((Point2D) TBEGraphConstants.getPoints(this.getAttributes()).get(size - 1));
			TBEGraphConstants.getPoints(this.getAttributes()).remove(lastP);

		}
	}

	public Icon getIcon() {
		return itemType.getIcon();
	}

	public ItemType getItemType() {
		return itemType;
	}

	public String getText() {
		if (super.getUserObject() == null)
			return null;
		return super.getUserObject().toString();
	}

	public void setText(String s) {
		super.setUserObject(s);
	}
}
