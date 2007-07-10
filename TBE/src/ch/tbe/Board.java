package ch.tbe;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import ch.tbe.command.MoveCommand;
import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.Command;
import ch.tbe.framework.ItemComponent;
import ch.tbe.gui.TBE;
import ch.tbe.jgraph.TBEBasicGraphUI;
import ch.tbe.jgraph.TBEGraphConstants;

/**
 * Tactic Board Editor
 * **********************
 * Board 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class Board extends JGraph {

	private static final long serialVersionUID = 1L;
	private String path = "";
	private Field field;
	private Description description;
	private Sport sport;
	private TBE tbe = TBE.getInstance();

	/**
	 * Creats a new Board
	 * @param model GraphModel
	 * @param view GraphLayoutCache
	 * @param sport Sport
	 */
	public Board(GraphModel model, GraphLayoutCache view, Sport sport) {
		super(model, view);
		this.setUI(new TBEBasicGraphUI());
		this.sport = sport;
		this.field = sport.getFields().get(0);
		this.description = new Description();
		this.setBackground(Color.GRAY);
		this.setBackgroundImage((ImageIcon) this.field.getIcon());
		addKeyListener();
	}

	/**
	 * Adds the KeyListener to move the Items with the Arrow-Keys
	 *
	 */
	private void addKeyListener() {
		this.addKeyListener(new KeyListener() {
			private MoveCommand mc = null;
			private boolean keyDown = false;
			private ItemComponent[] items;

			public void keyPressed(KeyEvent e) {
				//Fires only if at leas one Item is selected and an Arrow-Key is pressed
				if (Board.this.getSelectionCount() > 0 && (e.getKeyCode() == KeyEvent.VK_UP || (e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_RIGHT))) {
					Object[] objects = Board.this.getSelectionCells();
					//Fires only if the Arroy-Key is pressed down and not always if key is hold down
					if (!keyDown) {
						items = Board.this.getSelectedItems();
						mc = new MoveCommand(items);
						keyDown = true;
					}

					//Moves the selected Items 1 Pixel to left
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						for (Object o : objects) {
							if (o instanceof ArrowItem) {
								List<?> points = TBEGraphConstants.getPoints(((DefaultGraphCell) o).getAttributes());
								for (Object ob : points) {
									Point2D p = ((Point2D) ob);
									p.setLocation(new Point2D.Double(p.getX() - 1, p.getY()));
								}
								TBEGraphConstants.setPoints(((DefaultGraphCell) o).getAttributes(), points);
							} else {

								Rectangle2D r = TBEGraphConstants.getBounds(((DefaultGraphCell) o).getAttributes());
								TBEGraphConstants.setBounds(((DefaultGraphCell) o).getAttributes(), new Rectangle2D.Double(r.getX() - 1, r.getY(), r.getWidth(), r.getHeight()));
							}
						}

						//Moves the selected Items 1 Pixel to right
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

						for (Object o : objects) {
							if (o instanceof ArrowItem) {
								List<?> points = TBEGraphConstants.getPoints(((DefaultGraphCell) o).getAttributes());
								for (Object ob : points) {
									Point2D p = ((Point2D) ob);
									p.setLocation(new Point2D.Double(p.getX() + 1, p.getY()));
								}
								TBEGraphConstants.setPoints(((DefaultGraphCell) o).getAttributes(), points);
							} else {
								Rectangle2D r = TBEGraphConstants.getBounds(((DefaultGraphCell) o).getAttributes());
								TBEGraphConstants.setBounds(((DefaultGraphCell) o).getAttributes(), new Rectangle2D.Double(r.getX() + 1, r.getY(), r.getWidth(), r.getHeight()));
							}
						}

						//Moves the selected Items 1 Pixel down
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						for (Object o : objects) {
							if (o instanceof ArrowItem) {
								List<?> points = TBEGraphConstants.getPoints(((DefaultGraphCell) o).getAttributes());
								for (Object ob : points) {
									Point2D p = ((Point2D) ob);
									p.setLocation(new Point2D.Double(p.getX(), p.getY() + 1));
								}
								TBEGraphConstants.setPoints(((DefaultGraphCell) o).getAttributes(), points);
							} else {
								Rectangle2D r = TBEGraphConstants.getBounds(((DefaultGraphCell) o).getAttributes());
								TBEGraphConstants.setBounds(((DefaultGraphCell) o).getAttributes(), new Rectangle2D.Double(r.getX(), r.getY() + 1, r.getWidth(), r.getHeight()));
							}
						}

						//Moves the selected Items 1 Pixel up
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						for (Object o : objects) {
							if (o instanceof ArrowItem) {
								List<?> points = TBEGraphConstants.getPoints(((DefaultGraphCell) o).getAttributes());
								for (Object ob : points) {
									Point2D p = ((Point2D) ob);
									p.setLocation(new Point2D.Double(p.getX(), p.getY() - 1));
								}
								TBEGraphConstants.setPoints(((DefaultGraphCell) o).getAttributes(), points);
							} else {
								Rectangle2D r = TBEGraphConstants.getBounds(((DefaultGraphCell) o).getAttributes());
								TBEGraphConstants.setBounds(((DefaultGraphCell) o).getAttributes(), new Rectangle2D.Double(r.getX(), r.getY() - 1, r.getWidth(), r.getHeight()));
							}
						}

					}
					//Adds the moved Items to the Board (easy way to repanit the Bord)
					Board.this.getGraphLayoutCache().insert(objects);
				}
			}

			public void keyReleased(KeyEvent e) {
				//Adds the MoveCommand to the Invoker
				if (mc != null) {
					mc.setMoveEnd(items);
					ArrayList<Command> actCommands = new ArrayList<Command>();
					actCommands.add(mc);
					TBE.getInstance().addCommands(actCommands);
					keyDown = false;
				}
			}

			public void keyTyped(KeyEvent arg0) {
			}

		});
	}

	/**
	 * Retuns an ItemComponent-Array containing all Items of the Board
	 * @return ItemComponent[] 
	 */
	public ItemComponent[] getItems() {

		Object[] objects = this.getGraphLayoutCache().getCells(true, true, true, true);
		ItemComponent[] comps = new ItemComponent[objects.length];
		for (int i = 0; i < objects.length; i++) {
			comps[i] = (ItemComponent) objects[i];
		}
		return comps;

	}

	/**
	 * Retuns an ItemComponent-Array containing all selected Icons
	 * @return ItemComponent[] 
	 */
	public ItemComponent[] getSelectedItems() {
		Object[] objects = this.getSelectionCells();
		ItemComponent[] comps = new ItemComponent[objects.length];
		for (int i = 0; i < objects.length; i++) {
			comps[i] = (ItemComponent) objects[i];
		}
		return comps;
	}

	/**
	 * Adds ItemComponents to the Board
	 * @param i ItemComponent-Array to add
	 */
	public void addItem(ItemComponent[] i) {
		this.getGraphLayoutCache().insert(i);
		this.refresh();
	}

	/**
	 * Adds one ItemComponent to the Board
	 * @param item ItemComponent to add
	 */
	public void addItem(ItemComponent item) {
		this.getGraphLayoutCache().insert(item);
		this.refresh();
	}

	/**
	 * Removes ItemComponents from to Board
	 * @param i ItemComponent-Array to remove
	 */
	public void removeItem(ItemComponent[] i) {
		for (ItemComponent ic : i) {
			this.removeSelectionCell(ic);
		}
		this.getGraphLayoutCache().removeCells(i);
		this.refresh();
	}

	/**
	 * Sets the SaveFile-Path
	 * @param path String of the Filepath
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Returns the SaveFile-Path
	 * @return path String of the Filepath
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Sets the Field of the Board
	 * @param field
	 */
	public void setField(Field field) {
		this.field = field;
		this.refresh();
	}

	/**
	 * Adds an Attribute to the Board
	 * @param title String Attribute-Title
	 * @param text String Attribute-Text
	 */
	public void addAttribute(String title, String text) {
		this.description.addAttribute(new Attribute(title, text));
	}

	/**
	 * Removes an Attribute from the Board
	 * @param attribute to remove
	 */
	public void removeAttribute(Attribute attribute) {
		this.description.removeAttribute(attribute);
	}

	/**
	 * Retuns the Description of the Board
	 * @return description as Description
	 */
	public Description getDescription() {
		return this.description;
	}

	/**
	 * Returns the Sport of the Board
	 * @return sport as Sport
	 */
	public Sport getSport() {
		return this.sport;
	}

	/**
	 * Returns the Field of the Board
	 * @return field as Field
	 */
	public Field getField() {
		return this.field;
	}

	/**
	 * Sets the BackGroundImage and refreshs the Board and the current View 
	 */
	public void refresh() {
		// Board
		this.setBackgroundImage((ImageIcon) this.field.getIcon());
		this.validate();
		tbe.getMenu().refreshInvokerVisibility();
		TBE.getInstance().getView().refresh();
	}

	/**
	 * Clones an Array of ItemComponents. This method demand an Object[] because
	 * the JGraph methods often returning Object[].
	 * @param cArray Object[] items to clone
	 * @return ItemComponent[] cloned items
	 */
	public ItemComponent[] cloneItems(Object[] cArray) {
		ItemComponent[] rArray = new ItemComponent[cArray.length];
		for (int i = 0; i < cArray.length; i++) {
			if (cArray[i] instanceof ItemComponent){
			rArray[i] = (ItemComponent) ((DefaultGraphCell) cArray[i]).clone();}
		}
		return rArray;
	}
}
