package ch.pacman.game;

import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;

import ch.pacman.Game;
import ch.pacman.graph.PacVertex;
import ch.pacman.graph.Pathfinder;
import jdsl.graph.algo.IntegerDijkstraPathfinder;
import jdsl.graph.api.EdgeIterator;
import jdsl.graph.api.Vertex;
import jdsl.graph.ref.IncidenceListGraph;

public class Ghost
{

	private int ghostId;
	
	private int actX = 0;

	private int actY = 0;

	private int destX = 0;

	private int destY = 0;

	private int speed = 0;

	private Vertex currentVertex = null;
	private Vertex oldVertex = null;

	private int currentRow, currentCol;

	private Game game;

	private int[] dx = new int[4];

	private int[] dy = new int[4];

	private MediaTracker thetracker = null;

	private Image ghostRight, ghostLeft, ghostscared1, ghostscared2;

	private final int ghostanimcount = 2;

	private int ghostanimpos = 0;

	private final int animdelay = 8;

	private int animcount = animdelay;

	public Ghost(int actX, int actY, int speed, Game game, int ghostId)
	{
		super();
		this.actX = actX;
		this.actY = actY;
		this.speed = speed;
		this.game = game;
		this.ghostId = ghostId;
		this.GetImages();
	}

	public Vertex getCurrentVertex()
	{
		return currentVertex;
	}

	public void setCurrentVertex(Vertex current)
	{
		this.setOldVertex(currentVertex);
		this.currentVertex = current;
	}

