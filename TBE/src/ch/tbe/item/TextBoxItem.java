package ch.tbe.item;

import java.awt.geom.Rectangle2D;

import org.jgraph.graph.DefaultGraphCell;

import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.TBEGraphConstants;

/**
 * Tactic Board Editor
 * **********************
 * TextBoxItem 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
 */

public class TextBoxItem extends DefaultGraphCell implements ItemComponent {
	private static final long serialVersionUID = 1L;

	public TextBoxItem(Rectangle2D.Double rect) {

		super(new String("Text"));
		TBEGraphConstants.setBounds(this.getAttributes(), rect);
	}

	public String getType() {
		return "TextBox";
	}

	public String getText() {
		if (super.getUserObject() == null)
			return null;
		return super.getUserObject().toString();
	}

	public void setText(String s) {
		super.setUserObject(s);
	}

}
