package ch.tbe;

import java.util.List;
import java.util.Stack;
import ch.tbe.framework.*;
import ch.tbe.gui.TBE;

/**
 * Tactic Board Editor
 * **********************
 * Invoker 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
 */

public class Invoker {
	private static Invoker instance = null;
	private Stack<List<Command>> doneCommands = new Stack<List<Command>>();
	private Stack<List<Command>> undoneCommands = new Stack<List<Command>>();
	private List<Command> actCommands;

	private Invoker() {
	}

	public static Invoker getInstance() {
		if (instance == null) {
			instance = new Invoker();
		}
		return instance;
	}

	public void execute(List<Command> actCommands) {
		doneCommands.push(actCommands);
		TBE.getInstance().setSaved(false);

		for (int i = 0; i < actCommands.size() - 1; i++) {
			actCommands.get(i).redo();
		}
	}

	public void undo() {
		if (this.canUndo()) {
			actCommands = doneCommands.pop();
			undoneCommands.push(actCommands);
			TBE.getInstance().setSaved(false);
			for (int i = 0; i <= actCommands.size() - 1; i++) {
				actCommands.get(i).undo();
			}
		}
	}

	public void redo() {
		if (this.canRedo()) {
			actCommands = undoneCommands.pop();
			doneCommands.push(actCommands);
			TBE.getInstance().setSaved(false);
			for (int i = 0; i <= actCommands.size() - 1; i++) {
				actCommands.get(i).redo();
			}
		}
	}

	public boolean canUndo() {
		return !doneCommands.isEmpty();
	}

	public boolean canRedo() {
		return !undoneCommands.isEmpty();
	}

	public void clear() {
		doneCommands.clear();
		undoneCommands.clear();
	}

}
