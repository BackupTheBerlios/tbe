package ch.pacman.game;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import ch.pacman.graph.PacVertex;
import jdsl.graph.api.Vertex;
import jdsl.graph.api.VertexIterator;
import jdsl.graph.ref.IncidenceListGraph;

public class Heuristic
{
	private static int DEPTH = 5;
	private static JTree tree;

	public static Vertex getBestMove(Vertex[][] screendata, boolean pac)
	{
		//create root node and its children
		DefaultMutableTreeNode node = new DefaultMutableTreeNode();
		Vertex vertex = null;
		PacMan tempPac = null;
		Vertex[][] pathdata = Level.clonePathdata(screendata);
		ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
		
		for(Vertex[] vs : pathdata){
			for(Vertex v : vs){
				PacVertex pv = (PacVertex)v;
				if(pv.isPacMan()){
					vertex = v;
					tempPac = pv.getPacMan();
				}else if(pv.getGhostCount() > 0){
					ghosts.addAll(pv.getGhosts());
				}
			}
		}
		
		Ghost[] tempGhosts = (Ghost[]) ghosts.toArray();
		IncidenceListGraph igraph = ((PacVertex) pathdata[0][0].element()).getGraph();
		Heuristic.makeTree(node, vertex, Heuristic.DEPTH, pac, igraph, tempPac, tempGhosts);
		
		//create java tree from root node
		tree = new JTree(node);
		
		//evalute the tree
		return null;
	}

	private static void makeTree(DefaultMutableTreeNode node, Vertex vertex,
			int depth, boolean pac, IncidenceListGraph igraph, PacMan tempPac,
			Ghost[] tempGhosts)
	{
		IncidenceListGraph graph = Level.cloneGraph(igraph);
		PacMan pacman = tempPac.clone();
		Ghost[] ghosts = new Ghost[tempGhosts.length];
		for (int i = 0; i < tempGhosts.length; i++)
		{
			ghosts[i] = tempGhosts[i].clone();
		}

		if (depth != 0)
		{
			if (pac)
			{
				VertexIterator vi = graph.adjacentVertices(vertex);
				while (vi.hasNext())
				{
					DefaultMutableTreeNode child = new DefaultMutableTreeNode();
					node.add(child);
					// TODO: child set element
					makeTree(child, vi.nextVertex(), depth - 1, true, graph,
							pacman, ghosts);
				}
			} else
			{
				ArrayList<Ghost> decision = new ArrayList<Ghost>();
				ArrayList<Ghost> nonDecision = new ArrayList<Ghost>();
				for (int i = 0; i < tempGhosts.length; i++)
				{
					if (igraph.degree(tempGhosts[i].getCurrentVertex()) > 2)
					{
						decision.add(tempGhosts[i]);
					} else
					{
						nonDecision.add(tempGhosts[i]);
					}
				}
				for (int i = 0; i < decision.size(); i++){
					//for each adjacent vertex
					DefaultMutableTreeNode child = new DefaultMutableTreeNode();
					node.add(child);
				}
				for (int i = 0; i < nonDecision.size(); i++){
					//only one
					DefaultMutableTreeNode child = new DefaultMutableTreeNode();
					node.add(child);
				}

			}
		}
	}
}
