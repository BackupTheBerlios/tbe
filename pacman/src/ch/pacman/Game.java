package ch.pacman;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;
import jdsl.graph.api.Vertex;
import ch.pacman.game.*;
import ch.pacman.graph.PacVertex;

public class Game extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;

	private Level level;

	private Graphics graphics;

	private boolean ingame = true;

	private boolean scared = false;

	private boolean human;

	private Dimension d = new Dimension(Level.screensize, Level.screensize + 100);

	private Image ii;

	private int nrofGhosts;

	private int reqdx, reqdy;
	private int		pacsleft,score;
	private Vertex[][] screendata;

	private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

	private PacMan pacman;

	private int scaredcount;
	

	public Game(JFrame f)
	{
		this.setSize(d);
		this.setBackground(Color.black);
		Object[] options = { "Human", "Computer" };
		String question = "Human or Computer Player?";
		int answer = JOptionPane.showOptionDialog(null, question, "",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[1]);
		if (answer == 0)
		{
			human = true;
		} else
		{
			human = false;
		}
		f.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{

				if (ingame && human)
				{

					if (e.getKeyCode() == KeyEvent.VK_LEFT)
					{
						reqdx = -1;
						reqdy = 0;
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					{
						reqdx = 1;
						reqdy = 0;
					} else if (e.getKeyCode() == KeyEvent.VK_UP)
					{
						reqdx = 0;
						reqdy = -1;
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					{
						reqdx = 0;
						reqdy = 1;
					} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					{
						ingame = false;
					}
				}
				// else
				// {
				// if (key == 's' || key == 'S')
				// {
				// ingame=true;
				// GameInit();
				// }
				// }

			}

		});
		GameInit();

	}

	public void GameInit()
	{
		nrofGhosts = 4;
		for (int i = 0; i < nrofGhosts; i++)
		{
			ghosts.add(new Ghost(0, 0, 3, this));
		}
		pacman = new PacMan(human, 0, 0, 4, this);
		pacsleft = 3;
		score = 0;
		this.LevelInit();
	}

	public int getReqdx()
	{
		return reqdx;
	}

	public int getReqdy()
	{
		return reqdy;
	}

	public void LevelInit()
	{
		level = new Level1();
		screendata = level.getClonedPathdata();
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
		if (graphics == null && d.width > 0 && d.height > 0)
		{
			ii = createImage(d.width, d.height);
			graphics = ii.getGraphics();
		}
		if (graphics == null || ii == null)
			return;

		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, d.width, d.height);

		DrawMaze();
		DrawScore();
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
		// this.debug(); //TODO: remove debug
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
				graphics.setColor(Level.mazecolor);
				PacVertex vertex = (PacVertex) screendata[i][j].element();

				int x = vertex.getX();
				int y = vertex.getY();

				if ((vertex.getType() & 1) != 0)
				{
					graphics.drawLine(x, y, x, y + Level.blocksize - 1);
				}
				if ((vertex.getType() & 2) != 0)
				{
					graphics.drawLine(x, y, x + Level.blocksize - 1, y);
				}
				if ((vertex.getType() & 4) != 0)
				{
					graphics.drawLine(x + Level.blocksize - 1, y, x
							+ Level.blocksize - 1, y + Level.blocksize - 1);
				}
				if ((vertex.getType() & 8) != 0)
				{
					graphics.drawLine(x, y + Level.blocksize - 1, x
							+ Level.blocksize - 1, y + Level.blocksize - 1);
				}
				if ((vertex.hasLittleDot()))
				{
					graphics.setColor(Level.dotcolor);
					graphics.fillRect(x + 11, y + 11, 2, 2);
				}
				if ((vertex.hasBigDot()))
				{
					graphics.setColor(new Color(224, 224 - Level.bigdotcolor,
							Level.bigdotcolor));
					graphics.fillRect(x + 8, y + 8, 8, 8);
				}

			}
		}

	}
	
	  public void DrawScore()
	  {
	    int i;
	    String s;
	    graphics.setFont(new Font("Helvetica", Font.BOLD, 14));
	    graphics.setColor(new Color(96,128,255));
	    s="Score: "+score;
	    graphics.drawString(s,Level.screensize/2+96,Level.screensize+16);
	    for (i=0; i<pacsleft; i++)
	    {
	      graphics.drawImage(pacman.getPacman3left(),i*28+8,Level.screensize+1,this);
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
		f.setSize(367, 420);
		f.setBackground(Color.BLACK);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		Game pacman = new Game(f);
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
		return graphics;
	}

	public void debug()
	{
		System.out.println();
		for (int i = 0; i < Level.nrofblocks; i++)
		{

			for (int j = 0; j < Level.nrofblocks; j++)
			{
				int numghosts = ((PacVertex) screendata[i][j].element())
						.getGhostCount();
				boolean pacman = ((PacVertex) screendata[i][j].element())
						.isPacMan();
				if (numghosts == 0 && !pacman)
				{
					System.out.print("[  ]");
				} else if (pacman && numghosts > 0)
				{
					System.out.print("[P" + numghosts + "]");

				} else if (pacman)
				{
					System.out.print("[P ]");
				} else
				{
					System.out.print("[0" + numghosts + "]");
				}

			}
			System.out.println();
		}
		System.out.println();
	}

	public PacMan getPacman()
	{
		return pacman;
	}

	public ArrayList<Ghost> getGhosts()
	{
		return ghosts;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public int getScore()
	{
		return score;
	}

}
