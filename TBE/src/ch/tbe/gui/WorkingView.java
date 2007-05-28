package ch.tbe.gui;

import ch.tbe.Attribute;
import ch.tbe.BezierSolidArrowTool;
import ch.tbe.CursorTool;
import ch.tbe.Invoker;
import ch.tbe.Board;
import ch.tbe.ShapeTool;
import ch.tbe.ShapeType;

import ch.tbe.ToolFactory;
import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.ArrowTool;
import ch.tbe.framework.Tool;
import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.View;
import ch.tbe.jgraph.JGraph;
import ch.tbe.jgraph.event.GraphSelectionListener;
import ch.tbe.jgraph.graph.DefaultCellViewFactory;
import ch.tbe.jgraph.graph.DefaultGraphCell;
import ch.tbe.jgraph.graph.DefaultGraphModel;
import ch.tbe.jgraph.graph.GraphLayoutCache;
import ch.tbe.jgraph.graph.GraphModel;
import ch.tbe.Field;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import ch.tbe.Sport;

public class WorkingView extends View
{
	private TBE tbe = TBE.getInstance();
	private Invoker invoker;
	private Board board;
	private Sport sport;
	private Tool cursorTool;
	private Tool currentTool;
	private JButton currentButton;
	private JButton cursorButton;
	private ItemComponent currentItem;
	private Attribute currentAttribute;
	private JToolBar toolbar = new JToolBar();
	private List<JButton> toolButtons = new ArrayList<JButton>();
	private LegendPanel legendPanel;
	private JGraph graph;
	private MouseListener[] listeners = new MouseListener[2];

	public WorkingView(Sport sport)
	{
		this.sport = sport;
		this.board = new Board(sport);
		createWorkingView();
	}
	
	public WorkingView(Board board)
	{
		this.sport = board.getSport();
		this.board = board;
		createWorkingView();
	}
	
	private void createWorkingView()
	{
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		
		// Toolbar
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
		graph = new JGraph(model, view);

		rightPanel.add(graph, BorderLayout.CENTER);
		class ViewMouseListener extends MouseAdapter
		{
			public void mousePressed(MouseEvent e)
			{

				Point p = new Point(e.getX(), e.getY());
				((WorkingView) TBE.getInstance().getView()).getTool()
						.mouseDown(p.x, p.y, e);
			}
		}
		cursorTool = currentTool = ToolFactory.getCursorTool();
		this.installToolInToolBar(toolbar, currentTool);
		installAddRemovePointButtons();
		cursorButton = currentButton = (JButton) toolbar.getComponent(0);
		currentButton.setText("Cursor");// TODO only for Debugging
		this.installToolInToolBar(toolbar, new BezierSolidArrowTool(
				new ShapeType("BezierSolidArrow", "", null))); // TODO only for
		// debugging

		for (ShapeTool s : ToolFactory.getShapeTools(sport))
		{
			this.installToolInToolBar(toolbar, s);
		}
		for (ArrowTool a : ToolFactory.getArrowTools(sport))
		{
			this.installToolInToolBar(toolbar, a);
		}

		listeners[0] = graph.getMouseListeners()[0];

		listeners[1] = new ViewMouseListener();
		graph.removeMouseListener(listeners[0]);
		graph.addMouseListener(listeners[1]);

		// Legend
		legendPanel = new LegendPanel(board);
		legendPanel.add(new JLabel("Legend"));
		rightPanel.add(legendPanel, BorderLayout.SOUTH);

		this.add(rightPanel, BorderLayout.CENTER);

		/*
		 * 
		 */
	}

	private void installAddRemovePointButtons()
	{
		JButton add = new JButton("+"); // TODO Item
		JButton rem = new JButton("-");// TODO Item
		add.setToolTipText("Add Point"); // TODO Language
		rem.setToolTipText("Remove Point"); // TODO Language
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WorkingView.this.addRemovePoint(true);
				}
			}
		);
		rem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WorkingView.this.addRemovePoint(false);
			}
		});


		toolbar.add(add);
		toolbar.add(rem);
	}
	
	public void addRemovePoint(boolean b){

		if (graph.getSelectionCount() == 1)
		{
			if (graph.getSelectionCell() instanceof ArrowItem)
			{

				ArrowItem a = (ArrowItem) graph.getSelectionCell();
				if(b){a.addPoint();}
				else{a.removePoint();}
				WorkingView.this.refresh();
				graph.setSelectionCell(a);
				WorkingView.this.currentTool = WorkingView.this.cursorTool;
				setTool(cursorTool, cursorButton);
			}
		}
	}

	public Board getBoard()
	{
		return this.board;
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
		Insets margins = new Insets(0, 0, 0, 0);
		final JButton button;
		button = new JButton();

		if (tool.getShapeType() != null)
		{
			button.setIcon(tool.getShapeType().getIcon());
			button.setToolTipText(tool.getShapeType().getName());
			button.setMargin(margins);
		} else
		{
			button.setText("Tool"); // For Debugging
		}
		toolbar.add(button);
		toolButtons.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				WorkingView.this.setTool(tool, button);

			}

		});
	}

	public void setTool(Tool tool, JButton button)
	{

		if (this.currentTool instanceof CursorTool
				&& !(tool instanceof CursorTool))
		{
			graph.removeMouseListener(graph.getMouseListeners()[0]);
			graph.addMouseListener(listeners[1]);

		} else if (tool instanceof CursorTool
				&& !(this.currentTool instanceof CursorTool))
		{
			graph.removeMouseListener(graph.getMouseListeners()[0]);
			graph.addMouseListener(listeners[0]);
		}
		if (tool == null)
			throw new IllegalArgumentException("Tool must not be null.");

		if (this.currentTool != tool)
		{
			if (this.currentButton != null)
			{
				this.currentButton.setEnabled(true);
			}
			this.currentButton = button;
			// TODO button.setDisabledIcon(arg0);

			this.currentTool = tool;

		}
	}

	public Tool getTool()
	{
		return this.currentTool;
	}

	public void refresh()
	{
		List<ItemComponent> items = board.getItems();
		System.out.println(items.size());

		DefaultGraphCell[] cells = new DefaultGraphCell[items.size()];
		for (int i = 0; i < items.size(); i++)
		{
			cells[i] = (DefaultGraphCell) items.get(i);
		}
		graph.getGraphLayoutCache().remove(
				graph.getGraphLayoutCache().getCells(
						graph.getGraphLayoutCache().getAllViews()));
		graph.getGraphLayoutCache().insert(cells);

		if (items.size() > 0)
		{
			graph.setSelectionCell(items.get(items.size() - 1));

		}

		tbe.getMenu().refreshInvokerVisibility();
		
		this.legendPanel.reload();
	}
}