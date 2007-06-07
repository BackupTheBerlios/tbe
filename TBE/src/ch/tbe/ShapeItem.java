package ch.tbe;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;

import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.TBEGraphConstants;


public class ShapeItem extends DefaultGraphCell implements ItemComponent
{
	private ItemType itemType;

	public ShapeItem(ItemType shapeType, Point2D p)
	{

		this.itemType = shapeType;
		TBEGraphConstants.setBounds(this.getAttributes(), new Rectangle2D.Double(p
				.getX(), p.getY(), shapeType.getIcon().getIconWidth(), shapeType
				.getIcon().getIconHeight()));
		TBEGraphConstants.setIcon(this.getAttributes(), shapeType.getPicture());
		TBEGraphConstants.setEditable(this.getAttributes(), false);
		
	}
	
	public Icon getIcon(){
		return itemType.getIcon();
	}
	
	public ShapeItem clone(){
		ShapeItem s = (ShapeItem) super.clone();
		s.attributes = (AttributeMap) attributes.clone();
		s.itemType = this.itemType;
		return s;
	}
	public void setRotation(int degree){
		TBEGraphConstants.setRotation(this.getAttributes(), degree);
	}
	
	public int getRotation(){
		return TBEGraphConstants.getRotation(this.getAttributes());
	}

	public String getType()
	{
		return itemType.getName();
	}

}
