package ch.tbe.command;

import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.jgraph.TBEGraphConstants;
import java.awt.geom.Rectangle2D;
import java.util.List;
import org.jgraph.graph.DefaultGraphCell;

/**
 * Tactic Board Editor
 * **********************
 * MoveCommand 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class MoveCommand extends Command {
	private WorkingView view;
	private ItemComponent[] endItems;
	private ItemComponent[] startItems;

	/**
	 * Command to undo/redo the moving of ItemComponents
	 * @param items ItemComponents
	 */
	public MoveCommand(ItemComponent[] items) {
		super(items);
		this.view = (WorkingView) TBE.getInstance().getView();
		this.startItems = view.getBoard().cloneItems(items);
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Command#undo()
	 */
	public void undo() {

		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof ArrowItem) {
				List<?> p = TBEGraphConstants.getPoints(((DefaultGraphCell) startItems[i]).getAttributes());
				TBEGraphConstants.setPoints(((DefaultGraphCell) items[i]).getAttributes(), p);
			} else {
				Rectangle2D r = TBEGraphConstants.getBounds(((DefaultGraphCell) startItems[i]).getAttributes());
				TBEGraphConstants.setBounds(((DefaultGraphCell) items[i]).getAttributes(), r);
			}
		}
		view.getBoard().addItem(items);

	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.Command#redo()
	 */
	public void redo() {

		for (int i = 0; i < items.length; i++) {

			if (items[i] instanceof ArrowItem) {
				List<?> p = TBEGraphConstants.getPoints(((DefaultGraphCell) endItems[i]).getAttributes());
				TBEGraphConstants.setPoints(((DefaultGraphCell) items[i]).getAttributes(), p);

			} else {
				Rectangle2D r = TBEGraphConstants.getBounds(((DefaultGraphCell) endItems[i]).getAttributes());
				TBEGraphConstants.setBounds(((DefaultGraphCell) items[i]).getAttributes(), r);
			}
		}
		view.getBoard().addItem(items);

	}

	/**
	 * Sets the end Position of the ItemComponents
	 * @param endItems
	 */
	public void setMoveEnd(ItemComponent[] endItems) {
		this.endItems = view.getBoard().cloneItems(endItems);
	}
}
