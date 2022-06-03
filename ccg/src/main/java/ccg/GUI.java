package ccg;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class GUI extends JFrame implements Runnable {

    private final double DIF_H = 50, DIF_W = 29.5, OFFSET_Y = 0, OFFSET_X = 0;
    private Image img, redMarble, blueMarble, empty, optinal;
    private BackPanelGraph[][] graphicMat;
    private BackPanelGraph background;
    private ChineseCheckersGraph cc;

    public GUI(String text) throws IOException {
        super(text);
        super.setSize(787, 890);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        cc = new ChineseCheckersGraph();
        //icon = 
        img = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/board.png"))).getImage(); 
        
        background = new BackPanelGraph(img, null);
        background.setLayout(null);
        background.setBounds(0, 0, 770, 850);
        // background.addMouseListener(new M_over());

		redMarble = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/RedMarble.png"))).getImage();
		blueMarble = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/BlueMarble.png"))).getImage();
		optinal = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/optinal.png"))).getImage();

		empty = null;
        graphicMat = new BackPanelGraph[17][25];
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 25; j++) {
                if (GraphFacilities.g.containsVertex(new CellVertex(new Point(j, i)))) {
				
					switch (GraphFacilities.vertexMat[i][j].getContent()) {
						
						case PlayerEnum.COMPUTER:
							graphicMat[i][j] = new BackPanelGraph(redMarble, new Point(j, i));
							break;
						case PlayerEnum.PLAYER:
							graphicMat[i][j] = new BackPanelGraph(blueMarble, new Point(j, i));
							break;
						default:
							graphicMat[i][j] = new BackPanelGraph(empty, new Point(j, i));
							break;
					}

					graphicMat[i][j].setBounds((int) Math.round(OFFSET_X + (j * DIF_W)), (int) Math.round(OFFSET_Y + (i * DIF_H)), 59, 50);
					graphicMat[i][j].setOpaque(false);
					//graphicMat[i][j].addMouseListener(new M_over(i, j));
					background.add(graphicMat[i][j]);
				}
            }
        }
        // CcMenuBar cMenuBar=new CcMenuBar(cc,this);
        // setJMenuBar(cMenuBar);
        super.add(background);
        super.setLayout(null);
        super.setVisible(true);
    }

    public static void main(String[] args) throws IOException {

        // System.out.println("Hello world");
        // JOptionPane.showMessageDialog(null, "Hello world");

        new GUI("Batta Checker Game");

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }
}
