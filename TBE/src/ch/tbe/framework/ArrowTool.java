package ch.tbe.framework;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import ch.tbe.ShapeType;

public abstract class ArrowTool extends Tool
{
	public ArrowTool(ShapeType shapeType)
	{
		super(shapeType);
		// TODO Auto-generated constructor stub
	}

	protected final int DEFAULTLENGTH = 20;
	

	public void activate()
	{
		// TODO Auto-generated method stub

	}

	public void deactivate()
	{
		// TODO Auto-generated method stub

	}

	public void mouseDown(int x, int y, MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseDrag(int x, int y, MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseOver(int x, int y, MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseUp(int x, int y, MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public ShapeType getShapeType()
	{
		// TODO Auto-generated method stub
		return null;
	}


}
