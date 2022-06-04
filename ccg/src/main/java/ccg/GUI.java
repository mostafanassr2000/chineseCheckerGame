package ccg;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class GUI extends JFrame implements Runnable {

    private final double DIF_H = 47, DIF_W = 27.5, OFFSET_Y = -94, OFFSET_X = -108; 
   
    private Image img, redMarble, blueMarble, empty, optional;
    private BackJPanel[][] graphicMat;
    private BackJPanel background;
    private Game game;
    private Thread thread;
    private JFrame mainFrame;
    ArrayList<Vertex> availableVertices;

    public GUI(String text) throws IOException {
        mainFrame = new JFrame(text);
        mainFrame.setSize(787, 890);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        game = new Game();

        img = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/board.png"))).getImage();

        background = new BackJPanel(img, null);
        background.setLayout(null);
        background.setBounds(0, 0, 720, 800); // 800
        background.addMouseListener(new PerformMouseAction());
        availableVertices = new ArrayList<Vertex>();

        redMarble = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/red#1.png")))
                .getImage();
        blueMarble = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/green#1.png")))
                .getImage();
        optional = (new ImageIcon(this.getClass().getClassLoader().getResource("ccg/Assets/optinal.png"))).getImage();

        empty = null;
        graphicMat = new BackJPanel[game.H][game.W];

        for (int i = 0; i < game.H; i++) {
            for (int j = 0; j < game.W; j++) {
                if (game.vertexMat[i][j] != null) {
                    switch (game.vertexMat[i][j].getContent()) {
                        case PlayerEnum.COMPUTER:
                            graphicMat[i][j] = new BackJPanel(redMarble, new Point(j, i));
                            break;
                        case PlayerEnum.PLAYER:
                            graphicMat[i][j] = new BackJPanel(blueMarble, new Point(j, i));
                            break;
                        default:

                            graphicMat[i][j] = new BackJPanel(empty, new Point(j, i));
                            break;
                    }

                    graphicMat[i][j].setBounds((int) Math.round(OFFSET_X + (j * DIF_W)),
                            (int) Math.round(OFFSET_Y + (i * DIF_H)), 55, 47);
                    graphicMat[i][j].setOpaque(false);
                    graphicMat[i][j].addMouseListener(new PerformMouseAction(i, j));
                    background.add(graphicMat[i][j]);
                }
            }
        }

        mainFrame.add(background);
        game.gameStart();
        thread = new Thread(this);
        thread.start();
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new GUI("Chinese Checker Game");
    }

    public class PerformMouseAction implements MouseListener {

        private int row;
        private int col;

        public PerformMouseAction(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public PerformMouseAction() {
            super();
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {
            if (!game.isRunning()) {
                JOptionPane.showConfirmDialog(background, "Please select the level", "Start Error",
                        JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (game.getPlayer() == 2) { // Player
                    if (game.vertexMat[row][col].content == game.getPlayer()) {

                        clearAvailableVertices(); // Clearing all available vertices

                        game.setTempX(col); 
                        game.setTempY(row); 

                        availableVertices = game.findNextMoves(game.getTempX(), game.getTempY());

                        for (int i = 0; i < availableVertices.size(); i++) {
                            int x = (int) availableVertices.get(i).getLocation().getX();
                            int y = (int) availableVertices.get(i).getLocation().getY();
                            graphicMat[y][x].setImg(optional);

                        }
                    } else if (game.vertexMat[row][col].content == 0
                            && availableVertices.contains(game.vertexMat[row][col])) { // Move Vertix to empty cell

                        for (int i = 0; i < availableVertices.size(); i++) {
                            int x = (int) availableVertices.get(i).getLocation().getX();
                            int y = (int) availableVertices.get(i).getLocation().getY();

                            if (row == y && col == x) { // Vertix exists
                                // System.out.println("exists");
                                game.move(col, row);
                                availableVertices.remove(availableVertices.get(i));
                                break;
                            }
                        }
                        updateGame();
                        game.switchTurn(game.getPlayer());
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
        }

        @Override
        public void mouseExited(MouseEvent arg0) {
        }

        @Override
        public void mousePressed(MouseEvent arg0) {
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
        }
    }

    public void clearAvailableVertices() {
        for (int i = 0; i < availableVertices.size(); i++) {
            int x = (int) availableVertices.get(i).getLocation().getX();
            int y = (int) availableVertices.get(i).getLocation().getY();
            graphicMat[y][x].setImg(empty);
        }

        mainFrame.repaint();
    }

    public void updateGame() {
        for (int i = 0; i < game.H; i++) {
            for (int j = 0; j < game.W; j++) {
                if (game.vertexMat[i][j] != null) {
                    switch (game.vertexMat[i][j].getContent()) {
                        case PlayerEnum.COMPUTER:
                            graphicMat[i][j].setImg(redMarble);
                            break;
                        case PlayerEnum.PLAYER:
                            graphicMat[i][j].setImg(blueMarble);
                            break;
                        default: // PlayerEnum.NONE
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
        while (game.getPlayer() != PlayerEnum.NONE) {

            if (game.getPlayer() == PlayerEnum.COMPUTER) {
                game.callAI();
                updateGame();
                game.switchTurn(game.getPlayer());
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}
