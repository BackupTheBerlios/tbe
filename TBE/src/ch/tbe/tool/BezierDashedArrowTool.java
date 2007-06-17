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
import ch.tbe.item.BezierDashedArrowItem;

public class BezierDashedArrowTool extends ArrowTool
{

	public BezierDashedArrowTool(ItemType shapeType)
	{
		super(shapeType);
	
	}

	public void mouseDown(int x, int y, MouseEvent e)
	{

		List<Point2D> points = new ArrayList<Point2D>();
		points.add(new Point2D.Double(x,y));
		points.add(new Point2D.Double(x+DEFAULTLENGTH,y+DEFAULTLENGTH));
		points.add(new Point2D.Double(x+2*DEFAULTLENGTH,y));
		
		ItemComponent[] items = new ItemComponent[]{new BezierDashedArrowItem(points, itemType)};
		super.createCommand(items);
		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(items);
	}
}
