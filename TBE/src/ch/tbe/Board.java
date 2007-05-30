package ch.tbe;


import java.util.ArrayList;
import java.util.List;

import org.jgraph.JGraph;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

public class Board extends JGraph
{
	

	private List<ItemComponent> items = new ArrayList<ItemComponent>();
	private String path;
	private Field field;
	private Description description;
	private Sport sport;
	private TBE tbe = TBE.getInstance();
	
	public Board(GraphModel arg0, GraphLayoutCache arg1, Sport sport)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		this.sport = sport;
		this.field = sport.getFields().get(0);
		this.description = new Description();
	}

	public void clear()
	{
		this.getGraphLayoutCache().removeCells(this.getGraphLayoutCache().getCells(true, true, true, true));
		this.refresh();
	}
//
	public List<ItemComponent> getItems()
	{
//		return this.items;
		return null;//this.getGraphLayoutCache().getC;
	}
//	
	public void addItem(ItemComponent i)
	{
		this.getGraphLayoutCache().insert(i);
		this.refresh();
	}
	
	public void removeItem(ItemComponent i)
	{
		ItemComponent[] ii = new ItemComponent[]{i};
		this.getGraphLayoutCache().removeCells(ii);
		this.refresh();
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public void setField(Field field)
	{
		this.field = field;
	}

	public void addAttribute(String title, String text)
	{
		this.description.addAttribute(new Attribute(title, text));
	}

	public void removeAttribute(Attribute attribute)
	{
		this.description.removeAttribute(attribute);
	}

	public Description getDescription()
	{
		return this.description;
	}
	
	public Sport getSport()
	{
		return this.sport;
	}
	public void refresh(){
		this.repaint();
		tbe.getMenu().refreshInvokerVisibility();
	}
}
