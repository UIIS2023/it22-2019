package logRead;

import command.Command;

public class UndoCommand implements Command {
	Command command;
	
	public UndoCommand(Command command) {
		this.command = command;
	}

	@Override
	public void execute() {
		command.unexecute();
		
	}

	@Override
	public void unexecute() {
		command.execute();
		
	}
	
	@Override
	public String toString() {
		return "undo";
	}
}
