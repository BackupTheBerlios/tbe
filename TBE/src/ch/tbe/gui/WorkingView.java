package ch.tbe.gui;

import ch.tbe.Invoker;
import ch.tbe.Board;
import ch.tbe.framework.Tool;
import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.View;
import ch.tbe.jgraph.JGraph;
import ch.tbe.jgraph.graph.DefaultCellViewFactory;
import ch.tbe.jgraph.graph.DefaultGraphCell;
import ch.tbe.jgraph.graph.DefaultGraphModel;
import ch.tbe.jgraph.graph.GraphLayoutCache;
import ch.tbe.jgraph.graph.GraphModel;
import ch.tbe.Attribute;
import ch.tbe.Field;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.tbe.Sport;

public class WorkingView extends View
{
	private TBE tbe = TBE.getInstance();
	private Invoker invoker;
	private Board board;
	private Tool currentTool;
	private ItemComponent currentItem;
	private Attribute currentAttribute;

	public WorkingView(Board board)
	{
			
	}

	public WorkingView(Sport sport)
	{
		GridBagLayout globalGridbag = new GridBagLayout();
		GridBagConstraints globalConstraints = new GridBagConstraints();
		globalConstraints.gridheight = 1;
		globalConstraints.gridwidth = 1;
		globalConstraints.anchor = GridBagConstraints.NORTHWEST;
		globalConstraints.fill = GridBagConstraints.BOTH;
		
		this.setLayout(globalGridbag);
		
		JPanel working = new JPanel();
		
		working.setBackground(Color.WHITE);
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		working.setLayout(gridbag);
		
		this.add(working, constraints);
		
		// Toolbar
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		JPanel toolbarPanel = new JPanel();
		toolbarPanel.setBackground(Color.BLACK);
		toolbarPanel.add(new JLabel("Toolbar !"));
		
		working.add(toolbarPanel, constraints);
		
		// Attributebar
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridheight = 2;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		JPanel attributePanel = new JPanel();
		attributePanel.setBackground(Color.BLUE);
		attributePanel.add(new JLabel("AttributeBar !"));
		
		working.add(attributePanel, constraints);
		
		// Board
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		JPanel boardPanel = new JPanel();
		boardPanel.setBackground(Color.RED);
		boardPanel.add(new JLabel("Board !"));
		
		working.add(boardPanel, constraints);
		
		// Legend
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		JPanel legendPanel = new JPanel();
		legendPanel.setBackground(Color.CYAN);
		legendPanel.add(new JLabel("Legend !"));
		
		working.add(legendPanel, constraints);
		
		/*
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model, new DefaultCellViewFactory());
		JGraph graph = new JGraph(model, view);
		
		// TODO this is only for testing (by David Meier)
		
		this.board = new Board(new Field("", ""));
		List<ItemComponent> items = board.getItems();
		
		DefaultGraphCell[] cells = new DefaultGraphCell[items.size()];
		for (int i = 0; i < items.size(); i++)
		{
			cells[i] = (DefaultGraphCell) items.get(i);
		}
		
		graph.getGraphLayoutCache().insert(cells);
		this.add(new JScrollPane(graph));

		// END
		*/
	}

	public Board getBoard()
	{
		return null;
	}

	public void changeField(Field field)
	{
	}

	public void clear()
	{
	}

	public void select(ItemComponent item)
	{
	}

	public void resize(ArrayList points)
	{
	}

	public void cut()
	{
	}

	public void paste()
	{
	}

	public void undo()
	{
	}

	public void redo()
	{
	}

	public void selectAttribute(Attribute attribute)
	{
	}

	public void saveAttribute(String title, String text)
	{
	}

	public void setText(String text)
	{
	}

	public void move(int xdiff, int ydiff)
	{
	}

	public void delete()
	{
	}

	public void setCurrentTool(Tool tool)
	{
	}

	public void draw(int x, int y)
	{
	}

	public void addAttribute(String title, String text)
	{
	}

	public void removeAttribute(Attribute attribute)
	{
	}

	public void setBoard(Board board)
	{
		this.board = board;
	}

}
