package controller;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import command.AddCircleCmd;
import command.AddDonutCmd;
import command.AddHexagonCmd;
import command.AddLineCmd;
import command.AddPointCmd;
import command.AddRectangleCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.DeleteCmd;
import command.ModifyCircleCmd;
import command.ModifyDonutCmd;
import command.ModifyHexagonCmd;
import command.ModifyLineCmd;
import command.ModifyPointCmd;
import command.ModifyRectangleCmd;
import command.MoveBackCmd;
import command.MoveFrontCmd;
import command.SelectCmd;
import command.UnSelectCmd;
import helper.EnableDisable;
import logRead.LoggReader;
import model.Circle;
import model.Donut;
import model.DrawingModel;
import model.HexagonAdapter;
import model.Line;
import model.Point;
import model.Rectangle;
import model.Shape;
import save.SaveBinary;
import save.SaveLog;
import save.Strategy;
import view.DIDialogCircle;
import view.DIDialogDonut;
import view.DIDialogLine;
import view.DIDialogPoint;
import view.DIDialogRectangle;
import view.DlDialogHexagon;
import view.FrmDrawing;

public class DrawingController extends Observable {

	private FrmDrawing frame;
	private DrawingModel model;
	Deque<Command> redoStack = new ArrayDeque<Command>();
	Deque<Command> undoStack = new ArrayDeque<Command>();

	private Point lineStart;
	
	String filePath;
	Strategy strategy = new Strategy();
	
	List<String> comandsFile = new ArrayList<String>();
	private LoggReader logReader;

	public DrawingController(DrawingModel model, FrmDrawing frame) {

		this.frame = frame;
		this.model = model;
		logReader = new LoggReader(model);
	}

	public void mouseClicked(MouseEvent event) {

		if (frame.getMode().equals("Dodavanje")) {

			switch (frame.getChoosenShape()) {
				case "Tacka":
					Point point = new Point(event.getX(), event.getY());
					point.setColor(frame.getBtnEdgeColor().getBackground());
					Command cmd = new AddPointCmd(point, model);
					
					cmd.execute();
					undoStack.addFirst(cmd);
					redoStack.clear();
					frame.getDlm().addElement(cmd.toString());
					frame.getExecuteLog().setEnabled(false);
					
					break;
				case "Linija":
					if (lineStart != null) {
						Line line = new Line(lineStart, new Point(event.getX(), event.getY()));
						line.setColor(frame.getBtnEdgeColor().getBackground());
						Command cmdLine = new AddLineCmd(line, model);
						cmdLine.execute();
						undoStack.addFirst(cmdLine);
						redoStack.clear();
						frame.getDlm().addElement(cmdLine.toString());
						frame.getExecuteLog().setEnabled(false);
						
						lineStart = null;
					} else {
						lineStart = new Point(event.getX(), event.getY());
					}
					
					break;
				case "Krug":
					DIDialogCircle dialogCircle = new DIDialogCircle();
					dialogCircle.openInAddingMode(event.getX(), event.getY(), frame.getBtnEdgeColor().getBackground(), frame.getBtnInnerColor().getBackground());
					if (dialogCircle.getCircle() == null) { 
						break;
					}
					
					Command cmdCirlce = new AddCircleCmd(dialogCircle.getCircle(), model);
					cmdCirlce.execute();
					redoStack.clear();
					undoStack.addFirst(cmdCirlce);
					frame.getDlm().addElement(cmdCirlce.toString());
					frame.getExecuteLog().setEnabled(false);
					
					break;
			case "Pravougaonik":
				DIDialogRectangle dialogRectangle = new DIDialogRectangle();
				dialogRectangle.openInAddingMode(event.getX(), event.getY(), frame.getBtnEdgeColor().getBackground(), frame.getBtnInnerColor().getBackground());
				
				if (dialogRectangle.getRectangle() == null) { 
					break;
				}
				
				AddRectangleCmd cmdRect = new AddRectangleCmd(dialogRectangle.getRectangle(), model);
				cmdRect.execute();
				undoStack.addFirst(cmdRect);
				redoStack.clear();
				frame.getDlm().addElement(cmdRect.toString());
				frame.getExecuteLog().setEnabled(false);
				
				break;
			case "Krug sa rupom":
				DIDialogDonut dialogDonut = new DIDialogDonut();
				dialogDonut.openInAddingMode(event.getX(), event.getY(), frame.getBtnEdgeColor().getBackground(), frame.getBtnInnerColor().getBackground());
				
				if (dialogDonut.getDonut() == null) {
					break;
				}
				
				Command cmdDonut = new AddDonutCmd(dialogDonut.getDonut(), model);
				cmdDonut.execute();
				undoStack.addFirst(cmdDonut);
				redoStack.clear();
				frame.getDlm().addElement(cmdDonut.toString());
				frame.getExecuteLog().setEnabled(false);
				
				break;
			case "Heksagon":
				DlDialogHexagon dialogHexagon= new DlDialogHexagon();
				dialogHexagon.openInAddingMode(event.getX(), event.getY(), frame.getBtnEdgeColor().getBackground(), frame.getBtnInnerColor().getBackground());
				
				if (dialogHexagon.getHexagon() == null) {
					break;
				}
				
				Command cmdHex = new AddHexagonCmd(dialogHexagon.getHexagon(), model);
				cmdHex.execute();
				undoStack.addFirst(cmdHex);
				redoStack.clear();
				frame.getDlm().addElement(cmdHex.toString());
				frame.getExecuteLog().setEnabled(false);
				
				break;
			}
		} else if (frame.getMode().equals("Selekcija")) {
			boolean shapeContainingPointFound = false;
			List<Shape> list = model.getShapes();
			for (int i= list.size() - 1; i >= 0; i--) {
				Shape shape = list.get(i);
				if (shape.contains(event.getX(), event.getY())) {
					shapeContainingPointFound = true;
					if(shape.isSelected() == false) {
						SelectCmd cmd = new SelectCmd(shape);
						cmd.execute();
						undoStack.addFirst(cmd);
						redoStack.clear();
						frame.getDlm().addElement(cmd.toString());
						frame.getExecuteLog().setEnabled(false);
						break;
					}
					else {
						List<Shape> deselectList = new ArrayList<Shape>();
						deselectList.add(shape);
						UnSelectCmd cmd = new UnSelectCmd(deselectList);
						cmd.execute();
						undoStack.addFirst(cmd);
						redoStack.clear();
						frame.getDlm().addElement(cmd.toString());
						frame.getExecuteLog().setEnabled(false);
						break;
					}
				}
			}
			
			if (shapeContainingPointFound == false) {
				List<Shape> deselectList = new ArrayList<Shape>();
				for (Shape s : model.getShapes()) {
					if (s.isSelected()) {
						deselectList.add(s);
					}
				}
				
				if(deselectList.size() > 0) {
					UnSelectCmd cmd = new UnSelectCmd(deselectList);
					cmd.execute();
					redoStack.clear();
					undoStack.addFirst(cmd);
					frame.getDlm().addElement(cmd.toString());
					frame.getExecuteLog().setEnabled(false);
				}
			} 
		}
		
		
		frame.getView().repaint();
		informObserver();
	

	}
	
