package command;

import model.DrawingModel;
import model.Shape;

public class BringToBackCmd implements Command {
	
	private DrawingModel model;
	private Shape shape;
	private int index;

	public BringToBackCmd(DrawingModel model, int index) {
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		shape = model.get(index);
		model.getShapes().remove(shape);
		model.getShapes().add(0, shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.addOnPosition(shape, index);
	}

	@Override
	public String toString() {
		return "Bring to end_" + index;
	}

}
