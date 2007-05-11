package ch.tbe.schrott;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import ch.tbe.jgraph.*;
import ch.tbe.jgraph.graph.*;

public class Line extends JFrame
{
	static final int ARROW = GraphConstants.ARROW_CLASSIC;
	
	
	public static void main(String[] args)
	{
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,	new	DefaultCellViewFactory());
		JGraph graph = new JGraph(model, view);
		DefaultGraphCell[] cells = new DefaultGraphCell[1];
		
		// Zelle 0: Bezier Line
		DefaultEdge edge2 = new DefaultEdge();
		cells[0] = edge2;
		List<Point2D> myPoints = new ArrayList<Point2D>();
		myPoints.add(new Point2D.Double(40, 40));
		myPoints.add(new Point2D.Double(140, 140));	
		myPoints.add(new Point2D.Double(100, 150));
		myPoints.add(new Point2D.Double(20, 250));
		myPoints.add(new Point2D.Double(300, 150));
		int style = GraphConstants.STYLE_CURVED;
		GraphConstants.setLineStyle(edge2.getAttributes(), style);

		GraphConstants.setPoints(edge2.getAttributes(), myPoints);
		GraphConstants.setLineEnd(edge2.getAttributes(), ARROW);
		GraphConstants.setEndFill(edge2.getAttributes(), true);

		
		graph.getGraphLayoutCache().insert(cells);
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(graph));
		frame.pack();
		frame.setVisible(true);
	}
	public static List calculate(List<Point2D> points){
		
		int space = 3;
		int xSpace, ySpace;
		int height = 2;
		int j = 0;
		
		List p = new Vector();
		
		double l = Math.sqrt(Math.pow(points.get(1).getX() - points.get(0).getX(), 2)+Math.pow(points.get(1).getY() - points.get(0).getY(), 2));
		int npoints = (int) l / space;
		
		xSpace = (int) (points.get(1).getX() - points.get(0).getX()) / npoints;
		ySpace = (int) (points.get(1).getY() - points.get(0).getY()) / npoints;
		
		int normx = -ySpace;
		int normy = xSpace;
		int yHeight = (int) Math.sqrt(Math.pow(normx, 2)+Math.pow(normy, 2))/height*normx;
		int xHeight = (int) Math.sqrt(Math.pow(normx, 2)+Math.pow(normy, 2))/height*normy;
		
		p.add(points.get(0));
		
		for(int i=0; i<=npoints-1;i++){
			
			switch (j){
			case 0:
				p.add(new Point2D.Double(((Point2D)p.get(i)).getX()+xSpace+xHeight,((Point2D)p.get(i)).getY()+ySpace+yHeight));
				j++;
				break;
			case 1:
				p.add(new Point2D.Double(((Point2D)p.get(i)).getX()+xSpace-xHeight,((Point2D)p.get(i)).getY()+ySpace-yHeight));
				j++;
				break;
			case 2:
				p.add(new Point2D.Double(((Point2D)p.get(i)).getX()+xSpace-xHeight,((Point2D)p.get(i)).getY()+ySpace-yHeight));
				j++;
				break;
			case 3:
				p.add(new Point2D.Double(((Point2D)p.get(i)).getX()+xSpace+xHeight,((Point2D)p.get(i)).getY()+ySpace+yHeight));
				j=0;
				break;}
			
		}
		
		p.add(points.get(1));

		
		return p;
	}
	
}
