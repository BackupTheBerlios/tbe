package ch.tbe.jgraph;

import org.jgraph.graph.*;

/**
 * Tactic Board Editor
 * **********************
 * TBEVertexView 
 * 
 * @version 1.0 7/07 
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

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
