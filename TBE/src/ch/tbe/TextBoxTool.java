package ch.tbe;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

public class TextBoxTool
{

	private final int HEIGHT = 20;
	private final int WIDTH = 40;

	public void mouseDown(int x, int y, MouseEvent e)
	{

		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(
				new TextBoxItem(new Rectangle2D.Double(x, y, WIDTH, HEIGHT)));
	}

}
