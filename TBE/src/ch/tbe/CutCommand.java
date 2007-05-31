package ch.tbe;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

import ch.tbe.framework.Command;

import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.util.ComponentSelection;

public class CutCommand extends Command implements ClipboardOwner{
 
	private Board board;
	private Clipboard cb = TBE.getInstance().getClipboard();
	
	public CutCommand(ItemComponent[] items) {
		super(items);
		this.board = ((WorkingView) TBE.getInstance().getView()).getBoard();
	}

	public void redo()
	{
		 ComponentSelection contents = new ComponentSelection (items);
	       cb.setContents (contents, this);
		board.removeItem(items);
	}

	public void undo()
	{
		
		board.addItem(items);
	}

	public void lostOwnership(Clipboard arg0, Transferable arg1)
	{
		System.out.println("Lost ownership");
		
	}
	 
}
 
