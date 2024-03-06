package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Circle;
import model.Point;
import model.Rectangle;

public class DIDialogCircle extends JDialog {
	
	private JLabel lblX;
	private JTextField txtX;
	private JLabel lblY;
	private JTextField txtY;
	private JLabel lblradius;
	private JTextField txtradius;
	private JButton btncolor;
	private JButton btnfillColor;
	
	private JPanel pX;
	private JPanel pY;
	private JPanel pRadius;
	private JPanel pcolor;
	
	private Circle circle = null;
	
	public DIDialogCircle() {
		
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
				
				Color color = JColorChooser.showDialog(null, "Choose a color", btncolor.getBackground());
				if (color != null) {
					btncolor.setBackground(color);
				}
				
			}
			
		});
		btnfillColor=new JButton("Boja povrsine");
		btnfillColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Color color = JColorChooser.showDialog(null, "Choose a color", btnfillColor.getBackground());
				if (color != null) {
					btnfillColor.setBackground(color);                						
				}
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
					
					circle = new Circle();
					circle.setCenter(new Point(x, y));
					circle.setRadius(radiusInt);					
					circle.setColor(btncolor.getBackground());
					circle.setInnerColor(btnfillColor.getBackground());
					
					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(DIDialogCircle.this,"Sva polja moraju biti popunjena brojevima","Upozorenje",JOptionPane.WARNING_MESSAGE);
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
	
	public Circle getCircle() {
		return this.circle;
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
	
	public void openInEditingMode(Circle circleToEdit) {
		
		txtradius.setText(String.valueOf(circleToEdit.getRadius()));
		txtY.setText(String.valueOf(circleToEdit.getCenter().getY()));
		txtX.setText(String.valueOf(circleToEdit.getCenter().getX()));
		btncolor.setBackground(circleToEdit.getColor());
		btnfillColor.setBackground(circleToEdit.getInnerColor());
		
		setVisible(true);	
	}
	
	


}
