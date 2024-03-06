package model;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends ArealShape {

	private Point upperLeftPoint;
	private int width;
	private int height;
	
	public Rectangle() {
		super();
	}

	public Rectangle(Point upperLeftPoint, int height, int width,Color fillColor) {
		super();
		this.upperLeftPoint = upperLeftPoint;
		super.setInnerColor(fillColor);
		setHeight(height);
		setWidth(width);
	}

	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected,Color fillColor) {
		this(upperLeftPoint, height, width,fillColor);
		setSelected(selected);
	}

	@Override
	public void draw(Graphics g) {
		if (getInnerColor() != null) {
			fill(g);
		}
		
		g.setColor(getColor());
		g.drawRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.getWidth(), this.height);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			
			g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() - 3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() - 3 + getWidth(), this.getUpperLeftPoint().getY() - 3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() - 3 + getHeight(), 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() + getWidth() - 3, this.getUpperLeftPoint().getY() + getHeight() - 3, 6, 6);
		}
		
		g.setColor(Color.BLACK);
	}

	@Override
	public void moveBy(int byX, int byY) {
		upperLeftPoint.moveBy(byX, byY);
		
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Rectangle) {
			return (int) (this.area() - ((Rectangle) o).area());
		}
		return 0;
	}
	
	public boolean contains(int x, int y) {
		if (this.getUpperLeftPoint().getX() <= x 
				&& x <= this.getUpperLeftPoint().getX() + width
				&& this.getUpperLeftPoint().getY() <= y
				&& y <= this.getUpperLeftPoint().getY() + height) {
			return true;
		} else {
			return false;
		}
	}

	public boolean contains(Point p) {
		if (this.getUpperLeftPoint().getX() <= p.getX()
				&& p.getX() <= this.getUpperLeftPoint().getX() + width
				&& this.getUpperLeftPoint().getY() <= p.getY()
				&& p.getY() <= this.getUpperLeftPoint().getY() + height) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			if (this.upperLeftPoint.equals(r.getUpperLeftPoint()) && this.height == r.getHeight()
					&& this.width == r.getWidth()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double area() {
		return width * height;
	}
	
	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}
	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setColor(Color c) {
		super.setColor(c);
	}
	
	@Override
	public String toString() {
		return "Rectangle:Upper left point-" + upperLeftPoint +
				",height=" + height +
				",width=" + width + 
				",outline color=" + getColor().getRGB() +
				",inner color=" + getInnerColor().getRGB() +
				",selected=" + isSelected();
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.getWidth(), this.height);
	}
	
	@Override
	public Rectangle clone() {
		Rectangle rectangle = new Rectangle();
		rectangle.setUpperLeftPoint(new Point(upperLeftPoint.getX(),upperLeftPoint.getY()));		
		rectangle.setHeight(height);
		rectangle.setWidth(width);
		rectangle.setColor(getColor());
		rectangle.setInnerColor(getInnerColor());
		rectangle.setSelected(isSelected());
		
		return rectangle;
	}
	

}
