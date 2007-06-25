package ch.tbe;

import java.awt.Color;

import javax.swing.ImageIcon;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.jgraph.TBEBasicGraphUI;

public class Board extends JGraph {

	private static final long serialVersionUID = 1L;
	private String path = "";
	private Field field;
	private Description description;
	private Sport sport;
	private TBE tbe = TBE.getInstance();

	public Board(GraphModel model, GraphLayoutCache view, Sport sport) {
		super(model, view);
		this.setUI(new TBEBasicGraphUI());
		this.sport = sport;
		this.field = sport.getFields().get(0);
		this.description = new Description();
		this.setBackground(Color.GRAY);
		this.setBackgroundImage((ImageIcon) this.field.getIcon());
	}

	public ItemComponent[] getItems() {

		Object[] objects = this.getGraphLayoutCache().getCells(true, true, true, true);
		ItemComponent[] comps = new ItemComponent[objects.length];
		for (int i = 0; i < objects.length; i++) {
			comps[i] = (ItemComponent) objects[i];
		}
		return comps;

	}

	public ItemComponent[] getSelectedItems() {
		Object[] objects = this.getSelectionCells();
		ItemComponent[] comps = new ItemComponent[objects.length];
		for (int i = 0; i < objects.length; i++) {
			comps[i] = (ItemComponent) objects[i];
		}
		return comps;
	}

	public void addItem(ItemComponent[] i) {
		this.getGraphLayoutCache().insert(i);
		this.refresh();
	}

	public void addItem(ItemComponent item) {
		this.getGraphLayoutCache().insert(item);
		this.refresh();
	}

	public void removeItem(ItemComponent[] i) {
		for (ItemComponent ic : i) {
			this.removeSelectionCell(ic);
		}
		this.getGraphLayoutCache().removeCells(i);
		this.refresh();
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public void setField(Field field) {
		this.field = field;
		this.refresh();
	}

	public void addAttribute(String title, String text) {
		this.description.addAttribute(new Attribute(title, text));
	}

	public void removeAttribute(Attribute attribute) {
		this.description.removeAttribute(attribute);
	}

	public Description getDescription() {
		return this.description;
	}

	public Sport getSport() {
		return this.sport;
	}

	public Field getField() {
		return this.field;
	}

	public void refresh() {
		// Board
		this.setBackgroundImage((ImageIcon) this.field.getIcon());
		this.repaint();
		tbe.getMenu().refreshInvokerVisibility();
		TBE.getInstance().getView().refresh();
	}

	public ItemComponent[] cloneItems(Object[] cArray) {
		ItemComponent[] rArray = new ItemComponent[cArray.length];
		for (int i = 0; i < cArray.length; i++) {
			rArray[i] = (ItemComponent) ((DefaultGraphCell) cArray[i]).clone();
		}
		return rArray;
	}
}
