package ch.tbe.jgraph;

import java.awt.geom.Point2D;

/**
 * Tactic Board Editor
 * **********************
 * DoubleLine 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
 */

public class DoubleLine {

	private Point2D[] rArray;
	private final int HEIGHT = 2;

	public DoubleLine(Point2D[] points) {
		int n = points.length * 2 + (points.length - 2) * 2;
		rArray = new Point2D[n];

		int j = 0;
		for (int i = 0; i < points.length - 1; i++) {

			double x = points[i + 1].getX() - points[i].getX();
			double y = points[i + 1].getY() - points[i].getY();

			double eVectorx = x / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
			double eVectory = y / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

			double yHeight = eVectorx * HEIGHT;
			double xHeight = -eVectory * HEIGHT;

			rArray[j] = new Point2D.Double(points[i].getX() + xHeight, points[i].getY() + yHeight);
			rArray[n / 2 + j] = new Point2D.Double(points[i].getX() - xHeight, points[i].getY() - yHeight);
			j++;
			rArray[j] = new Point2D.Double(points[i + 1].getX() + xHeight, points[i + 1].getY() + yHeight);
			rArray[n / 2 + j] = new Point2D.Double(points[i + 1].getX() - xHeight, points[i + 1].getY() - yHeight);
			j++;

		}

	}

	/**
   * Returns the calculated double points.
   * 
   * @return the calculated double points
   */
	public Point2D[] getPoints() {

		return rArray;
	}

	/**
   * Returns the double points at position i.
   * 
   * @param i
   * @return the double point at position i
   */
	public Point2D getPoint(int i) {
		return rArray[i];
	}

}
