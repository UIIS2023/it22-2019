package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;


public class Donut extends Circle {
	
	private int innerRadius;
	
	
	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius, Color fillColor) {
		super(center, radius, fillColor);
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color fillColor) {
		this(center, radius, innerRadius, fillColor);
		setSelected(selected);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        java.awt.Shape outer = new Ellipse2D.Double(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(), 2*getRadius(),2*getRadius());
        java.awt.Shape inner = new Ellipse2D.Double(getCenter().getX() - innerRadius, getCenter().getY() - innerRadius, 2*getInnerRadius(),2*getInnerRadius());

        Area donut = new Area( outer );
        donut.subtract( new Area(inner) );

        g2d.setColor(getInnerColor());
        g2d.fill(donut);
        g2d.setColor(getColor());
        g2d.draw(donut);

        g2d.dispose();
        
        if(isSelected()) {
        	super.drawSelection(g);
        	
        	g.setColor(Color.BLUE);
    		g.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX() + getInnerRadius() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX() - getInnerRadius() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX() - 3, getCenter().getY() + getInnerRadius() - 3, 6, 6);
    		g.drawRect(getCenter().getX() - 3, getCenter().getY() - getInnerRadius() - 3, 6, 6);
    		g.setColor(Color.BLACK);
        }
	}
	
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}
	
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			if (this.getCenter().equals(d.getCenter()) &&
					this.getRadius() == d.getRadius()
					&& this.innerRadius == d.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}
	
	@Override
	public String toString() {
		return "Donut:Center-" + getCenter() + 
				", radius=" + getRadius() + 
				",inner radius=" + innerRadius + 
				",outline color=" + getColor().getRGB() + 
				",inner color=" + getInnerColor().getRGB() + 
				",selected=" + isSelected(); 
	}
	

	@Override 
	public Donut clone() {
		Donut donut = new Donut();
		donut.setCenter(new Point(getCenter().getX(),getCenter().getY()));
		try {
			donut.setRadius(getRadius());
			donut.setInnerRadius(getInnerRadius());
		} catch(Exception e) {}
		donut.setColor(getColor());
		donut.setInnerColor(getInnerColor());
		donut.setSelected(isSelected());
		return donut;
	}
	
	
}
