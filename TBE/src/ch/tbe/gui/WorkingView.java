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
import ch.tbe.util.ToolButton;
import ch.tbe.Attribute;
import ch.tbe.Field;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import ch.tbe.Sport;

public class WorkingView extends View
{
	private TBE tbe = TBE.getInstance();
	private Invoker invoker;
	private Board board;
	private Tool currentTool;
	private ItemComponent currentItem;
	private Attribute currentAttribute;
	private JToolBar toolbar = new JToolBar();
	private List<ToolButton> toolButtons = new ArrayList<ToolButton>();

	public WorkingView(Board board)
	{

	}

	public WorkingView(Sport sport)
	{
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);

		// Toolbar
		toolbar.add(new JButton("Tool 1"));
		this.add(toolbar, BorderLayout.NORTH);

		// Attributebar
		JToolBar sideBar = new SideBar(board);
		this.add(sideBar, BorderLayout.WEST);

		// gemeinsames Panel für Board und Legend
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		// Board
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,
				new DefaultCellViewFactory());
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
		rightPanel.add(new JScrollPane(graph), BorderLayout.CENTER);

		// END

		// Legend
		JPanel legendPanel = new JPanel();
		legendPanel.add(new JLabel("Legend"));
		rightPanel.add(legendPanel, BorderLayout.SOUTH);

		this.add(rightPanel, BorderLayout.CENTER);

		/*
		 * 
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

	public void installToolInToolBar(JToolBar toolbar, final Tool tool)
	{
		ToolButton button;
		button = new ToolButton(tool);
		toolbar.add(button);
		toolButtons.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WorkingView.this.setTool(tool);
			}

		});
	}

	public void setTool(Tool tool)
	{
		if (tool == null)
			throw new IllegalArgumentException("Tool must not be null.");

		if (this.currentTool != tool)
		{
			if (this.currentTool != null)
				this.currentTool.deactivate();
			this.currentTool = tool;
			this.currentTool.activate();

			repaint();
		}
	}

	public Tool getTool()
	{
		return this.currentTool;
	}

}
