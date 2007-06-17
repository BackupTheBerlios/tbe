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

public class PrintView extends View {
    private final Board board;

    private TBE tbe = TBE.getInstance();

    public PrintView(Board board) {

	this.board = board;
	this.setBackground(Color.white);
	this.setLayout(new BorderLayout(20, 20));
	this.createView();
    }

    private void createView() {
	// headerPanel
	JPanel headerPanel = new JPanel();
	headerPanel.setBackground(Color.white);
	headerPanel.setLayout(new BorderLayout());

	// Box with Title, Name, Prename and email
	Box header = Box.createVerticalBox();
	JLabel title = new JLabel(board.getSport().getName());  // TODO: JLabel title = new JLabel(board.getDescription().getDescription());
	Font f = new Font(title.getFont().getFontName(), Font.BOLD, 40);
	title.setFont(f);
	title.setAlignmentY(Component.TOP_ALIGNMENT);
	title.setAlignmentX(Component.LEFT_ALIGNMENT);
	JLabel autor = new JLabel(" " + tbe.getUserPrename() + " "
		+ tbe.getUserName() + " (" + tbe.getUserEmail() + ")");
	autor.setAlignmentY(Component.TOP_ALIGNMENT);
	autor.setAlignmentX(Component.LEFT_ALIGNMENT);
	JLabel path = new JLabel(" " + board.getPath());
	path.setAlignmentY(Component.TOP_ALIGNMENT);
	path.setAlignmentX(Component.LEFT_ALIGNMENT);

	// Logo Icon
	URL imgURL = PrintView.class.getResource("../pics/logo.jpg"); // TODO smaller Logo
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
	for (MouseListener ml : temp.getMouseListeners()) {
	    temp.removeMouseListener(ml);
	}
	for (MouseMotionListener ml : temp.getMouseMotionListeners()) {
	    temp.removeMouseMotionListener(ml);
	}
	temp.clearSelection();
	temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));

	// Add Legende
	JPanel lb = new LegendBar(temp).getLegend();
	lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));

	// Add Attributes
	List<Attribute> attributes = board.getDescription().getAttributes();
	ArrayList<Box> attribTitle = new ArrayList<Box>();
	ArrayList<Box> attribText = new ArrayList<Box>();

	for (Attribute a : attributes) {
	    Box tempBox = Box.createVerticalBox();
	    JTextArea aTitle = new JTextArea(a.getTitle());
	    aTitle.setFont(new Font(aTitle.getFont().getFontName(), Font.BOLD,
		    aTitle.getFont().getSize()));
	    aTitle.setLineWrap(true);
	    tempBox.add(aTitle);
	    attribTitle.add(tempBox);
	    tempBox = Box.createVerticalBox();
	    JTextArea aText = new JTextArea(a.getText());
	    aText.setLineWrap(true);
	    tempBox.add(aText);
	    attribText.add(tempBox);
	}

	Box attributesPanel = Box.createHorizontalBox();
	attributesPanel.setBackground(Color.WHITE);
	attributesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	attributesPanel.add(Box.createHorizontalStrut(5));
	Box internPanel = Box.createVerticalBox();
	internPanel.setBackground(Color.white);

	for (int i = 0; i < attributes.size(); i++) {
	    JPanel attribPanel = new JPanel();
	    int k = i;
	    for (int j = 0; j != 4 && i < attribTitle.size(); j++) {

		attribPanel.setBackground(Color.white);
		attribPanel.setLayout(new GridLayout(0, 4, 10, 20));
		attribPanel.add(attribTitle.get(i));
		i++;

	    }
	    internPanel.add(attribPanel);
	    internPanel.add(Box.createVerticalStrut(10));
	    JPanel attribPanel1 = new JPanel();
	    i = k;

	    for (int j = 0; j != 4 && i < attribText.size(); j++) {

		attribPanel1.setBackground(Color.white);
		attribPanel1.setLayout(new GridLayout(0, 4, 10, 20));
		attribPanel1.add(attribText.get(i));
		i++;
	    }
	    internPanel.add(attribPanel1);
	    internPanel.add(Box.createVerticalStrut(20));
	}
	attributesPanel.add(internPanel);
	center.add(temp, BorderLayout.NORTH);
	center.add(lb, BorderLayout.CENTER);
	center.add(attributesPanel, BorderLayout.SOUTH);

	this.add(center, BorderLayout.CENTER);
    }

    @Override
    public void refresh() {
	// Nothing

    }

}
