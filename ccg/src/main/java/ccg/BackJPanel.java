package ccg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;


public class BackJPanel extends JPanel {
	private Point p;
	private Image img;
	
	public BackJPanel(Image img , Point p){
		this.p = p;
		this.img=img;
	}
	
	public Point getLocation() {
		return p;
	}
	public Image getImg(){
		return img;
	}
	public void setImg(Image img){
		this.img=img;
	}
	public void paintComponent(Graphics g){
		if(img!=null)
		{
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(),null);
		}
	}
}
