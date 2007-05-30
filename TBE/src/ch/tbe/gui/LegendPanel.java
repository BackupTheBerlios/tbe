package ch.tbe.gui;



import javax.swing.JPanel;

import ch.tbe.Board;

public class LegendPanel extends JPanel
{
	private Board board;
	
	public LegendPanel(Board board)
	{
		this.board = board;
		showLegend();
	}
	
	private void showLegend()
	{
		Object[] items = board.getItems(); //TODO: muss noch kucken by dave ;-)
		
		
		
		for(Object i : items)
		{
			System.out.println(i.getClass());
			
		}
		
		
	}
	
	public void reload()
	{
		showLegend();
		this.setVisible(false);
		this.setVisible(true);
	}
}
