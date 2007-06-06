package ch.tbe;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jgraph.graph.AttributeMap;
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
		TBEGraphConstants.setIcon(this.getAttributes(), shapeType.getPicture());
		TBEGraphConstants.setEditable(this.getAttributes(), false);
		
	}
	
	public String getType(){
		return shapeType.getName();
	}
	
	public ShapeItem clone(){
		ShapeItem s = (ShapeItem) super.clone();
		s.attributes = (AttributeMap) attributes.clone();
		s.shapeType = this.shapeType;
		return s;
	}
	public void setRotation(int degree){
		TBEGraphConstants.setRotation(this.getAttributes(), degree);
	}
	
	public int getRotation(){
		return TBEGraphConstants.getRotation(this.getAttributes());
	}

}
