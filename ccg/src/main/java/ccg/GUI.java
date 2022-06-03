package ccg;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class GUI extends JFrame implements Runnable {

    private final double DIF_H = 50, DIF_W = 29.5, OFFSET_Y = -101, OFFSET_X = -118; //-15, -5
    private Image img, redMarble, blueMarble, empty, optional;
    private BackPanelGraph[][] graphicMat;
    private BackPanelGraph background;
    private ChineseCheckersGraph cc;
    private Thread thread;
    private JFrame mainFrame; 
    ArrayList<CellVertex> availableVertices;


    public GUI(String text) throws IOException {
        mainFrame = new JFrame(text);
        mainFrame.setSize(787, 890);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        cc = new ChineseCheckersGraph();
        // icon =
        img = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/board.png"))).getImage();

        background = new BackPanelGraph(img, null);
        background.setLayout(null);
        background.setBounds(0, 0, 770, 850);   //800
        background.addMouseListener(new M_over());
        availableVertices = new ArrayList<CellVertex>();

        redMarble = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/RedMarble.png")))
                .getImage();
        blueMarble = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/BlueMarble.png")))
                .getImage();
        optional = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/optinal.png"))).getImage();

        empty = null;
        graphicMat = new BackPanelGraph[cc.H][cc.W];

        for (int i = 0; i < cc.H; i++) {
            for (int j = 0; j < cc.W; j++) {
                if (cc.vertexMat[i][j] != null) {
                    switch (cc.vertexMat[i][j].getContent()) {
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



                    graphicMat[i][j].setBounds((int) Math.round(OFFSET_X + (j * DIF_W)),
                            (int) Math.round(OFFSET_Y + (i * DIF_H)), 59, 50);
                    graphicMat[i][j].setOpaque(false);
                    graphicMat[i][j].addMouseListener(new M_over(i, j));
                    background.add(graphicMat[i][j]);
                }
            }
        }

       
        // CcMenuBar cMenuBar=new CcMenuBar(cc,this);
        // setJMenuBar(cMenuBar);
        mainFrame.add(background);
        cc.gameStart();
        thread = new Thread(this);
        thread.start();
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {

        // System.out.println("Hello world");
        // JOptionPane.showMessageDialog(null, "Hello world");

        new GUI("Batta Checker Game");

    }

    public class M_over implements MouseListener {

        private int row;
        private int col;

        public M_over(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public M_over() {
            super();
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {
            if (!cc.run) {
                JOptionPane.showConfirmDialog(background, "Please select the level", "Start Error",
                        JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (cc.getPlayer() == 2) { // Player
                    if (cc.vertexMat[row][col].content == cc.getPlayer()) {
                        //System.out.println(col + ", " + row);
                        
                        clearOptionals();   //Clearing all optional vertices

                        cc.setTempX(col); // x
                        cc.setTempY(row); // y
                        // cc.resetBoard();
                        availableVertices = cc.availableMoves(cc.getTempX(), cc.getTempY());
                                             
                        for (int i = 0; i < availableVertices.size(); i++) {
                            int x = (int) availableVertices.get(i).getLocation().getX();
                            int y = (int) availableVertices.get(i).getLocation().getY();
                            graphicMat[y][x].setImg(optional);
                        }

                        mainFrame.repaint();

                    } else if (cc.vertexMat[row][col].content == 0) {   //Move Vertix to empty cell

                        for (int i = 0; i < availableVertices.size(); i++) {
                            int x = (int) availableVertices.get(i).getLocation().getX();
                            int y = (int) availableVertices.get(i).getLocation().getY();

                            if (row == y && col == x) { //Vertix exists
                                //System.out.println("exists");
                                cc.move(col, row);
                                availableVertices.remove(availableVertices.get(i));
                                break;
                            }
                        }



                        //clearOptionals();   //Clearing all optional vertices
                        ay7aga();


                    
                        // cc.endTurn();
                    }
                    // reImage();
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {

        }

        @Override
        public void mouseExited(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mousePressed(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }
    }


    public void clearOptionals() {
        for (int i = 0; i < availableVertices.size(); i++) {
            int x = (int) availableVertices.get(i).getLocation().getX();
            int y = (int) availableVertices.get(i).getLocation().getY();
            graphicMat[y][x].setImg(empty);
        }

        mainFrame.repaint();
    }

    public void ay7aga() {
        for (int i = 0; i < cc.H; i++) {
            for (int j = 0; j < cc.W; j++) {
                if (cc.vertexMat[i][j] != null) {
                    switch (cc.vertexMat[i][j].getContent()) {
                        case PlayerEnum.COMPUTER:
                            graphicMat[i][j].setImg(redMarble);
                            break;
                        case PlayerEnum.PLAYER:
                            graphicMat[i][j].setImg(blueMarble);
                            break;
                        default:    //PlayerEnum.NONE
                            graphicMat[i][j].setImg(empty);
                            break;
                    }

                }
            }
        }

        mainFrame.repaint();
    }



    @Override
    public void run() {
        while (cc.getPlayer() != PlayerEnum.NONE) {
            /*
             * if (cc.getPlayer() == 1 && cc.getStatus() == 1) {
             * //JOptionPane.showConfirmDialog(null, "Computer Thinking.....Done!", "CPU",
             * JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
             * cc.AI();
             * cc.endTurn();
             * reImage();
             * }
             */

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
