package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.DrawingController;
import helper.EnableDisable;
import model.Circle;
import model.Donut;
import model.Line;
import model.Point;
import model.Rectangle;
import model.Shape;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Menu;

public class FrmDrawing extends JFrame implements Observer {

    private DrawingView view = new DrawingView();
    private DrawingController controller;
    
    private JPanel toolBar;
	private JToggleButton btnDodavanje;
	private JButton btnBrisanje;
	private JButton btnIzmeni;
	private JToggleButton btnSelektovanje;
	private ArrayList<Shape> shapes;
	private JPanel contentPane;
	private JComboBox<String> shapeChooser; 
	private JButton undoBtn;
	private JButton redoBtn;
	private JButton btnEdgeColor;
	private JButton btnInnerColor;
	private JButton moveFront;
	private JButton moveBack;
	private JButton bringToFront;
	private JButton bringToBack;
	private JButton executeLog;
	private JPanel header;
	
    private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem binaryOpen;
	private JMenuItem binarySave;
	private JMenuItem logOpen;
	private JMenuItem logSave;
	private JMenuItem save;

    private String mode = "Dodavanje";
    
    private JList logList;
    private DefaultListModel<String> dlm=new DefaultListModel<>();

	public FrmDrawing() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setSize(new Dimension(1024,768));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.EAST);
		logList = new JList();

		logList.setModel(dlm);
		scrollPane.setViewportView(logList);

		shapes=new ArrayList<Shape>();
		
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		toolBar=new JPanel();
		btnDodavanje=new JToggleButton("Dodaj");
		btnDodavanje.setSelected(true);
		btnDodavanje.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				btnSelektovanje.setSelected(false);
				btnIzmeni.setEnabled(false);
				btnBrisanje.setEnabled(false);
				clearSelection();
				mode = "Dodavanje";
			}			

		});
		btnBrisanje=new JButton("Brisanje");
		btnBrisanje.setEnabled(false);
		btnBrisanje.addActionListener(new ActionListener() {

			@Override
		public void actionPerformed(ActionEvent e) {
			controller.deleteSelectedShapes();
		}

		});
		btnIzmeni=new JButton("Izmena");
		btnIzmeni.setEnabled(false);
		btnIzmeni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.editShape();
			}

		});
		btnSelektovanje=new JToggleButton("Selektovanje");
		btnSelektovanje.setEnabled(false);
		btnSelektovanje.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				btnDodavanje.setSelected(false);
				mode = "Selekcija";
			}

		});
		

		undoBtn = new JButton("Undo");
		undoBtn.setEnabled(false);
		undoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				controller.undoCommand();
			}

		});
		redoBtn = new JButton("Redo");
		redoBtn.setEnabled(false);
		redoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				controller.redoCommand();
			}

		});
		
		String [] options= {
				"Tacka",
				"Linija",
				"Pravougaonik",
				"Krug",
				"Krug sa rupom", 
				"Heksagon"};
		shapeChooser = new JComboBox<String>(options);
		
		btnEdgeColor = new JButton("Edge color");
		btnEdgeColor.setBackground(Color.BLACK);
		btnEdgeColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				Color color = JColorChooser.showDialog(null, "Choose a color", btnEdgeColor.getBackground());
				if (color != null) {
					btnEdgeColor.setBackground(color);
				}
			}

		});
		
		btnInnerColor = new JButton("Inner color");
		btnInnerColor.setBackground(Color.WHITE);
		btnInnerColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				Color color = JColorChooser.showDialog(null, "Choose a color", btnInnerColor.getBackground());
				if (color != null) {
					btnInnerColor.setBackground(color);
				}
			}

		});
		
		bringToBack = new JButton("Bring to back");
		bringToBack.setEnabled(false);
		bringToBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				controller.bringToBack();
			}

		});
		
		bringToFront = new JButton("Bring to front");
		bringToFront.setEnabled(false);
		bringToFront.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				controller.bringToFront();
			}

		});
		
		moveFront = new JButton("Move to front");
		moveFront.setEnabled(false);
		moveFront.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				controller.moveToFront();
			}

		});
		
		moveBack = new JButton("Move to back");
		moveBack.setEnabled(false);
		moveBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				controller.moveToBack();
			}

		});
		
		executeLog = new JButton("Execute log");
		executeLog.setEnabled(false);
		executeLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				controller.executeLog();
			}

		});
		
		binaryOpen = new JMenuItem("Open binary");
		binaryOpen.setFont(new Font("Arial", Font.PLAIN, 16));
		binaryOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.openBinary();
			}
		});
		
		binarySave = new JMenuItem("Save binary");
		binarySave.setFont(new Font("Arial", Font.PLAIN, 16));
		
		binarySave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveBinary();
			}
		});
		
		logOpen = new JMenuItem("Open log");
		logOpen.setFont(new Font("Arial", Font.PLAIN, 16));
		logOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.openLog();
			}
		});
		
		logSave = new JMenuItem("Save log");
		logSave.setFont(new Font("Arial", Font.PLAIN, 16));
		logSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		save = new JMenuItem("Save");
		save.setEnabled(false);
		save.setFont(new Font("Arial", Font.PLAIN, 16));
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.save();
			}
		});
		

		toolBar.add(shapeChooser);	
		toolBar.add(btnDodavanje);
		toolBar.add(btnBrisanje);
		toolBar.add(btnIzmeni);
		toolBar.add(btnSelektovanje);
		toolBar.add(undoBtn);
		toolBar.add(redoBtn);
		toolBar.add(btnEdgeColor);
		toolBar.add(btnInnerColor);
		toolBar.add(moveBack);
		toolBar.add(moveFront);
		toolBar.add(bringToBack);
		toolBar.add(bringToFront);
		toolBar.add(executeLog);
		
		toolBar.setBackground(Color.ORANGE);
		
		contentPane.add(toolBar, BorderLayout.NORTH);	
		contentPane.add(view, BorderLayout.CENTER);
		contentPane.add(scrollPane, BorderLayout.SOUTH);

		setContentPane(contentPane);
		
		menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(0, 0, 70, 0));
		setJMenuBar(menuBar);
		
		menu = new JMenu();
		menu.setText("Save&Read");		
		menuBar.add(menu);
		menu.add(binarySave);
		menu.add(binaryOpen);	
		menu.add(logSave);
		menu.add(logOpen);
		menu.add(save);
		
	}
		
	public DrawingView getView() {
		return view;
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public void clearSelection() {
		for (Shape s: shapes) {
			s.setSelected(false);
		}
		repaint();
	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}


	public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}

	public String getChoosenShape() {
		return shapeChooser.getSelectedItem().toString();
	}


	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public JButton getBtnBrisanje() {
		return btnBrisanje;
	}


	public JButton getBtnIzmeni() {
		return btnIzmeni;
	}

	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

	public void setBtnEdgeColor(JButton btnEdgeColor) {
		this.btnEdgeColor = btnEdgeColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}

	public DefaultListModel<String> getDlm() {
		return dlm;
	}

	public void setDlm(DefaultListModel<String> dlm) {
		this.dlm = dlm;
	}

	public JButton getExecuteLog() {
		return executeLog;
	}

	@Override
	public void update(Observable o, Object arg) {
		EnableDisable obj = (EnableDisable) arg;
		btnSelektovanje.setEnabled(obj.isEnableSelection());
		btnIzmeni.setEnabled(obj.isEnableModification());
		btnBrisanje.setEnabled(obj.isEnableDelete());
		moveFront.setEnabled(obj.isEnableMoveFront());
		moveBack.setEnabled(obj.isEnableMoveBack());
		bringToBack.setEnabled(obj.isEnableBringToBack());
		bringToFront.setEnabled(obj.isEnableBringToFront());
		undoBtn.setEnabled(obj.isEnableUndo());
		redoBtn.setEnabled(obj.isEnableRedo());
		save.setEnabled(obj.isEnableSave());
	}

	
}
