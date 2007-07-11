package ch.tbe.framework;

import javax.swing.JPanel;

/**
 * Tactic Board Editor
 * **********************
 * View 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public abstract class View extends JPanel {
	/**
	 * Refresh the view (for ex. to change the language)
	 */
	public abstract void refresh();
}
