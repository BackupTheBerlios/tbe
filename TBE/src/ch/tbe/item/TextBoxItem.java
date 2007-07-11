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
 * @copyright by BFH-TI, Team TBE
 */

public class TextBoxItem extends DefaultGraphCell implements ItemComponent {
	private static final long serialVersionUID = 1L;

	/**
	 * @param rect Rectangle2D as Bounds
	 */
	public TextBoxItem(Rectangle2D.Double rect) {

		super(new String("Text"));
		TBEGraphConstants.setBounds(this.getAttributes(), rect);
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.ItemComponent#getType()
	 */
	public String getType() {
		return "TextBox";
	}

	/**
	 * Returns the text of the textbox
	 * @return text as String
	 */
	public String getText() {
		if (super.getUserObject() == null)
			return null;
		return super.getUserObject().toString();
	}

	/**
	 * Sets the text of the textbox
	 * @param text as String
	 */
	public void setText(String text) {
		super.setUserObject(text);
	}

}
