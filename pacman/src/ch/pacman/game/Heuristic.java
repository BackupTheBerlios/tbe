package ch.pacman.game;

import javax.swing.JTree;

import ch.pacman.graph.PacVertex;

import jdsl.graph.api.Vertex;
import jdsl.graph.ref.IncidenceListGraph;

public class Heuristic
{
	private static int DEPTH = 5;

	public static Vertex getBestMove(Vertex[][] screendata){
		JTree tree = new JTree();
		Vertex[][] pathdata = Level.clonePathdata(screendata);
		IncidenceListGraph graph = ((PacVertex) pathdata[0][0].element()).getGraph();
		
		return null;
	}
}
