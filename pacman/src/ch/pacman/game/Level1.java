package ch.pacman.game;

import jdsl.graph.api.Edge;
import jdsl.graph.api.Vertex;

public class Level1 extends Level
{
	public Level1(){
		this.createLevel();
	}
	
	private void createLevel(){
		 Vertex s0 = graph.insertVertex("s0");
	     Vertex s1 = graph.insertVertex("s1");
	     Vertex s2 = graph.insertVertex("s2");
	     Vertex s3 = graph.insertVertex("s3");
	     Vertex s4 = graph.insertVertex("s4");
	     Vertex s5 = graph.insertVertex("s5");
	     Vertex s6 = graph.insertVertex("s6");
	     Vertex s7 = graph.insertVertex("s7");
	     

	     Edge e01 = graph.insertEdge(s0,s1,"s0-->s1");
	     Edge e02 = graph.insertEdge(s0,s2,"s0-->s2");
	     Edge e04 = graph.insertEdge(s0,s4,"s0-->s4");
	     Edge e14 = graph.insertEdge(s1,s4,"s1-->s4");
	     Edge e15 = graph.insertEdge(s1,s5,"s1-->s5");
	     Edge e23 = graph.insertEdge(s2,s3,"s2-->s3");
	     Edge e34 = graph.insertEdge(s3,s4,"s3-->s4");
	     Edge e46 = graph.insertEdge(s4,s6,"s4-->s6");
	     Edge e57 = graph.insertEdge(s5,s7,"s5-->s7");
	     Edge e76 = graph.insertEdge(s7,s6,"s7-->s6");
	}
}
