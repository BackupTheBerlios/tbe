package ch.pacman.game;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import ch.pacman.graph.PacVertex;

import jdsl.graph.api.Edge;
import jdsl.graph.api.EdgeIterator;
import jdsl.graph.api.Vertex;
import jdsl.graph.api.VertexIterator;
import jdsl.graph.ref.IncidenceListGraph;

public abstract class Level
{
	protected IncidenceListGraph graph = new IncidenceListGraph();

	public static Color dotcolor = new Color(192, 192, 0);

	public static int bigdotcolor = 192;

	public static int dbigdotcolor = -2;

	public static Color mazecolor;

	public static final int blocksize = 24;

	public static final int nrofblocks = 15;

	public static final int screensize = nrofblocks * blocksize;
	
	public static  int scaredtime = 120;

	public static Image ghost1, ghost2, ghostscared1, ghostscared2;

	public static Image pacman1, pacman2up, pacman2left, pacman2right,
			pacman2down;

	public static Image pacman3up, pacman3down, pacman3left, pacman3right;

	public static Image pacman4up, pacman4down, pacman4left, pacman4right;

	protected Vertex[][] pathdata = new Vertex[nrofblocks][nrofblocks];

	protected short[][] leveldata = null;

	protected Point pacManStart = new Point(0, 0);

	protected Point ghostStart = new Point(nrofblocks, nrofblocks);

	public short[][] getLeveldata()
	{
		return leveldata;
	}

	public Point getPacManStart()
	{
		return pacManStart;
	}

	public Point getGhostStart()
	{
		return ghostStart;
	}

	public IncidenceListGraph getGraph()
	{
		return graph;
	}
	public IncidenceListGraph getClonedGraph(){
		return this.cloneGraph(graph);
	}
	

	public IncidenceListGraph cloneGraph(IncidenceListGraph graph)
	{
		IncidenceListGraph cloneGraph = new IncidenceListGraph();
		VertexIterator vi = graph.vertices();
		while (vi.hasNext())
		{
			cloneGraph.insertVertex(((PacVertex) vi.nextVertex().element())
					.clone(cloneGraph));
		}

		EdgeIterator ei = graph.edges();
		
		while (ei.hasNext())
		{
			Edge e = ei.nextEdge();
			Vertex[] verts = graph.endVertices(e);
			Vertex dest = verts[0];
			Vertex orig = verts[1];
			boolean destfound = false;
			boolean origfound = false;
			Vertex cloneDest = null; 
			Vertex cloneOrig = null;
			VertexIterator vi2 = cloneGraph.vertices();
			while(vi2.hasNext()){
				Vertex cloneVert = vi2.nextVertex();
				if(dest.element().equals(cloneVert.element())){
					cloneDest = cloneVert;
					destfound = true;
				}
				if(orig.element().equals(cloneVert.element())){
					cloneOrig = cloneVert;
					origfound = true;
				}
				
				if (destfound && origfound) break;
				
			}
			
			cloneGraph.insertEdge(cloneOrig, cloneDest, "");//TODO: sting clonen;
		}
		return cloneGraph;

	}

	public Vertex[][] getPathdata()
	{
		return pathdata;
	}
	
	public Vertex[][] getClonedPathdata(){
		return this.clonePathdata(pathdata);
	}
	
	public Vertex[][] clonePathdata( Vertex[][] pathdata)
	{
		IncidenceListGraph cloneGraph = new IncidenceListGraph();
		Vertex[][] cloneVertex = new Vertex[nrofblocks][nrofblocks];
		for (int i = 0; i < nrofblocks; i++) {
			
			for (int j = 0; j < nrofblocks; j++) {
				cloneVertex[i][j] = cloneGraph.insertVertex(((PacVertex) pathdata[i][j].element()).clone(cloneGraph));
				
			}
			
			
		}
		IncidenceListGraph graph = ((PacVertex) pathdata[0][0].element()).getGraph();
		EdgeIterator ei = graph.edges();
		
		while (ei.hasNext())
		{
			Edge e = ei.nextEdge();
			Vertex[] verts = graph.endVertices(e);
			Vertex dest = verts[0];
			Vertex orig = verts[1];
			boolean destfound = false;
			boolean origfound = false;
			Vertex cloneDest = null; 
			Vertex cloneOrig = null;
			VertexIterator vi2 = cloneGraph.vertices();
			while(vi2.hasNext()){
				Vertex cloneVert = vi2.nextVertex();
				if(dest.element().equals(cloneVert.element())){
					cloneDest = cloneVert;
					destfound = true;
				}
				if(orig.element().equals(cloneVert.element())){
					cloneOrig = cloneVert;
					origfound = true;
				}
				
				if (destfound && origfound) break;
				
			}
			
			cloneGraph.insertEdge(cloneOrig, cloneDest, "");//TODO: sting clonen;
		}
		return cloneVertex;
	}

}
