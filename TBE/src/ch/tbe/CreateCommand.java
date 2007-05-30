package ch.tbe;

import ch.tbe.framework.*;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

public class CreateCommand extends Command{
	private Board board;
	
	public CreateCommand(ItemComponent[] items) {
		super(items);
		this.board = ((WorkingView) TBE.getInstance().getView()).getBoard();
	}

	public void redo()
	{
		board.addItem(items);
	}

	public void undo()
	{
		board.removeItem(items);
		
	}
	 
}
 