	public void deleteSelectedShapes() {
		int reply = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete element?", "Brisanje elementa", JOptionPane.YES_NO_OPTION);
        if (reply != JOptionPane.YES_OPTION) {	                    
        	return;
        }						
		ArrayList<Shape> toRemove = new ArrayList<Shape>();
		for (Shape s: model.getShapes()) {
			if (s.isSelected()) { 
				toRemove.add(s);
			}						
		}
		DeleteCmd cmd = new DeleteCmd(toRemove, model);
		cmd.execute();
		undoStack.addFirst(cmd);
		redoStack.clear();
		frame.getDlm().addElement(cmd.toString());
		frame.getExecuteLog().setEnabled(false);
		
		frame.getView().repaint();
		informObserver();
	}
	
	public void editShape() {
		Shape selectedShape = model.getSelectedShape();

		if (selectedShape != null) {
			if (selectedShape instanceof Point) {
				DIDialogPoint dialog = new DIDialogPoint();
				Point selectedPoint = (Point) selectedShape;
				
				dialog.openInEditingMode(selectedPoint);
				
				if (dialog.getPoint() == null) {
					return;
				}
				
				Point pointAfterEditing = dialog.getPoint();
				ModifyPointCmd cmd = new ModifyPointCmd(selectedPoint, pointAfterEditing);
				cmd.execute();
				undoStack.addFirst(cmd);
				redoStack.clear();
				frame.getDlm().addElement(cmd.toString());
				frame.getExecuteLog().setEnabled(false);
			} else if (selectedShape instanceof Line) {
				DIDialogLine dialog = new DIDialogLine();
				Line selectedLine = (Line) selectedShape;
				
				dialog.openInEditingMode(selectedLine);
				
				if (dialog.getLine() == null) {
					return;
				}
				
				ModifyLineCmd cmd = new ModifyLineCmd(selectedLine, dialog.getLine());
				cmd.execute();
				undoStack.addFirst(cmd);
				frame.getDlm().addElement(cmd.toString());
				frame.getExecuteLog().setEnabled(false);
			} else if (selectedShape instanceof Rectangle) {
				DIDialogRectangle dialog = new DIDialogRectangle();
				Rectangle selectedRectangle = (Rectangle) selectedShape; 
				
				dialog.openInEditingMode(selectedRectangle);
				
				if (dialog.getRectangle() == null) {
					return;
				}
				
				Rectangle rectangleAfterEditing = dialog.getRectangle();
				ModifyRectangleCmd cmd = new ModifyRectangleCmd(selectedRectangle, rectangleAfterEditing);
				cmd.execute();
				undoStack.addFirst(cmd);
				redoStack.clear();
				frame.getDlm().addElement(cmd.toString());
				frame.getExecuteLog().setEnabled(false);
			} else if (selectedShape instanceof Circle) {
				DIDialogCircle dialog = new DIDialogCircle();
				Circle selectedCircle = (Circle) selectedShape;
				
				dialog.openInEditingMode(selectedCircle);
				
				if (dialog.getCircle() == null) {
					return;
				}
				
				Circle circleAfterEditing = dialog.getCircle();
				ModifyCircleCmd cmd = new ModifyCircleCmd(selectedCircle, circleAfterEditing);
				cmd.execute();
				redoStack.clear();
				undoStack.addFirst(cmd);
				frame.getDlm().addElement(cmd.toString());
				frame.getExecuteLog().setEnabled(false);
			} else if (selectedShape instanceof Donut) {
				DIDialogDonut dialog = new DIDialogDonut();
				Donut selectedDonut = (Donut) selectedShape;
				dialog.openInEditingMode(selectedDonut);
				
				if (dialog.getDonut() == null) {
					return;
				}
				
				Donut donutAfterEditing = dialog.getDonut();
				ModifyDonutCmd cmd = new ModifyDonutCmd(selectedDonut, donutAfterEditing);
				cmd.execute();
				redoStack.clear();
				undoStack.addFirst(cmd);
				frame.getDlm().addElement(cmd.toString());
				frame.getExecuteLog().setEnabled(false);
			} else if (selectedShape instanceof HexagonAdapter) {
				DlDialogHexagon dialog = new DlDialogHexagon();
				HexagonAdapter selectedHexagon = (HexagonAdapter) selectedShape;
				
				dialog.openInEditingMode(selectedHexagon);
				
				if (dialog.getHexagon() == null) {
					return;
				}
				
				HexagonAdapter hexagonAfterEditing = dialog.getHexagon();
				ModifyHexagonCmd cmd = new ModifyHexagonCmd(selectedHexagon, hexagonAfterEditing);
				cmd.execute();
				redoStack.clear();
				undoStack.addFirst(cmd);
				frame.getDlm().addElement(cmd.toString());
				frame.getExecuteLog().setEnabled(false);
			}
		}

	
		frame.getView().repaint();
		informObserver();

	}
	
