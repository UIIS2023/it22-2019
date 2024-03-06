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

import model.Point;
import model.Rectangle;

public class DIDialogPoint extends JDialog {
	
	private JLabel lblX;
	private JTextField txtX;
	private JLabel lblY;
	private JTextField txtY;
	private JButton btncolor;
	
	private JPanel pX;
	private JPanel pY;
	private JPanel pcolor;
	private Point point;
	
	public DIDialogPoint() {

		setTitle("Point dialog");
		setModal(true);
		lblX =new JLabel("X:");
		lblY=new JLabel("Y:");
		txtX=new JTextField(20);
		txtY=new JTextField(20);

		pX=new JPanel();
		pY=new JPanel();
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
		
		pX.add(lblX);
		pX.add(txtX);
		pY.add(lblY);
		pY.add(txtY);
		pcolor.add(btncolor);
		JButton OK=new JButton("OK");
		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String X = txtX.getText();
				String Y = txtY.getText();
				
				try {
					int x = Integer.parseInt(X);
					int y = Integer.parseInt(Y);
					
					point = new Point();
					
					point.setX(x);
					point.setY(y);		
					point.setColor(btncolor.getBackground());
					
					dispose();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(DIDialogPoint.this,"Sva polja moraju biti popunjena brojevima","Upozorenje",JOptionPane.WARNING_MESSAGE);
					return;
				}			
			}
		});
		
		JPanel contentPane = new JPanel();
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);		
		contentPane.setLayout(boxlayout);
		
		contentPane.add(pX);
		contentPane.add(pY);	
		contentPane.add(pcolor);
		contentPane.add(OK);
		
		setContentPane(contentPane);
		setMinimumSize(new Dimension(400, 300));
		
	}
	
	public void openInAddingMode(int x, int y, Color color) {
		txtX.setText(String.valueOf(x));
		txtY.setText(String.valueOf(y));
		
		btncolor.setBackground(color);
		
		setVisible(true);
	}
	
	public void openInEditingMode(Point pointToEdit) {
		txtX.setText(String.valueOf(pointToEdit.getX()));
		txtY.setText(String.valueOf(pointToEdit.getY()));
		btncolor.setBackground(pointToEdit.getColor());
		
		setVisible(true);
	}
	
	public Point getPoint() {
		return point;
	}


}
