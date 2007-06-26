package ch.pacman;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.pacman.game.Ghost;
import ch.pacman.game.Level;
import ch.pacman.game.Level1;

public class Game extends JPanel implements Runnable
{

	private Level level = new Level1(this);

	private Graphics goff;

	private Dimension d = new Dimension(400, 400);
	private int		deathcounter;
	private Image ii;
	private final int nrofGhosts = 3;
	private short[][] screendata = level.getLeveldata();
	private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

	public Game()
	{
		this.setSize(d);
		for(int i = 0 ; i < 4; i++){
			ghosts.add(new Ghost(0,0));
		}
		
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

		g.drawImage(ii, 0, 0, this);
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
	    if (ghostanimpos==0 && !scared)
	    {
	      goff.drawImage(ghost1,x,y,this);
	    }
	    else if (ghostanimpos==1 && !scared)
	    {
	      goff.drawImage(ghost2,x,y,this);
	    }
	    else if (ghostanimpos==0 && scared)
	    {
	      goff.drawImage(ghostscared1,x,y,this);
	    }
	    else if (ghostanimpos==1 && scared)
	    {
	      goff.drawImage(ghostscared2,x,y,this);
	    }
	  }
	 
	  public void MoveGhosts()
	  {
	    short i;
	    int pos;
	    int count;

	    for (Ghost g : ghosts)
	    {
	      if (g.getActX() % Level.blocksize==0 && ghosty[i]%Level.blocksize==0)
	      {
	        pos=g.getActX()/Level.blocksize+Level.nrofblocks*(int)(ghosty[i]/Level.blocksize);

	        count=0;
	        if ((screendata[pos]&1)==0 && ghostdx[i]!=1)
	        {
	          dx[count]=-1;
	          dy[count]=0;
	          count++;
	        }
	        if ((screendata[pos]&2)==0 && ghostdy[i]!=1)
	        {
	          dx[count]=0;
	          dy[count]=-1;
	          count++;
	        }
	        if ((screendata[pos]&4)==0 && ghostdx[i]!=-1)
	        {
	          dx[count]=1;
	          dy[count]=0;
	          count++;
	        }
	        if ((screendata[pos]&8)==0 && ghostdy[i]!=-1)
	        {
	          dx[count]=0;
	          dy[count]=1;
	          count++;
	        }
	        if (count==0)
	        {
	          if ((screendata[pos]&15)==15)
	          {
	            ghostdx[i]=0;
	            ghostdy[i]=0;
	          }
	          else
	          {
	            ghostdx[i]=-ghostdx[i];
	            ghostdy[i]=-ghostdy[i];
	          }
	        }
	        else
	        {
	          count=(int)(Math.random()*count);
	          if (count>3) count=3;
	          ghostdx[i]=dx[count];
	          ghostdy[i]=dy[count];
	        }
	      }
	      ghostx[i]=ghostx[i]+(ghostdx[i]*ghostspeed[i]);
	      ghosty[i]=ghosty[i]+(ghostdy[i]*ghostspeed[i]);
	      DrawGhost(ghostx[i]+1,ghosty[i]+1);

	      if (pacmanx>(ghostx[i]-12) && pacmanx<(ghostx[i]+12) &&
	          pacmany>(ghosty[i]-12) && pacmany<(ghosty[i]+12) && ingame)
	      {
	        if (scared)
	        {
	          score+=10;
	          g.setActX(7*Level.blocksize);
	          g.setActY(7*Level.blocksize);
	        }
	        else
	        {
	          dying=true;
	          deathcounter=64;
	        }
	      }
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

}
