package ch.tbe;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import org.jgraph.graph.DefaultGraphCell;

import ch.tbe.framework.ItemComponent;
import ch.tbe.jgraph.TBEGraphConstants;


public class TextBoxItem extends DefaultGraphCell implements ItemComponent {
 
	public TextBoxItem(Rectangle2D.Double rect){
		
		super(new String("Text"));
		TBEGraphConstants.setBounds(this.getAttributes(), rect);
	}
	public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public DataFlavor[] getTransferDataFlavors()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDataFlavorSupported(DataFlavor arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
 
