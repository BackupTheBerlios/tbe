package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ch.tbe.Board;
import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.ItemComponent;
import ch.tbe.item.ShapeItem;

/**
 * Tactic Board Editor
 * **********************
 * LegendBar 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

@SuppressWarnings("serial")
public class LegendBar extends JToolBar {
	private Board board;

	public LegendBar(Board board) {
		this.board = board;
		this.setLayout(new BorderLayout());
		this.setFloatable(false);
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		showLegend();
	}

	private void showLegend() {

		this.add(getLegend());
	}

	public JPanel getLegend() {
		JPanel legendPanel = new JPanel(new BorderLayout());
		GridLayout gridLayout = new GridLayout(0, 4);
		JPanel shapeGridPanel = new JPanel(gridLayout);
		shapeGridPanel.setBackground(Color.white);
		JPanel arrowGridPanel = new JPanel(gridLayout);
		arrowGridPanel.setBackground(Color.white);
		String actType;
		Icon actIcon;
		this.removeAll();

		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		header.setBackground(Color.white);
		header.add(new JLabel("Legende:"));
		this.add(header, BorderLayout.NORTH);

		ItemComponent[] items = sortLegendByDescription(board.getItems());
		if (items.length != 0) {
			for (ItemComponent item : items) {
				if (item != null) {
					actType = item.getType();
					if (item instanceof ShapeItem) {
						actIcon = ((ShapeItem) item).getIcon();
						JPanel sPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
						sPanel.add(new JLabel(actIcon));
						sPanel.add(new JLabel(actType));
						sPanel.setBackground(Color.white);
						shapeGridPanel.add(sPanel);
					}

					for (int i = 0; i < items.length; i++)
						if (items[i] != null)
							if (items[i].getType() == actType)
								items[i] = null;
				}// IF
			}// FOR

			items = sortLegendByDescription(board.getItems());
			for (ItemComponent item : items) {
				if (item != null) {
					if (item instanceof ArrowItem) {
						actType = ((ArrowItem) item).getItemType().getDescription();
						actIcon = ((ArrowItem) item).getIcon();
						JPanel aPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
						aPanel.add(new JLabel(actIcon));
						aPanel.add(new JLabel(actType));
						aPanel.setBackground(Color.white);
						arrowGridPanel.add(aPanel);
					}

					actType = item.getType();
					for (int i = 0; i < items.length; i++)
						if (items[i] != null)
							if (items[i].getType() == actType)
								items[i] = null;
				}// IF
			}// FOR
		}

		legendPanel.add(shapeGridPanel, BorderLayout.NORTH);
		legendPanel.add(arrowGridPanel, BorderLayout.SOUTH);

		return legendPanel;
	}
	
	private ItemComponent[] sortLegendByDescription(ItemComponent[] items){
		ItemComponent[] sortedArray = new ItemComponent[items.length];
		ArrayList<String> desc = new ArrayList<String>();
		
		for (ItemComponent item: items){
			if (item instanceof ArrowItem){
				desc.add(((ArrowItem) item).getItemType().getDescription());
			}else if (item instanceof ShapeItem){
				desc.add(item.getType());
			}
		}
		Collections.sort(desc);
		
		for (int i=0; i< desc.size();i++){
			for (ItemComponent item: items){
				if (item instanceof ArrowItem){
					if (((ArrowItem) item).getItemType().getDescription().equals(desc.get(i))){
						sortedArray[i] = item;
					}
				}else if (item instanceof ShapeItem){
					if (item.getType().equals(desc.get(i))){
						sortedArray[i] = item;
					}
				}
			}
		}
		
		return sortedArray;
	}

	public void refresh() {
		showLegend();
		this.setVisible(false);
		this.setVisible(true);
	}
}
