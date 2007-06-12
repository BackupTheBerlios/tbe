package ch.tbe.gui;

import ch.tbe.*;
import ch.tbe.command.CutCommand;
import ch.tbe.command.DeleteCommand;
import ch.tbe.command.MoveCommand;
import ch.tbe.command.PasteCommand;
import ch.tbe.framework.*;
import ch.tbe.item.ShapeItem;
import ch.tbe.jgraph.*;
import ch.tbe.tool.CursorTool;
import ch.tbe.tool.ShapeTool;
import ch.tbe.tool.TextBoxTool;
import ch.tbe.util.*;
import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jgraph.graph.*;
import ch.tbe.Sport;

public class WorkingView extends View
{
	private TBE tbe = TBE.getInstance();
	private Board board;
	private Sport sport;
	private Tool cursorTool;
	private Tool currentTool;
	private JButton currentButton, cursorButton, add, rem, rotate;
	private JToolBar toolbar = new JToolBar();
	private JToolBar sideBar, rotatePanel;
	private List<JButton> toolButtons = new ArrayList<JButton>();
	private LegendBar legendBar;
	private MouseListener[] listeners = new MouseListener[2];
	private JPanel rightPanel = new JPanel();
	private ResourceBundle workingViewLabels;
	private JSlider rotateSlider;
	private JTextField sliderValue = new JTextField();
	private boolean showRotate = false;

