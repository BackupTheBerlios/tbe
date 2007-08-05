package ch.pacman.game;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import ch.pacman.graph.PacVertex;
import jdsl.graph.api.Edge;
import jdsl.graph.api.EdgeIterator;
import jdsl.graph.api.Vertex;
import jdsl.graph.api.VertexIterator;
import jdsl.graph.ref.IncidenceListGraph;

public class Heuristic
{
	private static int DEPTH = 5;

	private static JTree tree;

	public static DecisionObject getBestMove(Vertex[][] screendata)
	{
		// create root node and its children
		DefaultMutableTreeNode node = new DefaultMutableTreeNode();
		Vertex vertex = null;
		PacMan tempPac = null;
		Vertex[][] pathdata = Level.clonePathdata(screendata);
		ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

		for (Vertex[] vs : pathdata)
		{
			for (Vertex v : vs)
			{
				PacVertex pv = (PacVertex) v;
				if (pv.isPacMan())
				{
					tempPac = pv.getPacMan();
				} else if (pv.getGhostCount() > 0)
				{
					ghosts.addAll(pv.getGhosts());
				}
			}
		}

		node.setUserObject(new DecisionObject());
		Ghost[] tempGhosts = (Ghost[]) ghosts.toArray();
		IncidenceListGraph igraph = ((PacVertex) pathdata[0][0].element())
				.getGraph();
		return Heuristic.makeTree(node, Heuristic.DEPTH, true, igraph, tempPac,
				tempGhosts);
	}

