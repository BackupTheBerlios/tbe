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
	public static ArrayList<ArrowTool> getArrowTools(Sport sport){
		ArrayList<ArrowTool> arrowTools = new ArrayList<ArrowTool>();
		
		for(ShapeType type : sport.getArrowTypes()){
			if(type.getName() == "BezierCurvedArrowTool") arrowTools.add(new BezierCurvedArrowTool(type));
			if(type.getName() == "BezierDashedArrowTool") arrowTools.add(new BezierDashedArrowTool(type));
			if(type.getName() == "BezierDoubleArrowTool") arrowTools.add(new BezierDoubleArrowTool(type));
			if(type.getName() == "BezierSolidArrowTool") arrowTools.add(new BezierSolidArrowTool(type));
			if(type.getName() == "PolyCurvedArrowTool") arrowTools.add(new PolyCurvedArrowTool(type));
			if(type.getName() == "PolyCurvedBlockTool") arrowTools.add(new PolyCurvedBlockTool(type));
			if(type.getName() == "PolyDashedArrowTool") arrowTools.add(new PolyDashedArrowTool(type));
			if(type.getName() == "PolyDashedBlockTool") arrowTools.add(new PolyDashedBlockTool(type));
			if(type.getName() == "PolyDoubleArrowTool") arrowTools.add(new PolyDoubleArrowTool(type));
			if(type.getName() == "PolySolidArrowTool") arrowTools.add(new PolySolidArrowTool(type));
			if(type.getName() == "PolySolidBlockTool") arrowTools.add(new PolySolidBlockTool(type));
		}
		
		return arrowTools;
	}
	public static TextBoxTool getTextBoxTool(){
		return new TextBoxTool();
	}
}
 
