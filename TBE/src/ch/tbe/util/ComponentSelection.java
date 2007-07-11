package ch.tbe.util;

import ch.tbe.framework.ItemComponent;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 * Tactic Board Editor
 * **********************
 * ComponentSelection 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class ComponentSelection implements Transferable, ClipboardOwner {

	static public DataFlavor itemFlavor;
	private DataFlavor[] supportedFlavors = { itemFlavor };
	private ItemComponent[] item;

	/**
	 * @param item ItemComponent[]
	 */
	public ComponentSelection(ItemComponent[] item) {

		this.item = item;

		itemFlavor = new DataFlavor(ItemComponent.class, "Image");

	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	public synchronized DataFlavor[] getTransferDataFlavors() {

		return (supportedFlavors);

	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 */
	public boolean isDataFlavorSupported(DataFlavor parFlavor) {

		return (parFlavor.equals(itemFlavor));

	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 */
	public synchronized Object[] getTransferData(DataFlavor parFlavor) throws UnsupportedFlavorException {
		if (parFlavor.equals(itemFlavor))
			return (item);
		else
			throw new UnsupportedFlavorException(itemFlavor);

	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.ClipboardOwner#lostOwnership(java.awt.datatransfer.Clipboard, java.awt.datatransfer.Transferable)
	 */
	public void lostOwnership(Clipboard parClipboard, Transferable parTransferable) {

		System.out.println("Lost ownership");
	}
}