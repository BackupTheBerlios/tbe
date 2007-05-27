package ch.tbe.framework;

import ch.tbe.gui.TBE;

public abstract class Command {
	protected ItemComponent item;
	protected TBE tbe = TBE.getInstance();
	
	public Command(ItemComponent item){
		this.item = item;
	}
	public void undo(){}
	public void redo(){}
	 
}
 
