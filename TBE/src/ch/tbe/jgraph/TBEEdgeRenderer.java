package ch.tbe.jgraph;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import org.jgraph.graph.*;
import org.jgraph.util.Bezier;
import org.jgraph.util.Spline2D;

public class TBEEdgeRenderer extends EdgeRenderer {
	private static final long serialVersionUID = 1L;

	/**
   * Returns the shape that represents the current edge in the context of the
   * current graph. This method sets the global beginShape, lineShape and
   * endShape variables as a side-effect.
   */
	protected Shape createShape() {
		int n = view.getPointCount();
		if (n > 1) {
			// Following block may modify static vars as side effect
			// (Flyweight
			// Design)
			EdgeView tmp = view;
			Point2D[] p = null;
			p = new Point2D[n];
			for (int i = 0; i < n; i++) {
				Point2D pt = tmp.getPoint(i);
				if (pt == null)
					return null; // exit
				p[i] = new Point2D.Double(pt.getX(), pt.getY());
			}

			// End of Side-Effect Block
			// Undo Possible MT-Side Effects
			if (view != tmp) {
				view = tmp;
				installAttributes(view);
			}
			// End of Undo
			if (view.sharedPath == null) {
				view.sharedPath = new GeneralPath(GeneralPath.WIND_NON_ZERO, n);
			} else {
				view.sharedPath.reset();
			}
			view.beginShape = view.lineShape = view.endShape = null;
			Point2D p0 = p[0];
			Point2D pe = p[n - 1];
			Point2D p1 = p[1];
			Point2D p2 = p[n - 2];

			if (lineStyle == TBEGraphConstants.STYLE_BEZIER && n > 2) {
				bezier = new Bezier(p);
				p2 = bezier.getPoint(bezier.getPointCount() - 1);
			}

			else if (lineStyle == TBEGraphConstants.STYLE_SPLINE && n > 2) {
				spline = new Spline2D(p);
				double[] point = spline.getPoint(0.9875);
				// Extrapolate p2 away from the end point, pe, to avoid integer
				// rounding errors becoming too large when creating the line end
				double scaledX = pe.getX() - ((pe.getX() - point[0]) * 128);
				double scaledY = pe.getY() - ((pe.getY() - point[1]) * 128);
				p2.setLocation(scaledX, scaledY);
			}

			if (beginDeco != TBEGraphConstants.ARROW_NONE) {
				view.beginShape = createLineEnd(beginSize, beginDeco, p1, p0);
			}
			if (endDeco != TBEGraphConstants.ARROW_NONE) {
				view.endShape = createLineEnd(endSize, endDeco, p2, pe);
			}
			view.sharedPath.moveTo((float) p0.getX(), (float) p0.getY());

			if (lineStyle == TBEGraphConstants.STYLE_BEZIER && n > 2) {
				Point2D[] b = bezier.getPoints();
				view.sharedPath.quadTo((float) b[0].getX(), (float) b[0].getY(), (float) p1.getX(), (float) p1.getY());
				for (int i = 2; i < n - 1; i++) {
					Point2D b0 = b[2 * i - 3];
					Point2D b1 = b[2 * i - 2];
					view.sharedPath.curveTo((float) b0.getX(), (float) b0.getY(), (float) b1.getX(), (float) b1.getY(), (float) p[i].getX(), (float) p[i].getY());
				}
				view.sharedPath.quadTo((float) b[b.length - 1].getX(), (float) b[b.length - 1].getY(), (float) p[n - 1].getX(), (float) p[n - 1].getY());
			} else if (lineStyle == TBEGraphConstants.STYLE_SPLINE && n > 2) {
				for (double t = 0; t <= 1; t += 0.0125) {
					double[] xy = spline.getPoint(t);
					view.sharedPath.lineTo((float) xy[0], (float) xy[1]);
				}
			}
			/* END */
			/* THIS CODE WAS ADDED BY DAVID MEIER 10/05/2007 */

			else if (lineStyle == TBEGraphConstants.STYLE_CURVED) {

				n = p.length;

				for (int q = 1; q < n; q++) {

					Curved curved = new Curved(p[q - 1], p[q]);
					Point2D[] b = curved.getPoints();

					view.sharedPath.quadTo((float) b[0].getX(), (float) b[0].getY(), (float) b[1].getX(), (float) b[1].getY());
					for (int i = 2; i < b.length - 2; i += 2) {
						view.sharedPath.quadTo((float) b[i].getX(), (float) b[i].getY(), (float) b[i + 1].getX(), (float) b[i + 1].getY());
					}
					view.sharedPath.quadTo((float) b[b.length - 1].getX(), (float) b[b.length - 1].getY(), (float) p[q].getX(), (float) p[q].getY());
				}

			}

			else if (lineStyle == TBEGraphConstants.STYLE_DOUBLELINE) {
				DoubleLine d = new DoubleLine(p);
				Point2D[] b = d.getPoints();

				view.sharedPath.moveTo(b[0].getX(), b[0].getY());
				int i;
				for (i = 1; i < b.length / 2; i += 2) {
					view.sharedPath.lineTo((float) b[i].getX(), (float) b[i].getY());
					view.sharedPath.moveTo(b[b.length / 2 + i - 1].getX(), b[b.length / 2 + i - 1].getY());
					view.sharedPath.lineTo((float) b[b.length / 2 + i].getX(), (float) b[b.length / 2 + i].getY());
					view.sharedPath.moveTo(b[i + 1].getX(), b[i + 1].getY());

				}

			} else if (lineStyle == TBEGraphConstants.STYLE_DOUBLEBEZIER) {

				DoubleLine d = new DoubleLine(p);
				Point2D[] dp = d.getPoints();

				Point2D[] line = new Point2D[p.length];

				line[0] = dp[0];
				int j = 1;
				for (int i = 1; i < dp.length / 2; i += 2) {
					line[j] = dp[i];
					j++;
				}
				bezier = new Bezier(line);
				Point2D[] upper = bezier.getPoints();

				line[0] = dp[dp.length / 2];
				j = 1;
				for (int i = dp.length / 2 + 1; i < dp.length; i += 2) {
					line[j] = dp[i];
					j++;
				}
				bezier = new Bezier(line);
				Point2D[] lower = bezier.getPoints();

				view.sharedPath.moveTo(dp[0].getX(), dp[0].getY());
				view.sharedPath.quadTo((float) upper[0].getX(), (float) upper[0].getY(), (float) dp[1].getX(), (float) dp[1].getY());

				view.sharedPath.moveTo(dp[dp.length / 2].getX(), dp[dp.length / 2].getY());
				view.sharedPath.quadTo((float) lower[0].getX(), (float) lower[0].getY(), (float) dp[dp.length / 2 + 1].getX(), (float) dp[dp.length / 2 + 1].getY());

				view.sharedPath.moveTo(dp[1].getX(), dp[1].getY());

				j = 2;
				for (int i = 3; i < dp.length / 2 - 2; i += 2) {
					Point2D b0 = upper[2 * j - 3];
					Point2D b1 = upper[2 * j - 2];
					view.sharedPath.curveTo((float) b0.getX(), (float) b0.getY(), (float) b1.getX(), (float) b1.getY(), (float) dp[i].getX(), (float) dp[i].getY());
					j++;
				}

				view.sharedPath.moveTo(dp[dp.length / 2 + 1].getX(), dp[dp.length / 2 + 1].getY());

				j = 2;
				for (int i = dp.length / 2 + 3; i < dp.length - 2; i += 2) {
					Point2D b0 = lower[2 * j - 3];
					Point2D b1 = lower[2 * j - 2];
					view.sharedPath.curveTo((float) b0.getX(), (float) b0.getY(), (float) b1.getX(), (float) b1.getY(), (float) dp[i].getX(), (float) dp[i].getY());
					j++;
				}

				view.sharedPath.moveTo(dp[dp.length / 2 - 2].getX(), dp[dp.length / 2 - 2].getY());
				view.sharedPath.quadTo((float) upper[upper.length - 1].getX(), (float) upper[upper.length - 1].getY(), (float) dp[dp.length / 2 - 1].getX(), (float) dp[dp.length / 2 - 1].getY());

				view.sharedPath.moveTo(dp[dp.length - 2].getX(), dp[dp.length - 2].getY());
				view.sharedPath.quadTo((float) lower[lower.length - 1].getX(), (float) lower[lower.length - 1].getY(), (float) dp[dp.length - 1].getX(), (float) dp[dp.length - 1].getY());

			}
			/* END */
			else {
				for (int i = 1; i < n - 1; i++)
					view.sharedPath.lineTo((float) p[i].getX(), (float) p[i].getY());
				view.sharedPath.lineTo((float) pe.getX(), (float) pe.getY());
			}

			view.sharedPath.moveTo((float) pe.getX(), (float) pe.getY());
			if (view.endShape == null && view.beginShape == null) {
				// With no end decorations the line shape is the same as the
				// shared path and memory
				view.lineShape = view.sharedPath;
			} else {
				view.lineShape = (GeneralPath) view.sharedPath.clone();
				if (view.endShape != null)
					view.sharedPath.append(view.endShape, true);
				if (view.beginShape != null)
					view.sharedPath.append(view.beginShape, true);
			}
			return view.sharedPath;
		}
		return null;
	}

