package ch.tbe.jgraph;

import java.awt.geom.Point2D;

/**
 * Tactic Board Editor
 * **********************
 * Curved 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class Curved {

	private Point2D[] p;
	private final int SPACE = 2;
	private final int HEIGHT = 4;

	public Curved(Point2D start, Point2D end) {

		double xSpace, ySpace;

		double x = end.getX() - start.getX();
		double y = end.getY() - start.getY();
		double l = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double eVectorx = x / l;
		double eVectory = y / l;

		int npoints = (int) l / SPACE;

		xSpace = (end.getX() - start.getX()) / npoints;
		ySpace = (end.getY() - start.getY()) / npoints;

		double yHeight = eVectorx * HEIGHT;
		double xHeight = -eVectory * HEIGHT;

		p = new Point2D[npoints];
		p[0] = new Point2D.Double(start.getX() + xSpace + xHeight, start.getY() + ySpace + yHeight);

		int j = 1;
		for (int i = 1; i < npoints; i++) {

			switch (j) {
			case 0:
				p[i] = new Point2D.Double(p[i - 1].getX() + xSpace + xHeight, p[i - 1].getY() + ySpace + yHeight);
				j++;
				break;
			case 1:
			case 2:
				p[i] = new Point2D.Double(p[i - 1].getX() + xSpace - xHeight, p[i - 1].getY() + ySpace - yHeight);
				j++;
				break;
			case 3:
				p[i] = new Point2D.Double(p[i - 1].getX() + xSpace + xHeight, p[i - 1].getY() + ySpace + yHeight);
				j = 0;
				break;
			}

		}

	}

	/**
   * Returns the calculated courved points.
   * 
   * @return the calculated courved points
   */
	public Point2D[] getPoints() {

		return p;
	}

	/**
   * Returns the curved points at position i.
   * 
   * @param i
   * @return the curved point at position i
   */
	public Point2D getPoint(int i) {
		return p[i];
	}

}
