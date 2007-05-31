package ch.tbe;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.swing.Icon;

import org.jgraph.graph.DefaultGraphCell;

import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.TBEGraphConstants;


public class ShapeItem extends DefaultGraphCell implements ItemComponent
{
	private ShapeType shapeType;

	public ShapeItem(ShapeType shapeType, Point2D p)
	{

		this.shapeType = shapeType;
		TBEGraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(p
				.getX(), p.getY(), shapeType.getIcon().getIconWidth(), shapeType
				.getIcon().getIconHeight()));
		TBEGraphConstants.setIcon(this.getAttributes(), shapeType.getIcon());
		TBEGraphConstants.setEditable(this.getAttributes(), false);
		TBEGraphConstants.setSizeable(this.getAttributes(), false);
	}

	public ShapeType getIcon()
	{
		return shapeType;
	}
	public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public DataFlavor[] getTransferDataFlavors()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDataFlavorSupported(DataFlavor arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