	private static DecisionObject makeTree(DefaultMutableTreeNode node,
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
				VertexIterator vi = graph.adjacentVertices(pacman
						.getCurrentVertex());
				while (vi.hasNext())
				{

					Vertex v = vi.nextVertex();
					PacVertex next = (PacVertex) v.element();
					PacVertex current = (PacVertex) pacman.getCurrentVertex()
							.element();

					if (next.getGhostCount() < 1)
					{
						DefaultMutableTreeNode child = new DefaultMutableTreeNode();
						DefaultMutableTreeNode n = (DefaultMutableTreeNode) node
								.getParent();
						DecisionObject dobj = (DecisionObject) n
								.getUserObject();

						if (current.getX() == next.getX())
						{
							pacman.setDestX(0);

						} else if (current.getX() < next.getX())
						{
							pacman.setDestX(1);

						} else if (current.getX() > next.getX())
						{
							pacman.setDestX(-1);
						}

						if (current.getY() == next.getY())
						{
							pacman.setDestY(0);
						} else if (current.getY() < next.getY())
						{
							pacman.setDestY(1);
						} else if (current.getY() > next.getY())
						{
							pacman.setDestY(-1);

						}

						pacman.changeVertex(v);

						if (dobj.getPacman() == null)
						{
							dobj.setPacman(pacman);
						}
						child.setUserObject(dobj);

						node.add(child);
						makeTree(child, depth - 1, false, graph, pacman, ghosts);
					}

				}
			} else
			{
				ArrayList<Ghost> decision = new ArrayList<Ghost>();
				ArrayList<Ghost> nonDecision = new ArrayList<Ghost>();

				DefaultMutableTreeNode n = (DefaultMutableTreeNode) node
						.getParent();
				DecisionObject dobj = (DecisionObject) n.getUserObject();

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
				for (int i = 0; i < decision.size(); i++)
				{
					// for each adjacent vertex
					DefaultMutableTreeNode child = new DefaultMutableTreeNode();
					if (((DefaultMutableTreeNode) node.getParent())
							.getUserObject() == null)
					{
						child.setUserObject("");
					} else
					{
						child.setUserObject(((DefaultMutableTreeNode) node
								.getParent()).getUserObject());
					}

				}

				// nondecision ghosts
				for (Ghost ghost : nonDecision)
				{
					if (dobj.getGhostsSize() < tempGhosts.length)
					{
						dobj.addGhost(ghost);
					}

					Vertex current = ghost.getCurrentVertex();
					EdgeIterator ei = graph.incidentEdges(current);
					for (Edge e = ei.nextEdge(); ei.hasNext(); ei.nextEdge())
					{
						Vertex tempNext = graph.opposite(current, e);
						PacVertex tempPacNext = (PacVertex) tempNext;

						if (ghost.getDestX() == 0)
						{
							if (((tempPacNext.getY() - ghost.getActY()) < 0 && 0 > ghost
									.getDestY())
									|| ((tempPacNext.getY() - ghost.getActY()) > 0 && 0 < ghost
											.getDestY()))
							{
								ghost.changeVertex(tempNext);
								ghost.setActX(tempPacNext.getX());
								ghost.setActY(tempPacNext.getY());
							}
						} else
						{
							if (((tempPacNext.getX() - ghost.getActX()) < 0 && 0 > ghost
									.getDestX())
									|| ((tempPacNext.getX() - ghost.getActX()) > 0 && 0 < ghost
											.getDestX()))
							{
								ghost.changeVertex(tempNext);
								ghost.setActX(tempPacNext.getX());
								ghost.setActY(tempPacNext.getY());
							}
						}

					}

					
				}

				ArrayList<Vertex>[] blub = new ArrayList[decision.size()];
				int i = 0;
				for (Ghost decGhost : decision)
				{
					EdgeIterator ei = graph.incidentEdges(decGhost
							.getCurrentVertex());
					while (ei.hasNext())
					{
						blub[i].add(graph.opposite(decGhost.getCurrentVertex(),
								ei.nextEdge()));
					}
					i++;
				}
	
					if(decision.size() > 0){
					for (int q= 0; q < blub[0].size(); q++)
					{
						if(decision.size() > 1){
						for (int w= 0; w < blub[1].size(); w++)
						{
							if(decision.size() > 2){
							for (int e= 0; e < blub[2].size(); e++)
							{
								if(decision.size() > 3){
								for (int r= 0; r < blub[3].size(); r++)
								{
									decision.get(0).changeVertex(blub[0].get(q));
									decision.get(0).setActX(((PacVertex)blub[0].get(q).element()).getX());
									decision.get(0).setActY(((PacVertex)blub[0].get(q).element()).getY());
									decision.get(1).changeVertex(blub[1].get(w));
									decision.get(1).setActX(((PacVertex)blub[1].get(w).element()).getX());
									decision.get(1).setActY(((PacVertex)blub[1].get(w).element()).getY());
									decision.get(2).changeVertex(blub[2].get(e));
									decision.get(2).setActX(((PacVertex)blub[2].get(e).element()).getX());
									decision.get(2).setActY(((PacVertex)blub[2].get(e).element()).getY());
									decision.get(3).changeVertex(blub[3].get(r));
									decision.get(3).setActX(((PacVertex)blub[3].get(r).element()).getX());
									decision.get(3).setActY(((PacVertex)blub[3].get(r).element()).getY());
									DefaultMutableTreeNode child = new DefaultMutableTreeNode();
									child.setUserObject(dobj);
									node.add(child);
									makeTree(child, depth - 1, true, graph, pacman, ghosts);
									
									
								}}else{
									decision.get(0).changeVertex(blub[0].get(q));
									decision.get(0).setActX(((PacVertex)blub[0].get(q).element()).getX());
									decision.get(0).setActY(((PacVertex)blub[0].get(q).element()).getY());
									decision.get(1).changeVertex(blub[1].get(w));
									decision.get(1).setActX(((PacVertex)blub[1].get(w).element()).getX());
									decision.get(1).setActY(((PacVertex)blub[1].get(w).element()).getY());
									decision.get(2).changeVertex(blub[2].get(e));
									decision.get(2).setActX(((PacVertex)blub[2].get(e).element()).getX());
									decision.get(2).setActY(((PacVertex)blub[2].get(e).element()).getY());
									DefaultMutableTreeNode child = new DefaultMutableTreeNode();
									child.setUserObject(dobj);
									node.add(child);
									makeTree(child, depth - 1, true, graph, pacman, ghosts);
								}
							}}else{
								decision.get(0).changeVertex(blub[0].get(q));
								decision.get(0).setActX(((PacVertex)blub[0].get(q).element()).getX());
								decision.get(0).setActY(((PacVertex)blub[0].get(q).element()).getY());
								decision.get(1).changeVertex(blub[1].get(w));
								decision.get(1).setActX(((PacVertex)blub[1].get(w).element()).getX());
								decision.get(1).setActY(((PacVertex)blub[1].get(w).element()).getY());
								DefaultMutableTreeNode child = new DefaultMutableTreeNode();
								child.setUserObject(dobj);
								node.add(child);
								makeTree(child, depth - 1, true, graph, pacman, ghosts);
								}
							
						}}else{
							decision.get(0).changeVertex(blub[0].get(q));
							decision.get(0).setActX(((PacVertex)blub[0].get(q).element()).getX());
							decision.get(0).setActY(((PacVertex)blub[0].get(q).element()).getY());
							DefaultMutableTreeNode child = new DefaultMutableTreeNode();
							child.setUserObject(dobj);
							node.add(child);
							makeTree(child, depth - 1, true, graph, pacman, ghosts);
							}
						}
					}}
	
//				
//				for (Ghost decGhost : decision)
//				{
//
//					if (((DefaultMutableTreeNode) node.getParent())
//							.getUserObject() == null)
//					{
//						child.setUserObject("");
//					} else
//					{
//						child.setUserObject(((DefaultMutableTreeNode) node
//								.getParent()).getUserObject());
//					}
//
//				}
//
//				for (int i = 0; i < node.getChildCount(); i++)
//				{
//					makeTree((DefaultMutableTreeNode) node.getChildAt(i),
//							depth - 1, true, graph, pacman, ghosts);
//				}

			
		}
		return null;
	}
}
