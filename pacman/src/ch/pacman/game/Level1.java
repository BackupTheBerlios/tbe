package ch.pacman.game;

import ch.pacman.Game;
import ch.pacman.graph.PacVertex;
import jdsl.graph.api.Edge;
import jdsl.graph.api.Vertex;

public class Level1 extends Level
{
	Game game;
	
	public Level1(Game game){
		this.createLevel();
	}
	
	private void createLevel(){
		
		
		leveldata = new short[]{ 
				19,26,26,22, 9,12,19,26,22, 9,12,19,26,26,22,
				37,11,14,17,26,26,20,15,17,26,26,20,11,14,37,
				17,26,26,20,11, 6,17,26,20, 3,14,17,26,26,20,
				21, 3, 6,25,22, 5,21, 7,21, 5,19,28, 3, 6,21,
				21, 9, 8,14,21,13,21, 5,21,13,21,11, 8,12,21,
				25,18,26,18,24,18,28, 5,25,18,24,18,26,18,28,
				 6,21, 7,21, 7,21,11, 8,14,21, 7,21, 7,21,03,
				 4,21, 5,21, 5,21,11,10,14,21, 5,21, 5,21, 1,
				12,21,13,21,13,21,11,10,14,21,13,21,13,21, 9,
				19,24,26,24,26,16,26,18,26,16,26,24,26,24,22,
				21, 3, 2, 2, 6,21,15,21,15,21, 3, 2, 2,06,21,
				21, 9, 8, 8, 4,17,26, 8,26,20, 1, 8, 8,12,21,
				17,26,26,22,13,21,11, 2,14,21,13,19,26,26,20,
				37,11,14,17,26,24,22,13,19,24,26,20,11,14,37,
				25,26,26,28, 3, 6,25,26,28, 3, 6,25,26,26,28  };
		
		
		 Vertex s0 = graph.insertVertex(new PacVertex(game, 0, 0, true, false));
	     Vertex s1 = graph.insertVertex(new PacVertex(game, 0, 0, true, false));
	     Vertex s2 = graph.insertVertex(new PacVertex(game, 0, 0, true, false));
	     Vertex s3 = graph.insertVertex(new PacVertex(game, 0, 0, true, false));
	     Vertex s4 = graph.insertVertex(new PacVertex(game, 0, 0, true, false));
	     Vertex s5 = graph.insertVertex(new PacVertex(game, 0, 0, true, false));
	     Vertex s6 = graph.insertVertex(new PacVertex(game, 0, 0, true, false));
	     Vertex s7 = graph.insertVertex(new PacVertex(game, 0, 0, true, false));
	     

	     Edge e01 = graph.insertEdge(s0,s1,"south");
	     Edge e02 = graph.insertEdge(s0,s2,"north");
	     Edge e04 = graph.insertEdge(s0,s4,"east");
	     Edge e14 = graph.insertEdge(s1,s4,"south");
	     Edge e15 = graph.insertEdge(s1,s5,"south");
	     Edge e23 = graph.insertEdge(s2,s3,"north");
	     Edge e34 = graph.insertEdge(s3,s4,"east");
	     Edge e46 = graph.insertEdge(s4,s6,"west");
	     Edge e57 = graph.insertEdge(s5,s7,"south");
	     Edge e76 = graph.insertEdge(s7,s6,"north");
	}
}
