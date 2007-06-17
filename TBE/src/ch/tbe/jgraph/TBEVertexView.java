package ch.tbe.jgraph;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

public class TBEVertexView extends VertexView
{

	public static transient TBEVertexRenderer renderer = new TBEVertexRenderer();
	
	public TBEVertexView()
	{
		super();
	}

	public TBEVertexView(Object arg0)
	{
		super(arg0);
	}

	@Override
	public CellViewRenderer getRenderer()
	{
		
		return renderer;
	}

}
