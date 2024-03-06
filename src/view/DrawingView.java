package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import model.DrawingModel;
import model.Shape;
import javax.swing.border.LineBorder;

public class DrawingView extends JPanel {
		
	private DrawingModel model;

	public DrawingView() {
		super();
		setBackground(Color.WHITE);
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (Shape shape : model.getShapes()) {
			shape.draw(g);
		}
	}
	
}
