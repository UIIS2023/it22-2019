package app;

import javax.swing.JFrame;

import controller.DrawingController;
import model.DrawingModel;
import view.FrmDrawing;

public class App {
	
	public static void main(String[] args) {
		DrawingModel drawingModel = new DrawingModel();
		
		FrmDrawing frmDrawing = new FrmDrawing();
		frmDrawing.getView().setModel(drawingModel);
		frmDrawing.setSize(800, 600);
		frmDrawing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDrawing.setVisible(true);
		
		DrawingController drawingControler = new DrawingController(drawingModel, frmDrawing);
		drawingControler.addObserver(frmDrawing);
		frmDrawing.setController(drawingControler);
	}
}
