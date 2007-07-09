package ch.tbe.jgraph;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.EdgeView;

/**
 * Tactic Board Editor
 * **********************
 * TBEEdgeView 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class TBEEdgeView extends EdgeView {
	private static final long serialVersionUID = 1L;
	public static transient TBEEdgeRenderer renderer = new TBEEdgeRenderer();

	public TBEEdgeView() {
		super();

	}

	public TBEEdgeView(Object arg0) {
		super(arg0);

	}

	public CellViewRenderer getRenderer() {
		return renderer;
	}
}