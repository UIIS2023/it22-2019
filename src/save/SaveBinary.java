package save;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.DrawingModel;
import model.Shape;

public class SaveBinary implements Save {
	
	private DrawingModel model;
	
	public SaveBinary() {
		
	}

	public SaveBinary(DrawingModel model) {
		this.model = model;
	}
	
	@Override
	public void save(String path) {
		List<Shape> list = new ArrayList<Shape>();
		for(Shape s : model.getShapes()) {
			list.add(s);
		}
		
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));
			os.writeObject(list);
			os.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"Not found", "Error",JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Error", "Error",JOptionPane.WARNING_MESSAGE);
		}
	}


	@Override
	public String saveAs() {
		JFileChooser jFileChooser = new JFileChooser(new File("C:\\"));
		
		int result = jFileChooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			String path = jFileChooser.getSelectedFile().getAbsolutePath();
			try {
				ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));
				os.writeObject(model.getShapes());	
				os.close();
				JOptionPane.showMessageDialog(null,"Successfully saved", "Success",JOptionPane.INFORMATION_MESSAGE);
				return path;
			}catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Saving failed", "Error",JOptionPane.WARNING_MESSAGE);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Saving failed", "Error",JOptionPane.WARNING_MESSAGE);
				return null;
			}
		}
		
		return null;
	}

}
