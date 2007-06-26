package ch.pacman;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import ch.pacman.game.Ghost;
import ch.pacman.game.Level;
import ch.pacman.game.Level1;


public class Game extends JPanel implements Runnable
{

	private Level level;

	private Graphics goff;

	private boolean scared = false;

	private Dimension d = new Dimension(400, 400);

	private int deathcounter;

	private Image ii;

	private int nrofGhosts;

	private short[][] screendata;

	private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

	private Image ghost1, ghost2, ghostscared1, ghostscared2;

	private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;

	private Image pacman3up, pacman3down, pacman3left, pacman3right;

	private Image pacman4up, pacman4down, pacman4left, pacman4right;
	private MediaTracker  thetracker = null;
	private  int[]		dx,dy;
	private int		ghostanimpos=0;
	private boolean ingame = true; //TODO

	public Game()
	{
		
		this.setSize(d);
		this.setBackground(Color.black);
		GetImages();
	    dx=new int[4];
	    dy=new int[4];
		for (int i = 0; i < 4; i++)
		{
			ghosts.add(new Ghost(0, 0, 3));
		}
		GameInit();
		

	}
	
	  public void GameInit()
	  {
	    //pacsleft=3;
	    //score=0;
	    //scaredtime=maxscaredtime;

	    nrofGhosts=3;
	    //scaredtime=maxscaredtime;
	    this.LevelInit();
	  }
	  
	  public void LevelInit()
	  {
		  level = new Level1();
		  screendata = level.getLeveldata().clone();
			System.out.println(screendata);
	  LevelContinue();}
	  
	  public void LevelContinue()
	  {
	    short i;
	    int dx=1;
	    int random;

	    for (Ghost g : ghosts)
	    {
	      g.setActY(7*Level.blocksize);
	      g.setActY(7*Level.blocksize);
	      
	      g.setDestY(0);
	      g.setDestX(dx);
	      dx=-dx;
//	      random=(int)(Math.random()*(currentspeed+1));
//	      if (random>currentspeed)
//	        random=currentspeed;
//	      ghostspeed[i]=validspeeds[random];
	    }
	    screendata[7][6]=10;
	    screendata[7][8]=10;
//	    pacmanx=7*blocksize;
//	    pacmany=11*blocksize;
//	    pacmandx=0;
//	    pacmandy=0;
//	    reqdx=0;
//	    reqdy=0;
//	    viewdx=-1;
//	    viewdy=0;
//	    dying=false;
	    scared=false;
	  }

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (goff == null && d.width > 0 && d.height > 0)
		{
			ii = createImage(d.width, d.height);
			goff = ii.getGraphics();
		}
		if (goff == null || ii == null)
			return;

		goff.setColor(Color.black);
		goff.fillRect(0, 0, d.width, d.height);

		DrawMaze();

		if (ingame)
		      PlayGame();
		    //else
		      //PlayDemo();

