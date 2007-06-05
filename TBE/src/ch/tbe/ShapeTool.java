package ch.tbe;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.Tool;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

 public class ShapeTool extends Tool {
	
	public ShapeTool(ShapeType shapeType){
		super(shapeType);
		
	}
 
	public void mouseDown(int x, int y, MouseEvent e)
	{
		ItemComponent[] items = new ItemComponent[] {new ShapeItem(shapeType ,new Point2D.Double(x,y))}; 
		super.createCommand(items);
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
 
