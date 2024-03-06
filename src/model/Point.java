package model;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {

	private int x;
	private int y;
	
	public Point() {
		
	}
	
	public Point(int x, int y) {
		this.x = x;
		setY(y);
	}
	
	public Point(int x, int y, boolean selected) {
		this(x, y);
		setSelected(selected);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(this.x-2, y, this.x+2, y);
		g.drawLine(x, this.y-2, x, this.y+2);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.x-3, this.y-3, 6, 6);
			
		}
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.x = this.x + byX;
		this.y += byY;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Point) {
			Point start = new Point(0, 0);
			return (int) (this.distance(start.getX(), start.getY()) - ((Point) o).distance(start.getX(), start.getY()));
		}
		return 0;
	}
	
	public boolean contains(int x, int y) {
		if(this.distance(x, y) <= 3)
			return true;
		return false;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point p = (Point) obj;
			if (this.x == p.getX() && 
					this.y == p.getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double distance (int x2, int y2) {
		double dx = this.x - x2;
		double dy = this.y - y2;
		double d = Math.sqrt (dx*dx + dy*dy);
		return d;
	}
	
	@Override
	public Point clone() {
		Point point = new Point(this.getX(), this.getY(), this.isSelected());
		point.setColor(this.getColor());
		return point;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		String s = "x=" + x + ",y=" + y;
		if(getColor() != null) {
			s += ",outline color=" + getColor().getRGB() + ",selected=" + isSelected();
			return "Point:" + s;
		}
		return s;
	}

	
}
