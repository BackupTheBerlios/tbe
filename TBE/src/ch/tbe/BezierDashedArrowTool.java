package ch.tbe;

import java.awt.event.MouseEvent;
import ch.tbe.framework.Tool;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

public class BezierDashedArrowTool implements Tool
{

	public void mouseDown(int x, int y, MouseEvent e)
	{

		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(new BezierDashedArrowItem());
	}
}
