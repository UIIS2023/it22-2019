package command;

import model.DrawingModel;
import model.HexagonAdapter;

public class AddHexagonCmd implements Command {
	
	private HexagonAdapter hexagon;
	private DrawingModel model;


	public AddHexagonCmd(HexagonAdapter hexagon, DrawingModel model) {
		this.hexagon = hexagon;
		this.model = model;
	}

	@Override
	public void execute() {

		model.add(hexagon);
	}

	@Override
	public void unexecute() {

		model.remove(hexagon);

	}
	
	@Override
	public String toString() {
		return "Add_" + hexagon.toString();
	}
	
}
