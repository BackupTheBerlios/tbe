package ch.tbe.tool;

import java.awt.event.MouseEvent;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowTool;

/**
 * Tactic Board Editor
 * **********************
 * BezierCurvedArrowTool 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */
public class BezierCurvedArrowTool extends ArrowTool {

	/**
	 * @param shapeType
	 */
	public BezierCurvedArrowTool(ItemType shapeType) {
		super(shapeType);

	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.ArrowTool#mouseDown(int, int, java.awt.event.MouseEvent)
	 */
	public void mouseDown(int x, int y, MouseEvent e) {

	}

}
