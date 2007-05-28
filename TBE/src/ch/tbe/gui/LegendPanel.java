package ch.tbe.gui;

import java.util.List;

import javax.swing.JPanel;

import ch.tbe.Board;
import ch.tbe.framework.ItemComponent;

public class LegendPanel extends JPanel
{
	private Board board;
	
	public LegendPanel(Board board)
	{
		this.board = board;
		showLegend();
	}
	
	public void showLegend()
	{
		List<ItemComponent> items = board.getItems();
		for(ItemComponent i : items)
		{
			System.out.println(i.getClass());
		}
		// TODO: Aktualisierung?!?
	}
	
	public void reload()
	{
		
	}
}
