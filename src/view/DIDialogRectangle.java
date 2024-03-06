package view;

import java.awt.Color;
import java.awt.Dimension;
import model.Point;
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

import model.Rectangle;

public class DIDialogRectangle  extends JDialog {

	private JLabel lblX;
	private JTextField txtX;
	private JLabel lblY;
	private JTextField txtY;
	private JLabel lblwidth;
	private JTextField txtwidth;
	private JLabel lblhight;
	private JTextField txthight;
	private JButton btncolor;
	private JButton btnfillColor;
	
	private JPanel pX;
	private JPanel pY;
	private JPanel pWidth;
	private JPanel pHight;
	private JPanel pcolor;
	
	private Rectangle rectange = null;

	public DIDialogRectangle() {

		setTitle("Rectangle dialog");
		setModal(true);
		lblX =new JLabel("X:");
		lblY=new JLabel("Y:");
		lblwidth=new JLabel("Sirina:");
		lblhight=new JLabel("Visina:");
		txtX=new JTextField(20);
		txtY=new JTextField(20);
		txtwidth=new JTextField(20);
		txthight=new JTextField(20);
		
		pX=new JPanel();
		pY=new JPanel();
		pWidth=new JPanel();
		pHight=new JPanel();
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
		pWidth.add(lblwidth);
		pWidth.add(txtwidth);
		pHight.add(lblhight);
		pHight.add(txthight);
		JButton OK=new JButton("OK");
		OK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String X = txtX.getText();
				String Y = txtY.getText();
				String width = txtwidth.getText();
				String hight = txthight.getText();
				
				try {
					int x = Integer.parseInt(X);
					int y = Integer.parseInt(Y);
					
					int widthInt = Integer.parseInt(width);
					int heightInt = Integer.parseInt(hight);
					
					rectange = new Rectangle();
					
					rectange.setUpperLeftPoint(new Point(x, y));
					rectange.setWidth(widthInt);
					rectange.setHeight(heightInt);						
					rectange.setColor(btncolor.getBackground());
					rectange.setInnerColor(btnfillColor.getBackground());
					
					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(DIDialogRectangle.this,"Sva polja moraju biti popunjena brojevima","Upozorenje",JOptionPane.WARNING_MESSAGE);
					return;
				}				
			}
			
		});
		
		JPanel contentPane = new JPanel();
		BoxLayout boxlayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);		
		contentPane.setLayout(boxlayout);
		
		
		contentPane.add(pX);
		contentPane.add(pY);
		contentPane.add(pHight);
		contentPane.add(pWidth);
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
	
	public void openInEditingMode(Rectangle rectangleToEdit) {
		txtX.setText(String.valueOf(rectangleToEdit.getUpperLeftPoint().getX()));
		txtY.setText(String.valueOf(rectangleToEdit.getUpperLeftPoint().getY()));
		txtwidth.setText(String.valueOf(rectangleToEdit.getWidth()));
		txthight.setText(String.valueOf(rectangleToEdit.getHeight()));
		btncolor.setBackground(rectangleToEdit.getColor());
		btnfillColor.setBackground(rectangleToEdit.getInnerColor());
		btnfillColor.setBackground(rectangleToEdit.getInnerColor());
		
		setVisible(true);
	}
	
	public Rectangle getRectangle() {
		return rectange;
	}

}
