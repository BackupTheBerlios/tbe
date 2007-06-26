package ch.pacman.game;

import java.awt.Point;
import ch.pacman.graph.PacVertex;


public class Level1 extends Level
{

	public Level1()
	{
		this.createLevel();
		this.pacManStart = new Point(7 * Level.blocksize,13 * Level.blocksize);
		this.ghostStart = new Point(7 * Level.blocksize,7 * Level.blocksize);
	}

	private void createLevel()
	{

		leveldata = new short[][] {
				{ 19, 26, 26, 22, 9, 12, 19, 26, 22, 9, 12, 19, 26, 26, 22 },
				{ 37, 3, 6, 25, 18, 26, 20, 15, 17, 26, 18, 28, 3, 6, 37 },
				{ 21, 1, 0, 6, 21, 7, 25, 26, 28, 7, 21, 3, 0, 4, 21 },
				{ 21, 1, 0, 4, 21, 1, 2, 2, 2, 4, 21, 1, 0, 4, 21 },
				{ 21, 9, 8, 12, 21, 9, 0, 0, 0, 12, 21, 9, 8, 12, 21 },
				{ 25, 26, 26, 18, 24, 22, 1, 0, 4, 19, 24, 18, 26, 26, 28 },
				{ 2, 2, 6, 21, 7, 21, 9, 8, 12, 21, 7, 21, 3, 2, 2 },
				{ 0, 0, 4, 21, 5, 21, 1, 10, 4, 21, 5, 21, 1, 0, 0 },
				{ 8, 8, 12, 21, 13, 21, 11, 10, 14, 21, 13, 21, 9, 8, 8 },
				{ 19, 26, 26, 24, 26, 16, 26, 26, 26, 16, 26, 24, 26, 26, 22 },
				{ 21, 3, 2, 2, 6, 21, 3, 2, 6, 21, 3, 2, 2, 06, 21 },
				{ 21, 9, 8, 8, 4, 21, 1, 0, 4, 21, 1, 8, 8, 12, 21 },
				{ 17, 26, 26, 22, 13, 21, 9, 8, 12, 21, 13, 19, 26, 26, 20 },
				{ 37, 11, 14, 17, 26, 24, 26, 26, 26, 24, 26, 20, 11, 14, 37 },
				{ 25, 26, 26, 28, 3, 2, 2, 2, 2, 2, 6, 25, 26, 26, 28 } };

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
		pathdata[0][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[0][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[1][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[1][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[2][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[2][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[3][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[3][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[4][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[4][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[5][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[5][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[6][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[6][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[7][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[7][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[8][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[8][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[9][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[9][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[10][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[10][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[11][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[11][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[12][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[12][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[13][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[13][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		pathdata[14][0] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][1] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][2] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][3] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][4] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][5] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][6] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][7] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][8] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][9] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][10] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][11] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][12] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][13] = graph.insertVertex(new PacVertex(0, 0, true, false));
		pathdata[14][14] = graph.insertVertex(new PacVertex(0, 0, true, false));

		graph.insertEdge(pathdata[0][0], pathdata[0][1], "east");
		graph.insertEdge(pathdata[0][1], pathdata[0][2], "east");
		graph.insertEdge(pathdata[0][2], pathdata[0][3], "east");
		graph.insertEdge(pathdata[0][6], pathdata[0][7], "east");
		graph.insertEdge(pathdata[0][7], pathdata[0][8], "east");
		graph.insertEdge(pathdata[0][11], pathdata[0][12], "east");
		graph.insertEdge(pathdata[0][12], pathdata[0][13], "east");
		graph.insertEdge(pathdata[0][13], pathdata[0][14], "east");

		graph.insertEdge(pathdata[0][0], pathdata[1][0], "south");
		graph.insertEdge(pathdata[0][3], pathdata[1][3], "south");
		graph.insertEdge(pathdata[0][6], pathdata[1][6], "south");
		graph.insertEdge(pathdata[0][8], pathdata[1][8], "south");
		graph.insertEdge(pathdata[0][14], pathdata[1][14], "south");

		graph.insertEdge(pathdata[1][3], pathdata[1][4], "east");
		graph.insertEdge(pathdata[1][4], pathdata[1][5], "east");
		graph.insertEdge(pathdata[1][5], pathdata[1][6], "east");
		graph.insertEdge(pathdata[1][8], pathdata[1][9], "east");
		graph.insertEdge(pathdata[1][9], pathdata[1][10], "east");
		graph.insertEdge(pathdata[1][10], pathdata[1][11], "east");

		graph.insertEdge(pathdata[1][0], pathdata[2][0], "south");
		graph.insertEdge(pathdata[1][4], pathdata[2][4], "south");
		graph.insertEdge(pathdata[1][6], pathdata[2][6], "south");
		graph.insertEdge(pathdata[1][8], pathdata[2][8], "south");
		graph.insertEdge(pathdata[1][10], pathdata[2][10], "south");
		graph.insertEdge(pathdata[1][14], pathdata[2][14], "south");

		graph.insertEdge(pathdata[2][6], pathdata[2][7], "east");
		graph.insertEdge(pathdata[2][7], pathdata[2][8], "east");

		graph.insertEdge(pathdata[2][0], pathdata[3][0], "south");
		graph.insertEdge(pathdata[2][4], pathdata[3][4], "south");
		graph.insertEdge(pathdata[2][10], pathdata[3][10], "south");
		graph.insertEdge(pathdata[2][14], pathdata[3][14], "south");

		graph.insertEdge(pathdata[3][0], pathdata[4][0], "south");
		graph.insertEdge(pathdata[3][4], pathdata[4][4], "south");
		graph.insertEdge(pathdata[3][10], pathdata[4][10], "south");
		graph.insertEdge(pathdata[3][14], pathdata[4][14], "south");

		graph.insertEdge(pathdata[4][0], pathdata[5][0], "south");
		graph.insertEdge(pathdata[4][4], pathdata[5][4], "south");
		graph.insertEdge(pathdata[4][10], pathdata[5][10], "south");
		graph.insertEdge(pathdata[4][14], pathdata[5][14], "south");

		graph.insertEdge(pathdata[5][0], pathdata[5][1], "east");
		graph.insertEdge(pathdata[5][1], pathdata[5][2], "east");
		graph.insertEdge(pathdata[5][2], pathdata[5][3], "east");
		graph.insertEdge(pathdata[5][3], pathdata[5][4], "east");
		graph.insertEdge(pathdata[5][4], pathdata[5][5], "east");
		graph.insertEdge(pathdata[5][9], pathdata[5][10], "east");
		graph.insertEdge(pathdata[5][10], pathdata[5][11], "east");
		graph.insertEdge(pathdata[5][11], pathdata[5][12], "east");
		graph.insertEdge(pathdata[5][12], pathdata[5][13], "east");
		graph.insertEdge(pathdata[5][13], pathdata[5][14], "east");

		graph.insertEdge(pathdata[5][3], pathdata[6][3], "south");
		graph.insertEdge(pathdata[5][5], pathdata[6][5], "south");
		graph.insertEdge(pathdata[5][9], pathdata[6][9], "south");
		graph.insertEdge(pathdata[5][11], pathdata[6][11], "south");

		graph.insertEdge(pathdata[6][3], pathdata[7][3], "south");
		graph.insertEdge(pathdata[6][5], pathdata[7][5], "south");
		graph.insertEdge(pathdata[6][9], pathdata[7][9], "south");
		graph.insertEdge(pathdata[6][11], pathdata[7][11], "south");

		graph.insertEdge(pathdata[7][3], pathdata[8][3], "south");
		graph.insertEdge(pathdata[7][5], pathdata[8][5], "south");
		graph.insertEdge(pathdata[7][9], pathdata[8][9], "south");
		graph.insertEdge(pathdata[7][11], pathdata[8][11], "south");

		graph.insertEdge(pathdata[8][3], pathdata[9][3], "south");
		graph.insertEdge(pathdata[8][5], pathdata[9][5], "south");
		graph.insertEdge(pathdata[8][9], pathdata[9][9], "south");
		graph.insertEdge(pathdata[8][11], pathdata[9][11], "south");

		graph.insertEdge(pathdata[9][0], pathdata[9][1], "east");
		graph.insertEdge(pathdata[9][1], pathdata[9][2], "east");
		graph.insertEdge(pathdata[9][2], pathdata[9][3], "east");
		graph.insertEdge(pathdata[9][3], pathdata[9][4], "east");
		graph.insertEdge(pathdata[9][4], pathdata[9][5], "east");
		graph.insertEdge(pathdata[9][5], pathdata[9][6], "east");
		graph.insertEdge(pathdata[9][6], pathdata[9][7], "east");
		graph.insertEdge(pathdata[9][7], pathdata[9][8], "east");
		graph.insertEdge(pathdata[9][8], pathdata[9][9], "east");
		graph.insertEdge(pathdata[9][9], pathdata[9][10], "east");
		graph.insertEdge(pathdata[9][10], pathdata[9][11], "east");
		graph.insertEdge(pathdata[9][11], pathdata[9][12], "east");
		graph.insertEdge(pathdata[9][12], pathdata[9][13], "east");
		graph.insertEdge(pathdata[9][13], pathdata[9][14], "east");

		graph.insertEdge(pathdata[9][0], pathdata[10][0], "south");
		graph.insertEdge(pathdata[9][5], pathdata[10][5], "south");
		graph.insertEdge(pathdata[9][9], pathdata[10][9], "south");
		graph.insertEdge(pathdata[9][14], pathdata[10][14], "south");

		graph.insertEdge(pathdata[10][0], pathdata[11][0], "south");
		graph.insertEdge(pathdata[10][5], pathdata[11][5], "south");
		graph.insertEdge(pathdata[10][9], pathdata[11][9], "south");
		graph.insertEdge(pathdata[10][14], pathdata[11][14], "south");

		graph.insertEdge(pathdata[11][0], pathdata[12][0], "south");
		graph.insertEdge(pathdata[11][5], pathdata[12][5], "south");
		graph.insertEdge(pathdata[11][9], pathdata[12][9], "south");
		graph.insertEdge(pathdata[11][14], pathdata[12][14], "south");

		graph.insertEdge(pathdata[12][0], pathdata[12][1], "east");
		graph.insertEdge(pathdata[12][1], pathdata[12][2], "east");
		graph.insertEdge(pathdata[12][2], pathdata[12][3], "east");
		graph.insertEdge(pathdata[12][11], pathdata[12][12], "east");
		graph.insertEdge(pathdata[12][12], pathdata[12][13], "east");
		graph.insertEdge(pathdata[12][13], pathdata[12][14], "east");

		graph.insertEdge(pathdata[12][0], pathdata[13][0], "south");
		graph.insertEdge(pathdata[12][3], pathdata[13][3], "south");
		graph.insertEdge(pathdata[12][5], pathdata[13][5], "south");
		graph.insertEdge(pathdata[12][9], pathdata[13][9], "south");
		graph.insertEdge(pathdata[12][11], pathdata[13][11], "south");
		graph.insertEdge(pathdata[12][14], pathdata[13][14], "south");

		graph.insertEdge(pathdata[13][3], pathdata[13][4], "east");
		graph.insertEdge(pathdata[13][4], pathdata[13][5], "east");
		graph.insertEdge(pathdata[13][5], pathdata[13][6], "east");
		graph.insertEdge(pathdata[13][6], pathdata[13][7], "east");
		graph.insertEdge(pathdata[13][7], pathdata[13][8], "east");
		graph.insertEdge(pathdata[13][8], pathdata[13][9], "east");
		graph.insertEdge(pathdata[13][9], pathdata[13][10], "east");
		graph.insertEdge(pathdata[13][10], pathdata[13][11], "east");

		graph.insertEdge(pathdata[13][0], pathdata[14][0], "south");
		graph.insertEdge(pathdata[13][3], pathdata[14][3], "south");
		graph.insertEdge(pathdata[13][11], pathdata[14][11], "south");
		graph.insertEdge(pathdata[13][14], pathdata[14][14], "south");

		graph.insertEdge(pathdata[14][0], pathdata[14][1], "east");
		graph.insertEdge(pathdata[14][1], pathdata[14][2], "east");
		graph.insertEdge(pathdata[14][2], pathdata[14][3], "east");
		graph.insertEdge(pathdata[14][11], pathdata[14][12], "east");
		graph.insertEdge(pathdata[14][12], pathdata[14][13], "east");
		graph.insertEdge(pathdata[14][13], pathdata[14][14], "east");

	}
}
