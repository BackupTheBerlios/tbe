package ch.tbe.command;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

/**
 * Tactic Board Editor
 * **********************
 * PasteCommand 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
 */

public class PasteCommand extends Command implements ClipboardOwner {

	private WorkingView view;

	public PasteCommand(ItemComponent[] items) {
		super(items);
		this.view = ((WorkingView) TBE.getInstance().getView());
	}

	public void redo() {

		view.paste();

	}

	public void undo() {

		view.getBoard().removeItem(items);
	}

	public void lostOwnership(Clipboard arg0, Transferable arg1) {

	}

}
