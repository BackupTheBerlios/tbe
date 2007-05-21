package ch.tbe.gui;

import ch.tbe.Invoker;
import ch.tbe.Board;
import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.Tool;
import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.View;
import ch.tbe.jgraph.JGraph;
import ch.tbe.jgraph.graph.DefaultCellViewFactory;
import ch.tbe.jgraph.graph.DefaultEdge;
import ch.tbe.jgraph.graph.DefaultGraphCell;
import ch.tbe.jgraph.graph.DefaultGraphModel;
import ch.tbe.jgraph.graph.GraphLayoutCache;
import ch.tbe.jgraph.graph.GraphModel;
import ch.tbe.Attribute;
import ch.tbe.Field;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.tbe.Sport;

public class WorkingView extends JPanel implements View
{
	private TBE tbe;
	private Invoker invoker;
	private Board board;
	private Tool currentTool;
	private ItemComponent currentItem;
	private Attribute currentAttribute;

	public WorkingView(Board board)
	{
	}

	public WorkingView(TBE tbe, Sport sport)
	{	
		this.tbe = tbe;
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model, new DefaultCellViewFactory());
		JGraph graph = new JGraph(model, view);
		
		// TODO this is only for testing (by David Meier)
		
		this.board = new Board(new Field("", ""));
		List<ItemComponent> items = board.getItems();
		
		DefaultGraphCell[] cells = new DefaultGraphCell[items.size()];
		for (int i = 0; i < items.size(); i++)
		{
			cells[i] = items.get(i);
		}
		
		graph.getGraphLayoutCache().insert(cells);
		this.add(new JScrollPane(graph));

		// END
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

}
