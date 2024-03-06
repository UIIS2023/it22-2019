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

import model.Donut;
import model.Point;
import model.Rectangle;

public class DIDialogDonut extends JDialog {

	private JLabel lblX;
	private JTextField txtX;
	private JLabel lblY;
	private JTextField txtY;
	private JLabel lblradius1;
	private JTextField txtradius1;
	private JLabel lblradius2;
	private JTextField txtradius2;
	private JButton btncolor;
	private JButton btnfillColor;
	
	private JPanel pX;
	private JPanel pY;
	private JPanel pRadius1;
	private JPanel pRadius2;
	private JPanel pcolor;
	
	private Donut donut;

	public DIDialogDonut() {
		
		setTitle("Dialog donut");
		setModal(true);
		lblX =new JLabel("X:");
		lblY=new JLabel("Y:");
		lblradius1=new JLabel("Spoljni radius:");
		lblradius2=new JLabel("Unutrasnji radius:");
		txtX=new JTextField(20);
		txtY=new JTextField(20);
		txtradius1=new JTextField(20);
		txtradius2=new JTextField(20);
		
		pX=new JPanel();
		pY=new JPanel();
		pRadius1=new JPanel();
		pRadius2=new JPanel();
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
		pRadius1.add(lblradius1);
		pRadius1.add(txtradius1);
		pRadius2.add(lblradius2);
		pRadius2.add(txtradius2);
		JButton OK=new JButton("OK");
		OK.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
	
				String X = txtX.getText();
				String Y = txtY.getText();	
				String radius1 = txtradius1.getText();	
				String radius2 = txtradius2.getText();		
				
				try {
					donut = new Donut();
					
					int radius1Int = Integer.parseInt(radius1);
					int radius2Int = Integer.parseInt(radius2);
					
					if (radius2Int >= radius1Int) {
						JOptionPane.showMessageDialog(DIDialogDonut.this,"Spoljni radius mora biti veci od unutrasnjeg","Upozorenje",JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					int x = Integer.parseInt(X);
					int y = Integer.parseInt(Y);
					
					donut.setCenter(new Point(x, y));
					donut.setInnerRadius(radius2Int);
					donut.setRadius(radius1Int);
					donut.setColor(btncolor.getBackground());
					donut.setInnerColor(btnfillColor.getBackground());
					
					dispose();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(DIDialogDonut.this,"Sva polja moraju biti popunjena brojevima","Upozorenje",JOptionPane.WARNING_MESSAGE);
					return;
				}					
			}

		});
		
		JPanel contentPane = new JPanel();
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);		
		contentPane.setLayout(boxlayout);
		
		contentPane.add(pX);
		contentPane.add(pY);
		contentPane.add(pRadius1);
		contentPane.add(pRadius2);
		contentPane.add(pcolor);
		contentPane.add(OK);
		
		
		setContentPane(contentPane);
		setMinimumSize(new Dimension(400, 300));
		
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
	
	public void openInEditingMode(Donut donutToEdit) {
		txtX.setText(String.valueOf(donutToEdit.getCenter().getX()));
		txtY.setText(String.valueOf(donutToEdit.getCenter().getY()));
		txtradius1.setText(String.valueOf(donutToEdit.getRadius()));
		txtradius2.setText(String.valueOf(donutToEdit.getInnerRadius()));
		btncolor.setBackground(donutToEdit.getColor());
		btnfillColor.setBackground(donutToEdit.getInnerColor());
		
		setVisible(true);
	}
	
	public Donut getDonut() {
		return donut;
	}
	
}
