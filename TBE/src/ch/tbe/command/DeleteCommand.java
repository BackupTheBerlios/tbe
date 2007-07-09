package ch.tbe.command;

import ch.tbe.Board;
import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

/**
 * Tactic Board Editor
 * **********************
 * DeleteCommand 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
 */

public class DeleteCommand extends Command {

	private Board board;

	public DeleteCommand(ItemComponent[] items) {
		super(items);
		this.board = ((WorkingView) TBE.getInstance().getView()).getBoard();
	}

	public void redo() {

		board.removeItem(items);
	}

	public void undo() {

		board.addItem(items);
	}

}
