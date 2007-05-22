package ch.tbe;

import java.awt.geom.Rectangle2D;

import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.graph.DefaultGraphCell;
import ch.tbe.jgraph.graph.GraphConstants;

public class TextBoxItem extends DefaultGraphCell implements ItemComponent {
 
	public TextBoxItem(Rectangle2D.Double rect){
		
		super(new String("Text"));
		GraphConstants.setBounds(this.getAttributes(), rect);
	}
}
 
