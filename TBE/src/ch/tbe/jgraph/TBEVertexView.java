package ch.tbe.jgraph;


import org.jgraph.graph.*;



public class TBEVertexView extends VertexView
{

  private static final long serialVersionUID = 1L;
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
