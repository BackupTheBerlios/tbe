package ch.tbe;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

 class ShapeTool {
	
	private ShapeType icon;
	
	public ShapeTool(ShapeType icon){
		this.icon = icon;
	}
 
	public void mouseDown(int x, int y, MouseEvent e)
	{
		
		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(new ShapeItem(icon ,new Point2D.Double(x,y)));
	}
 
}
 
