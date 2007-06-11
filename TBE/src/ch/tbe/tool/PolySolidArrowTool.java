package ch.tbe.tool;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import ch.tbe.ItemType;
import ch.tbe.framework.ArrowTool;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.item.PolySolidArrowItem;

public class PolySolidArrowTool extends ArrowTool {
 
	public PolySolidArrowTool(ItemType shapeType)
	{
		super(shapeType);
		// TODO Auto-generated constructor stub
	}

	public void mouseDown(int x, int y, MouseEvent e)
	{

		List<Point2D> points = new ArrayList<Point2D>();
		points.add(new Point2D.Double(x,y));		
		points.add(new Point2D.Double(x+DEFAULTLENGTH,y));
		
		ItemComponent[] items = new ItemComponent[] {new PolySolidArrowItem(points, itemType)};
		super.createCommand(items);
		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(items);
	}
 
}
 
