package ch.tbe;

import java.util.List;
import java.util.Stack;

import ch.tbe.framework.*;

public class Invoker {
	private static Invoker instance = null;
	private Stack<List<Command>> doneCommands = new Stack<List<Command>>();
	private Stack<List<Command>> undoneCommands = new Stack<List<Command>>();
	private List<Command> actCommands;
	
	private Invoker(){}
	
	public static Invoker getInstance() {
		if(instance == null) {
			instance = new Invoker();
		}
		return instance;
	}
 
	public void execute(List<Command> actCommands) {
		doneCommands.push(actCommands);
		
		for (int i=0; i< actCommands.size()-1; i++){
			actCommands.get(i).redo();
		}
	}
	 
	public void undo() {
		if (this.canUndo()){
			actCommands = doneCommands.pop();
			undoneCommands.push(actCommands);
			
			for (int i=0; i< actCommands.size()-1; i++){
				actCommands.get(i).undo();
			}
		}
	}
	 
	public void redo() {
		if (this.canRedo()){
			actCommands = undoneCommands.pop();
			doneCommands.push(actCommands);
		
			for (int i=0; i< actCommands.size()-1; i++){
				actCommands.get(i).redo();
			}
		}
	}
	
	public boolean canUndo(){
		return doneCommands.isEmpty();
	}
	
	public boolean canRedo(){
		return undoneCommands.isEmpty();
	}
	 
}
 
