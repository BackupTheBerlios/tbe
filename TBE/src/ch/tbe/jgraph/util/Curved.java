
package ch.tbe.jgraph.util;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Vector;


public class Curved {

	private List p = new Vector();
	private final int SPACE = 4;
	private final int HEIGHT = 8;
	
	public Curved(Point2D start, Point2D end) {
		
		double xSpace, ySpace;

		double x = end.getX()-start.getX();
		double y = end.getY()-start.getY();
		double l = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
		double eVectorx = x/l;
		double eVectory = y/l;
		
		int npoints = (int) l / SPACE;
		
		xSpace =  (end.getX() - start.getX()) / npoints;
		ySpace =  (end.getY() - start.getY()) / npoints;
		
		double yHeight = eVectorx*HEIGHT;
		double xHeight = -eVectory*HEIGHT;
		
		p.add(start);
		int j = 0;
		for(int i=0; i<=npoints-1;i++){
			

			switch (j){
			case 0:
				p.add(new Point2D.Double(((Point2D)p.get(i)).getX()+xSpace+xHeight,((Point2D)p.get(i)).getY()+ySpace+yHeight));
				j++;
				break;
			case 1:
			case 2:
				p.add(new Point2D.Double(((Point2D)p.get(i)).getX()+xSpace-xHeight,((Point2D)p.get(i)).getY()+ySpace-yHeight));
				j++;
				break;
			case 3:
				p.add(new Point2D.Double(((Point2D)p.get(i)).getX()+xSpace+xHeight,((Point2D)p.get(i)).getY()+ySpace+yHeight));
				j=0;
				break;}
			
		}
		p.remove(0);


	}

	/**
	 * Returns the calculated courved points.
	 * @return the calculated courved points
	 */
	public Point2D[] getPoints() {
		
		Point2D[] rArray = new Point2D[p.size()-1];
		for(int i = 0; i < p.size()-1;i++){
			rArray[i] = (Point2D)p.get(i);
		
		}
		return rArray;
	}

	/**
	 * Returns the number of curved points.
	 * @return number of bezier points
	 */
	public int getPointCount() {
		return p.size();
	}

	/**
	 * Returns the curved points at position i.
	 * @param i
	 * @return the curved point at position i
	 */
	public Point2D getPoint(int i) {
		return (Point2D) p.get(i);
	}

}
