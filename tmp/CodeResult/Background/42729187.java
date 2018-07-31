package fp.s100502013;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Background extends JPanel{ //gameplaying-background
	private Timer bgtimer = new Timer(100,new backgroundtime());
	private ImageIcon background = new ImageIcon("src/fp/s100502013/BACK01.png");
	private int bgtype = 1;
	private String bgplaydir = "lighter";
	
	public Background(){
		bgtimer.start();
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(background.getImage(), 0, 0, 600, 600, null);
	}
	
	class backgroundtime implements ActionListener{ //background animate set
		public void actionPerformed(ActionEvent e) {
			if(bgtype==1){
				background = new ImageIcon("src/fp/s100502013/BACK01.png");
				bgtype++;
				bgplaydir="lighter";
			}
			else if(bgtype==2){
				background = new ImageIcon("src/fp/s100502013/BACK02.png");
				if(bgplaydir=="lighter"){
					bgtype++;
				}
				else{
					bgtype--;
				}
			}
			else if(bgtype==3){
				background = new ImageIcon("src/fp/s100502013/BACK03.png");
				if(bgplaydir=="lighter"){
					bgtype++;
				}
				else{
					bgtype--;
				}
			}
			else if(bgtype==4){
				background = new ImageIcon("src/fp/s100502013/BACK04.png");
				if(bgplaydir=="lighter"){
					bgtype++;
				}
				else{
					bgtype--;
				}
			}
			else if(bgtype==5){
				background = new ImageIcon("src/fp/s100502013/BACK05.png");
				if(bgplaydir=="lighter"){
					bgtype++;
				}
				else{
					bgtype--;
				}
			}
			else if(bgtype==6){
				background = new ImageIcon("src/fp/s100502013/BACK06.png");
				if(bgplaydir=="lighter"){
					bgtype++;
				}
				else{
					bgtype--;
				}
			}
			else if(bgtype==7){
				background = new ImageIcon("src/fp/s100502013/BACK07.png");
				if(bgplaydir=="lighter"){
					bgtype++;
				}
				else{
					bgtype--;
				}
			}
			else if(bgtype==8){
				background = new ImageIcon("src/fp/s100502013/BACK08.png");
				if(bgplaydir=="lighter"){
					bgtype++;
				}
				else{
					bgtype--;
				}
			}
			else if(bgtype==9){
				background = new ImageIcon("src/fp/s100502013/BACK09.png");
				if(bgplaydir=="lighter"){
					bgtype++;
				}
				else{
					bgtype--;
				}
			}
			else{
				background = new ImageIcon("src/fp/s100502013/BACK10.png");
				bgtype--;
				bgplaydir="darker";
			}
			repaint();
		}
	}
}