	public void changeVertex(Vertex newVertex)
	{
		this.setOldVertex(currentVertex);
		((PacVertex) currentVertex.element()).ghostDecrement(this);
		((PacVertex) newVertex.element()).ghostIncrement(this);
		currentVertex = newVertex;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public int getActX()
	{
		return actX;
	}

	public synchronized void setActX(int actX)
	{
		this.actX = actX;
	}

	public int getActY()
	{
		return actY;
	}

	public synchronized void setActY(int actY)
	{
		this.actY = actY;
	}

	public int getDestX()
	{
		return destX;
	}

	public void setDestX(int destX)
	{
		this.destX = destX;
	}

	public int getDestY()
	{
		return destY;
	}

	public void setDestY(int destY)
	{
		this.destY = destY;
	}

	int count;

	public void move(Vertex[][] screendata)
	{


//			count = 0;
			currentCol = this.getActX() / Level.blocksize;
			currentRow = this.getActY() / Level.blocksize;
//			currentVertex = screendata[currentRow][currentCol];
//			PacVertex vertex = (PacVertex) screendata[currentRow][currentCol]
//					.element();
//			if ((vertex.getType() & 1) == 0 && this.getDestX() != 1)
//			{
//				count++;
//			}
//			if ((vertex.getType() & 2) == 0 && this.getDestY() != 1)
//			{
//				count++;
//			}
//			if ((vertex.getType() & 4) == 0 && this.getDestX() != -1)
//			{
//				count++;
//			}
//			if ((vertex.getType() & 8) == 0 && this.getDestY() != -1)
//			{
//				count++;
//			}
//
//			IntegerDijkstraPathfinder dfs = new Pathfinder();
//			IncidenceListGraph graph = ((PacVertex) currentVertex.element())
//					.getGraph();
//			dfs.execute(graph, currentVertex, game.getPacman()
//					.getCurrentVertex());
//			PacVertex current = (PacVertex) screendata[currentRow][currentCol]
//					.element();
//
//			if (count < 2)
//			{
//				this.setRandomDirection();
//			} else if (dfs.pathExists())
//			{
//
//				EdgeIterator ei = dfs.reportPath();
//
//				PacVertex next = (PacVertex) graph.opposite(currentVertex,
//						ei.nextEdge()).element();
//				// in 90% he takes the right way
//				Random r = new Random();
//				int rand = r.nextInt(9);
//				if (current.getX() == next.getX())
//				{
//
//					this.destX = 0;
//				} else if (current.getX() < next.getX() && this.destX != -1)
//				{
//					if (rand == 0)
//					{
//						this.setRandomDirection();
//					} else
//					{
//						this.destX = 1;
//					}
//				} else if (current.getX() > next.getX() && this.destX != 1)
//				{
//					if (rand == 0)
//					{
//						this.setRandomDirection();
//					} else
//					{
//						this.destX = -1;
//					}
//				} else
//				{
//					this.setRandomDirection();
//				}
//				if (current.getY() == next.getY())
//				{
//					this.destY = 0;
//				} else if (current.getY() < next.getY() && this.destY != -1)
//				{
//					if (rand == 0)
//					{
//						this.setRandomDirection();
//					} else
//					{
//						this.destY = 1;
//					}
//				} else if (current.getY() > next.getY() && this.destY != 1)
//				{
//					if (rand == 0)
//					{
//						this.setRandomDirection();
//					} else
//					{
//						this.destY = -1;
//					}
//				} else
//				{
//					this.setRandomDirection();
//				}
//
//			}
//
//		}

		this.setActX(this.getActX() + (this.getDestX() * this.getSpeed()));
		this.setActY(this.getActY() + (this.getDestY() * this.getSpeed()));

		if(currentVertex != null){
			// sets ghost-int
			if (this.getActX() % Level.blocksize == Level.blocksize / 2)
			{

				((PacVertex) currentVertex.element()).ghostDecrement(this);
				if (this.getDestX() >= 0)
				{
					((PacVertex) screendata[currentRow][currentCol + 1].element())
							.ghostIncrement(this);
					currentVertex = screendata[currentRow][currentCol + 1];
				} else
				{
					((PacVertex) screendata[currentRow][currentCol - 1].element())
							.ghostIncrement(this);
					currentVertex = screendata[currentRow][currentCol - 1];
				}

			} else if (this.getActY() % Level.blocksize == Level.blocksize / 2)
			{

				((PacVertex) currentVertex.element()).ghostDecrement(this);

				if (this.getDestY() >= 0)
				{
					((PacVertex) screendata[currentRow + 1][currentCol].element())
							.ghostIncrement(this);
					currentVertex = screendata[currentRow + 1][currentCol];
				} else
				{
					((PacVertex) screendata[currentRow - 1][currentCol].element())
							.ghostIncrement(this);
					currentVertex = screendata[currentRow - 1][currentCol];
				}
			}
		}
	}

	public void draw(int x, int y)
	{
		if (ghostanimpos == 0 && !game.isScared())
		{
			game.getGraphic().drawImage(ghostRight, x, y, game);
		} else if (ghostanimpos == 1 && !game.isScared())
		{
			game.getGraphic().drawImage(ghostLeft, x, y, game);
		} else if (ghostanimpos == 0 && game.isScared())
		{
			game.getGraphic().drawImage(ghostscared1, x, y, game);
		} else if (ghostanimpos == 1 && game.isScared())
		{
			game.getGraphic().drawImage(ghostscared2, x, y, game);
		}
	}

	private void GetImages()
	{
		thetracker = new MediaTracker(game);
		ghostRight = createImage("../../pacpix/GhostRight.gif");
		thetracker.addImage(ghostRight, 0);
		ghostLeft = createImage("../../pacpix/GhostLeft.gif");
		thetracker.addImage(ghostLeft, 0);
		ghostscared1 = createImage("../../pacpix/GhostScared1.gif");
		thetracker.addImage(ghostscared1, 0);
		ghostscared2 = createImage("../../pacpix/GhostScared2.gif");
		thetracker.addImage(ghostscared2, 0);

		try
		{
			thetracker.waitForAll();
		} catch (InterruptedException e)
		{
			return;
		}
	}

	private Image createImage(String path)
	{
		URL folderURL = Game.class.getResource(path);
		Image image = (new ImageIcon(folderURL)).getImage();
		return image;
	}

	public void anim()
	{
		animcount--;
		if (animcount <= 0)
		{
			animcount = animdelay;
			ghostanimpos++;
			if (ghostanimpos >= ghostanimcount)
				ghostanimpos = 0;
		}

	}

	public Ghost clone()
	{
		Ghost newGhost = new Ghost(actX, actY, speed, game, ghostId);
		newGhost.setDestX(destX);
		newGhost.setDestY(destX);
		newGhost.setCurrentVertex(this.currentVertex);
		newGhost.setOldVertex(oldVertex);
		return newGhost;
	}

	private void setRandomDirection()
	{
		// System.out.println("random");
		int count = 0;
		PacVertex vertex = (PacVertex) currentVertex.element();
		if ((vertex.getType() & 1) == 0 && this.getDestX() != 1)
		{
			dx[count] = -1;
			dy[count] = 0;
			count++;
		}
		if ((vertex.getType() & 2) == 0 && this.getDestY() != 1)
		{
			dx[count] = 0;
			dy[count] = -1;
			count++;
		}
		if ((vertex.getType() & 4) == 0 && this.getDestX() != -1)
		{
			dx[count] = 1;
			dy[count] = 0;
			count++;
		}
		if ((vertex.getType() & 8) == 0 && this.getDestY() != -1)
		{
			dx[count] = 0;
			dy[count] = 1;
			count++;
		}
		if (count == 0)
		{
			if ((vertex.getType() & 15) == 15)
			{
				this.setDestX(0);
				this.setDestY(0);
			} else
			{
				this.setDestX(-this.getDestX());
				this.setDestY(-this.getDestY());
			}
		} else
		{
			count = (int) (Math.random() * count);
			if (count > 3)
				count = 3;
			this.setDestX(dx[count]);
			this.setDestY(dy[count]);
		}
	}

	public Vertex getOldVertex()
	{
		return oldVertex;
	}

	public void setOldVertex(Vertex oldVertex)
	{
		this.oldVertex = oldVertex;
	}

	public int getGhostId()
	{
		return ghostId;
	}

	public void setGhostId(int ghostId)
	{
		this.ghostId = ghostId;
	}
	

}
