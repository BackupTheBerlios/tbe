package ch.tbe;

import java.util.ArrayList;

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
		
	}
	public static TextBoxTool getTextBoxTool(){
		return new TextBoxTool();
	}
}
 
