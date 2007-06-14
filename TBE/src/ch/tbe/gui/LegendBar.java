package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import ch.tbe.Board;
import ch.tbe.ShapeItem;
import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.ItemComponent;

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
		JPanel legendPanel = new JPanel(new GridLayout(0,4));
		legendPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
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
					if(item instanceof ArrowItem)
					{
						actType = ((ArrowItem)item).getItemType().getDescription();
						actIcon = ((ArrowItem)item).getIcon();
						JPanel iPanel = new JPanel();
						iPanel.add(new JLabel(actIcon));
						iPanel.add(new JLabel(actType));
						legendPanel.add(iPanel);
					}
					
					actType = item.getType();
					if(item instanceof ShapeItem)
					{
						actIcon = ((ShapeItem)item).getIcon();
						JPanel iPanel = new JPanel();
						iPanel.add(new JLabel(actIcon));
						iPanel.add(new JLabel(actType));
						legendPanel.add(iPanel);
					}
					
					for(int i=0; i<items.length; i++)
					{
						if (items[i] != null)
						{
							if (items[i].getType() == actType)
							{
								items[i] = null;
							}
						}
					}
				}
			}
		}
		
		//JPanel bigPanel = new JPanel(new BorderLayout());
		//bigPanel.add(shapePanel, BorderLayout.NORTH);
		//bigPanel.add(arrowPanel, BorderLayout.SOUTH);
		//this.add(bigPanel, BorderLayout.SOUTH);
		this.add(legendPanel);

		
	}
	
	public void refresh()
	{
		showLegend();
		this.setVisible(false);
		this.setVisible(true);
	}
}
