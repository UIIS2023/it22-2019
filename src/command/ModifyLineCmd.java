package command;

import model.Line;

public class ModifyLineCmd implements Command {
	
	Line oldLine;
	Line newLine;
	Line original;
	
	public ModifyLineCmd(Line oldLine, Line newLine) {
		this.oldLine = oldLine;
		this.newLine = newLine;
	}

	@Override
	public void execute() {
		
		original = oldLine.clone();
		
		oldLine.getStartPoint().setX(newLine.getStartPoint().getX());
		oldLine.getStartPoint().setY(newLine.getStartPoint().getY());
		oldLine.getEndPoint().setX(newLine.getEndPoint().getX());
		oldLine.getEndPoint().setY(newLine.getEndPoint().getY());
		oldLine.setColor(newLine.getColor());
	}

	@Override
	public void unexecute() {
		
		oldLine.getStartPoint().setX(original.getStartPoint().getX());
		oldLine.getStartPoint().setY(original.getStartPoint().getY());
		oldLine.getEndPoint().setX(original.getEndPoint().getX());
		oldLine.getEndPoint().setY(original.getEndPoint().getY());
		oldLine.setColor(original.getColor());
	}
	
	@Override
	public String toString() {
		return "Update_" + original.toString() + ";" + newLine.toString();
	}
	
}
