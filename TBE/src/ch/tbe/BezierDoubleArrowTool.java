package ch.tbe;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import ch.tbe.framework.ArrowItemTool;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

public class BezierDoubleArrowTool extends ArrowItemTool{
 
	public void mouseDown(int x, int y, MouseEvent e)
	{

		List<Point2D> points = new ArrayList<Point2D>();
		points.add(new Point2D.Double(x,y));
		points.add(new Point2D.Double(x+DEFAULTLENGTH,y+DEFAULTLENGTH));
		points.add(new Point2D.Double(x+2*DEFAULTLENGTH,y));
		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(new BezierDoubleArrowItem(points));
	}
}
 
