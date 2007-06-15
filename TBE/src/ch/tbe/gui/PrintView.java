package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import ch.tbe.Attribute;
import ch.tbe.Board;
import ch.tbe.framework.View;
import ch.tbe.jgraph.TBECellViewFactory;

public class PrintView extends View
{
	private final Board board;
	private TBE tbe = TBE.getInstance();

	public PrintView(Board board)
	{

		this.board = board;
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout(20, 20));
		this.createView();
	}

	private void createView()
	{
		// headerPanel
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.white);
		headerPanel.setLayout(new BorderLayout());

		// Box with Title, Name, Prename and email
		Box header = Box.createVerticalBox();
		JLabel title = new JLabel(board.getSport().getName());
		Font f = new Font(title.getFont().getFontName(), Font.BOLD, 40);
		title.setFont(f);
		title.setAlignmentY(Component.TOP_ALIGNMENT);
		title.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel autor = new JLabel(tbe.getUserPrename() + " "
				+ tbe.getUserName() + " (" + tbe.getUserEmail() + ")");
		autor.setAlignmentY(Component.TOP_ALIGNMENT);
		autor.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel path = new JLabel(board.getPath());
		path.setAlignmentY(Component.TOP_ALIGNMENT);
		path.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Logo Icon
		URL imgURL = PrintView.class.getResource("../pics/logo.jpg"); // TODO
																		// smaller
																		// Icon
		ImageIcon logoIcon = new ImageIcon(imgURL);
		JLabel logo = new JLabel(logoIcon);
		logo.setAlignmentX(Component.RIGHT_ALIGNMENT);

		header.add(title);
		header.add(autor);
		header.add(path);
		header.add(logo);
		headerPanel.add(header, BorderLayout.WEST);
		headerPanel.add(logo, BorderLayout.EAST);

		this.add(headerPanel, BorderLayout.NORTH);
		// END headerPanel

		// center-panel
		JPanel center = new JPanel();
		center.setBackground(Color.WHITE);
		center.setLayout(new BorderLayout(20, 20));

		// Create a clone of the Board
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,
				new TBECellViewFactory());
		Board temp = new Board(model, view, board.getSport());
		temp.getGraphLayoutCache().insert(
				board.cloneItems(board.getGraphLayoutCache().getCells(true,
						true, true, true)));
		temp.setBackgroundImage(board.getBackgroundImage());
		temp.setBackground(Color.WHITE);		
		for (MouseListener ml : temp.getMouseListeners()){
			temp.removeMouseListener(ml);
		}
		for (MouseMotionListener ml : temp.getMouseMotionListeners()){
			temp.removeMouseMotionListener(ml);
		}
		temp.clearSelection();

		// Add Legende
		JPanel lb = new LegendBar(temp).getLegend();
		lb.setBorder(BorderFactory.createLineBorder(Color.black));

		// Add Attributes
		ArrayList<Box> attrib = new ArrayList<Box>();
		List<Attribute> attributes = board.getDescription().getAttributes();
		for (Attribute a : attributes)
		{
			Box tempB = Box.createVerticalBox();
			JTextArea aTitle = new JTextArea(a.getTitle());
			aTitle.setFont(new Font(aTitle.getFont().getFontName(), Font.BOLD,
					aTitle.getFont().getSize()));
			aTitle.setLineWrap(true);
			aTitle.setAlignmentY(Component.LEFT_ALIGNMENT);
			aTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
			JTextArea aText = new JTextArea(a.getText());
			aText.setLineWrap(true);
			aText.setAlignmentY(Component.LEFT_ALIGNMENT);
			aText.setAlignmentX(Component.LEFT_ALIGNMENT);
			tempB.add(aTitle);
			tempB.add(Box.createVerticalStrut(10));
			tempB.add(aText);
			attrib.add(tempB);
		}

		JPanel attribPanel = new JPanel();
		attribPanel.setBackground(Color.white);

		for (Box b : attrib)
		{
			attribPanel.add(b);
		}
		attribPanel.setLayout(new GridLayout(0, 4, 10, 20));

		center.add(temp, BorderLayout.NORTH);
		center.add(lb, BorderLayout.CENTER);
		center.add(attribPanel, BorderLayout.SOUTH);

		this.add(center, BorderLayout.CENTER);
	}

	@Override
	public void refresh()
	{
		// Nothing

	}

}
