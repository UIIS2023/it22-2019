package command;

import model.Point;

public class ModifyPointCmd implements Command {
	
	Point oldPoint;
	Point original;
	Point newPoint;
	
	
	public ModifyPointCmd(Point oldPoint, Point newPoint) {
		this.oldPoint = oldPoint;
		this.newPoint = newPoint;
	}

	@Override
	public void execute() {
		original = oldPoint.clone();
		oldPoint.setX(newPoint.getX());
		oldPoint.setY(newPoint.getY());
		oldPoint.setColor(newPoint.getColor());
	}

	@Override
	public void unexecute() {
		oldPoint.setX(original.getX());
		oldPoint.setY(original.getY());
		oldPoint.setColor(original.getColor());
	}
	
	@Override
	public String toString() {
		return "Update_" + original.toString() + ";" + newPoint.toString();
	}

}
