package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import ch.tbe.Board;
import ch.tbe.framework.View;
import ch.tbe.jgraph.TBECellViewFactory;

public class PrintView extends View
{
	private final Board board;

	public PrintView(Board board){
		
		this.board = board;
		this.createView();
	}
	
	
	
	
	
	private void createView()
	{

		this.setLayout(new BorderLayout());
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

		this.add(temp, BorderLayout.CENTER);
		this.add(lb,BorderLayout.SOUTH);
		
	}





	@Override
	public void refresh()
	{
		// TODO Auto-generated method stub
		
	}



}
