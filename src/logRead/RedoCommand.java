package logRead;

import command.Command;

public class RedoCommand implements Command {
	Command command;
	
	public RedoCommand(Command command) {
		this.command = command;
	}

	@Override
	public void execute() {
		command.execute();
	}

	@Override
	public void unexecute() {
		command.unexecute();
	}
	
	@Override
	public String toString() {
		return "redo";
	}
}
