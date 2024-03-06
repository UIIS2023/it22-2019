package save;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SaveLog implements Save {

	private DefaultListModel<String> listModel;

	public SaveLog() {
	
	}
	
	public SaveLog(DefaultListModel<String> model) {
		this.listModel = model;
	}
	
	@Override
	public void save(String path) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < listModel.size(); i++) {
			list.add(listModel.get(i));
		}
		
		try {
			FileWriter fw = new FileWriter(path);
			
			for(String s: list) {
				fw.write(s + System.lineSeparator());
			}
			
			fw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Not found", "Error", JOptionPane.WARNING_MESSAGE);
		}
		
	}

	@Override
	public String saveAs() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < listModel.size(); i++) {
			list.add(listModel.get(i));
		}
		
		JFileChooser jFileChooser = new JFileChooser(new File("C:\\"));
		jFileChooser.setDialogTitle("Save file");
		int result = jFileChooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			String path = jFileChooser.getSelectedFile().getAbsolutePath() + ".txt";
			try {
				FileWriter fw = new FileWriter(path);
				for(String s: list) {
					fw.write(s + System.lineSeparator());
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Saving failed.", "Failed!", JOptionPane.WARNING_MESSAGE);
				return null;
			}
			return path;
		} else {
			return null;
		}
		
	}
	

}