	/**
   * Paint the current view's direction. Sets tmpPoint as a side-effect such
   * that the invoking method can use it to determine the connection point to
   * this decoration.
   */
	protected Shape createLineEnd(int size, int style, Point2D src, Point2D dst) {

		if (src == null || dst == null)
			return null;
		int d = (int) Math.max(1, dst.distance(src));
		int ax = (int) -(size * (dst.getX() - src.getX()) / d);
		int ay = (int) -(size * (dst.getY() - src.getY()) / d);
		if (style == TBEGraphConstants.ARROW_DIAMOND) {
			Polygon poly = new Polygon();
			poly.addPoint((int) dst.getX(), (int) dst.getY());
			poly.addPoint((int) (dst.getX() + ax / 2 + ay / 3), (int) (dst.getY() + ay / 2 - ax / 3));
			Point2D last = (Point2D) dst.clone();
			dst.setLocation(dst.getX() + ax, dst.getY() + ay);
			poly.addPoint((int) dst.getX(), (int) dst.getY());
			poly.addPoint((int) (last.getX() + ax / 2 - ay / 3), (int) (last.getY() + ay / 2 + ax / 3));
			return poly;

		} else if (style == TBEGraphConstants.ARROW_TECHNICAL || style == TBEGraphConstants.ARROW_CLASSIC) {
			Polygon poly = new Polygon();
			poly.addPoint((int) dst.getX(), (int) dst.getY());
			poly.addPoint((int) (dst.getX() + ax + ay / 2), (int) (dst.getY() + ay - ax / 2));
			Point2D last = (Point2D) dst.clone();
			if (style == TBEGraphConstants.ARROW_CLASSIC) {
				dst.setLocation((int) (dst.getX() + ax * 2 / 3), (int) (dst.getY() + ay * 2 / 3));
				poly.addPoint((int) dst.getX(), (int) dst.getY());
			} else if (style == TBEGraphConstants.ARROW_DIAMOND) {
				dst.setLocation(dst.getX() + 2 * ax, dst.getY() + 2 * ay);
				poly.addPoint((int) dst.getX(), (int) dst.getY());
			} else
				dst.setLocation((int) (dst.getX() + ax), (int) (dst.getY() + ay));
			poly.addPoint((int) (last.getX() + ax - ay / 2), (int) (last.getY() + ay + ax / 2));
			return poly;

		} else if (style == TBEGraphConstants.ARROW_SIMPLE) {
			GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO, 4);
			path.moveTo((float) (dst.getX() + ax + ay / 2), (float) (dst.getY() + ay - ax / 2));
			path.lineTo((float) dst.getX(), (float) dst.getY());
			path.lineTo((float) (dst.getX() + ax - ay / 2), (float) (dst.getY() + ay + ax / 2));
			return path;

		} else if (style == TBEGraphConstants.ARROW_CIRCLE) {
			Ellipse2D ellipse = new Ellipse2D.Float((float) (dst.getX() + ax / 2 - size / 2), (float) (dst.getY() + ay / 2 - size / 2), size, size);
			dst.setLocation(dst.getX() + ax, dst.getY() + ay);
			return ellipse;

		} else if (style == TBEGraphConstants.ARROW_LINE || style == TBEGraphConstants.ARROW_DOUBLELINE || style == TBEGraphConstants.ARROW_BLOCK) {
			GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO, 4);
			if (style != TBEGraphConstants.ARROW_BLOCK) {
				path.moveTo((float) (dst.getX() + ax / 2 + ay / 2), (float) (dst.getY() + ay / 2 - ax / 2));
				path.lineTo((float) (dst.getX() + ax / 2 - ay / 2), (float) (dst.getY() + ay / 2 + ax / 2));
			}
			if (style == TBEGraphConstants.ARROW_DOUBLELINE) {
				path.moveTo((float) (dst.getX() + ax / 3 + ay / 2), (float) (dst.getY() + ay / 3 - ax / 2));
				path.lineTo((float) (dst.getX() + ax / 3 - ay / 2), (float) (dst.getY() + ay / 3 + ax / 2));
			}
			/* THIS CODE WAS ADDED BY DAVID MEIER 10/05/2007 */
			else if (style == TBEGraphConstants.ARROW_BLOCK) {
				double dx = dst.getX() - src.getX();
				double dy = dst.getY() - src.getY();
				size = 10;
				ax = (int) (((-dy) / d) * size);
				ay = (int) ((dx / d) * size);
				path.moveTo((float) (dst.getX() + ax), (float) (dst.getY() + ay));
				path.lineTo((float) (dst.getX() - ax), (float) (dst.getY() - ay));
				path.lineTo((float) (dst.getX() - ax + (dx / d) * size), (float) (dst.getY() - ay + (dy / d) * size));
				path.moveTo((float) (dst.getX() + ax), (float) (dst.getY() + ay));
				path.lineTo((float) (dst.getX() + ax + (dx / d) * size), (float) (dst.getY() + ay + (dy / d) * size));
				/* END */
			}
			return path;
		}
		return null;
	}

}
