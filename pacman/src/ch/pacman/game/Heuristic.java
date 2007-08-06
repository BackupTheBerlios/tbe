package ch.pacman.game;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

import ch.pacman.Game;
import ch.pacman.graph.PacVertex;
import jdsl.graph.api.Edge;
import jdsl.graph.api.EdgeIterator;
import jdsl.graph.api.Vertex;
import jdsl.graph.api.VertexIterator;
import jdsl.graph.ref.IncidenceListGraph;

public class Heuristic
{
	private static DecisionResult endResult = new DecisionResult();

	private static int DEPTH = 5;

	public static DecisionResult getBestMove(Vertex[][] screendata)
	{
		// create root node and its children
		DefaultMutableTreeNode node = new DefaultMutableTreeNode();
		PacMan tempPac = Game.getPacman();
		Vertex[][] pathdata = Level.clonePathdata(screendata);
		ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
		PacMan pacman;
		ghosts = Game.getGhosts();
		pacman = Game.getPacman();

		node.setUserObject(new DecisionObject(pacman, ghosts));
		Ghost[] tempGhosts = new Ghost[ghosts.size()];
		int i = 0;
		for (Ghost g : ghosts)
		{
			tempGhosts[i] = g;
			i++;
		}
		IncidenceListGraph igraph = ((PacVertex) pathdata[0][0].element())
				.getGraph();
		return Heuristic.makeTree(node, Heuristic.DEPTH, true, igraph, tempPac,
				tempGhosts);
	}

