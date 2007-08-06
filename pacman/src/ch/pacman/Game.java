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

	private boolean showtitle = true;

	private boolean scared = false;

	private boolean human, dead, ingame;

	private Dimension d = new Dimension(Level.screensize,
			Level.screensize + 100);

	private Image ii;

	private final int screendelay = 120;

	private int count = screendelay;

	private int nrofGhosts;

	private int deadCounter;

	private int reqdx, reqdy;

	private int pacsleft, score;

	private int dx = 1;

	private Vertex[][] screendata;

	private static ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

	private static PacMan pacman;

	private int scaredcount;

	private Font largefont = new Font("Helvetica", Font.BOLD, 24);

	private Font smallfont = new Font("Helvetica", Font.BOLD, 14);
	
	private DecisionResult res = null;

	public Game(JFrame f)
	{
		this.setSize(d);
		this.setBackground(Color.black);
		level = new Level1();
		screendata = level.getClonedPathdata();

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
				} else if (ingame && !human)
				{
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					{
						ingame = false;

					}
				} else

				{
					if (e.getKeyCode() == KeyEvent.VK_H)
					{
						ingame = true;
						human = true;
						GameInit();
					} else if (e.getKeyCode() == KeyEvent.VK_C)
					{
						ingame = true;
						human = false;
						GameInit();
					}
				}

			}

		});

	}

	public void GameInit()
	{
		nrofGhosts = 4;
		ghosts.clear();
		for (int i = 0; i < nrofGhosts; i++)
		{
			ghosts.add(new Ghost(0, 0, 3, this, i));
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

		pacman.setDestX(0);
		pacman.setDestY(0);
		reqdx = 0;
		reqdy = 0;
		for (Ghost g : ghosts)
		{
			g.setActX(level.getGhostStart().x);
			g.setActY(level.getGhostStart().y);
			//g.setCurrentVertex(screendata[level.getGhostStart().y / Level.blocksize][level.getGhostStart().x / Level.blocksize]);

			g.setDestY(0);
			g.setDestX(dx);
			dx = -dx;
		}
		((PacVertex) screendata[7][6].element()).setType((short) 10);
		((PacVertex) screendata[7][8].element()).setType((short) 10);
		pacman.setActX(level.getPacManStart().x);
		pacman.setActY(level.getPacManStart().y);
		pacman.setCurrentVertex(screendata[level.getPacManStart().y / Level.blocksize][level.getPacManStart().x / Level.blocksize]);
		scared = false;
		dead = false;
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

		if (ingame)
		{
			pacman.anim();
			PlayGame();
		} else
		{
			CheckScared();
			ShowIntroScreen();
		}

		g.drawImage(ii, 0, 0, this);
	}

	public void PlayGame()
	{
		
//		if(pacman.getActX() % Level.blocksize == 0 && pacman.getActY() % Level.blocksize == 0)
//			res = Heuristic.getBestMove(screendata);
		
		if (dead)
		{
			Death();
		} else
		{
			CheckScared();
			
//			if(pacman.getActX() % Level.blocksize == 0 && pacman.getActY() % Level.blocksize == 0){
//				pacman.setDestX(res.getPacObject().getPacman().getDestX());
//				pacman.setDestY(res.getPacObject().getPacman().getDestY());
//			}
			int i = 0;
			for (Ghost g : ghosts)
			{
//				Ghost found = null;
//				
//				if(g.getActX() % Level.blocksize == 0 && g.getActY() % Level.blocksize == 0){
//					for(Ghost search : res.getGhostObject().getGhosts()){
//						if(search != null && search.getGhostId() == g.getGhostId()){
//							found = search;
//							g.setDestX(found.getDestX());
//							g.setDestY(found.getDestY());
//
//							break;
//						}
//					}
//
//				}

				g.move(screendata);
				g.draw(g.getActX() + 1, (g.getActY() + 1));
				checkDead(g);
			}
			

			
			pacman.move(screendata);
			pacman.draw();

			//this.debug(); //TODO: remove debug
		}
	}

	public void checkDead(Ghost g)
	{
		if (pacman.getActX() > (g.getActX() - 12)
				&& pacman.getActX() < (g.getActX() + 12)
				&& pacman.getActY() > (g.getActY() - 12)
				&& pacman.getActY() < (g.getActY() + 12) && ingame)
		{
			if (scared)
			{
				score += 10;
				g.changeVertex(screendata[level.getGhostStart().x
						/ Level.blocksize][level.getGhostStart().y
						/ Level.blocksize]);
				g.setActX(level.getGhostStart().x);
				g.setActY(level.getGhostStart().y);
				g.setDestY(0);
				g.setDestX(dx);
				dx = -dx;
			} else
			{
				dead = true;
				deadCounter = 64;
			}
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
		graphics.setFont(smallfont);
		graphics.setColor(new Color(96, 128, 255));
		s = "Score: " + score;
		graphics
				.drawString(s, Level.screensize / 2 + 96, Level.screensize + 16);
		for (i = 0; i < pacsleft; i++)
		{
			graphics.drawImage(pacman.getPacman3left(), i * 28 + 8,
					Level.screensize + 1, this);
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
		f.setLocationRelativeTo(null);
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

	public void Death()
	{
		int i;

		deadCounter--;
		i = (deadCounter & 15) / 4;
		pacman.dead(i);

		if (deadCounter == 0)
		{
			pacsleft--;
			if (pacsleft == 0)
				ingame = false;
			LevelContinue();
		}
	}

	public Graphics getGraphic()
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

	public static PacMan getPacman()
	{
		return pacman;
	}

	public void ShowIntroScreen()
	{
		String s;

		graphics.setFont(largefont);

		graphics.setColor(new Color(0, 32, 48));
		graphics.fillRect(16, Level.screensize / 2 - 40, Level.screensize - 32,
				80);
		graphics.setColor(Color.white);
		graphics.drawRect(16, Level.screensize / 2 - 40, Level.screensize - 32,
				80);

		if (showtitle)
		{
			s = "PacMan";
			scared = false;

			graphics.setColor(Color.white);
			FontMetrics fmlarge = graphics.getFontMetrics();
			graphics.drawString(s,
					(Level.screensize - fmlarge.stringWidth(s)) / 2 + 2,
					Level.screensize / 2 - 20 + 2);
			graphics.setColor(new Color(96, 128, 255));
			graphics.drawString(s,
					(Level.screensize - fmlarge.stringWidth(s)) / 2,
					Level.screensize / 2 - 20);

			s = "by David Meier and Lars Schnyder";
			graphics.setFont(smallfont);
			graphics.setColor(new Color(255, 160, 64));
			FontMetrics fmsmall = graphics.getFontMetrics();
			graphics.drawString(s,
					(Level.screensize - fmsmall.stringWidth(s)) / 2,
					Level.screensize / 2 + 10);

		} else
		{
			graphics.setFont(smallfont);
			graphics.setColor(new Color(96, 128, 255));
			s = "'H' for Human-Player or 'C' for Computer";
			FontMetrics fmsmall = graphics.getFontMetrics();
			graphics.drawString(s,
					(Level.screensize - fmsmall.stringWidth(s)) / 2,
					Level.screensize / 2 - 10);
			graphics.setColor(new Color(255, 160, 64));
			s = "Use cursor keys to move";
			graphics.drawString(s,
					(Level.screensize - fmsmall.stringWidth(s)) / 2,
					Level.screensize / 2 + 20);
			scared = true;
		}
		count--;
		if (count <= 0)
		{
			count = screendelay;
			showtitle = !showtitle;
		}
	}

	public static ArrayList<Ghost> getGhosts()
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
