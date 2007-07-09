package ch.tbe.jgraph;

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.Edge;
import org.jgraph.graph.VertexView;

/**
 * Tactic Board Editor
 * **********************
 * TBECellViewFactory 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class TBECellViewFactory extends DefaultCellViewFactory {
	private static final long serialVersionUID = 1L;

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
   * @deprecated replaced by {@link #createEdgeView(Object)}since JGraph no
   *             longer exposes dependecies on GraphCell subclasses (Port, Edge)
   */
	protected TBEEdgeView createEdgeView(Edge cell) {
		return new TBEEdgeView(cell);
	}

	/**
   * Constructs a VertexView view for the specified object.
   */
	protected VertexView createVertexView(Object cell) {
		return new TBEVertexView(cell);
	}

}
