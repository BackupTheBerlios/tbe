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
		myPoints.add(new Point2D.Double(140, 100));	
		myPoints.add(new Point2D.Double(40, 140));
		myPoints.add(new Point2D.Double(20, 250));
		myPoints.add(new Point2D.Double(300, 150));
		int style = GraphConstants.STYLE_DOUBLELINE;
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
	
}
