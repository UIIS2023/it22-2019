package command;

import model.Circle;

public class ModifyCircleCmd implements Command {
	
	Circle oldCircle;
	Circle newCircle;
	Circle original;
	
	public ModifyCircleCmd(Circle oldCircle, Circle newCircle) {
		this.oldCircle = oldCircle;
		this.newCircle = newCircle;
	}

	@Override
	public void execute() {
		original = oldCircle.clone();
		
		oldCircle.getCenter().setX(newCircle.getCenter().getX());
		oldCircle.getCenter().setY(newCircle.getCenter().getY());
		try {
			oldCircle.setRadius(newCircle.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldCircle.setInnerColor(newCircle.getInnerColor());
		oldCircle.setColor(newCircle.getColor());
	}

	@Override
	public void unexecute() {
		oldCircle.getCenter().setX(original.getCenter().getX());
		oldCircle.getCenter().setY(original.getCenter().getY());
		try {
			oldCircle.setRadius(original.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldCircle.setInnerColor(original.getInnerColor());
		oldCircle.setColor(original.getColor());
	}
	
	@Override
	public String toString() {
		return "Update_" + original.toString() + ";" + newCircle.toString();
	}
	
}
