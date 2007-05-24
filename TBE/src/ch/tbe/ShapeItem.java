package ch.tbe;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.graph.DefaultGraphCell;
import ch.tbe.jgraph.graph.GraphConstants;

public class ShapeItem extends DefaultGraphCell implements ItemComponent
{
	private ShapeType icon;

	public ShapeItem(ShapeType icon, Point2D p)
	{

		this.icon = icon;
		GraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(p
				.getX(), p.getY(), icon.getPicture().getIconWidth(), icon
				.getPicture().getIconHeight()));
		GraphConstants.setIcon(this.getAttributes(), icon.getPicture());
	}

	public ShapeType getIcon()
	{
		return icon;
	}

}
