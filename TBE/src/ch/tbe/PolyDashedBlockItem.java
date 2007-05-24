package ch.tbe;

import java.awt.geom.Point2D;
import java.util.List;
import ch.tbe.framework.ArrowItem;
import ch.tbe.jgraph.graph.GraphConstants;

public class PolyDashedBlockItem extends ArrowItem
{

	private final float[] DASH = new float[] { 6, 6 };

	public PolyDashedBlockItem(List<Point2D> points)
	{

		GraphConstants.setLineEnd(this.getAttributes(), GraphConstants.ARROW_BLOCK);
		GraphConstants.setDashPattern(this.getAttributes(), DASH);
	}

}
