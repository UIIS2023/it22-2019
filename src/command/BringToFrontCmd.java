package command;

import model.DrawingModel;
import model.Shape;

public class BringToFrontCmd implements Command {
	
	private DrawingModel model;
	private Shape shape;
	private int index;
	
	
	public BringToFrontCmd(DrawingModel model, int index) {
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		this.shape = model.get(index);
		model.remove(shape);
		model.add(shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.addOnPosition(shape,index);
		
	}
	
	@Override
	public String toString() {
		return "Bring to front_" + index;
	}

}
