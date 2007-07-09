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

	private void addKeyListener() {
		this.addKeyListener(new KeyListener() {
			private MoveCommand mc;
			private boolean keyDown = false;
			private ItemComponent[] items;

			public void keyPressed(KeyEvent e) {
				if (Board.this.getSelectionCount() > 0 && (e.getKeyCode() == KeyEvent.VK_UP || (e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_RIGHT))) {
					Object[] objects = Board.this.getSelectionCells();
					if (!keyDown) {
						items = Board.this.getSelectedItems();
						mc = new MoveCommand(items);
						keyDown = true;
					}

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

					Board.this.getGraphLayoutCache().insert(objects);
				}
			}

			public void keyReleased(KeyEvent e) {
				if (Board.this.getSelectionCount() > 0 && (e.getKeyCode() == KeyEvent.VK_UP || (e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_RIGHT))) {
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
