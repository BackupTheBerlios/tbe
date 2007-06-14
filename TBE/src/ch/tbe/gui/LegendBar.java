package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import ch.tbe.Board;
import ch.tbe.ShapeItem;
import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.ItemComponent;

@SuppressWarnings("serial")
public class LegendBar extends JToolBar
{
	private Board board;
	
	public LegendBar(Board board)
	{
		this.board = board;
		this.setLayout(new BorderLayout());
		this.setFloatable(false);
		showLegend();
	}
	
	private void showLegend()
	{
		this.add(getLegend());
	}
	
	public JPanel getLegend(){
		JPanel legendPanel = new JPanel(new BorderLayout());
		JPanel shapeGridPanel = new JPanel(new GridLayout(0,4));
		JPanel arrowGridPanel = new JPanel(new GridLayout(0,4));
		String actType;
		Icon actIcon;
		this.removeAll();
		this.add(new JLabel("Legende:"), BorderLayout.NORTH);
		ItemComponent[] items = board.getItems();
		if(items.length != 0)
		{
			for(ItemComponent item : items)
			{
				if(item != null)
				{				
					actType = item.getType();
					if(item instanceof ShapeItem)
					{
						actIcon = ((ShapeItem)item).getIcon();
						JPanel sPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
						sPanel.add(new JLabel(actIcon));
						sPanel.add(new JLabel(actType));
						shapeGridPanel.add(sPanel);
					}
					
					for(int i=0; i<items.length; i++)
						if (items[i] != null)
							if (items[i].getType() == actType)
								items[i] = null;
				}//IF
			}//FOR
			
			items = board.getItems();
			for(ItemComponent item : items)
			{
				if(item != null)
				{
					if(item instanceof ArrowItem)
					{
						actType = ((ArrowItem)item).getItemType().getDescription();
						actIcon = ((ArrowItem)item).getIcon();
						JPanel aPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
						aPanel.add(new JLabel(actIcon));
						aPanel.add(new JLabel(actType));
						arrowGridPanel.add(aPanel);
					}
					
					actType = item.getType();
					for(int i=0; i<items.length; i++)
						if (items[i] != null)
							if (items[i].getType() == actType)
								items[i] = null;
				}//IF
			}//FOR
		}
		
		legendPanel.add(shapeGridPanel, BorderLayout.NORTH);
		legendPanel.add(arrowGridPanel, BorderLayout.SOUTH);

		return legendPanel;
	}
	
	public void refresh()
	{
		showLegend();
		this.setVisible(false);
		this.setVisible(true);
	}
}