	public void informObserver() {
		EnableDisable enableDisable = new EnableDisable();
		
		enableDisable.setEnableSelection(model.getShapes().size() > 0);
		enableDisable.setEnableModification(model.getSelectedShapes().size() == 1);
		enableDisable.setEnableDelete(model.getSelectedShapes().size() >= 1);
		enableDisable.setEnableMoveBack(model.getSelectedShapes().size() == 1 && model.getShapes().indexOf(model.getSelectedShape()) != 0);
		enableDisable.setEnableBringToBack(model.getSelectedShapes().size() == 1 && model.getShapes().indexOf(model.getSelectedShape()) != 0);
		enableDisable.setEnableMoveFront(model.getSelectedShapes().size() == 1 && model.getShapes().indexOf(model.getSelectedShape()) != model.getShapes().size() - 1);
		enableDisable.setEnableBringToFront(model.getSelectedShapes().size() == 1 && model.getShapes().indexOf(model.getSelectedShape()) != model.getShapes().size() - 1);
		enableDisable.setEnableUndo(undoStack.size() > 0);
		enableDisable.setEnableRedo(redoStack.size() > 0);
		enableDisable.setEnableSave(strategy.getSaver() != null);
		setChanged();
		notifyObservers(enableDisable);
	}

	public void bringToBack() {
		Shape selectedShape = model.getSelectedShape();
		int index = model.getShapes().indexOf(selectedShape);
		BringToBackCmd cmd = new BringToBackCmd(model, index);
		cmd.execute();
		undoStack.addFirst(cmd);
		redoStack.clear();
		frame.getView().repaint();
		informObserver();
		frame.getDlm().addElement(cmd.toString());
		frame.getExecuteLog().setEnabled(false);
	}

