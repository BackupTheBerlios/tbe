package ch.tbe.jgraph;


import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.Edge;


public class TBECellViewFactory extends DefaultCellViewFactory {
	

	/**
	 * Constructs an EdgeView view for the specified object.
	 */
	protected TBEEdgeView createEdgeView(Object cell) {
		if (cell instanceof Edge)
			return createEdgeView((Edge) cell);
		else
			return new TBEEdgeView(cell);
	}

	/**
	 * Constructs an EdgeView view for the specified object.
	 * 
	 * @deprecated replaced by {@link #createEdgeView(Object)}since
	 *             JGraph no longer exposes dependecies on GraphCell subclasses
	 *             (Port, Edge)
	 */
	protected TBEEdgeView createEdgeView(Edge cell) {
		return new TBEEdgeView(cell);
	}


}
