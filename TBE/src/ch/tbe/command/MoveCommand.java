package ch.tbe.command;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.jgraph.graph.DefaultGraphCell;

import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.jgraph.TBEGraphConstants;

public class MoveCommand extends Command
{
	private WorkingView view;
	private ItemComponent[] endItems;
	private ItemComponent[] startItems;

	public MoveCommand(ItemComponent[] items)
	{
		super(items);
		this.view = (WorkingView) TBE.getInstance().getView();
		this.startItems = view.getBoard().cloneItems(items);
	}

	public void undo()
	{

		for (int i = 0; i < items.length; i++)
		{
			if (items[i] instanceof ArrowItem)
			{
				List p = TBEGraphConstants.getPoints(((DefaultGraphCell) startItems[i])
						.getAttributes());
				TBEGraphConstants.setPoints(((DefaultGraphCell) items[i])
						.getAttributes(), p);
			}
			else
			{
				Rectangle2D r = TBEGraphConstants
						.getBounds(((DefaultGraphCell) startItems[i])
								.getAttributes());
				TBEGraphConstants.setBounds(((DefaultGraphCell) items[i])
						.getAttributes(), r);
			}
		}
		view.getBoard().addItem(items);

	}

	public void redo()
	{

		for (int i = 0; i < items.length; i++)
		{

			if (items[i] instanceof ArrowItem)
			{
				List p = TBEGraphConstants.getPoints(((DefaultGraphCell) endItems[i])
						.getAttributes());
				TBEGraphConstants.setPoints(((DefaultGraphCell) items[i])
						.getAttributes(), p);
				
			}
			else
			{
				Rectangle2D r = TBEGraphConstants
						.getBounds(((DefaultGraphCell) endItems[i])
								.getAttributes());
				TBEGraphConstants.setBounds(((DefaultGraphCell) items[i])
						.getAttributes(), r);
			}
		}
		view.getBoard().addItem(items);

	}

	public void setMoveEnd(ItemComponent[] endItems)
	{
		this.endItems = view.getBoard().cloneItems(endItems);
	}
}