	private static DecisionResult makeTree(DefaultMutableTreeNode node,
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
		
		ArrayList<Ghost> ghostArrayList = new ArrayList<Ghost>();
		for (Ghost g : ghosts)
		{
			ghostArrayList.add(g);
		}

		DecisionResult pacResult = null;
		DecisionResult ghostResult = null;



		if (depth != 0)
		{
			if (pac)
			{
				Vertex newVertex = null;
				Vertex oldVertex = pacman.getCurrentVertex();
				PacVertex oldPacVertex = (PacVertex) oldVertex.element();
				VertexIterator findVi = graph.vertices();
				while (findVi.hasNext())
				{
					Vertex findVertex = findVi.nextVertex();
					PacVertex findPacVertex = (PacVertex) findVertex.element();
					if (findPacVertex.getX() == oldPacVertex.getX()
							&& findPacVertex.getY() == oldPacVertex.getY())
					{
						newVertex = findVertex;
						tempPac.setCurrentVertex(newVertex);
						break;
					}
				}

				VertexIterator vi = graph.adjacentVertices(newVertex);
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
						DecisionObject dobj;
						if (n == null)
						{
							dobj = (DecisionObject) node.getUserObject();
						} else
						{
							dobj = (DecisionObject) n.getUserObject();
						}

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
						pacResult = makeTree(child, depth - 1, false, graph,
								pacman, ghosts);
					}

				}
			} else
			{
				Vertex newVertex = null;
				for (Ghost g : ghosts)
				{
					Vertex oldVertex = g.getCurrentVertex();
					PacVertex oldPacVertex = (PacVertex) oldVertex.element();
					VertexIterator findVi = graph.vertices();
					while (findVi.hasNext())
					{
						Vertex findVertex = findVi.nextVertex();
						PacVertex findPacVertex = (PacVertex) findVertex
								.element();
						if (findPacVertex.getX() == oldPacVertex.getX()
								&& findPacVertex.getY() == oldPacVertex.getY())
						{
							newVertex = findVertex;
							g.setCurrentVertex(newVertex);
							break;
						}
					}
				}

				ArrayList<Ghost> decision = new ArrayList<Ghost>();
				ArrayList<Ghost> nonDecision = new ArrayList<Ghost>();

				DefaultMutableTreeNode n = (DefaultMutableTreeNode) node
						.getParent();
				DecisionObject dobj = (DecisionObject) n.getUserObject();

				for (Ghost g : ghosts)
				{
					if (graph.degree(g.getCurrentVertex()) > 2)
					{
						decision.add(g);
					} else
					{
						nonDecision.add(g);
					}
				}

				// nondecision ghosts
				if (nonDecision.size() < 1)
				{
					for (Ghost ghost : nonDecision)
					{
						Vertex current = ghost.getCurrentVertex();
						EdgeIterator ei = graph.incidentEdges(current);
						for (Edge e = ei.nextEdge(); ei.hasNext(); ei
								.nextEdge())
						{
							Vertex tempNext = graph.opposite(current, e);
							PacVertex tempPacNext = (PacVertex) tempNext
									.element();

							if (ghost.getDestX() == 0)
							{
								if (((tempPacNext.getY() - ghost.getActY()) < 0 && 0 > ghost
										.getDestY())
										|| ((tempPacNext.getY() - ghost
												.getActY()) > 0 && 0 < ghost
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
										|| ((tempPacNext.getX() - ghost
												.getActX()) > 0 && 0 < ghost
												.getDestX()))
								{
									ghost.changeVertex(tempNext);
									ghost.setActX(tempPacNext.getX());
									ghost.setActY(tempPacNext.getY());
								}
							}

						}

					}
				}

				// decision
				if (decision.size() > 0)
				{
					Object[] blub = new Object[decision.size()];
					for(int j = 0; j < decision.size(); j++){
						blub[j] = new ArrayList<Vertex>();
					}
					
					int i = 0;
					for (Ghost decGhost : decision)
					{
						VertexIterator vi = null;
						vi = graph.adjacentVertices(decGhost.getCurrentVertex());
						while (vi.hasNext())
						{
							Vertex nextVertex = vi.nextVertex();
							if(nextVertex != decGhost.getOldVertex()){
								((ArrayList<Vertex>)blub[i]).add(nextVertex);
							}
						}
						i++;
					}

					if(decision.size() > 0){
						for (int q= 0; q < ((ArrayList<Vertex>)blub[0]).size(); q++)
						{
							if(decision.size() > 1){
							for (int w= 0; w < ((ArrayList<Vertex>)blub[1]).size(); w++)
							{
								if(decision.size() > 2){
								for (int e= 0; e < ((ArrayList<Vertex>)blub[2]).size(); e++)
								{
									if(decision.size() > 3){
										for (int r= 0; r < ((ArrayList<Vertex>)blub[3]).size(); r++)
										{
											decision.get(0).changeVertex(((ArrayList<Vertex>)blub[0]).get(q));
											setDest(decision.get(0));
											decision.get(0).setActX(((PacVertex)((ArrayList<Vertex>)blub[0]).get(q).element()).getX());
											decision.get(0).setActY(((PacVertex)((ArrayList<Vertex>)blub[0]).get(q).element()).getY());
											decision.get(1).changeVertex(((ArrayList<Vertex>)blub[1]).get(w));
											setDest(decision.get(1));
											decision.get(1).setActX(((PacVertex)((ArrayList<Vertex>)blub[1]).get(w).element()).getX());
											decision.get(1).setActY(((PacVertex)((ArrayList<Vertex>)blub[1]).get(w).element()).getY());
											decision.get(2).changeVertex(((ArrayList<Vertex>)blub[2]).get(e));
											setDest(decision.get(2));
											decision.get(2).setActX(((PacVertex)((ArrayList<Vertex>)blub[2]).get(e).element()).getX());
											decision.get(2).setActY(((PacVertex)((ArrayList<Vertex>)blub[2]).get(e).element()).getY());
											decision.get(3).changeVertex(((ArrayList<Vertex>)blub[3]).get(r));
											setDest(decision.get(3));
											decision.get(3).setActX(((PacVertex)((ArrayList<Vertex>)blub[3]).get(r).element()).getX());
											decision.get(3).setActY(((PacVertex)((ArrayList<Vertex>)blub[3]).get(r).element()).getY());
											DefaultMutableTreeNode child = new DefaultMutableTreeNode();

											if(dobj.getGhostsSize() < 1){
												DecisionObject newDobj = new DecisionObject(pacman, new ArrayList<Ghost>());
												newDobj.setPacman(dobj.getPacman());
												for(Ghost ndg : nonDecision){
													dobj.addGhost(ndg);
												}
												for(Ghost dg : decision){
													dobj.addGhost(dg);
												}
												child.setUserObject(newDobj);
											}else{
												child.setUserObject(dobj);
											}
											
											node.add(child);
											ghostResult = makeTree(child, depth - 1, true, graph, pacman, ghosts);
										}
									}else{
										decision.get(0).changeVertex(((ArrayList<Vertex>)blub[0]).get(q));
										setDest(decision.get(0));
										decision.get(0).setActX(((PacVertex)((ArrayList<Vertex>)blub[0]).get(q).element()).getX());
										decision.get(0).setActY(((PacVertex)((ArrayList<Vertex>)blub[0]).get(q).element()).getY());
										decision.get(1).changeVertex(((ArrayList<Vertex>)blub[1]).get(w));
										setDest(decision.get(1));
										decision.get(1).setActX(((PacVertex)((ArrayList<Vertex>)blub[1]).get(w).element()).getX());
										decision.get(1).setActY(((PacVertex)((ArrayList<Vertex>)blub[1]).get(w).element()).getY());
										decision.get(2).changeVertex(((ArrayList<Vertex>)blub[2]).get(e));
										setDest(decision.get(2));
										decision.get(2).setActX(((PacVertex)((ArrayList<Vertex>)blub[2]).get(e).element()).getX());
										decision.get(2).setActY(((PacVertex)((ArrayList<Vertex>)blub[2]).get(e).element()).getY());
										DefaultMutableTreeNode child = new DefaultMutableTreeNode();
										
										if(dobj.getGhostsSize() < 1){
											DecisionObject newDobj = new DecisionObject(pacman, new ArrayList<Ghost>());
											newDobj.setPacman(dobj.getPacman());
											for(Ghost ndg : nonDecision){
												dobj.addGhost(ndg);
											}
											for(Ghost dg : decision){
												dobj.addGhost(dg);
											}
											child.setUserObject(newDobj);
										}else{
											child.setUserObject(dobj);
										}
										
										node.add(child);
										ghostResult = makeTree(child, depth - 1, true, graph, pacman, ghosts);
										}
									}
								}else{
									decision.get(0).changeVertex(((ArrayList<Vertex>)blub[0]).get(q));
									setDest(decision.get(0));
									decision.get(0).setActX(((PacVertex)((ArrayList<Vertex>)blub[0]).get(q).element()).getX());
									decision.get(0).setActY(((PacVertex)((ArrayList<Vertex>)blub[0]).get(q).element()).getY());
									decision.get(1).changeVertex(((ArrayList<Vertex>)blub[1]).get(w));
									setDest(decision.get(1));
									decision.get(1).setActX(((PacVertex)((ArrayList<Vertex>)blub[1]).get(w).element()).getX());
									decision.get(1).setActY(((PacVertex)((ArrayList<Vertex>)blub[1]).get(w).element()).getY());
									DefaultMutableTreeNode child = new DefaultMutableTreeNode();

									if(dobj.getGhostsSize() < 1){
										DecisionObject newDobj = new DecisionObject(pacman, new ArrayList<Ghost>());
										newDobj.setPacman(dobj.getPacman());
										for(Ghost ndg : nonDecision){
											dobj.addGhost(ndg);
										}
										for(Ghost dg : decision){
											dobj.addGhost(dg);
										}
										child.setUserObject(newDobj);
									}else{
										child.setUserObject(dobj);
									}
									
									node.add(child);
									ghostResult = makeTree(child, depth - 1, true, graph, pacman, ghosts);
									}
								}
							}else{
								decision.get(0).changeVertex(((ArrayList<Vertex>)blub[0]).get(q));
								setDest(decision.get(0));
								decision.get(0).setActX(((PacVertex)((ArrayList<Vertex>)blub[0]).get(q).element()).getX());
								decision.get(0).setActY(((PacVertex)((ArrayList<Vertex>)blub[0]).get(q).element()).getY());
								DefaultMutableTreeNode child = new DefaultMutableTreeNode();

								if(dobj.getGhostsSize() < 1){
									DecisionObject newDobj = new DecisionObject(pacman, new ArrayList<Ghost>());
									newDobj.setPacman(dobj.getPacman());
									for(Ghost ndg : nonDecision){
										dobj.addGhost(ndg);
									}
									for(Ghost dg : decision){
										dobj.addGhost(dg);
									}
									child.setUserObject(newDobj);
								}else{
									child.setUserObject(dobj);
								}
								
								node.add(child);
								ghostResult = makeTree(child, depth - 1, true, graph, pacman, ghosts);
								}
							}
						}else{
							DefaultMutableTreeNode child = new DefaultMutableTreeNode();

							if(dobj.getGhostsSize() < 1){
								DecisionObject newDobj = new DecisionObject(pacman, new ArrayList<Ghost>());
								newDobj.setPacman(dobj.getPacman());
								for(Ghost ndg : nonDecision){
									dobj.addGhost(ndg);
								}
								for(Ghost dg : decision){
									dobj.addGhost(dg);
								}
								child.setUserObject(newDobj);
							}else{
								child.setUserObject(dobj);
							}
							
							node.add(child);
							ghostResult = makeTree(child, depth - 1, true, graph, pacman, ghosts);
						}
				}
			}
			
		}else{
			endResult.setPacObject(new DecisionObject(pacman, ghostArrayList));
			endResult.setPacDepht(depth);
			endResult.setGhostObject(new DecisionObject(pacman, ghostArrayList));
			endResult.setGhostDepht(depth);
		}

