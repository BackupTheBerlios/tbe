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
 * @copyright by BHF-TI, Team TBE
 */

public abstract class ArrowTool extends Tool {
	public ArrowTool(ItemType itemType) {
		super(itemType);
	}

	protected final int DEFAULTLENGTH = 20;

	public void activate() {
	}

	public void deactivate() {
	}

	public void mouseDown(int x, int y, MouseEvent e) {
	}

	public void mouseDrag(int x, int y, MouseEvent e) {
	}

	public void mouseOver(int x, int y, MouseEvent e) {
	}

	public void mouseUp(int x, int y, MouseEvent e) {
	}
}
