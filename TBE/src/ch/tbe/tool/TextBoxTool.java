package ch.tbe.tool;

import ch.tbe.ItemType;
import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.Tool;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.item.TextBoxItem;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * Tactic Board Editor
 * **********************
 * TextBoxTool 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */
public class TextBoxTool extends Tool {

	/**
	 * @param shapeType
	 */
	public TextBoxTool(ItemType shapeType) {
		super(shapeType);

	}

	private final int HEIGHT = 20;
	private final int WIDTH = 40;

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#mouseDown(int, int, java.awt.event.MouseEvent)
	 */
	public void mouseDown(int x, int y, MouseEvent e) {
		ItemComponent[] items = new ItemComponent[] { new TextBoxItem(new Rectangle2D.Double(x, y, WIDTH, HEIGHT)) };
		super.createCommand(items);
		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(items);
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#activate()
	 */
	@Override
	public void activate() {

	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#deactivate()
	 */
	@Override
	public void deactivate() {

	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#mouseDrag(int, int, java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#mouseOver(int, int, java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseOver(int x, int y, MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#mouseUp(int, int, java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseUp(int x, int y, MouseEvent e) {

	}

}
