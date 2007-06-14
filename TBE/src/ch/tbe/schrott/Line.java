package ch.tbe.schrott;
import java.awt.geom.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import ch.tbe.ItemType;
import ch.tbe.ShapeItem;
import ch.tbe.gui.TBE;
import ch.tbe.item.BezierDoubleArrowItem;
import ch.tbe.item.BezierSolidArrowItem;
import ch.tbe.item.PolyCurvedArrowItem;
import ch.tbe.item.PolyCurvedBlockItem;
import ch.tbe.item.TextBoxItem;
import ch.tbe.jgraph.*;


public class Line extends JFrame
{
	
	public static void main(String[] args)
	{
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,	new	TBECellViewFactory());
		JGraph graph = new JGraph(model, view);
		DefaultGraphCell[] cells = new DefaultGraphCell[3];
		
		
		List<Point2D> myPoints = new ArrayList<Point2D>();
		myPoints.add(new Point2D.Double(40, 40));
		myPoints.add(new Point2D.Double(140, 100));	
		myPoints.add(new Point2D.Double(40, 140));
		myPoints.add(new Point2D.Double(20, 250));
		myPoints.add(new Point2D.Double(300, 150));

	
		DefaultGraphCell[] cells2 = new DefaultGraphCell[1];
		cells2[0] = new TextBoxItem(new Rectangle2D.Double(50,50,120,120));;
		cells[1] = new TextBoxItem(new Rectangle2D.Double(100,100,120,120));
		
		URL imgURL = TBE.class.getResource("../pics/logo.jpg");
		Icon logoIcon = new ImageIcon(imgURL);
		ItemType icon = new ItemType("test","test",logoIcon);
		cells[2] = new ShapeItem(icon, new Point2D.Double(200,200));
		
		graph.getGraphLayoutCache().insert(cells);
		
		graph.getGraphLayoutCache().insert(cells2);
		
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(graph));
		frame.pack();
		frame.setVisible(true);
		//PrintHandler.export(graph);
		//PrintHandler.printBoard(graph);
		
	}
	
}
