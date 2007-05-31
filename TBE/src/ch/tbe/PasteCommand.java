package ch.tbe;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.util.ComponentSelection;

public class PasteCommand extends Command implements ClipboardOwner
{

	private Board board;
	private Clipboard cb = TBE.getInstance().getClipboard();

	public PasteCommand(ItemComponent[] items)
	{
		super(items);
		this.board = ((WorkingView) TBE.getInstance().getView()).getBoard();
	}

	public void redo()
	{
		ComponentSelection clipboardContent = (ComponentSelection) cb
				.getContents(this);

		if ((clipboardContent != null)
				&& (clipboardContent
						.isDataFlavorSupported(ComponentSelection.itemFlavor)))
		{
			try
			{
				Object[] tempItems = clipboardContent
						.getTransferData(ComponentSelection.itemFlavor);
				ItemComponent[] comps = new ItemComponent[tempItems.length];
				for (int i = 0; i < tempItems.length; i++)
				{
					comps[i] = (ItemComponent) tempItems[i];
				}
				board.addItem(comps);
				board.removeItem(comps);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	public void undo()
	{

		board.removeItem(items);
	}

	public void lostOwnership(Clipboard arg0, Transferable arg1)
	{
		System.out.println("Lost ownership");

	}

}
