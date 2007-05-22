package ch.tbe;

import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.graph.DefaultGraphCell;
import ch.tbe.jgraph.graph.GraphConstants;

public class ShapeItem extends DefaultGraphCell implements ItemComponent
{

	public ShapeItem(Icon icon, Rectangle2D.Double rect)
	{
		GraphConstants.setBounds(this.getAttributes(), rect);
		GraphConstants.setIcon(this.getAttributes(), icon);
	}
}
