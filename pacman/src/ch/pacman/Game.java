package ch.pacman;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import jdsl.graph.api.Vertex;
import ch.pacman.game.*;
import ch.pacman.graph.PacVertex;


public class Game extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;

	private Level level;

	private Graphics goff;

	private boolean scared = false;

	private Dimension d = new Dimension(Level.screensize, Level.screensize);

	private Image ii;

	private int nrofGhosts;

	private Vertex[][] screendata;

	private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

	private PacMan pacman;

	private int scaredcount;

	public Game()
	{
		this.setSize(d);
		this.setBackground(Color.black);
		GameInit();

	}

	public void GameInit()
	{
		nrofGhosts = 3;
		for (int i = 0; i <= nrofGhosts; i++)
		{
			ghosts.add(new Ghost(0, 0, 3, this));
		}
		pacman = new PacMan(0, 0, 4, this);

		this.LevelInit();
	}

	public void LevelInit()
	{
		level = new Level1();
		screendata = level.getPathdata().clone();
		LevelContinue();
	}

	public void LevelContinue()
	{

		int dx = 1;

		for (Ghost g : ghosts)
		{
			g.setActX(level.getGhostStart().x);
			g.setActY(level.getGhostStart().y);

			g.setDestY(0);
			g.setDestX(dx);
			dx = -dx;
		}
		((PacVertex) screendata[7][6].element()).setType((short) 10);
		((PacVertex) screendata[7][8].element()).setType((short) 10);
		pacman.setActX(level.getPacManStart().x);
		pacman.setActY(level.getPacManStart().y);
		scared = false;
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
		for (Ghost gs : ghosts)
		{
			gs.anim();
		}
		pacman.anim();
		PlayGame();

		g.drawImage(ii, 0, 0, this);
	}

	public void PlayGame()
	{

		CheckScared();
		pacman.move(screendata);
		pacman.draw();

		for (Ghost g : ghosts)
		{
			g.move(screendata);
			g.draw(g.getActX() + 1, (g.getActY() + 1));
		}
	}

	public void DrawMaze()
	{

		Level.bigdotcolor = Level.bigdotcolor + Level.dbigdotcolor;
		if (Level.bigdotcolor <= 64 || Level.bigdotcolor >= 192)
			Level.dbigdotcolor = -Level.dbigdotcolor;

		for (int i = 0; i < Level.nrofblocks; i++)
		{

			for (int j = 0; j < Level.nrofblocks; j++)
			{
				goff.setColor(Level.mazecolor);
				PacVertex vertex = (PacVertex) screendata[i][j].element();

				int x = vertex.getX();
				int y = vertex.getY();

				if ((vertex.getType() & 1) != 0)
				{
					goff.drawLine(x, y, x, y + Level.blocksize - 1);
				}
				if ((vertex.getType() & 2) != 0)
				{
					goff.drawLine(x, y, x + Level.blocksize - 1, y);
				}
				if ((vertex.getType() & 4) != 0)
				{
					goff.drawLine(x + Level.blocksize - 1, y, x
							+ Level.blocksize - 1, y + Level.blocksize - 1);
				}
				if ((vertex.getType() & 8) != 0)
				{
					goff.drawLine(x, y + Level.blocksize - 1, x
							+ Level.blocksize - 1, y + Level.blocksize - 1);
				}
				if ((vertex.hasLittleDot()))
				{
					goff.setColor(Level.dotcolor);
					goff.fillRect(x + 11, y + 11, 2, 2);
				}
				if ((vertex.hasBigDot()))
				{
					goff.setColor(new Color(224, 224 - Level.bigdotcolor,
							Level.bigdotcolor));
					goff.fillRect(x + 8, y + 8, 8, 8);
				}

			}
		}

	}

	public void run()
	{
		while (true)
		{
			this.repaint();
			try
			{
				Thread.currentThread().sleep(50);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void CheckScared()
	{
		scaredcount--;
		if (scaredcount <= 0)
			scared = false;

		if (scared && scaredcount >= 30)
			Level.mazecolor = new Color(192, 32, 255);
		else
			Level.mazecolor = new Color(32, 192, 255);

		if (scared)
		{
			((PacVertex) screendata[7][6].element()).setType((short) 11);
			((PacVertex) screendata[7][8].element()).setType((short) 14);

		} else
		{
			((PacVertex) screendata[7][6].element()).setType((short) 10);
			((PacVertex) screendata[7][8].element()).setType((short) 10);
		}
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
		t.start();
	}

	public boolean isScared()
	{
		return scared;
	}

	public void setScared(boolean scared)
	{
		this.scared = scared;
		if (scared)
		{
			scaredcount = Level.scaredtime;
		}
	}

	public Graphics getGoff()
	{
		return goff;
	}

}
