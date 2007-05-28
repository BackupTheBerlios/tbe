package ch.tbe;


import java.util.ArrayList;
import java.util.List;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;

public class Board
{
	private List<ItemComponent> items = new ArrayList<ItemComponent>();
	private String path;
	private Field field;
	private Description description;
	private Sport sport;

	public Board(Sport sport)
	{
		this.sport = sport;
		this.field = sport.getFields().get(0);
		this.description = new Description();
	}

	public void clear()
	{
		this.items.clear();
		((WorkingView) TBE.getInstance().getView()).refresh();
	}

	public List<ItemComponent> getItems()
	{
		return this.items;
	}
	
	public void addItem(ItemComponent i)
	{
		this.items.add(i);
		((WorkingView) TBE.getInstance().getView()).refresh();
	}
	
	public void removeItem(ItemComponent i)
	{
		this.items.remove(i);
		((WorkingView) TBE.getInstance().getView()).refresh();
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
}
