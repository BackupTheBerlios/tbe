package ch.tbe.schrott;
import java.awt.geom.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import ch.tbe.BezierSolidArrowItem;
import ch.tbe.ShapeItem;
import ch.tbe.ShapeType;
import ch.tbe.TextBoxItem;
import ch.tbe.gui.TBE;
import ch.tbe.jgraph.*;
import ch.tbe.jgraph.graph.*;

public class Line extends JFrame
{
	
	public static void main(String[] args)
	{
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,	new	DefaultCellViewFactory());
		JGraph graph = new JGraph(model, view);
		DefaultGraphCell[] cells = new DefaultGraphCell[3];
		

		List<Point2D> myPoints = new ArrayList<Point2D>();
		myPoints.add(new Point2D.Double(40, 40));
		myPoints.add(new Point2D.Double(140, 100));	
		myPoints.add(new Point2D.Double(40, 140));
		myPoints.add(new Point2D.Double(20, 250));
		myPoints.add(new Point2D.Double(300, 150));

		cells[0] = new BezierSolidArrowItem(myPoints);
		
		cells[1] = new TextBoxItem(new Rectangle2D.Double(100,100,120,120));
		
		URL imgURL = TBE.class.getResource("../pics/logo.jpg");
		Icon logoIcon = new ImageIcon(imgURL);
		ShapeType icon = new ShapeType("test","test",logoIcon);
		cells[2] = new ShapeItem(icon, new Point2D.Double(200,200));
		
		graph.getGraphLayoutCache().insert(cells);
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(graph));
		frame.pack();
		frame.setVisible(true);
		//PrintHandler.export(graph);
		//PrintHandler.printBoard(graph);
		
	}
	
}
