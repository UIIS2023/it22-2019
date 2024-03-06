package save;

import java.util.List;

public class Strategy implements Save {

	private Save saver;
	
	public Strategy() {
	}

	public Strategy(Save saver) {
		this.saver = saver;
	}
	
	public void setSaver(Save saver) {
		this.saver = saver;
	}
	
	public Save getSaver() {
		return this.saver;
	}
	
	@Override
	public String saveAs() {
		return saver.saveAs();
	}

	@Override
	public void save(String path) {
		saver.save(path);
	}

}
