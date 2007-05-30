package ch.tbe.framework;

import java.awt.geom.Point2D;
import java.util.List;

import org.jgraph.graph.DefaultEdge;

import ch.tbe.Invoker;
import ch.tbe.jgraph.TBEGraphConstants;

public abstract class ArrowItem extends DefaultEdge implements ItemComponent
{
	protected final int DEFAULTLENGTH = 20;

	protected ArrowItem()
	{
		
		TBEGraphConstants.setLineEnd(this.getAttributes(), TBEGraphConstants.ARROW_CLASSIC);
		TBEGraphConstants.setEndFill(this.getAttributes(), true);
		
	}
	public void setPoints(List<Point2D> points){
		TBEGraphConstants.setPoints(this.getAttributes(), points);
	}
	
	public List<Point2D> getPoints(){
		return TBEGraphConstants.getPoints(this.getAttributes());
	}

	public void addPoint()
	{
		int size = TBEGraphConstants.getPoints(this.getAttributes()).size();
		double lastX = ((Point2D) TBEGraphConstants.getPoints(this.getAttributes()).get(size - 1)).getX();
		double lastY = ((Point2D) TBEGraphConstants.getPoints(this.getAttributes()).get(size - 1)).getY();
		
		Point2D point = new Point2D.Double(lastX + DEFAULTLENGTH, lastY);
		TBEGraphConstants.getPoints(this.getAttributes()).add(point);
	}

	public void removePoint()
	{
		if (TBEGraphConstants.getPoints(this.getAttributes()).size() > 2)
		{
			int size = TBEGraphConstants.getPoints(this.getAttributes()).size();
			Point2D lastP = ((Point2D) TBEGraphConstants.getPoints(this.getAttributes()).get(size - 1));
			TBEGraphConstants.getPoints(this.getAttributes()).remove(lastP);

		}
	}
}
