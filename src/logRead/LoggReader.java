package logRead;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
import model.Circle;
import model.Donut;
import model.DrawingModel;
import model.HexagonAdapter;
import model.Line;
import model.Point;
import model.Rectangle;
import model.Shape;


public class LoggReader {
	
	private DrawingModel model;
	private String filePath;
	private Deque<Command> undoList = new ArrayDeque<Command>();
	private Deque<Command> redoList = new ArrayDeque<Command>();
	
	public LoggReader(DrawingModel model) {
		this.model = model;
	}
	
	public List<String> read() {
		JFileChooser jFileChooser = new JFileChooser(new File("D:\\"));
		jFileChooser.setDialogTitle("Open file");
		jFileChooser.setFileFilter(new FileNameExtensionFilter("Text file", "txt"));
		
		List<String> loadedCommandsAsStrings = new ArrayList<String>();
		int result = jFileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			filePath = jFileChooser.getSelectedFile().getAbsolutePath();
			try {
				BufferedReader buffer = new BufferedReader(new FileReader(filePath));
				
				String line;
				while((line = buffer.readLine()) != null) {
					loadedCommandsAsStrings.add(line);
				}
				
				buffer.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,"File not found","Error",JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Format problem","Error",JOptionPane.WARNING_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"File not loaded.","Error",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		return loadedCommandsAsStrings;
	}
	
	public Command parse(String line) throws Exception {
		Command command;
		if(line.equals("undo")) {
			command = new UndoCommand(undoList.getFirst());
			redoList.addFirst(undoList.getFirst());
			undoList.removeFirst();
		} else if(line.equals("redo")) {
			command = new RedoCommand(redoList.getFirst());
			undoList.addFirst(redoList.getFirst());
			redoList.removeFirst();
		} else {
			command = parseCommand(line);
			undoList.addFirst(command);
			redoList.clear();
		}
		
		return command;
	}
	
	public Command parseCommand(String line) throws Exception {
		String command = line.split("_")[0];
		String withoutCommand = line.split("_")[1];
		if(command.equals("Add")) {
			return parseAdd(withoutCommand);
		} else if(command.equals("Remove")) {
			return parseRemove(withoutCommand);
		} else if (command.equals("Update")) {
			return parseUpdate(withoutCommand);
		} else if (command.equals("One step to back")) {
			return parseOneStepBack(withoutCommand);
		} else if (command.equals("One step to front")) {
			return parseOneStepToFront(withoutCommand);
		} else if (command.equals("Bring to end")) {
			return parseBringToEnd(withoutCommand);
		} else if (command.equals("Bring to front")) {
			return parseBringToFront(withoutCommand);
		} else if (command.equals("Select")) {
			return parseSelect(withoutCommand);
		} else if (command.equals("Unselect")) {
			return parseDeselect(withoutCommand);
		}
		return null;
	}
	
	
	private Command parseAdd(String text) throws Exception {
		Shape shape = parseShape(text);
		if (shape instanceof Point) {
			return new AddPointCmd((Point)shape, model);
		} else if (shape instanceof Line) {
			return new AddLineCmd((Line)shape, model);
		} else if (shape instanceof Rectangle) {
			return new AddRectangleCmd((Rectangle)shape, model);
		} else if (shape instanceof Donut) {
			return new AddDonutCmd((Donut)shape, model);
		} else if (shape instanceof Circle) {
			return new AddCircleCmd((Circle)shape, model);
		} else if (shape instanceof HexagonAdapter) {
			return new AddHexagonCmd((HexagonAdapter)shape, model);
		}
		
		return null;
	}
	
	private Command parseSelect(String text) throws Exception {
		Shape shape = parseShape(text);
		Command command = new SelectCmd(findShapeInModelListWhichIsEqual(shape));
		return command;
	}
	
	private Command parseDeselect(String text) throws Exception {
		String[] shapeStrings = text.split(";");
		List<Shape> helperList = new ArrayList<Shape>();
		for(String row : shapeStrings) {
			Shape shape = parseShape(row);
			helperList.add(findShapeInModelListWhichIsEqual(shape));
		}
		return new UnSelectCmd(helperList);
	}
	
	private Command parseBringToFront(String oldIndex) {
		return new BringToFrontCmd(model, Integer.parseInt(oldIndex));
	}
		
	private Command parseBringToEnd(String oldIndex) {
		return new BringToBackCmd(model, Integer.parseInt(oldIndex));	
	}
	
	private Command parseOneStepBack(String oldIndex) {
		return new MoveBackCmd(model, Integer.parseInt(oldIndex));
	}
	
	private Command parseOneStepToFront(String oldIndex) {
		return new MoveFrontCmd(model, Integer.parseInt(oldIndex));
	}
	
	
	private Command parseUpdate(String text) throws Exception{
		Command command = null;
		Shape oldShape = parseShape(text.split(";")[0]);
		Shape newShape = parseShape(text.split(";")[1]);
		if (oldShape instanceof Point && newShape instanceof Point) {
			for (Shape shape : this.model.getShapes()) {
				if (shape instanceof Point && ((Point) oldShape).equals((Point)shape)) {
					oldShape = shape;
				}
			}
			command = new ModifyPointCmd((Point)oldShape, (Point)newShape);
		} else if(oldShape instanceof Line && newShape instanceof Line) {
			for(Shape shape : this.model.getShapes()) {
				if(shape instanceof Line && ((Line) oldShape).equals(shape)) {
					oldShape = shape;
				}
			}
			command = new ModifyLineCmd((Line)oldShape, (Line)newShape);
		} else if(oldShape instanceof Rectangle && newShape instanceof Rectangle) {
			for(Shape shape : this.model.getShapes()) {
				if(shape instanceof Rectangle && ((Rectangle) oldShape).equals((Rectangle)shape)) {
					oldShape = shape;
				}
			}
			command = new ModifyRectangleCmd((Rectangle)oldShape, (Rectangle)newShape);
		} else if(oldShape instanceof Donut && newShape instanceof Donut) {
			for(Shape shape : this.model.getShapes()) {
				if(shape instanceof Donut && ((Donut) oldShape).equals((Donut)shape)) {
					oldShape = shape;
				}
			}
			command = new ModifyDonutCmd((Donut)oldShape, (Donut)newShape);
		} else if(oldShape instanceof Circle && newShape instanceof Circle) {
			for(Shape shape : this.model.getShapes()) {
				if(shape instanceof Circle && ((Circle) oldShape).equals((Circle)shape)) {
					oldShape = shape;
				}
			}
			command = new ModifyCircleCmd((Circle)oldShape, (Circle)newShape);
		} else if(oldShape instanceof HexagonAdapter && newShape instanceof HexagonAdapter) {
			for(Shape shape : this.model.getShapes()) {
				if(shape instanceof HexagonAdapter && ((HexagonAdapter) oldShape).equals((HexagonAdapter)shape)) {
					oldShape = shape;
				}
			}
			command = new ModifyHexagonCmd((HexagonAdapter)oldShape,(HexagonAdapter)newShape);
		}
		return command;
	}
	
	private Command parseRemove(String text)  throws Exception{
		String[] shapeStrings = text.split(";");
		List<Shape> helperList = new ArrayList<Shape>();
		for(String row : shapeStrings) {
			Shape shape = parseShape(row);
			helperList.add(findShapeInModelListWhichIsEqual(shape));
		}
		return new DeleteCmd(helperList, model);
	}
	
	private Shape parseShape(String text) throws Exception {
		String shape = text.split(":")[0];
		String[] props = text.split(",");
		if(shape.equals("Point")) {
			Point point = new Point();
			point.setX(Integer.parseInt(props[0].split("=")[1]));
			point.setY(Integer.parseInt(props[1].split("=")[1]));
			point.setColor(new Color(Integer.parseInt(props[2].split("=")[1])));
			point.setSelected(Boolean.parseBoolean(props[3].split("=")[1]));	
			return point;
		} else if(shape.equals("Line")) {
			Line line = new Line();
			line.setStartPoint(new Point(Integer.parseInt(props[0].split("=")[1]),Integer.parseInt(props[1].split("=")[1])));
			line.setEndPoint(new Point(Integer.parseInt(props[2].split("=")[1]),Integer.parseInt(props[3].split("=")[1])));
			line.setColor(new Color(Integer.parseInt(props[4].split("=")[1])));
			line.setSelected(Boolean.parseBoolean(props[5].split("=")[1]));
			return line;
		} else if(shape.equals("Rectangle")) {
			Rectangle rectangle = new Rectangle();
			rectangle.setUpperLeftPoint(new Point(Integer.parseInt(props[0].split("=")[1]),Integer.parseInt(props[1].split("=")[1])));
			rectangle.setHeight(Integer.parseInt(props[2].split("=")[1]));
			rectangle.setWidth(Integer.parseInt(props[3].split("=")[1]));
			rectangle.setColor(new Color(Integer.parseInt(props[4].split("=")[1])));
			rectangle.setInnerColor(new Color(Integer.parseInt(props[5].split("=")[1])));
			rectangle.setSelected(Boolean.parseBoolean(props[6].split("=")[1]));
			return rectangle;
		} else if(shape.equals("Circle")) {
			Circle circle = new Circle();
			circle.setCenter(new Point(Integer.parseInt(props[0].split("=")[1]),Integer.parseInt(props[1].split("=")[1])));
			circle.setRadius(Integer.parseInt(props[2].split("=")[1]));
			circle.setColor(new Color(Integer.parseInt(props[3].split("=")[1])));
			circle.setInnerColor(new Color(Integer.parseInt(props[4].split("=")[1])));
			circle.setSelected(Boolean.parseBoolean(props[5].split("=")[1]));
			return circle;
		} else if(shape.equals("Donut")) {
			Donut donut = new Donut();
			donut.setCenter(new Point(Integer.parseInt(props[0].split("=")[1]),Integer.parseInt(props[1].split("=")[1])));
			donut.setRadius(Integer.parseInt(props[2].split("=")[1]));
			donut.setInnerRadius(Integer.parseInt(props[3].split("=")[1]));
			donut.setColor(new Color(Integer.parseInt(props[4].split("=")[1])));
			donut.setInnerColor(new Color(Integer.parseInt(props[5].split("=")[1])));
			donut.setSelected(Boolean.parseBoolean(props[6].split("=")[1]));
			return donut;
		} else if(shape.equals("Hexagon")) {
			HexagonAdapter hexagonAdapter = new HexagonAdapter(
					new Point(Integer.parseInt(props[0].split("=")[1]),Integer.parseInt(props[1].split("=")[1])),
					Integer.parseInt(props[2].split("=")[1]));
			hexagonAdapter.setOutlineColor(new Color(Integer.parseInt(props[3].split("=")[1])));
			hexagonAdapter.setInnerColor(new Color(Integer.parseInt(props[4].split("=")[1])));
			hexagonAdapter.setSelected(Boolean.parseBoolean(props[5].split("=")[1]));
			return hexagonAdapter;
		}
		return null;
	}
	
	private Shape findShapeInModelListWhichIsEqual(Shape shape) {
		for (Shape s : this.model.getShapes()) {
			if (shape.equals(s)) {
				return s;
			}
		}
		
		return null;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	
}
