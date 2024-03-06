package command;

import model.DrawingModel;

public class MoveFrontCmd implements Command {
	
	private DrawingModel model;
	private int index;
	
	
	public MoveFrontCmd(DrawingModel model, int index) {
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		model.changePositionsOfElements(index, index + 1);
	}

	@Override
	public void unexecute() {
		model.changePositionsOfElements(index,index + 1);
	}
	
	@Override
	public String toString() {
		return "One step to front_" + index;
	}
	
}
