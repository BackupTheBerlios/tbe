package ch.pacman.game;

import java.awt.Color;
import java.awt.Image;
import jdsl.graph.api.Vertex;
import jdsl.graph.ref.IncidenceListGraph;


public abstract class Level
{
	protected IncidenceListGraph graph = new IncidenceListGraph();
	
	
	public static Color		dotcolor=new Color(192,192,0);
	public static int		bigdotcolor=192;
	public static  int		dbigdotcolor=-2;
	public static  Color		mazecolor;

	public static  final int     blocksize=24;
	public static  final int     nrofblocks=15;
	public static  final int     scrsize=nrofblocks*blocksize;

	public static  Image		ghost1,ghost2,ghostscared1,ghostscared2;
	public static  Image		pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
	public static  Image		pacman3up, pacman3down, pacman3left, pacman3right;
	public static  Image		pacman4up, pacman4down, pacman4left, pacman4right;
	
	protected Vertex[][] pathdata = new Vertex[nrofblocks][nrofblocks];
	protected short[][]   leveldata = null;
	
	public short[][] getLeveldata()
	{
		return leveldata;
	}
	


}
