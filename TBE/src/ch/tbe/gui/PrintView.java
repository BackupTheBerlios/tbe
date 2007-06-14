package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import ch.tbe.Board;
import ch.tbe.framework.View;
import ch.tbe.jgraph.TBECellViewFactory;

public class PrintView extends View
{
	private final Board board;
	private TBE tbe = TBE.getInstance();

	public PrintView(Board board){
		
		this.board = board;
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		this.createView();
	}
	
	
	
	
	
	private void createView()
	{
		//header
		Box header = Box.createVerticalBox();
		JLabel title = new JLabel(board.getSport().getName());
		title.setAlignmentY(Component.LEFT_ALIGNMENT);
		  title.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel autor = new JLabel(tbe.getUserPrename()+" "+tbe.getUserName()+" "+tbe.getUserEmail());
		autor.setAlignmentY(Component.LEFT_ALIGNMENT);
		  autor.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel path = new JLabel(board.getPath());
		path.setAlignmentY(Component.LEFT_ALIGNMENT);
		  path.setAlignmentX(Component.LEFT_ALIGNMENT);

		Font f = new Font(title.getFont().getFontName() ,Font.BOLD,30);
		title.setFont(f);
		header.add(title);
		header.add(autor);
		header.add(path);
		this.add(header, BorderLayout.NORTH);
		
		//center-panel
		
		
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,
				new TBECellViewFactory());
		Board temp = new Board(model, view, board.getSport());
		temp.getGraphLayoutCache().insert(
				board.cloneItems(board.getGraphLayoutCache().getCells(true,
						true, true, true)));
		temp.setBackgroundImage(board.getBackgroundImage());
		temp.setBackground(Color.WHITE);
		temp.clearSelection();
		LegendBar lb = new LegendBar(temp);
		lb.setBackground(Color.WHITE);

		
		center.add(temp, BorderLayout.CENTER);
		center.add(lb,BorderLayout.SOUTH);
		
		this.add(center, BorderLayout.CENTER);
	}





	@Override
	public void refresh()
	{
		// Nothing
		
	}



}