		if (!pac && depth < endResult.getGhostDepht())
		{
			endResult.setGhostDepht(depth);
			endResult.setGhostObject(ghostResult.getGhostObject());
		}

		if (pac && depth > endResult.getPacDepht())
		{
			endResult.setPacDepht(depth);
			endResult.setPacObject(pacResult.getPacObject());
		}

		if(endResult.getGhostObject() == null || endResult.getPacObject() == null){
			endResult.setPacObject(new DecisionObject(pacman, ghostArrayList));
			endResult.setPacDepht(depth);
			endResult.setGhostObject(new DecisionObject(pacman, ghostArrayList));
			endResult.setGhostDepht(depth);
		}
		
		return endResult;
	}
	
	private static void setDest(Ghost g){
		PacVertex current = (PacVertex) g.getOldVertex().element();
		PacVertex next = (PacVertex) g.getCurrentVertex().element();
		
		if (current.getX() == next.getX())
		{
			g.setDestX(0);

		} else if (current.getX() < next.getX())
		{
			g.setDestX(1);

		} else if (current.getX() > next.getX())
		{
			g.setDestX(-1);
		}

		if (current.getY() == next.getY())
		{
			g.setDestY(0);
		} else if (current.getY() < next.getY())
		{
			g.setDestY(1);
		} else if (current.getY() > next.getY())
		{
			g.setDestY(-1);

		}
	}
}
