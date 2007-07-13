package ch.pacman.game;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import ch.pacman.graph.PacVertex;

import jdsl.graph.algo.DFS;
import jdsl.graph.api.Vertex;
import jdsl.graph.api.VertexIterator;
import jdsl.graph.ref.IncidenceListGraph;

public class Heuristic
{
	private static int DEPTH = 5;

	private static JTree tree;

	public static Vertex getBestMove(Vertex[][] screendata)
	{
		TreeNode root = new DefaultMutableTreeNode();
		tree = new JTree(root);
		Vertex[][] pathdata = Level.clonePathdata(screendata);
		IncidenceListGraph graph = ((PacVertex) pathdata[0][0].element())
				.getGraph();

		return null;
	}

	private void makeStep(DefaultMutableTreeNode node, Vertex vertex,
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
					makeStep(child, vi.nextVertex(), depth - 1, true, graph,
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
