package ch.tbe;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.Tool;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

public class TextBoxTool extends Tool
{

	public TextBoxTool(ShapeType shapeType)
	{
		super(shapeType);
		// TODO Auto-generated constructor stub
	}

	private final int HEIGHT = 20;
	private final int WIDTH = 40;

	public void mouseDown(int x, int y, MouseEvent e)
	{
		ItemComponent[] items = new ItemComponent[] { new TextBoxItem(
				new Rectangle2D.Double(x, y, WIDTH, HEIGHT)) };
		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(items);
	}

	@Override
	public void activate()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ShapeType getShapeType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mouseDrag(int x, int y, MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseOver(int x, int y, MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(int x, int y, MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

}
