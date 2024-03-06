package model;

import java.util.ArrayList;
import java.util.List;

public class DrawingModel {
	private List<Shape> shapes = new ArrayList<Shape>();

	public List<Shape> getShapes(){
		return shapes;
	}

	public void add(Shape shape) {
		shapes.add(shape);
	}

	public void remove(Shape shape) {
		shapes.remove(shape);
	}

	public Shape get(int index) {
		return shapes.get(index);
	}
	
	public void deselectAllShapes() {
		for (Shape shape : shapes) {
			shape.setSelected(false);
		}
	}
	
	public Shape getSelectedShape() {
		for (Shape shape : shapes) {
			if (shape.isSelected()) {
				return shape;
			}
		}
		
		return null;
	}
	
	public void cleanList() {
		shapes.clear();
	}

	public void addOnPosition(Shape shape, int positionInList) {
		shapes.add(positionInList, shape);
	}
	
	public List<Shape> getSelectedShapes() {
		List<Shape> pomocna = new ArrayList<Shape>();
		for (Shape s : shapes) {
			if (s.isSelected()) {
				pomocna.add(s);
			}
		}
		
		return pomocna;
	}
	
	public void changePositionsOfElements(int indexOne, int index2) {
		Shape help = shapes.get(indexOne);
		shapes.set(indexOne, shapes.get(index2));
		shapes.set(index2 ,help);
	}
	
	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}
	
}
