package command;

import model.DrawingModel;

public class MoveBackCmd implements Command {
	
	private DrawingModel model;
	private int index;
	
	
	public MoveBackCmd(DrawingModel model, int index) {
		super();
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		model.changePositionsOfElements(index, index - 1);	
	}

	@Override
	public void unexecute() {
		model.changePositionsOfElements(index, index - 1);
	}
	
	@Override
	public String toString() {
		return "One step to back_" + index;
	}
	
}
