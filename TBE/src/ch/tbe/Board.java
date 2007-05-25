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

	public Board(Field field)
	{
		this.field = field;

	}

	public void clear()
	{
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
}
