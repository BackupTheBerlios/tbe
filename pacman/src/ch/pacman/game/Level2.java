package ch.pacman.game;

import ch.pacman.Game;
import ch.pacman.graph.PacVertex;
import jdsl.graph.api.Edge;
import jdsl.graph.api.Vertex;

public class Level2 extends Level
{
	
	public Level2(){
		this.createLevel();
	}
	
	private void createLevel(){
		
		
		leveldata = new short[][]{ 
				{19,26,26,22, 9,12,19,26,22, 9,12,19,26,26,22},
				{37,11,14,17,26,26,20,15,17,26,26,20,11,14,37},
				{37,11,14,17,26,26,20,15,17,26,26,20,11,14,37},
				{17,26,26,20,11, 6,17,26,20, 3,14,17,26,26,20},
				{21, 3, 6,25,22, 5,21, 7,21, 5,19,28, 3, 6,21},
				{21, 9, 8,14,21,13,21, 5,21,13,21,11, 8,12,21},
				{25,18,26,18,24,18,28, 5,25,18,24,18,26,18,28},
				{ 6,21, 7,21, 7,21,11, 8,14,21, 7,21, 7,21,03},
				{ 4,21, 5,21, 5,21,11,10,14,21, 5,21, 5,21, 1},
				{12,21,13,21,13,21,11,10,14,21,13,21,13,21, 9},
				{19,24,26,24,26,16,26,18,26,16,26,24,26,24,22},
				{21, 3, 2, 2, 6,21,15,21,15,21, 3, 2, 2,06,21},
				{21, 3, 2, 2, 6,21,15,21,15,21, 3, 2, 2,06,21},
				{21, 9, 8, 8, 4,17,26, 8,26,20, 1, 8, 8,12,21},
				{17,26,26,22,13,21,11, 2,14,21,13,19,26,26,20},
				{37,11,14,17,26,24,22,13,19,24,26,20,11,14,37},
				{25,26,26,28, 3, 6,25,26,28, 3, 6,25,26,26,28}  };
		
		
		pathdata[0][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
	     

	     Edge e01 = graph.insertEdge(pathdata[0][0],pathdata[0][1],"south");
	     Edge e02 = graph.insertEdge(pathdata[0][0],pathdata[0][2],"north");
	     Edge e04 = graph.insertEdge(pathdata[0][0],pathdata[0][4],"east");
	     Edge e14 = graph.insertEdge(pathdata[0][1],pathdata[0][4],"south");
	     Edge e15 = graph.insertEdge(pathdata[0][1],pathdata[0][4],"south");
	     Edge e23 = graph.insertEdge(pathdata[0][2],pathdata[0][3],"north");
	     Edge e34 = graph.insertEdge(pathdata[0][3],pathdata[0][4],"east");
	     Edge e46 = graph.insertEdge(pathdata[0][4],pathdata[0][6],"west");
	     Edge e57 = graph.insertEdge(pathdata[0][5],pathdata[0][7],"south");
	     Edge e76 = graph.insertEdge(pathdata[0][6],pathdata[0][7],"north");
	}
}
