package ch.tbe.command;

import ch.tbe.Board;
import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

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
