package ch.tbe.tool;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowTool;
import ch.tbe.framework.ItemComponent;

import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.item.BezierDashedArrowItem;

/**
 * Tactic Board Editor
 * **********************
 * BezierDashedArrowTool 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */
public class BezierDashedArrowTool extends ArrowTool {

	/**
	 * @param shapeType
	 */
	public BezierDashedArrowTool(ItemType shapeType) {
		super(shapeType);

	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.ArrowTool#mouseDown(int, int, java.awt.event.MouseEvent)
	 */
	public void mouseDown(int x, int y, MouseEvent e) {

		List<Point2D> points = new ArrayList<Point2D>();
		points.add(new Point2D.Double(x, y));
		points.add(new Point2D.Double(x + DEFAULTLENGTH, y + DEFAULTLENGTH));
		points.add(new Point2D.Double(x + 2 * DEFAULTLENGTH, y));

		ItemComponent[] items = new ItemComponent[] { new BezierDashedArrowItem(points, itemType) };
		super.createCommand(items);
		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(items);
	}
}
