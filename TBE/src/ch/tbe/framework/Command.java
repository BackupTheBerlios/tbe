package ch.tbe.framework;

import ch.tbe.gui.TBE;

/**
 * Tactic Board Editor
 * **********************
 * Command 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
 */

public abstract class Command {
	protected ItemComponent[] items;
	protected TBE tbe = TBE.getInstance();

	public Command(ItemComponent[] items) {
		this.items = items;
	}

	public void undo() {
	}

	public void redo() {
	}

}
