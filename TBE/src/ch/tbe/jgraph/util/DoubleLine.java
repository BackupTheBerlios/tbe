
package ch.tbe.jgraph.util;

import java.awt.geom.Point2D;



public class DoubleLine {

	private Point2D[] rArray;
	private final int HEIGHT = 4;
	
	public DoubleLine(Point2D[] points) {
		
		rArray = new Point2D[points.length*2];

		double x = points[1].getX()-points[0].getX();
		double y = points[1].getY()-points[0].getY();
		
		double eVectorx = x/Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
		double eVectory = y/Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
		
		double yHeight = eVectorx*HEIGHT;
		double xHeight = -eVectory*HEIGHT;
		
		rArray[0] = new Point2D.Double(points[0].getX()+xHeight,points[0].getY()+yHeight);
		rArray[points.length] = new Point2D.Double(points[0].getX()-xHeight,points[0].getY()-yHeight);
		
		for(int i = 1; i < points.length-1; i++){
			
			x = points[i].getX()-points[i-1].getX() + points[i].getX()-points[i+1].getX();
			y = points[i].getY()-points[i-1].getY() + points[i].getY()-points[i+1].getY();
		
			eVectorx = x/Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
			eVectory = y/Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
				
			xHeight = eVectorx*HEIGHT;
			yHeight = eVectory*HEIGHT;
			
			rArray[i] = new Point2D.Double(points[i].getX()+xHeight,points[i].getY()+yHeight);
			rArray[points.length+i] = new Point2D.Double(points[i].getX()-xHeight,points[i].getY()-yHeight);
		
		}
		
		rArray[points.length-1] = new Point2D.Double(points[points.length-1].getX()+xHeight,points[points.length-1].getY()+yHeight);
		rArray[rArray.length-1] = new Point2D.Double(points[points.length-1].getX()-xHeight,points[points.length-1].getY()-yHeight);
		
	}

	/**
	 * Returns the calculated double points.
	 * @return the calculated double points
	 */
	public Point2D[] getPoints() {
				
		return rArray;
	}


	/**
	 * Returns the double points at position i.
	 * @param i
	 * @return the double point at position i
	 */
	public Point2D getPoint(int i) {
		return rArray[i];
	}

}
