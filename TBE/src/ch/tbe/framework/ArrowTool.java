package ch.tbe.framework;

import java.awt.event.MouseEvent;
import ch.tbe.ItemType;

/**
 * Tactic Board Editor
 * **********************
 * ArrowTool 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public abstract class ArrowTool extends Tool {
	/**
	 * @param itemType ItemType
	 */
	public ArrowTool(ItemType itemType) {
		super(itemType);
	}

	protected final int DEFAULTLENGTH = 20;

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#activate()
	 */
	public void activate() {
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#deactivate()
	 */
	public void deactivate() {
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#mouseDown(int, int, java.awt.event.MouseEvent)
	 */
	public void mouseDown(int x, int y, MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#mouseDrag(int, int, java.awt.event.MouseEvent)
	 */
	public void mouseDrag(int x, int y, MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#mouseOver(int, int, java.awt.event.MouseEvent)
	 */
	public void mouseOver(int x, int y, MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Tool#mouseUp(int, int, java.awt.event.MouseEvent)
	 */
	public void mouseUp(int x, int y, MouseEvent e) {
	}
}
