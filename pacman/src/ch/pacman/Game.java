package ch.pacman;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.pacman.game.Level;
import ch.pacman.game.Level1;

public class Game extends JPanel implements Runnable{
	
	private Level level = new Level1();
	private Graphics	goff;
	private Dimension	d = new Dimension(400,400);
	private Image		ii;
	private short[] screendata = level.getLeveldata();
	
	 
	public Game(){
		this.setSize(400,400);
	}
	
	

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (goff==null && d.width>0 && d.height>0)
	    {
	      ii = createImage(d.width, d.height);
	      goff = ii.getGraphics();
	    }
	    if (goff==null || ii==null)
	      return;

	    goff.setColor(Color.black);
	    goff.fillRect(0, 0, d.width, d.height);

	    DrawMaze();


	    g.drawImage(ii, 0, 0, this);
	}


	public void DrawMaze()
	  {
	    short i=0;
	    int x,y;

	    Level.bigdotcolor=Level.bigdotcolor+Level.dbigdotcolor;
	    if (Level.bigdotcolor<=64 || Level.bigdotcolor>=192)
	    	Level.dbigdotcolor=-Level.dbigdotcolor;

	    for (y=0; y<Level.scrsize; y+=Level.blocksize)
	    {
	      for (x=0; x<Level.scrsize; x+=Level.blocksize)
	      {
	    	  goff.setColor(Level.mazecolor);
	        if ((screendata[i]&1)!=0)
		{
	           goff.drawLine(x,y,x,y+Level.blocksize-1);
		}
		if ((screendata[i]&2)!=0)
		{
	          goff.drawLine(x,y,x+Level.blocksize-1,y);
		}
		if ((screendata[i]&4)!=0)
		{
	          goff.drawLine(x+Level.blocksize-1,y,x+Level.blocksize-1,y+Level.blocksize-1);
		}
		if ((screendata[i]&8)!=0)
		{
	          goff.drawLine(x,y+Level.blocksize-1,x+Level.blocksize-1,y+Level.blocksize-1);
		}
		if ((screendata[i]&16)!=0)
		{
	          goff.setColor(Level.dotcolor);
	          goff.fillRect(x+11,y+11,2,2);
		}
		if ((screendata[i]&32)!=0)
		{
	          goff.setColor(new Color(224,224-Level.bigdotcolor,Level.bigdotcolor));
	          goff.fillRect(x+8,y+8,8,8);
		}
		i++;
	      }
	    }
	  }

	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		JFrame f = new JFrame("PacMan");
		f.setSize(400,400);
		f.setBackground(Color.BLACK);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Game pacman = new Game();
		f.add(pacman);
		Thread t = new Thread(pacman);
		f.setVisible(true);
	}
	
	public void setVisited(int x, int y){
		
	}
	
	public void setSupreme(){
		
	}
	
	public void getSupreme(){
		
	}

}
