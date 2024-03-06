package model;

import java.awt.Color;
import java.awt.Graphics;


public class Circle extends ArealShape {

	private Point center;
	private int radius;
	
	public Circle() {

	}

	public Circle(Point center, int radius, Color fillColor) {
		this.center = center;
		this.radius = radius;
		setInnerColor(fillColor);
	}

	public Circle(Point center, int radius, boolean selected, Color fillColor) {
		this(center, radius, fillColor);
		setSelected(selected);
	}

	@Override
	public void draw(Graphics g) {
		if (getInnerColor() != null) {
			fill(g);
		}
		
		g.setColor(getColor());
		g.drawOval(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(), this.getRadius()*2, this.getRadius()*2);
		
		if (isSelected()) {
			drawSelection(g);
		}
	}
	
	public void drawSelection(Graphics g) {
		g.setColor(Color.BLUE);
		
		g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() - 3, 6, 6);
		g.drawRect(this.getCenter().getX() + getRadius() - 3, this.getCenter().getY()-3, 6, 6);
		g.drawRect(this.getCenter().getX() - getRadius() - 3, this.getCenter().getY()-3, 6, 6);
		g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + getRadius() -3, 6, 6);
		g.drawRect(this.getCenter().getX()  - 3, this.getCenter().getY() - getRadius() -3, 6, 6);
		
		g.setColor(Color.BLACK);
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Circle) {
			return (this.radius - ((Circle) o).radius);
		}
		return 0;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		center.moveBy(byX, byY);
		
	}
	
	public boolean contains(int x, int y) {
		return this.getCenter().distance(x, y) <= radius ;
	}
	
	public boolean contains(Point p) {
		if (p.distance(getCenter().getX(), getCenter().getY()) <= radius) {
			return true;
		} else {
			return false;
		} 
	}

	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle c = (Circle) obj;
			if (this.center.equals(c.getCenter()) && this.radius == c.getRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double area() {
		return radius*radius*Math.PI;
	}
	
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) throws Exception {
		if (radius >= 0) {
			this.radius = radius;
		} else {
			throw new NumberFormatException("Radius has to be a value greater then 0!");
		}
	}
	
	@Override
	public String toString() {
		return "Circle:Center-" + center + 
				", radius=" + radius + 
				",outline color=" + getColor().getRGB() +
				",inner color=" + getInnerColor().getRGB() + 
				",selected=" + isSelected(); 
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillOval(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(), this.getRadius()*2, this.getRadius()*2);
	}
	
	@Override
	public Circle clone() {
		Circle circle = new Circle();
		circle.setCenter(new Point(getCenter().getX(),getCenter().getY()));
		try {
			circle.setRadius(radius);
		} catch (Exception e) {}
		circle.setColor(getColor());
		circle.setInnerColor(getInnerColor());
		circle.setSelected(isSelected());
		return circle;
	}



	
}
