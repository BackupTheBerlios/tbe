package ch.tbe.command;

import org.jgraph.graph.DefaultGraphCell;

import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.jgraph.TBEGraphConstants;

public class RotateCommand  extends Command
{
    private int rotation;

	public RotateCommand(ItemComponent[] items) {
		super(items);
		this.rotation =  TBEGraphConstants.getRotation(((DefaultGraphCell)items[0]).getAttributes());
		
	}

	public void redo()
	{
	    int temp =  TBEGraphConstants.getRotation(((DefaultGraphCell)items[0]).getAttributes());
	    TBEGraphConstants.setRotation(((DefaultGraphCell)items[0]).getAttributes(), rotation);
	    ((WorkingView) TBE.getInstance().getView()).getBoard().addItem(items);
	    this.rotation = temp;
	}

	public void undo()
	{
	    int temp =   TBEGraphConstants.getRotation(((DefaultGraphCell)items[0]).getAttributes());
	    TBEGraphConstants.setRotation(((DefaultGraphCell)items[0]).getAttributes(), rotation);
	    ((WorkingView) TBE.getInstance().getView()).getBoard().addItem(items);
	    this.rotation = temp;
	}
}
