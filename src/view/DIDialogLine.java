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

import model.Line;
import model.Point;
import model.Rectangle;

public class DIDialogLine  extends JDialog{
	
	private JLabel lblX;
	private JTextField txtX;
	private JLabel lblY;
	private JTextField txtY;
	private JLabel lblX1;
	private JTextField txtX1;
	private JLabel lblY1;
	private JTextField txtY1;
	private JButton btncolor;
	
	private JPanel pX;
	private JPanel pY;
	private JPanel pX1;
	private JPanel pY1;
	private JPanel pcolor;
	
	private Line line;
	
	public DIDialogLine() {
		
		setTitle("Line dialog");
		setModal(true);
		lblX =new JLabel("pocetak X:");
		lblY=new JLabel("pocetak Y:");
		lblX1=new JLabel("kraj X:");
		lblY1=new JLabel("kraj Y:");
		txtX=new JTextField(20);
		txtY=new JTextField(20);
		txtX1=new JTextField(20);
		txtY1=new JTextField(20);
		
		pX=new JPanel();
		pY=new JPanel();
		pX1=new JPanel();
		pY1=new JPanel();
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
		pX1.add(lblX1);
		pX1.add(txtX1);
		pY1.add(lblY1);
		pY1.add(txtY1);
		JButton OK=new JButton("OK");
		OK.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				String X = txtX.getText();
				String Y = txtY.getText();
				String X1 = txtX1.getText();
				String Y1 = txtY1.getText();

				try {
					int startX = Integer.parseInt(X);
					int startY = Integer.parseInt(Y);
					
					int endX = Integer.parseInt(X1);
					int endY = Integer.parseInt(Y1);
					
					line = new Line();
					line.setStartPoint(new Point(startX, startY));
					line.setEndPoint(new Point(endX, endY));
					line.setColor(btncolor.getBackground());
					
					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(DIDialogLine.this,"Sva polja moraju biti popunjena brojevima","Upozorenje",JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		});
		
		JPanel contentPane = new JPanel();
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);		
		contentPane.setLayout(boxlayout);
	
		contentPane.add(pX);
		contentPane.add(pY);
		contentPane.add(pX1);
		contentPane.add(pY1);	
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
	
	public void openInEditingMode(Line lineToEdit) {
		txtX.setText(String.valueOf(lineToEdit.getStartPoint().getX()));
		txtY.setText(String.valueOf(lineToEdit.getStartPoint().getY()));			
		txtX1.setText(String.valueOf(lineToEdit.getEndPoint().getX()));
		txtY1.setText(String.valueOf(lineToEdit.getEndPoint().getY()));
		btncolor.setBackground(lineToEdit.getColor());
		
		setVisible(true);
	}
	
	public Line getLine() {
		return line;
	}


}
