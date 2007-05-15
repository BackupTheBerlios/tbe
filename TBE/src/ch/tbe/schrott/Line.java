package ch.tbe.schrott;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import ch.tbe.BezierDashedArrowItem;
import ch.tbe.BezierDoubleArrowItem;
import ch.tbe.BezierSolidArrowItem;
import ch.tbe.PolyCurvedArrowItem;
import ch.tbe.PolyCurvedBlockItem;
import ch.tbe.PolyDashedArrowItem;
import ch.tbe.PolyDashedBlockItem;
import ch.tbe.PolyDoubleArrowItem;
import ch.tbe.PolySolidArrowItem;
import ch.tbe.PolySolidBlockItem;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.*;
import ch.tbe.jgraph.graph.*;

public class Line extends JFrame
{
	
	public static void main(String[] args)
	{
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,	new	DefaultCellViewFactory());
		JGraph graph = new JGraph(model, view);
		DefaultGraphCell[] cells = new DefaultGraphCell[1];
		

		List<Point2D> myPoints = new ArrayList<Point2D>();
		myPoints.add(new Point2D.Double(40, 40));
		myPoints.add(new Point2D.Double(140, 100));	
		myPoints.add(new Point2D.Double(40, 140));
		myPoints.add(new Point2D.Double(20, 250));
		myPoints.add(new Point2D.Double(300, 150));

		ArrowItem edge = new BezierSolidArrowItem(myPoints);
		cells[0] = edge.getArrow();

		
		graph.getGraphLayoutCache().insert(cells);
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(graph));
		frame.pack();
		frame.setVisible(true);
	}
	
}
