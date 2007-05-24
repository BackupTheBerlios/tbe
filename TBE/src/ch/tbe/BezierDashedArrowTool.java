package ch.tbe;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import ch.tbe.framework.ArrowItemTool;

import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

public class BezierDashedArrowTool extends ArrowItemTool
{

	public void mouseDown(int x, int y, MouseEvent e)
	{

		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(new BezierDashedArrowItem(new Point2D.Double(x,y)));
	}
}