	public void bringToFront() {
		Shape selectedShape = model.getSelectedShape();
		int index = model.getShapes().indexOf(selectedShape);
		BringToFrontCmd cmd = new BringToFrontCmd(model, index);
		cmd.execute();
		undoStack.addFirst(cmd);
		redoStack.clear();
		frame.getView().repaint();
		informObserver();
		frame.getDlm().addElement(cmd.toString());
		frame.getExecuteLog().setEnabled(false);
		
	}

	public void moveToFront() {
		Shape selectedShape = model.getSelectedShape();
		int idx = model.getShapes().indexOf(selectedShape);
		MoveFrontCmd cmd = new MoveFrontCmd(model, idx);
		cmd.execute();
		undoStack.addFirst(cmd);
		redoStack.clear();
		frame.getView().repaint();
		informObserver();
		frame.getDlm().addElement(cmd.toString());
		frame.getExecuteLog().setEnabled(false);
		
	}

	public void moveToBack() {
		Shape selectedShape = model.getSelectedShape();
		int idx = model.getShapes().indexOf(selectedShape);
		MoveBackCmd cmd = new MoveBackCmd(model, idx);
		cmd.execute();
		undoStack.addFirst(cmd);
		redoStack.clear();
		frame.getView().repaint();
		informObserver();
		frame.getDlm().addElement(cmd.toString());
		frame.getExecuteLog().setEnabled(false);
	}
	
	public void undoCommand() {
		undoStack.getFirst().unexecute();
		redoStack.addFirst(undoStack.getFirst());
		undoStack.removeFirst();
		informObserver();
		frame.repaint();
		informObserver();
		frame.getDlm().addElement("undo");
		frame.getExecuteLog().setEnabled(false);
	}

	public void redoCommand() {
		redoStack.getFirst().execute();
		undoStack.addFirst(redoStack.getFirst());
		redoStack.removeFirst();
		informObserver();
		frame.repaint();
		informObserver();
		frame.getDlm().addElement("redo");
		frame.getExecuteLog().setEnabled(false);
	}

	public void openBinary() {
		JFileChooser jFileChooser = new JFileChooser(new File("C:\\"));
		jFileChooser.setDialogTitle("Open file");
		
		int result = jFileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			filePath = jFileChooser.getSelectedFile().getAbsolutePath();
			
			ObjectInputStream is = null;
			try {
				is = new ObjectInputStream(new FileInputStream(filePath));
				model.setShapes((List<Shape>)is.readObject());
				JOptionPane.showMessageDialog(null,"Successfully loaded", "Success",JOptionPane.INFORMATION_MESSAGE);
				frame.repaint();
				redoStack.clear();
				undoStack.clear();
				frame.setDlm(new DefaultListModel<String>());
				strategy.setSaver(new SaveBinary(model));
				informObserver();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"File not loaded.","Error!",JOptionPane.WARNING_MESSAGE);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"File not loaded.","Error!",JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"File not loaded.","Error!",JOptionPane.WARNING_MESSAGE);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"File not loaded.","Error!",JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		
	}

	public void saveBinary() {
		SaveBinary saveBinary = new SaveBinary(model);
		filePath = saveBinary.saveAs();
		strategy.setSaver(saveBinary);
		informObserver();
	}

	public void openLog() {
		comandsFile = logReader.read();
		filePath = logReader.getFilePath();
		frame.getExecuteLog().setEnabled(true);
		strategy.setSaver(new SaveLog(frame.getDlm()));
	}

	public void saveLog() {
		SaveLog saveLog = new SaveLog(frame.getDlm());
		filePath = saveLog.saveAs();
		strategy.setSaver(saveLog);
		informObserver();
	}

	public void save() {
		strategy.save(filePath);
	}

	public void executeLog() {
		try {
			Command cmd = logReader.parse(comandsFile.get(0));
			cmd.execute();
			comandsFile.remove(0);
			
			undoStack.addFirst(cmd);
			
			frame.getDlm().addElement(cmd.toString());;
			frame.getView().repaint();
			informObserver();
			
			if (comandsFile.size() == 0) {
				frame.getExecuteLog().setEnabled(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
