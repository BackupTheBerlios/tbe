package ch.tbe.command;

import org.jgraph.graph.DefaultGraphCell;

import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.jgraph.TBEGraphConstants;

/**
 * Tactic Board Editor
 * **********************
 * RotateCommand 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
 */

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
	    ((WorkingView) TBE.getInstance().getView()).setRotateValue(rotation);
	    this.rotation = temp;
	}

	public void undo()
	{
	    int temp =   TBEGraphConstants.getRotation(((DefaultGraphCell)items[0]).getAttributes());
	    TBEGraphConstants.setRotation(((DefaultGraphCell)items[0]).getAttributes(), rotation);
	    ((WorkingView) TBE.getInstance().getView()).getBoard().addItem(items);
	    ((WorkingView) TBE.getInstance().getView()).setRotateValue(rotation);
	    this.rotation = temp;
	}

	public void setRotation(int rotation) {
  	this.rotation = rotation;
  }
}
