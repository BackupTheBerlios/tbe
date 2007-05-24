package ch.tbe;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.graph.DefaultGraphCell;
import ch.tbe.jgraph.graph.GraphConstants;

public class ShapeItem extends DefaultGraphCell implements ItemComponent
{
	private ShapeType shapeType;

	public ShapeItem(ShapeType shapeType, Point2D p)
	{

		this.shapeType = shapeType;
		GraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(p
				.getX(), p.getY(), shapeType.getPicture().getIconWidth(), shapeType
				.getPicture().getIconHeight()));
		GraphConstants.setIcon(this.getAttributes(), shapeType.getPicture());
	}

	public ShapeType getIcon()
	{
		return shapeType;
	}

}
