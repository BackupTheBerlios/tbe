package ch.tbe;

import ch.tbe.framework.*;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

public class CreateCommand extends Command{
	private Board board;
	
	public CreateCommand(ItemComponent item) {
		super(item);
		this.board = ((WorkingView) TBE.getInstance().getView()).getBoard();
	}

	public void redo()
	{
		board.addItem(item);
	}

	public void undo()
	{
		board.removeItem(item);
		
	}
	 
}
 
