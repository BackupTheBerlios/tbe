package ch.pacman.game;

import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;
import javax.swing.ImageIcon;
import ch.pacman.Game;
import ch.pacman.graph.PacVertex;
import jdsl.graph.api.Vertex;

public class PacMan
{

	private int actX = 0;

	private int actY = 0;

	private int destX = 0;

	private int destY = 0;

	private int speed = 0;

	private boolean human;

	private int currentRow, currentCol;

	private int[] dx = new int[4];

	private int[] dy = new int[4];

	private Vertex currentVertex = null;

	private Game game;

	private MediaTracker thetracker = null;

	private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;

	private Image pacman3up, pacman3down, pacman3left, pacman3right;

	private Image pacman4up, pacman4down, pacman4left, pacman4right;

	private final int pacanimdelay = 2;

	private int pacmananimpos = 0;

	private int pacanimdir = 1;

	private final int pacmananimcount = 4;

	private int pacanimcount = pacanimdelay;

	public PacMan(boolean human, int actX, int actY, int speed, Game game)
	{
		super();
		this.actX = actX;
		this.actY = actY;
		this.speed = speed;
		this.game = game;
		this.human = human;
		this.GetImages();
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

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public Vertex getCurrentVertex()
	{
		return currentVertex;
	}

	public void setCurrentVertex(Vertex current)
	{
		this.currentVertex = current;
	}

	public void move(Vertex[][] screendata)
	{
		if (human)
		{
			this.setHumanMove(screendata);
		} else if (this.getActX() % Level.blocksize == 0
				&& this.getActY() % Level.blocksize == 0)
		{

			currentCol = this.getActX() / Level.blocksize;
			currentRow = this.getActY() / Level.blocksize;
			currentVertex = screendata[currentRow][currentCol];
			PacVertex vertex = (PacVertex) currentVertex.element();
			this.setRandomDirection();
			// checks for small/bigBoint
			int ch = vertex.getType();
			if ((ch & 16) != 0)
			{
				((PacVertex) screendata[currentRow][currentCol].element())
						.setType((short) (ch & 15));
				game.setScore(game.getScore()+1);

			}
			if ((ch & 32) != 0)
			{
				game.setScared(true);

				((PacVertex) screendata[currentRow][currentCol].element())
						.setType((short) (ch & 15));
				game.setScore(game.getScore()+5);

			}
		}
		this.setActX(this.getActX() + (this.getDestX() * this.getSpeed()));
		this.setActY(this.getActY() + (this.getDestY() * this.getSpeed()));

		// sets pacman
		if (this.getActX() % Level.blocksize == Level.blocksize / 2)
		{

			((PacVertex) currentVertex.element()).setPacMan(null);
			if (this.getDestX() >= 0)
			{
				((PacVertex) screendata[currentRow][currentCol + 1].element())
						.setPacMan(this);
				currentVertex = screendata[currentRow][currentCol + 1];
			} else
			{
				((PacVertex) screendata[currentRow][currentCol - 1].element())
						.setPacMan(this);
				currentVertex = screendata[currentRow][currentCol - 1];
			}

		} else if (this.getActY() % Level.blocksize == Level.blocksize / 2)
		{

			((PacVertex) currentVertex.element()).setPacMan(null);

			if (this.getDestY() >= 0)
			{
				((PacVertex) screendata[currentRow + 1][currentCol].element())
						.setPacMan(this);
				currentVertex = screendata[currentRow + 1][currentCol];
			} else
			{
				((PacVertex) screendata[currentRow - 1][currentCol].element())
						.setPacMan(this);
				currentVertex = screendata[currentRow - 1][currentCol];
			}
		}
	}

	private void setHumanMove(Vertex[][] screendata)
	{
		short ch;

		if (game.getReqdx() == -this.destX && game.getReqdy() == -this.destY)
		{
			this.destX = game.getReqdx();
			this.destX = game.getReqdx();
			// viewdx=pacmandx;
			// viewdy=pacmandy;
		}
		if (this.getActX() % Level.blocksize == 0
				&& this.actY % Level.blocksize == 0)
		{

			currentCol = this.getActX() / Level.blocksize;
			currentRow = this.getActY() / Level.blocksize;
			currentVertex = screendata[currentRow][currentCol];
			PacVertex vertex = (PacVertex) currentVertex.element();

			// checks for small/bigBoint
			ch = vertex.getType();

			if ((ch & 16) != 0)
			{
				((PacVertex) screendata[currentRow][currentCol].element())
						.setType((short) (ch & 15));
				game.setScore(game.getScore()+1);
			}
			if ((ch & 32) != 0)
			{
				game.setScared(true);

				((PacVertex) screendata[currentRow][currentCol].element())
						.setType((short) (ch & 15));
				game.setScore(game.getScore()+5);

			}

			if (game.getReqdx() != 0 || game.getReqdy() != 0)
			{
				if (!((game.getReqdx() == -1 && game.getReqdy() == 0 && (ch & 1) != 0)
						|| (game.getReqdx() == 1 && game.getReqdy() == 0 && (ch & 4) != 0)
						|| (game.getReqdx() == 0 && game.getReqdy() == -1 && (ch & 2) != 0) || (game
						.getReqdx() == 0
						&& game.getReqdy() == 1 && (ch & 8) != 0)))
				{
					this.destX = game.getReqdx();
					this.destY = game.getReqdy();
					// viewdx=pacmandx;
					// viewdy=pacmandy;
				}
			}

			// Check for standstill
			if ((this.destX == -1 && this.destY == 0 && (ch & 1) != 0)
					|| (this.destX == 1 && this.destY == 0 && (ch & 4) != 0)
					|| (this.destX == 0 && this.destY == -1 && (ch & 2) != 0)
					|| (this.destX == 0 && this.destY == 1 && (ch & 8) != 0))
			{
				this.destX = 0;
				this.destY = 0;
			}
		}

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

	public void draw()
	{
		if (this.getDestX() == -1)
			DrawPacManLeft();
		else if (this.getDestX() == 1)
			DrawPacManRight();
		else if (this.getDestY() == -1)
			DrawPacManUp();
		else
			DrawPacManDown();
	}

	private void DrawPacManUp()
	{
		switch (pacmananimpos)
		{
		case 1:
			game.getGraphic().drawImage(pacman2up, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		case 2:
			game.getGraphic().drawImage(pacman3up, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		case 3:
			game.getGraphic().drawImage(pacman4up, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		default:
			game.getGraphic().drawImage(pacman1, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		}
	}

	private void DrawPacManDown()
	{
		switch (pacmananimpos)
		{
		case 1:
			game.getGraphic().drawImage(pacman2down, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		case 2:
			game.getGraphic().drawImage(pacman3down, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		case 3:
			game.getGraphic().drawImage(pacman4down, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		default:
			game.getGraphic().drawImage(pacman1, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		}
	}

	private void DrawPacManLeft()
	{
		switch (pacmananimpos)
		{
		case 1:
			game.getGraphic().drawImage(pacman2left, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		case 2:
			game.getGraphic().drawImage(pacman3left, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		case 3:
			game.getGraphic().drawImage(pacman4left, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		default:
			game.getGraphic().drawImage(pacman1, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		}
	}

	private void DrawPacManRight()
	{
		switch (pacmananimpos)
		{
		case 1:
			game.getGraphic().drawImage(pacman2right, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		case 2:
			game.getGraphic().drawImage(pacman3right, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		case 3:
			game.getGraphic().drawImage(pacman4right, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		default:
			game.getGraphic().drawImage(pacman1, this.getActX() + 1,
					this.getActY() + 1, game);
			break;
		}
	}

	private void GetImages()
	{
		thetracker = new MediaTracker(game);
		pacman1 = createImage("../../pacpix/PacMan1.gif");
		thetracker.addImage(pacman1, 0);
		pacman2up = createImage("../../pacpix/PacMan2up.gif");
		thetracker.addImage(pacman2up, 0);
		pacman3up = createImage("../../pacpix/PacMan3up.gif");
		thetracker.addImage(pacman3up, 0);
		pacman4up = createImage("../../pacpix/PacMan4up.gif");
		thetracker.addImage(pacman4up, 0);

		pacman2down = createImage("../../pacpix/PacMan2down.gif");
		thetracker.addImage(pacman2down, 0);
		pacman3down = createImage("../../pacpix/PacMan3down.gif");
		thetracker.addImage(pacman3down, 0);
		pacman4down = createImage("../../pacpix/PacMan4down.gif");
		thetracker.addImage(pacman4down, 0);

		pacman2left = createImage("../../pacpix/PacMan2left.gif");
		thetracker.addImage(pacman2left, 0);
		pacman3left = createImage("../../pacpix/PacMan3left.gif");
		thetracker.addImage(pacman3left, 0);
		pacman4left = createImage("../../pacpix/PacMan4left.gif");
		thetracker.addImage(pacman4left, 0);

		pacman2right = createImage("../../pacpix/PacMan2right.gif");
		thetracker.addImage(pacman2right, 0);
		pacman3right = createImage("../../pacpix/PacMan3right.gif");
		thetracker.addImage(pacman3right, 0);
		pacman4right = createImage("../../pacpix/PacMan4right.gif");
		thetracker.addImage(pacman4right, 0);

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
		pacanimcount--;
		if (pacanimcount <= 0)
		{
			pacanimcount = pacanimdelay;
			pacmananimpos = pacmananimpos + pacanimdir;
			if (pacmananimpos == (pacmananimcount - 1) || pacmananimpos == 0)
				pacanimdir = -pacanimdir;
		}
	}
	
	public void dead(int i){
		switch(i)
	    {
	      case 0:
	        game.getGraphic().drawImage(pacman4up,actX+1,actY+1,game);
	        break;
	      case 1:
	    	  game.getGraphic().drawImage(pacman4right,actX+1,actY+1,game);
	        break;
	      case 2:
	    	  game.getGraphic().drawImage(pacman4down,actX+1,actY+1,game);
	        break;
	      default:
	    	  game.getGraphic().drawImage(pacman4left,actX+1,actY+1,game);
	    }
	}

	public PacMan clone()
	{
		return new PacMan(human, actX, actY, speed, game);
	}

	public Image getPacman3left()
	{
		return pacman3left;
	}
}
