package ce1002.A7.s101502513;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;

//Bugs are everywhere
public class A7 extends JFrame implements ActionListener {
	//symbol and i and num: record which button you press
	//sum: record result of operation
	//dnum: store sum
	private static String symbol = null;
	private static int i = 0, num = 0, sum = 0, dsum = 0;
	private static Label show = new Label("0", Label.RIGHT);
	private static JButton button_0 = new JButton("0");
	private static JButton button_1 = new JButton("1");
	private static JButton button_2 = new JButton("2");
	private static JButton button_3 = new JButton("3");
	private static JButton button_4 = new JButton("4");
	private static JButton button_5 = new JButton("5");
	private static JButton button_6 = new JButton("6");
	private static JButton button_7 = new JButton("7");
	private static JButton button_8 = new JButton("8");
	private static JButton button_9 = new JButton("9");
	private static JButton button_add = new JButton("+");
	private static JButton button_mi = new JButton("-");
	private static JButton button_eq = new JButton("=");
	private static JButton button_C = new JButton("C");
	
	public A7(String title) {
		super(title);
		setLayout(null);
		show.setBounds(20, 20, 230, 50);
		show.setBackground(Color.WHITE);
		show.setFont(new Font("SansSerif", Font.PLAIN, 30));
		add(show);
		button_7.setBounds(20, 80, 50, 50);
		add(button_7);
		button_8.setBounds(80, 80, 50, 50);
		add(button_8);
		button_9.setBounds(140, 80, 50, 50);
		add(button_9);
		button_add.setBounds(200, 80, 50, 50);
		add(button_add);
		button_4.setBounds(20, 140, 50, 50);
		add(button_4);
		button_5.setBounds(80, 140, 50, 50);
		add(button_5);
		button_6.setBounds(140, 140, 50, 50);
		add(button_6);
		button_mi.setBounds(200, 140, 50, 50);
		add(button_mi);
		button_1.setBounds(20, 200, 50, 50);
		add(button_1);
		button_2.setBounds(80, 200, 50, 50);
		add(button_2);
		button_3.setBounds(140, 200, 50, 50);
		add(button_3);
		button_eq.setBounds(200, 200, 50, 110);
		add(button_eq);
		button_0.setBounds(20, 260, 110, 50);
		add(button_0);
		button_C.setBounds(140, 260, 50, 50);
		add(button_C);
		button_0.addActionListener(this);
		button_1.addActionListener(this);
		button_2.addActionListener(this);
		button_3.addActionListener(this);
		button_4.addActionListener(this);
		button_5.addActionListener(this);
		button_6.addActionListener(this);
		button_7.addActionListener(this);
		button_8.addActionListener(this);
		button_9.addActionListener(this);
		button_add.addActionListener(this);
		button_mi.addActionListener(this);
		button_eq.addActionListener(this);
		button_C.addActionListener(this);
	}
	
	public static void main(String[] args) {
		A7 frame = new A7("¤pşâ˝L");
		frame.setSize(285, 380);
		frame.setResizable(false); //fixed size of the frame
		frame.setLocation(120, 80);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == button_0 ) {
			num = num * 10;
			if( num > 0 )
				i++;
			if( i > 0 && i <= 9 )
				show.setText(String.valueOf(num));
		}
		else if( e.getSource() == button_1 ) {
			i++;
			num = num * 10 + 1;
			if( i <= 9 )
				show.setText(String.valueOf(num));
		}
		else if( e.getSource() == button_2 ) {
			i++;
			num = num * 10 + 2;
			if( i <= 9 )
				show.setText(String.valueOf(num));
		}
		else if( e.getSource() == button_3 ) {
			i++;
			num = num * 10 + 3;
			if( i <= 9 )
				show.setText(String.valueOf(num));
		}
		else if( e.getSource() == button_4 ) {
			i++;
			num = num * 10 + 4;
			if( i <= 9 )
				show.setText(String.valueOf(num));
		}
		else if( e.getSource() == button_5 ) {
			i++;
			num = num * 10 + 5;
			if( i <= 9 )
				show.setText(String.valueOf(num));
		}
		else if( e.getSource() == button_6 ) {
			i++;
			num = num * 10 + 6;
			if( i <= 9 )
				show.setText(String.valueOf(num));
		}
		else if( e.getSource() == button_7 ) {
			i++;
			num = num * 10 + 7;
			if( i <= 9 )
				show.setText(String.valueOf(num));
		}
		else if( e.getSource() == button_8 ) {
			i++;
			num = num * 10 + 8;
			if( i <= 9 )
				show.setText(String.valueOf(num));
		}
		else if( e.getSource() == button_9 ) {
			i++;
			num = num * 10 + 9;
			if( i <= 9 )
				show.setText(String.valueOf(num));
		}
		else if( e.getSource() == button_add ) {
			//calculate result first
			if( symbol == "-" )
				sum = dsum - num;
			else //bug
				sum = dsum + num;
			
			show.setText(String.valueOf(sum));
			symbol = "+"; //record "+", prepare for next compute
			num = 0;
			dsum = sum;
			sum = 0;
			i = 0;
		}
		else if( e.getSource() == button_mi ) {
			//calculate result first
			if( symbol == "+" )
				sum = dsum + num;
			else //bug
				sum = dsum - num;
			
			show.setText(String.valueOf(sum));
			symbol = "-"; //record "-", prepare for next compute
			num = 0;
			dsum = sum;
			sum = 0;
			i = 0;
		}
		else if( e.getSource() == button_eq ) { //get sum
			if( symbol == "+" )
				sum = dsum + num;
			else if( symbol == "-" )
				sum = dsum - num;
			
			show.setText(String.valueOf(sum));
			num = 0;
			dsum = sum;
			sum = 0;
		}
		else if( e.getSource() == button_C ) {
			//empty variable
			i = 0;
			num = 0;
			sum = 0;
			dsum = 0;
			symbol = null;
			show.setText("0");
		}
		if( i > 9 || sum > 999999999 || sum < -999999999 )
			show.setText("error.");
	}
}
