package ch.pacman.game;

import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;

import javax.swing.ImageIcon;

import ch.pacman.Game;
import ch.pacman.graph.PacVertex;
import jdsl.graph.api.Vertex;

public class Ghost
{

	private int actX = 0;

	private int actY = 0;

	private int destX = 0;

	private int destY = 0;

	private int speed = 0;

	private Vertex currentVertex = null;

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

	public Ghost(int actX, int actY, int speed, Game game)
	{
		super();
		this.actX = actX;
		this.actY = actY;
		this.speed = speed;
		this.game = game;
		this.GetImages();
	}

	public Vertex getCurrentVertex()
	{
		return currentVertex;
	}

	public void setCurrentVertex(Vertex current)
	{
		this.currentVertex = current;
	}

	public void changeVertex(Vertex newVertex)
	{
		((PacVertex) currentVertex.element()).setPacMan(false);
		((PacVertex) newVertex.element()).setPacMan(true);
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

	public void setActX(int actX)
	{
		this.actX = actX;
	}

	public int getActY()
	{
		return actY;
	}

	public void setActY(int actY)
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

	public void move(Vertex[][] screendata)
	{
		int count;

		if (this.getActX() % Level.blocksize == 0
				&& this.getActY() % Level.blocksize == 0)
		{
			currentCol = this.getActX() / Level.blocksize;
			currentRow = this.getActY() / Level.blocksize;
			currentVertex = screendata[currentRow][currentCol];
			count = 0;
			PacVertex vertex = (PacVertex) screendata[currentRow][currentCol]
					.element();
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
		this.setActX(this.getActX() + (this.getDestX() * this.getSpeed()));
		this.setActY(this.getActY() + (this.getDestY() * this.getSpeed()));

		// sets ghost-int
		if (this.getActX() % Level.blocksize == Level.blocksize / 2)
		{

			((PacVertex) currentVertex.element()).ghostDecrement();
			if (this.getDestX() >= 0)
			{
				((PacVertex) screendata[currentRow][currentCol + 1].element())
						.ghostIncrement();
				currentVertex = screendata[currentRow][currentCol + 1];
			} else
			{
				((PacVertex) screendata[currentRow][currentCol - 1].element())
						.ghostIncrement();
				currentVertex = screendata[currentRow][currentCol - 1];
			}

		} else if (this.getActY() % Level.blocksize == Level.blocksize / 2)
		{

			((PacVertex) currentVertex.element()).ghostDecrement();

			if (this.getDestY() >= 0)
			{
				((PacVertex) screendata[currentRow + 1][currentCol].element())
						.ghostIncrement();
				currentVertex = screendata[currentRow + 1][currentCol];
			} else
			{
				((PacVertex) screendata[currentRow - 1][currentCol].element())
						.ghostIncrement();
				currentVertex = screendata[currentRow - 1][currentCol];
			}
		}

	}

	public void draw(int x, int y)
	{
		if (ghostanimpos == 0 && !game.isScared())
		{
			game.getGoff().drawImage(ghostRight, x, y, game);
		} else if (ghostanimpos == 1 && !game.isScared())
		{
			game.getGoff().drawImage(ghostLeft, x, y, game);
		} else if (ghostanimpos == 0 && game.isScared())
		{
			game.getGoff().drawImage(ghostscared1, x, y, game);
		} else if (ghostanimpos == 1 && game.isScared())
		{
			game.getGoff().drawImage(ghostscared2, x, y, game);
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

}
