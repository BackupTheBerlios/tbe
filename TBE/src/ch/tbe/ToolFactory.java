package ch.tbe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import ch.tbe.framework.ArrowTool;
import ch.tbe.gui.TBE;
import ch.tbe.tool.BezierCurvedArrowTool;
import ch.tbe.tool.BezierDashedArrowTool;
import ch.tbe.tool.BezierDoubleArrowTool;
import ch.tbe.tool.BezierSolidArrowTool;
import ch.tbe.tool.CursorTool;
import ch.tbe.tool.PolyCurvedArrowTool;
import ch.tbe.tool.PolyCurvedBlockTool;
import ch.tbe.tool.PolyDashedArrowTool;
import ch.tbe.tool.PolyDashedBlockTool;
import ch.tbe.tool.PolyDoubleArrowTool;
import ch.tbe.tool.PolySolidArrowTool;
import ch.tbe.tool.PolySolidBlockTool;
import ch.tbe.tool.ShapeTool;
import ch.tbe.tool.TextBoxTool;

public final class ToolFactory {
	
	private ToolFactory(){};
	
	public static CursorTool getCursorTool(){
		URL imgURL = ToolFactory.class.getResource("pics/cursoricon.gif");
		ImageIcon icon = new ImageIcon(imgURL);
		ResourceBundle rb = getResourceBundle(TBE.getInstance().getLang());
		return new CursorTool(new ItemType(rb.getString("cursor"),rb.getString("cursor"),icon));
	}
	
	public static ArrayList<ShapeTool> getShapeTools(Sport sport){
		ArrayList<ShapeTool> shapeTools = new ArrayList<ShapeTool>();
		for(ItemType types : sport.getShapeTypes()){
			shapeTools.add(new ShapeTool(types));
		}
		return shapeTools;
	}
	public static ArrayList<ArrowTool> getArrowTools(Sport sport){
		ArrayList<ArrowTool> arrowTools = new ArrayList<ArrowTool>();
		
		for(ItemType type : sport.getArrowTypes()){
						
			if(type.getName().equals("BezierCurvedArrowTool")) arrowTools.add(new BezierCurvedArrowTool(type));
			if(type.getName().equals("BezierDashedArrowTool")) arrowTools.add(new BezierDashedArrowTool(type));
			if(type.getName().equals("BezierDoubleArrowTool")) arrowTools.add(new BezierDoubleArrowTool(type));
			if(type.getName().equals("BezierSolidArrowTool")) arrowTools.add(new BezierSolidArrowTool(type));
			if(type.getName().equals("PolyCurvedArrowTool")) arrowTools.add(new PolyCurvedArrowTool(type));
			if(type.getName().equals("PolyCurvedBlockTool")) arrowTools.add(new PolyCurvedBlockTool(type));
			if(type.getName().equals("PolyDashedArrowTool")) arrowTools.add(new PolyDashedArrowTool(type));
			if(type.getName().equals("PolyDashedBlockTool")) arrowTools.add(new PolyDashedBlockTool(type));
			if(type.getName().equals("PolyDoubleArrowTool")) arrowTools.add(new PolyDoubleArrowTool(type));
			if(type.getName().equals("PolySolidArrowTool")) arrowTools.add(new PolySolidArrowTool(type));
			if(type.getName().equals("PolySolidBlockTool")) arrowTools.add(new PolySolidBlockTool(type));
		}
		
		return arrowTools;
	}
	public static TextBoxTool getTextBoxTool(){
		URL imgURL = ToolFactory.class.getResource("pics/text.gif");
		ImageIcon icon = new ImageIcon(imgURL);
		ResourceBundle rb = getResourceBundle(TBE.getInstance().getLang());
		return new TextBoxTool(new ItemType(rb.getString("textbox"),rb.getString("textbox"),icon));
	}
	
	private static ResourceBundle getResourceBundle(String lang)
	{
		InputStream toolFactoryStream;
		ResourceBundle labels = null;
		try
		{
			toolFactoryStream = ToolFactory.class.getResourceAsStream("config/lang/"
					+ lang + "/toolFactory.txt");
			labels = new PropertyResourceBundle(toolFactoryStream);
		} catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for ToolFactory not found !");
		} catch (IOException ioe)
		{
			System.out.println("Error with ResourceBundle ToolFactory!");
		}
		return labels;
	}
}
 
