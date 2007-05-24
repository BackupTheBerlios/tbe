package ch.tbe;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

 class ShapeTool {
	
	private ShapeType shapeType;
	
	public ShapeTool(ShapeType shapeType){
		this.shapeType = shapeType;
	}
 
	public void mouseDown(int x, int y, MouseEvent e)
	{
		
		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(new ShapeItem(shapeType ,new Point2D.Double(x,y)));
	}
 
}
 
