package ch.tbe.command;

import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;

/**
 * Tactic Board Editor
 * **********************
 * EditTextCommand 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class EditTextCommand extends Command {

	/**
	 * Command to undo/redo the editing of a TextBox
	 * @param text
	 * @param items
	 */
	public EditTextCommand(String text, ItemComponent[] items) {
		super(items);
		// TODO Change Text Command
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Command#redo()
	 */
	public void redo() {
		// TODO Change Text Command
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Command#undo()
	 */
	public void undo() {
		// TODO Change Text Command

	}
}
