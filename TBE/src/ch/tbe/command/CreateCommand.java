package ch.tbe.command;

import ch.tbe.Board;
import ch.tbe.framework.*;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

/**
 * Tactic Board Editor
 * **********************
 * CreateCommand 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class CreateCommand extends Command {
	private Board board;

	public CreateCommand(ItemComponent[] items) {
		super(items);
		this.board = ((WorkingView) TBE.getInstance().getView()).getBoard();
	}

	public void redo() {
		board.addItem(items);
	}

	public void undo() {
		board.removeItem(items);

	}

}
