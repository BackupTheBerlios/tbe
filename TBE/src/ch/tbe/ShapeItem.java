package ch.tbe;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.graph.DefaultGraphCell;
import ch.tbe.jgraph.graph.GraphConstants;

public class ShapeItem extends DefaultGraphCell implements ItemComponent
{

	public ShapeItem(Icon icon, Point2D p)
	{

		GraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(p.getX(),p.getY(),icon.getIconWidth(),icon.getIconHeight()));
		GraphConstants.setIcon(this.getAttributes(), icon);
	}
}
