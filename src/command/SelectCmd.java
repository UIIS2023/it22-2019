package command;

import model.Shape;

public class SelectCmd implements Command {
	
	private Shape shape;
	
	
	public SelectCmd(Shape shape) {
		this.shape = shape;
	}

	@Override
	public void execute() {
		shape.setSelected(true);
		
	}

	@Override
	public void unexecute() {
		shape.setSelected(false);
		
	}
	
	@Override
	public String toString() {
		return "Select_" + shape;
	}
}
