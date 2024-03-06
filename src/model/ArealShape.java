package model;

import java.awt.Color;
import java.awt.Graphics;

public abstract class ArealShape extends Shape {
	
	private Color innerColor = Color.WHITE;
	
	public abstract void fill(Graphics g);
	public abstract double area();
	
	public ArealShape() {
		
	}

	public ArealShape(Color innerColor) {
		this.innerColor = innerColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}
	
}
