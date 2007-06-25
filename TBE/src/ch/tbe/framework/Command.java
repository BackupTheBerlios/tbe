package ch.tbe.framework;

import ch.tbe.gui.TBE;

public abstract class Command {
	protected ItemComponent[] items;
	protected TBE tbe = TBE.getInstance();

	public Command(ItemComponent[] items) {
		this.items = items;
	}

	public void undo() {
	}

	public void redo() {
	}

}
