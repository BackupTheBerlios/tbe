package ch.tbe.jgraph;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.EdgeView;

public class TBEEdgeView extends EdgeView
{

	public static transient TBEEdgeRenderer renderer = new TBEEdgeRenderer();

	public TBEEdgeView()
	{
		super();

	}

	public TBEEdgeView(Object arg0)
	{
		super(arg0);

	}

	public CellViewRenderer getRenderer()
	{
		return renderer;
	}
}