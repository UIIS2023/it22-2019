package command;

import model.Rectangle;

public class ModifyRectangleCmd implements Command {
	
	private Rectangle oldRectangle;
	private Rectangle newRectangle;
	private Rectangle original;
	
	
	public ModifyRectangleCmd(Rectangle oldRectangle, Rectangle newRectangle) {
		this.oldRectangle = oldRectangle;
		this.newRectangle = newRectangle;
	}

	@Override
	public void execute() {
		
		original = oldRectangle.clone();
		
		oldRectangle.getUpperLeftPoint().setX(newRectangle.getUpperLeftPoint().getX());
		oldRectangle.getUpperLeftPoint().setY(newRectangle.getUpperLeftPoint().getY());
		oldRectangle.setHeight(newRectangle.getHeight());
		oldRectangle.setWidth(newRectangle.getWidth());
		oldRectangle.setColor(newRectangle.getColor());
		oldRectangle.setInnerColor(newRectangle.getInnerColor());
	}

	@Override
	public void unexecute() {
		
		oldRectangle.getUpperLeftPoint().setX(original.getUpperLeftPoint().getX());
		oldRectangle.getUpperLeftPoint().setY(original.getUpperLeftPoint().getY());
		oldRectangle.setHeight(original.getHeight());
		oldRectangle.setWidth(original.getWidth());
		oldRectangle.setColor(original.getColor());
		oldRectangle.setInnerColor(original.getInnerColor());
		oldRectangle.setInnerColor(original.getInnerColor());
	}
	
	@Override
	public String toString() {
		return "Update_" + original.toString() + ";" + newRectangle.toString();
	}
}
