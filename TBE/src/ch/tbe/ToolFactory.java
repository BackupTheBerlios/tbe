package ch.tbe;

import java.util.ArrayList;

import ch.tbe.framework.ArrowTool;

public class ToolFactory {
	
	private ToolFactory(){};
	public static ArrayList<ShapeTool> getShapeTools(Sport sport){
		ArrayList<ShapeTool> shapeTools = new ArrayList<ShapeTool>();
		for(ShapeType types : sport.getShapeTypes()){
			shapeTools.add(new ShapeTool(types));
		}
		return shapeTools;
	}
	public static ArrayList<ArrowTool> getArrowItemTools(){
		return null;
	}
	public static TextBoxTool getTextBoxTool(){
		return new TextBoxTool();
	}
}
 
