package ch.tbe.schrott;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import ch.tbe.jgraph.*;
import ch.tbe.jgraph.graph.*;

public class ArrowGUI extends JFrame
{
	static final int ARROW = GraphConstants.ARROW_CLASSIC;
	
	public static void main(String[] args)
	{
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,	new	DefaultCellViewFactory());
		JGraph graph = new JGraph(model, view);
		DefaultGraphCell[] cells = new DefaultGraphCell[9];
		// Zelle 0: Grafik Markierung
		cells[0] = new DefaultGraphCell();
		GraphConstants.setBounds(cells[0].getAttributes(), new Rectangle2D.Double(40,40,30,15));
		Icon myIcon = new ImageIcon("markierung.gif");
		GraphConstants.setIcon(cells[0].getAttributes(), myIcon);
		GraphConstants.setOpaque(cells[0].getAttributes(), true);
		// Zelle 1: T
		cells[1] = new DefaultGraphCell(new String("T"));
		GraphConstants.setBounds(cells[1].getAttributes(), new Rectangle2D.Double(140,140,20,20));
		GraphConstants.setOpaque(cells[1].getAttributes(), true);
		// Zelle 2: klassischer Pfeil
		DefaultEdge edge1 = new DefaultEdge();
		cells[2] = edge1;
		List myPoints1 = new Vector();
		myPoints1.add(new Point2D.Double(10, 10));
		myPoints1.add(new Point2D.Double(10, 100));
		GraphConstants.setPoints(edge1.getAttributes(), myPoints1);
		GraphConstants.setLineEnd(edge1.getAttributes(), ARROW);
		GraphConstants.setEndFill(edge1.getAttributes(), true);
		// Zelle 3: Bezier Line
		DefaultEdge edge2 = new DefaultEdge();
		cells[3] = edge2;
		List<Point2D> myPoints = new ArrayList<Point2D>();
		myPoints.add(new Point2D.Double(40, 40));
		myPoints.add(new Point2D.Double(100, 100));		
		int style = GraphConstants.STYLE_BEZIER;
		GraphConstants.setLineStyle(edge2.getAttributes(), style);
		List myPoints2 = new Vector();
		myPoints2.add(new Point2D.Double(30, 20));
		myPoints2.add(new Point2D.Double(50, 70));
		myPoints2.add(new Point2D.Double(100, 40));
		GraphConstants.setPoints(edge2.getAttributes(), myPoints2);
		GraphConstants.setLineEnd(edge2.getAttributes(), ARROW);
		GraphConstants.setEndFill(edge2.getAttributes(), true);
		// Zelle 4 : dashed Line
		DefaultEdge edge3 = new DefaultEdge();
		cells[4] = edge3;
		List myPoints3 = new Vector();
		myPoints3.add(new Point2D.Double(10, 150));
		myPoints3.add(new Point2D.Double(100, 100));
		GraphConstants.setPoints(edge3.getAttributes(), myPoints3);
		float[] dashPattern = new float[]{6,6};
		GraphConstants.setDashPattern(edge3.getAttributes(), dashPattern);
		GraphConstants.setLineEnd(edge3.getAttributes(), ARROW);
		GraphConstants.setEndFill(edge3.getAttributes(), true);
		// Zelle 5+6: Tempowechsel (nicht optimal, da nicht verknüpft!)
		DefaultEdge edge4 = new DefaultEdge();
		cells[5] = edge4;
		List myPoints4 = new Vector();
		myPoints4.add(new Point2D.Double(150, 30));
		myPoints4.add(new Point2D.Double(150, 90));
		myPoints4.add(new Point2D.Double(180, 100));
		GraphConstants.setPoints(edge4.getAttributes(), myPoints4);
		GraphConstants.setLineEnd(edge4.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
		GraphConstants.setEndFill(edge4.getAttributes(), true);
		DefaultEdge edge5 = new DefaultEdge();
		cells[6] = edge5;
		List myPoints5 = new Vector();
		myPoints5.add(new Point2D.Double(150, 90));
		myPoints5.add(new Point2D.Double(150, 130));
		GraphConstants.setPoints(edge5.getAttributes(), myPoints5);
		GraphConstants.setLineEnd(edge5.getAttributes(), ARROW);
		GraphConstants.setEndFill(edge5.getAttributes(), true);
		// Zelle 7+8: Schuss (nicht optimal, da nicht verknüpft!)
		DefaultEdge edge6 = new DefaultEdge();
		cells[7] = edge6;
		List myPoints6 = new Vector();
		myPoints6.add(new Point2D.Double(120, 30));
		myPoints6.add(new Point2D.Double(120, 120));
		GraphConstants.setPoints(edge6.getAttributes(), myPoints6);
		GraphConstants.setLineEnd(edge6.getAttributes(), ARROW);
		GraphConstants.setEndFill(edge6.getAttributes(), true);
		DefaultEdge edge7 = new DefaultEdge();
		cells[8] = edge7;
		List myPoints7 = new Vector();
		myPoints7.add(new Point2D.Double(122, 30));
		myPoints7.add(new Point2D.Double(122, 116));
		GraphConstants.setPoints(edge7.getAttributes(), myPoints7);
		GraphConstants.setLineEnd(edge7.getAttributes(), GraphConstants.ARROW_NONE);
		GraphConstants.setEndFill(edge7.getAttributes(), true);
		
		graph.getGraphLayoutCache().insert(cells);
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JScrollPane(graph));
		frame.pack();
		frame.setVisible(true);
	}
}