		    g.drawImage(ii, 0, 0, this);
	}
	
	  public void PlayGame()
	  {
//	    if (dying)
//	    {
//	      Death();
//	    }
//	    else
//	    {
//	      CheckScared();
//	      MovePacMan();
//	      DrawPacMan();
	      MoveGhosts();
	      CheckMaze();
//	    }  
	  }
	  
	  public void CheckMaze()
	  {
//	    short i=0;
//	    boolean finished=true;
//
//	    while (i<Level.nrofblocks*Level.nrofblocks && finished)
//	    {
//	      if ((screendata[i]&48)!=0)
//	        finished=false;
//	      i++;
//	    }
//	    if (finished)
//	    {
//	      score+=50;
//	      DrawScore();
//	      try
//	      { 
//	        Thread.sleep(3000);
//	      }
//	      catch (InterruptedException e)
//	      {
//	      }
//	      if (nrofghosts < maxghosts)
//	        nrofghosts++; 
//	      if (currentspeed<maxspeed)
//	        currentspeed++;
//	      scaredtime=scaredtime-20;
//	      if (scaredtime<minscaredtime)
//	        scaredtime=minscaredtime;
//	      LevelInit();
//	    }
	  }


	public void DrawMaze()
	{
		short i = 0;
		int x, y;

		Level.bigdotcolor = Level.bigdotcolor + Level.dbigdotcolor;
		if (Level.bigdotcolor <= 64 || Level.bigdotcolor >= 192)
			Level.dbigdotcolor = -Level.dbigdotcolor;

		for (y = 0; y < Level.scrsize; y += Level.blocksize)
		{
			int j = 0;
			for (x = 0; x < Level.scrsize; x += Level.blocksize)
			{
				goff.setColor(Level.mazecolor);
				System.out.println(screendata);
				if ((screendata[i][j] & 1) != 0)
				{
					goff.drawLine(x, y, x, y + Level.blocksize - 1);
				}
				if ((screendata[i][j] & 2) != 0)
				{
					goff.drawLine(x, y, x + Level.blocksize - 1, y);
				}
				if ((screendata[i][j] & 4) != 0)
				{
					goff.drawLine(x + Level.blocksize - 1, y, x
							+ Level.blocksize - 1, y + Level.blocksize - 1);
				}
				if ((screendata[i][j] & 8) != 0)
				{
					goff.drawLine(x, y + Level.blocksize - 1, x
							+ Level.blocksize - 1, y + Level.blocksize - 1);
				}
				if ((screendata[i][j] & 16) != 0)
				{
					goff.setColor(Level.dotcolor);
					goff.fillRect(x + 11, y + 11, 2, 2);
				}
				if ((screendata[i][j] & 32) != 0)
				{
					goff.setColor(new Color(224, 224 - Level.bigdotcolor,
							Level.bigdotcolor));
					goff.fillRect(x + 8, y + 8, 8, 8);
				}
				j++;
			}
			i++;
		}
	}

	public void DrawGhost(int x, int y)
	{
		if (ghostanimpos == 0 && !scared)
		{
			goff.drawImage(ghost1, x, y, this);
		} else if (ghostanimpos == 1 && !scared)
		{
			goff.drawImage(ghost2, x, y, this);
		} else if (ghostanimpos == 0 && scared)
		{
			goff.drawImage(ghostscared1, x, y, this);
		} else if (ghostanimpos == 1 && scared)
		{
			goff.drawImage(ghostscared2, x, y, this);
		}
	}

	public void MoveGhosts()
	{
		short i;
		int row;
		int col;
		int count;

		for (Ghost g : ghosts)
		{
			if (g.getActX() % Level.blocksize == 0
					&& g.getActY() % Level.blocksize == 0)
			{
				row = g.getActX() / Level.blocksize;
				col = (int) (g.getActY() / Level.blocksize);

				count = 0;
				if ((screendata[row][col] & 1) == 0 && g.getDestX() != 1)
				{
					dx[count] = -1;
					dy[count] = 0;
					count++;
				}
				if ((screendata[row][col] & 2) == 0 && g.getDestY() != 1)
				{
					dx[count] = 0;
					dy[count] = -1;
					count++;
				}
				if ((screendata[row][col] & 4) == 0 && g.getDestX() != -1)
				{
					dx[count] = 1;
					dy[count] = 0;
					count++;
				}
				if ((screendata[row][col] & 8) == 0 && g.getDestY() != -1)
				{
					dx[count] = 0;
					dy[count] = 1;
					count++;
				}
				if (count == 0)
				{
					if ((screendata[row][col] & 15) == 15)
					{
						g.setDestX(0);
						g.setDestY(0);
					} else
					{
						g.setDestX(-g.getDestX());
						g.setDestY(-g.getDestY());
					}
				} else
				{
					count = (int) (Math.random() * count);
					if (count > 3)
						count = 3;
					g.setDestX(dx[count]);
					g.setDestY(dy[count]);
				}
			}
			g.setActX(g.getDestX() + (g.getDestX() * g.getSpeed()));
			g.setActX(g.getDestY() + (g.getDestY() * g.getSpeed()));
			DrawGhost(g.getDestX() + 1, (g.getDestY() + 1));

			// if (pacmanx>(ghostx[i]-12) && pacmanx<(ghostx[i]+12) &&
			// pacmany>(ghosty[i]-12) && pacmany<(ghosty[i]+12) && ingame)
			// {
			// if (scared)
			// {
			// score+=10;
			// g.setActX(7*Level.blocksize);
			// g.setActY(7*Level.blocksize);
			// }
			// else
			// {
			// dying=true;
			// deathcounter=64;
			// }
			// }
		}
	}

	public void run()
	{
		// TODO Auto-generated method stub

	}

	public static void main(String[] args)
	{
		JFrame f = new JFrame("PacMan");
		f.setSize(400, 400);
		f.setBackground(Color.BLACK);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Game pacman = new Game();
		f.add(pacman);
		Thread t = new Thread(pacman);
		f.setVisible(true);
	}

	public void setVisited(int x, int y)
	{

	}

	public void setSupreme()
	{

	}

	public void getSupreme()
	{

	}

	public void GetImages()
	{
		thetracker = new MediaTracker(this);

		ghost1 = createImage("../../pacpix/Ghost1.gif");
		thetracker.addImage(ghost1, 0);
		ghost2 = createImage("../../pacpix/Ghost2.gif");
		thetracker.addImage(ghost2, 0);
		ghostscared1 = createImage("../../pacpix/GhostScared1.gif");
		thetracker.addImage(ghostscared1, 0);
		ghostscared2 = createImage("../../pacpix/GhostScared2.gif");
		thetracker.addImage(ghostscared2, 0);

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
	private Image createImage(String path) {
		URL folderURL = Game.class.getResource(path);
		Image image = (new ImageIcon(folderURL)).getImage();
		return image;
	}

}
