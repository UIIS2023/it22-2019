package command;

import model.Donut;

public class ModifyDonutCmd implements Command {
	
	Donut oldDonut;
	Donut newDonut;
	Donut original;
	
	
	public ModifyDonutCmd(Donut oldDonut, Donut newDonut) {
		this.original = oldDonut;
		this.newDonut = newDonut;
	}

	@Override
	public void execute() {
		original = oldDonut.clone();
		
		oldDonut.getCenter().setX(newDonut.getCenter().getX());
		oldDonut.getCenter().setY(newDonut.getCenter().getY());
		try {
			oldDonut.setRadius(newDonut.getRadius());
		} catch (Exception e) {}
		try {
			oldDonut.setInnerRadius(newDonut.getInnerRadius());
		} catch (Exception e) {}
		oldDonut.setInnerColor(newDonut.getInnerColor());
		oldDonut.setColor(newDonut.getColor());
	}

	@Override
	public void unexecute() {
		
		oldDonut.getCenter().setX(original.getCenter().getX());
		oldDonut.getCenter().setY(original.getCenter().getY());
		try {
			oldDonut.setRadius(original.getRadius());
		} catch (Exception e) {}
		try {
			oldDonut.setInnerRadius(original.getInnerRadius());
		} catch (Exception e) {}
		oldDonut.setInnerColor(original.getInnerColor());
		oldDonut.setColor(original.getColor());
	}
	
	@Override
	public String toString() {
		return "Update_" + original.toString() + ";" + newDonut.toString();
	}
	
}
