import java.awt.GridLayout;

import javax.swing.*;


public class Quotient {

	public static void main(String[] args) {
		JTextField numerator = new JTextField(10);
		JTextField denominator = new JTextField(10);
		JLabel label = new JLabel("Enter numerator and denominator to find the quotient.");
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(label);
		panel.add(numerator);
		panel.add(denominator);
		
		JOptionPane.showMessageDialog(null,panel);
		int top = Integer.parseInt(numerator.getText());
		int bottom = Integer.parseInt(denominator.getText());
		
		try {
			System.out.println(top/bottom);
		} catch(Exception e) {
			e.printStackTrace();
			//System.out.println(e.);
			System.out.println(e.getMessage());
			//JOptionPane.showMessageDialog(null, "Divisor cannot be zero");
		}
		
	}

}
