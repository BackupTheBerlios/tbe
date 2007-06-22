package ch.tbe.item;

import java.awt.geom.Rectangle2D;

import org.jgraph.graph.DefaultGraphCell;

import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.TBEGraphConstants;


public class TextBoxItem extends DefaultGraphCell implements ItemComponent {
 
	public TextBoxItem(Rectangle2D.Double rect){
		
		super(new String("Text"));
		TBEGraphConstants.setBounds(this.getAttributes(), rect);
	}
	
	public String getType(){
		return "TextBox";
	}
	
	public String getText(){
	    if (super.getUserObject() == null)
		    return null;
	    return super.getUserObject().toString();
	}
	
	public void setText(String s){
	    super.setUserObject(s);
	}

}
 
