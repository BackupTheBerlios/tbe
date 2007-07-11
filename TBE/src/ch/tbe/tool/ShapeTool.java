package ch.tbe.tool;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import ch.tbe.ItemType;
import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.Tool;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.item.ShapeItem;

/**
 * Tactic Board Editor
 * **********************
 * ShapeTool 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */
public class ShapeTool extends Tool {

	/**
	 * @param shapeType
	 */
	public ShapeTool(ItemType shapeType) {
		super(shapeType);

	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#mouseDown(int, int, java.awt.event.MouseEvent)
	 */
	public void mouseDown(int x, int y, MouseEvent e) {
		ItemComponent[] items = new ItemComponent[] { new ShapeItem(itemType, new Point2D.Double(x, y)) };
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