	public WorkingView(Sport sport)
	{
		this.sport = sport;
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,
				new TBECellViewFactory());
		this.board = new Board(model, view, sport);
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
		workingViewLabels = getResourceBundle(tbe.getLang());
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);

		// Toolbar
		this.add(toolbar, BorderLayout.NORTH);

		// Attributebar
		sideBar = new SideBar(board);
		this.add(sideBar, BorderLayout.WEST);

		// gemeinsames Panel für Board und Legend
		rightPanel.setLayout(new BorderLayout());

		// Board
		this.board.setBackgroundImage((ImageIcon) sport.getFields().get(0).getIcon());
		
		rightPanel.add(new JScrollPane(board), BorderLayout.CENTER);
		class ViewMouseListener extends MouseAdapter
		{
			public void mousePressed(MouseEvent e)
			{
				if (e.getButton() == 3 && !(currentTool instanceof CursorTool))
				{
					setTool(cursorTool, cursorButton);
				}
				else
				{
					Point p = new Point(e.getX(), e.getY());
					WorkingView.this.getTool().mouseDown(p.x, p.y, e);
				}
				if (currentTool instanceof ArrowTool || currentTool instanceof TextBoxTool)
				{
					setTool(cursorTool, cursorButton);
				}

			}
			public void mouseReleased(MouseEvent e){

				checkDefaultButtonVisibility();
			}

		}
		initDefaultTools();

		initSportTools();

		listeners[0] = board.getMouseListeners()[0];
		listeners[1] = new ViewMouseListener();
		board.addMouseListener(listeners[1]);

		// Legend
		// TODO
		legendBar = new LegendBar(board);
		rightPanel.add(legendBar, BorderLayout.SOUTH);

		this.add(rightPanel, BorderLayout.CENTER);
		this.activatePoints(false);

		tbe.getMenu().setVisibleToolbar(!this.toolbar.isVisible());
		tbe.getMenu().setVisibleLegend(!this.legendBar.isVisible());
		tbe.getMenu().setVisibleSidebar(!this.sideBar.isVisible());

	}

	private void initSportTools()
	{
		for (ShapeTool s : ToolFactory.getShapeTools(sport))
		{
			this.installToolInToolBar(toolbar, s);
		}
		toolbar.addSeparator();
		for (ArrowTool a : ToolFactory.getArrowTools(sport))
		{
			this.installToolInToolBar(toolbar, a);
		}
	}

	private void initDefaultTools()
	{
		cursorTool = currentTool = ToolFactory.getCursorTool();
		this.installToolInToolBar(toolbar, currentTool);
		toolbar.addSeparator();
		installAddRemovePointButtons();
		cursorButton = currentButton = (JButton) toolbar.getComponent(0);

		toolbar.addSeparator();
		this.installToolInToolBar(toolbar, ToolFactory.getTextBoxTool());
		toolbar.addSeparator();

		this.installRotateButton();
		toolbar.addSeparator();
	}

	public void activatePoints(boolean b)
	{
		tbe.getMenu().activatePoints(b);
		rem.setEnabled(b);
		add.setEnabled(b);
	}

	public void checkDefaultButtonVisibility(){
		if (board.getSelectionCount() == 1
				&& board.getSelectionCell() instanceof ArrowItem)
		{
			this.activatePoints(true);
		}
		else
		{
			this.activatePoints(false);
		}
		if (board.getSelectionCount() == 1
				&& board.getSelectionCell() instanceof ShapeItem)
		{
			this.activateRotation(true);
		}
		else
		{
			this.activateRotation(false);
		}
	}
	
	public void activateRotation(boolean b)
	{
		// TODO: tbe.getMenu().activatePoints(b);
		if (showRotate && b)
		{
			rotatePanel.setVisible(b);
			rotateSlider.setValue(((ShapeItem) board.getSelectedItems()[0])
					.getRotation());

		}
		else
		{
			rotatePanel.setVisible(false);
		}
		rotate.setEnabled(b);
	}

	private void installAddRemovePointButtons()
	{
		URL imgURL = WorkingView.class.getResource("../pics/plus.gif");
		ImageIcon plus = new ImageIcon(imgURL);
		imgURL = WorkingView.class.getResource("../pics/minus.gif");
		ImageIcon minus = new ImageIcon(imgURL);
		add = new JButton(plus);
		rem = new JButton(minus);
		add.setToolTipText(workingViewLabels.getString("plus"));
		rem.setToolTipText(workingViewLabels.getString("minus"));
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WorkingView.this.addRemovePoint(true);
			}
		});
		rem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WorkingView.this.addRemovePoint(false);
			}
		});

		add.setContentAreaFilled(false);
		add.setBorderPainted(false);
		rem.setContentAreaFilled(false);
		rem.setBorderPainted(false);
		toolbar.add(add);
		toolbar.add(rem);
	}

	private void installRotateButton()
	{
		// URL imgURL = WorkingView.class.getResource("../pics/rotate.gif");
		// ImageIcon plus = new ImageIcon(imgURL);

		rotate = new JButton("Rotate");
		rotate.setEnabled(false);
		rotatePanel = new JToolBar();
		rotatePanel.setLayout(new BorderLayout(0, 1));
		rotateSlider = new JSlider();
		rotateSlider.setMaximum(359);
		rotateSlider.setMinimum(0);
		rotateSlider.setMaximumSize(new Dimension(100, 100));
		rotateSlider.setOrientation(1);
		Box box = Box.createVerticalBox();

		sliderValue.setPreferredSize(new Dimension(30, 20));

		rotateSlider.setAlignmentY(Component.TOP_ALIGNMENT);
		box.add(sliderValue);
		box.add(rotateSlider);
		sliderValue.setAlignmentY(Component.TOP_ALIGNMENT);
		rotatePanel.add(box, BorderLayout.NORTH);

		sliderValue.addFocusListener(new FocusListener()
		{

			private int oldValue = 0;

			public void focusGained(FocusEvent arg0)
			{
				oldValue = Integer.parseInt(sliderValue.getText());

			}

			public void focusLost(FocusEvent arg0)
			{
				int newValue = 0;
				try
				{
					newValue = Integer.parseInt(sliderValue.getText());
				}
				catch (Exception ex)
				{
					sliderValue.setText(Integer.toString(oldValue));
				}
				if (newValue >= 0 && newValue <= 359)
				{
					rotateSlider.setValue(newValue);
				}
				else
				{
					sliderValue.setText(Integer.toString(oldValue));
				}
			}

		});

		rotateSlider.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent arg0)
			{

				if (board.getSelectionCount() == 1
						&& board.getSelectionCells()[0] instanceof ShapeItem)
				{
					sliderValue.setText(Integer.toString(rotateSlider
							.getValue()));
					ShapeItem s = (ShapeItem) board.getSelectionCells()[0];
					board.removeItem(new ItemComponent[] { s });
					s.setRotation(rotateSlider.getValue());
					board.addItem(s);

				}

			}

		});

		rotate.setToolTipText(workingViewLabels.getString("rotate"));

		rotate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (board.getSelectionCount() == 1
						&& board.getSelectedItems()[0] instanceof ShapeItem)
				{
					rotateSlider
							.setValue(((ShapeItem) board.getSelectedItems()[0])
									.getRotation());

				}
				rotatePanel.setVisible(!rotatePanel.isVisible());
				showRotate = !showRotate;
			}
		});

		rotate.setContentAreaFilled(false);
		rotate.setBorderPainted(false);
		toolbar.add(rotate);
		rotatePanel.setVisible(false);
		this.add(rotatePanel, BorderLayout.EAST);

	}

	public void addRemovePoint(boolean b)
	{
		if (board.getSelectionCount() == 1
				&& board.getSelectionCell() instanceof ArrowItem)
		{
			MoveCommand mc = new MoveCommand(board.getSelectedItems());
			ArrowItem a = (ArrowItem) board.getSelectionCell();
			if (b)
			{
				a.addPoint();
			}
			else
			{
				a.removePoint();
			}
			WorkingView.this.refresh();
			board.setSelectionCell(a);
			setTool(cursorTool, cursorButton);
			board.addItem(a);
			mc.setMoveEnd(board.getSelectedItems());
			ArrayList<Command> actCommands = new ArrayList<Command>();
			actCommands.add(mc);
			tbe.addCommands(actCommands);
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

		ItemComponent[] items = board.getItems();
		DeleteCommand del = new DeleteCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(del);
		tbe.addCommands(actCommands);
		board.removeItem(items);
	}

	public void cut()
	{

		ItemComponent[] items = board.getSelectedItems();
		CutCommand cut = new CutCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(cut);
		tbe.addCommands(actCommands);
		ComponentSelection contents = new ComponentSelection(this.getBoard()
				.cloneItems(items));
		tbe.getClipboard().setContents(contents, cut);
		board.removeItem(items);
	}

	public void copy()
	{
		ItemComponent[] items = board.getSelectedItems();
		CutCommand cut = new CutCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(cut);
		tbe.addCommands(actCommands);
		ComponentSelection contents = new ComponentSelection(this.getBoard()
				.cloneItems(items));
		tbe.getClipboard().setContents(contents, cut);

	}

	public void paste()
	{

		ItemComponent[] items = board.getSelectedItems();
		PasteCommand del = new PasteCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(del);
		tbe.addCommands(actCommands);

		ComponentSelection clipboardContent = (ComponentSelection) tbe
				.getClipboard().getContents(this);

		if ((clipboardContent != null)
				&& (clipboardContent
						.isDataFlavorSupported(ComponentSelection.itemFlavor)))
		{

			Object[] tempItems = null;
			try
			{
				tempItems = clipboardContent
						.getTransferData(ComponentSelection.itemFlavor);
			}
			catch (UnsupportedFlavorException e1)
			{

				e1.printStackTrace();
			}

			board.addItem(this.getBoard().cloneItems(tempItems));

		}
	}

	public void selectAllItems()
	{
		board.setSelectionCells(board.getItems());
	}

	public void delete()
	{

		ItemComponent[] items = board.getSelectedItems();
		DeleteCommand del = new DeleteCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(del);
		tbe.addCommands(actCommands);
		board.removeItem(items);
	}

	public void setBoard(Board board)
	{
		this.board = board;
	}

	public void installToolInToolBar(JToolBar toolbar, final Tool tool)
	{
		final JButton button;
		button = new JButton();

		button.setMargin(new Insets(0, 0, 0, 0));

		if (tool.getShapeType() != null)
		{
			button.setIcon(tool.getShapeType().getIcon());
			button.setToolTipText(tool.getShapeType().getDescription());

		}
		else
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
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				((JButton) e.getSource()).setBorderPainted(true);
			}

			public void mouseExited(MouseEvent e)
			{
				((JButton) e.getSource()).setBorderPainted(false);
			}
		});

	}

	public void setTool(Tool tool, JButton button)
	{
		// IF NO CURSORTOOL
		if (this.currentTool instanceof CursorTool
				&& !(tool instanceof CursorTool))
		{
			board.removeMouseListener(listeners[0]);

			// IF CURSORTOOL
		}
		else if (tool instanceof CursorTool
				&& !(this.currentTool instanceof CursorTool))
		{

			board.addMouseListener(listeners[0]);
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
		if (tool instanceof CursorTool || tool instanceof ArrowTool
				|| tool instanceof TextBoxTool)
		{
			board.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else
		{
			// TODO: Grosse Bilder verkleinern...
			int x = 16 - tool.getShapeType().getPicture().getIconWidth() / 2;
			int y = 16 - tool.getShapeType().getPicture().getIconHeight() / 2;
			
			if (x < 0) x =0;
			if (y < 0) y =0;
			Cursor c = getToolkit().createCustomCursor(CursorImage.getMergedImage((Image)((ImageIcon) tool.getShapeType().getPicture()).getImage()), new Point(x,y), "Cursor");

			board.setCursor(c);
		}
	}

	public void closeOrNew()
	{
		// TODO Save???

		tbe.setView(new WelcomeView(tbe.getSports(), tbe.getLang()));
	}

	public Tool getTool()
	{
		return this.currentTool;
	}

	public void hideSidebar()
	{
		sideBar.setVisible(!this.sideBar.isVisible());
		tbe.getMenu().setVisibleSidebar(!this.sideBar.isVisible());
	}

	public void hideLegend()
	{
		legendBar.setVisible(!this.legendBar.isVisible());
		tbe.getMenu().setVisibleLegend(!this.legendBar.isVisible());
	}

	public void hideToolbar()
	{
		toolbar.setVisible(!this.toolbar.isVisible());
		tbe.getMenu().setVisibleToolbar(!this.toolbar.isVisible());
	}

	private ResourceBundle getResourceBundle(String lang)
	{
		InputStream workingViewStream;
		ResourceBundle labels = null;
		try
		{
			workingViewStream = WorkingView.class
					.getResourceAsStream("../config/lang/" + lang
							+ "/workingView.txt");
			labels = new PropertyResourceBundle(workingViewStream);
		}
		catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for WorkingView not found !");
		}
		catch (IOException ioe)
		{
			System.out.println("Error with ResourceBundle WorkingView!");
		}
		return labels;
	}

	@Override
	public void refresh()
	{
		legendBar.refresh();
		
	}

}