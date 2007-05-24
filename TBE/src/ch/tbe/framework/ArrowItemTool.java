package ch.tbe.framework;


import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class ArrowItemTool implements Tool
{
	protected ArrowItem current;
	protected Point2D.Double down;

	public abstract void mouseDown(int x, int y, MouseEvent e);

	public void mouseDrag(int x, int y, MouseEvent e)
	{

		List<Point2D.Double> points = new ArrayList<Point2D.Double>();
		points.add(down);
		points.add(new Point2D.Double(x, y));
		this.current.setPoints(points);
	}

	public void mouseUp(int x, int y, MouseEvent e)
	{

		// TODO: falls mouse Up point = mouse Down point, shape wieder löschen,
		// da nur punkt
	}

	public ArrowItem getRecentlyCreatedArrow()
	{
		// TODO Auto-generated method stub
		return this.current;
	}
}
