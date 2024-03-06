package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Circle;
import model.HexagonAdapter;
import model.Point;

public class DlDialogHexagon extends JDialog {
	
	private JLabel lblX;
	private JTextField txtX;
	private JLabel lblY;
	private JTextField txtY;
	private JLabel lblradius;
	private JTextField txtradius;
	private JButton btncolor;
	private Color color; 
	private Color fillColor;
	private JButton btnfillColor;
	
	private JPanel pX;
	private JPanel pY;
	private JPanel pRadius;
	private JPanel pcolor;
	
	private HexagonAdapter hexagon = null;
	
	public DlDialogHexagon() {
		
		setTitle("Circle dialog");
		setModal(true);
		lblX =new JLabel("X:");
		lblY=new JLabel("Y:");
		lblradius=new JLabel("Poluprecnik:");
		txtX=new JTextField(20);
		txtY=new JTextField(20);
		txtradius=new JTextField(20);	
		
		pX=new JPanel();
		pY=new JPanel();
		pRadius=new JPanel();
		pcolor=new JPanel();
		btncolor=new JButton("Boja");
		btncolor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				color = JColorChooser.showDialog(null, "Choose a color", color);
				btncolor.setBackground(color);                
				
			}
			
		});
		btnfillColor=new JButton("Boja povrsine");
		btnfillColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				fillColor = JColorChooser.showDialog(null, "Choose a color", fillColor);
				btnfillColor.setBackground(fillColor);                	
			}
			
		});
				
		
		pX.add(lblX);
		pX.add(txtX);
		pY.add(lblY);
		pY.add(txtY);
		pcolor.add(btncolor);
		pcolor.add(btnfillColor);
		pRadius.add(lblradius);
		pRadius.add(txtradius);
		JButton OK=new JButton("OK");
		OK.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				
				String X = txtX.getText();
				String Y = txtY.getText();
				String radius= txtradius.getText();
				
				try {
					int x = Integer.parseInt(X);
					int y = Integer.parseInt(Y);
					int radiusInt = Integer.parseInt(radius);
					
					hexagon = new HexagonAdapter(new Point(x, y), radiusInt);					
					hexagon.setOutlineColor(btncolor.getBackground());
					hexagon.setInnerColor(btnfillColor.getBackground());
					
					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(DlDialogHexagon.this,"Sva polja moraju biti popunjena brojevima","Upozorenje",JOptionPane.WARNING_MESSAGE);
					return;
				}					
			}
			
		});
		
		JPanel contentPane = new JPanel();
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);		
		contentPane.setLayout(boxlayout);
		
		contentPane.add(pX);
		contentPane.add(pY);	
		contentPane.add(pRadius);
		contentPane.add(pcolor);
		contentPane.add(OK);
		
		
		setContentPane(contentPane);
		setMinimumSize(new Dimension(400, 300));
		
	}
	
	
	public HexagonAdapter getHexagon() {
		return this.hexagon;
	}
	
	public void openInAddingMode(int x, int y, Color edgeColor, Color innerColor) {
		txtX.setText(String.valueOf(x));
		txtX.setEnabled(false);
		
		txtY.setText(String.valueOf(y));
		txtY.setEnabled(false);
		
		btncolor.setBackground(edgeColor);
		btnfillColor.setBackground(innerColor);
		
		setVisible(true);
	}
	
	public void openInEditingMode(HexagonAdapter hexagonToEdit) {
		
		txtradius.setText(String.valueOf(hexagonToEdit.getRadius()));
		txtY.setText(String.valueOf(hexagonToEdit.getY()));
		txtX.setText(String.valueOf(hexagonToEdit.getX()));
		btncolor.setBackground(hexagonToEdit.getColor());
		color = hexagonToEdit.getColor();
		btnfillColor.setBackground(hexagonToEdit.getInnerColor());
		fillColor = hexagonToEdit.getInnerColor();
		
		setVisible(true);	
	}
	
}
