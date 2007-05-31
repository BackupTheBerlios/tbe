package ch.tbe;

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

}
 
