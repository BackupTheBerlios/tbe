package ch.tbe.util;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import ch.tbe.framework.ItemComponent;

public class ComponentSelection implements Transferable, ClipboardOwner {
	 
	 static public DataFlavor itemFlavor;
	private DataFlavor [] supportedFlavors = {itemFlavor};
	private ItemComponent[] item;



	public ComponentSelection (ItemComponent[] item) {

	 this.item = item;
  
	    itemFlavor = new DataFlavor (ItemComponent.class, "Image");
	   


	}

	public synchronized DataFlavor [] getTransferDataFlavors () {

	    return (supportedFlavors);

	}

	public boolean isDataFlavorSupported (DataFlavor parFlavor) {

	    return (parFlavor.equals (itemFlavor));

	}

	public synchronized Object[] getTransferData (DataFlavor parFlavor)
			   throws UnsupportedFlavorException {
	    if (parFlavor.equals (itemFlavor))
	    return (item);
	   else
	    throw new UnsupportedFlavorException (itemFlavor);

	 }

	public void lostOwnership (Clipboard parClipboard, Transferable parTransferable) {

	    System.out.println ("Lost ownership");
	}
  }