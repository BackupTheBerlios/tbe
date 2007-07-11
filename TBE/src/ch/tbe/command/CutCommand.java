package ch.tbe.command;

import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

/**
 * Tactic Board Editor
 * **********************
 * CutCommand 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class CutCommand extends Command implements ClipboardOwner {

	private WorkingView view;

	/**
	 * Command to undo/redo the cuting of ItemComponents
	 * @param items ItemComponents
	 */
	public CutCommand(ItemComponent[] items) {
		super(items);
		this.view = ((WorkingView) TBE.getInstance().getView());
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Command#redo()
	 */
	public void redo() {

		view.getBoard().removeItem(items);
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Command#undo()
	 */
	public void undo() {

		view.getBoard().addItem(items);
	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.ClipboardOwner#lostOwnership(java.awt.datatransfer.Clipboard, java.awt.datatransfer.Transferable)
	 */
	public void lostOwnership(Clipboard arg0, Transferable arg1) {

	}

}
