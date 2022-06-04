package ccg;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

public final class Game {

	private JFrame frame;
	private JButton easy, medium, hard;
	private boolean run = false;

	public static final int H = 21, W = 33;
	public static Vertex[][] vertexMat = new Vertex[H][W];

	private final int[][] win = {
			{ 0, 12 },
			{ 1, 11 },
			{ 1, 13 },
			{ 2, 10 },
			{ 2, 12 },
			{ 2, 14 },
			{ 3, 9 },
			{ 3, 11 },
			{ 3, 13 },
			{ 3, 15 }
	};
	private int tempX = 0, tempY = 0, activePlayer = 2, level, winner;
	// public static GraphFacilities graph;
	private int[][] logicMat = {
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1, 9, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1, 9, 1, 9, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1, 9, 1, 9, 1, 9, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 0, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 2, 9, 2, 9, 2, 9, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 2, 9, 2, 9, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 2, 9, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
	};
	// private Point[][] coordMat;
	private Point[] computerSoldiers = new Point[] {
		new Point(16, 2),
		new Point(15, 3),
		new Point(17, 3),
		new Point(14, 4),
		new Point(16, 4),
		new Point(18, 4),
		new Point(13, 5),
		new Point(15, 5),
		new Point(17, 5),
		new Point(19, 5)
};

private Point[] playerSoldiers = new Point[] {
	new Point(13, 15),
	new Point(15, 15),
	new Point(17, 15),
	new Point(19, 15),
	new Point(14, 16),
	new Point(16, 16),
	new Point(18, 16),
	new Point(15, 17),
	new Point(17, 17),
	new Point(16, 18)
};

	HashMap<Vertex, ArrayList<Vertex>> availableNodes = new HashMap<>();
	// private int coordIndex;

	public Game() {
		for (int i = 0; i < logicMat.length; i++) {
			for (int j = 0; j < logicMat[i].length; j++) {
				if (this.vertexMat[i][j] == null) {
					if (logicMat[i][j] == 1) { // Computer
						this.vertexMat[i][j] = new Vertex(new Point(j, i), PlayerEnum.COMPUTER);
					} else if (logicMat[i][j] == 2) { // Player
						this.vertexMat[i][j] = new Vertex(new Point(j, i), PlayerEnum.PLAYER);
					} else if (logicMat[i][j] == 0) { // Empty
						this.vertexMat[i][j] = new Vertex(new Point(j, i), PlayerEnum.NONE);
					} else { // Out of the star
						this.vertexMat[i][j] = null;
					}
				}
			}
		}

		tempX = tempY = level = 0;
		activePlayer = PlayerEnum.PLAYER; // Player stars first
	}

	public void gameStart() {
		JPanel menuPanel = new JPanel();
		easy = new JButton("Easy");
		easy.setActionCommand("Easy");
		easy.addActionListener(new OptionListener());
		easy.setBounds(25, 20, 180, 95);

		medium = new JButton("Medium");
		medium.setActionCommand("Medium");
		medium.addActionListener(new OptionListener());
		medium.setBounds(25, 130, 180, 95);

		hard = new JButton("Hard");
		hard.setActionCommand("Hard");
		hard.addActionListener(new OptionListener());
		hard.setBounds(25, 240, 180, 95);

		menuPanel.setLayout(null);
		menuPanel.setBounds(0, 0, 300, 450);
		menuPanel.add(easy);
		menuPanel.add(medium);
		menuPanel.add(hard);

		frame = new JFrame("Select Level: ");
		frame.add(menuPanel);
		frame.setDefaultCloseOperation(3);
		frame.setLocationRelativeTo(null);
		frame.setSize(300, 430);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	public class OptionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Easy")) {
				level = 1;
				run = true;
				frame.dispose();

			} else if (e.getActionCommand().equals("Medium")) {
				level = 2;
				run = true;
				frame.dispose();
			}

			else {
				level = 3;
				run = true;
				frame.dispose();
			}
		}
	}

	// Getting all possible next moves
	public ArrayList<Vertex> findNextMoves(int x, int y) {
		ArrayList<Vertex> availableVertices = availableMoves(x, y);
		reset();
		return availableVertices;
	}


	public ArrayList<Vertex> availableMoves(int x, int y) {

		ArrayList<Vertex> availableVertices = new ArrayList<Vertex>();

		Vertex topRight = vertexMat[y - 1][x + 1];
		Vertex topLeft = vertexMat[y - 1][x - 1];
		Vertex right = vertexMat[y][x + 2];
		Vertex left = vertexMat[y][x - 2];
		Vertex bottomRight = vertexMat[y + 1][x + 1];
		Vertex bottomLeft = vertexMat[y + 1][x - 1];

		if (topRight != null && topRight.content == 0) {
			//System.out.println("topRight: " + topRight.getLocation());
			availableVertices.add(topRight);
		}

		if (topLeft != null && topLeft.content == 0) {
			//System.out.println("topLeft: " + topLeft.getLocation());
			availableVertices.add(topLeft);
		}

		if (right != null && right.content == 0) {
			//System.out.println("right: " + right.getLocation());
			availableVertices.add(right);
		}

		if (left != null && left.content == 0) {
			//System.out.println("left: " + left.getLocation());
			availableVertices.add(left);
		}

		if (bottomRight != null && bottomRight.content == 0) {
			//System.out.println("bottomRight: " + bottomRight.getLocation());
			availableVertices.add(bottomRight);
		}

		if (bottomLeft != null && bottomLeft.content == 0) {
			//System.out.println("bottomLeft: " + bottomLeft.getLocation());
			availableVertices.add(bottomLeft);
		}

		return jump(availableVertices, x, y);
	}

	ArrayList<Vertex> jump(ArrayList<Vertex> availableVertices, int x, int y) {
		Vertex JTopRight = vertexMat[y - 2][x + 2];
		Vertex JTopLeft = vertexMat[y - 2][x - 2];
		Vertex JRight = vertexMat[y][x + 4];
		Vertex JLeft = vertexMat[y][x - 4];
		Vertex JBottomRight = vertexMat[y + 2][x + 2];
		Vertex JBottomLeft = vertexMat[y + 2][x - 2];

		Vertex topRight = vertexMat[y - 1][x + 1];
		Vertex topLeft = vertexMat[y - 1][x - 1];
		Vertex right = vertexMat[y][x + 2];
		Vertex left = vertexMat[y][x - 2];
		Vertex bottomRight = vertexMat[y + 1][x + 1];
		Vertex bottomLeft = vertexMat[y + 1][x - 1];

		if (topRight != null && JTopRight != null && topRight.content != 0 && JTopRight.content == 0
				&& !JTopRight.isVisited()) {
			vertexMat[y - 2][x + 2].setVisited(true);
			//System.out.println("topRight: " + topRight.getLocation());
			availableVertices.add(JTopRight);
			availableVertices = jump(availableVertices, JTopRight.getLocation().x, JTopRight.getLocation().y);
		}

		if (topLeft != null && JTopLeft != null && topLeft.content != 0 && JTopLeft.content == 0
				&& !JTopLeft.isVisited()) {
			vertexMat[y - 2][x - 2].setVisited(true);
			//System.out.println("topLeft: " + topLeft.getLocation());
			availableVertices.add(JTopLeft);
			availableVertices = jump(availableVertices, JTopLeft.getLocation().x, JTopLeft.getLocation().y);
		}

		if (right != null && JRight != null && right.content != 0 && JRight.content == 0 && !JRight.isVisited()) {
			vertexMat[y][x + 4].setVisited(true);
			//System.out.println("right: " + right.getLocation());
			availableVertices.add(JRight);
			availableVertices = jump(availableVertices, JRight.getLocation().x, JRight.getLocation().y);
		}

		if (left != null && JLeft != null && left.content != 0 && JLeft.content == 0 && !JLeft.isVisited()) {
			vertexMat[y][x - 4].setVisited(true);
			//System.out.println("left: " + left.getLocation());
			availableVertices.add(JLeft);
			availableVertices = jump(availableVertices, JLeft.getLocation().x, JLeft.getLocation().y);
		}

		if (bottomRight != null && JBottomRight != null && bottomRight.content != 0 && JBottomRight.content == 0
				&& !JBottomRight.isVisited()) {
			vertexMat[y + 2][x + 2].setVisited(true);
			//System.out.println("bottomRight: " + bottomRight.getLocation());
			availableVertices.add(JBottomRight);
			availableVertices = jump(availableVertices, JBottomRight.getLocation().x, JBottomRight.getLocation().y);
		}

		if (bottomLeft != null && JBottomLeft != null && bottomLeft.content != 0 && JBottomLeft.content == 0
				&& !JBottomLeft.isVisited()) {
			vertexMat[y + 2][x - 2].setVisited(true);
			//System.out.println("bottomLeft: " + bottomLeft.getLocation());
			availableVertices.add(JBottomLeft);
			availableVertices = jump(availableVertices, JBottomLeft.getLocation().x, JBottomLeft.getLocation().y);
		}

		

		return availableVertices;
	}

	public void reset() {
		for (int i = 0; i < this.H; i++) {
			for (int j = 0; j < this.W; j++) {
				if (vertexMat[i][j] != null && vertexMat[i][j].isVisited()) {
					vertexMat[i][j].setVisited(false);
				}
			}
		}
	}

	boolean hasWon() {
		if (activePlayer == PlayerEnum.PLAYER) {
			for (int i = 0; i < computerSoldiers.length; i++) {
				int x = computerSoldiers[i].x;
				int y = computerSoldiers[i].y;
				
				if (vertexMat[y][x].content != PlayerEnum.PLAYER) {
					return false;
				}
			}
			winner = PlayerEnum.PLAYER;	//Player has won
			return true;
		} else {	//Computer
			for (int i = 0; i < playerSoldiers.length; i++) {
				int x = playerSoldiers[i].x;
				int y = playerSoldiers[i].y;
				
				if (vertexMat[y][x].content != PlayerEnum.COMPUTER) {
					return false;
				}
			}
			winner = PlayerEnum.COMPUTER;	//Computer has won
			return true;
		}
	}

	public void switchTurn(int activePlayer) {
		if (activePlayer == PlayerEnum.PLAYER) {
			this.activePlayer = PlayerEnum.COMPUTER;
		} else {	//Computer
			this.activePlayer = PlayerEnum.PLAYER;
		}
	}

	void move(int destX, int destY) {
		vertexMat[tempY][tempX].content = PlayerEnum.NONE;
		vertexMat[destY][destX].content = activePlayer;
	}

	void AIMove(int destX, int destY, int tempX, int tempY) {
		vertexMat[tempY][tempX].content = PlayerEnum.NONE;
		vertexMat[destY][destX].content = activePlayer;
	}

	public void AI(int level){
		int max = 18, min = Integer.MAX_VALUE;
		Point currentGoal, bestMove = new Point();
		Vertex bestVertex = new Vertex(new Point());
		Utility();
		if (level == 1){
			for(Map.Entry<Vertex, ArrayList<Vertex>> entry : availableNodes.entrySet()) {
				for(Vertex move: entry.getValue()) {

					for (int i = 0; i < playerSoldiers.length; i++) {
						currentGoal = new Point(playerSoldiers[i].x, playerSoldiers[i].y);
						if (currentGoal.y - move.getLocation().y < max){
							System.out.println("Best move: " + move.getLocation());
							max = currentGoal.y - move.getLocation().y;
							bestMove = move.getLocation();
							bestVertex = entry.getKey();
						}
					}
				}
			}
			System.out.println("Best move: " + bestMove.getLocation());
			AIMove(bestMove.x, bestMove.y, bestVertex.getLocation().x, bestVertex.getLocation().y);
		}else if(level==2){

		}else if(level==3){

		}
	}

	public void Utility() {
		
		for (int i = 0; i < this.H; i++) {
			for (int j = 0; j < this.W; j++) {
				if(vertexMat[i][j] != null &&
				vertexMat[i][j].getContent() == PlayerEnum.COMPUTER &&
				!findNextMoves(vertexMat[i][j].getLocation().x, vertexMat[i][j].getLocation().y).isEmpty()){
					availableNodes.put(vertexMat[i][j], findNextMoves(vertexMat[i][j].getLocation().x, vertexMat[i][j].getLocation().y));
					break;
				}
			}
		}
	}

	double preBestScore = 0;

	public void call() {
		ArrayList<Double> scores = new ArrayList<Double>();
		int depth = 1;
		Vertex[][] tempBoard = new Vertex[H][W];
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for (int i = 0; i < this.H; i++) {
			for (int j = 0; j < this.W; j++) {
				if ( vertexMat[i][j] == null) {
					tempBoard[i][j] = null;
				}else {
					tempBoard[i][j] = new Vertex(vertexMat[i][j].getLocation(), vertexMat[i][j].content);
				 }
			}
		}



		//Current Computer Vertices
		for (int i = 0; i < this.H; i++) {
			for (int j = 0; j < this.W; j++) {
				if (vertexMat[i][j] != null && vertexMat[i][j].content == 1) {
					
					vertices.add(new Vertex(vertexMat[i][j].getLocation(), vertexMat[i][j].content));
					/* 
					if (countInsideSoldiers() > 5 ) {
						//System.out.println("OUTSIDE SOLDIERS: " + countInsideSoldiers());
						if (outSideSoldiers(vertexMat[i][j])) {	//if the soldier is outside
							vertices.add(new Vertex(vertexMat[i][j].getLocation(), vertexMat[i][j].content));
						}

					} else {

					}*/

				}
			}
		}

		Vertex goal = null;
	
		for(int i = playerSoldiers.length -1 ; i >= 0; i--) {
            int x = (int) playerSoldiers[i].getX();
            int y = (int) playerSoldiers[i].getY();

            if (this.vertexMat[y][x].content != 1) {    //Computer
                goal = this.vertexMat[y][x];
                break;
            }
        }
		
		
		Movement bestMove = null;
		double bestScore = Double.MAX_VALUE;
		

		//Getting best score
		for (Vertex currV: vertices) {
			Movement vertexMove = minmax(depth, tempBoard, currV, goal);

			if (vertexMove != null && vertexMove.score < bestScore && !scores.contains(vertexMove.score)) {
				bestScore = vertexMove.score;
				scores.add(bestScore);
				
				System.out.println("ArrayList: ");
				System.out.println(scores);
				System.out.println("----------------------------");

				preBestScore = bestScore;
				bestMove = new Movement(currV.getLocation(), vertexMove.dest, bestScore);
				System.out.println(bestScore);
			}
		}
	
		tempX = (int) bestMove.src.getLocation().getX();
		tempY = (int) bestMove.src.getLocation().getY();

		move((int) bestMove.dest.getLocation().getX(), (int) bestMove.dest.getLocation().getY());

		//check if vertex has reached goal
		if (bestMove.dest.getLocation() == goal.getLocation()) {
			vertexMat[(int)bestMove.dest.getLocation().getY()][(int)bestMove.dest.getLocation().getX()].arrived = true;
		}

	}

	public Movement minmax(int depth, Vertex copyBoard[][], Vertex vertex, Vertex goal) {

		ArrayList<Vertex> possibleMoves = findNextMoves((int) vertex.getLocation().getX(), (int) vertex.getLocation().getY());
		ArrayList<Vertex> all = new ArrayList<Vertex>();
		Vertex tempBoard[][] =  new Vertex[H][W];
		
		double bestScore = Double.MAX_VALUE;
		Point bestPoint = null;
		Movement bestMove = null;
		double tmpScore;

		for (Vertex vertexMove : possibleMoves) {
			Vertex newBoard[][] = new Vertex[H][W];
			
			newBoard = AIMove2((int) vertexMove.getLocation().getX(), (int) vertexMove.getLocation().getY(), (int) vertex.getLocation().getX(), (int) vertex.getLocation().getY(), copyBoard);
	
			if (Her(newBoard, goal) < bestScore) {
				bestScore = Her(newBoard, goal);
						
				bestPoint = new Point((int) vertexMove.getLocation().getX(), (int) vertexMove.getLocation().getY());
				
				bestMove = new Movement(vertex.getLocation(), bestPoint, bestScore);

			}
		}

		return bestMove;
	}

	Vertex[][] AIMove2(int destX, int destY, int srcX, int srcY, Vertex[][] copyBoard) {

		Vertex[][] tempBoard = new Vertex[H][W];

		for (int i = 0; i < this.H; i++) {
			for (int j = 0; j < this.W; j++) {
				if (copyBoard[i][j] == null) {
					tempBoard[i][j] = null;
				}else {
					tempBoard[i][j] = new Vertex(copyBoard[i][j].getLocation(), copyBoard[i][j].content);
				 }
			}
		}
		
		tempBoard[srcY][srcX].content = PlayerEnum.NONE;
		tempBoard[destY][destX].content = PlayerEnum.COMPUTER;

		return tempBoard;
	}


	public double Her(Vertex copyBoard[][], Vertex goal) {

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for (int i = 0; i < this.H; i++) {
			for (int j = 0; j < this.W; j++) {
				if (copyBoard[i][j] != null && copyBoard[i][j].content == PlayerEnum.COMPUTER) {
					vertices.add(new Vertex(copyBoard[i][j].getLocation(), copyBoard[i][j].content));
				}
			}
		}

		int sum = 0;
		for (Vertex vertix : vertices) {
			sum += Math.sqrt(Math.pow(goal.getLocation().getX() - vertix.getLocation().getX(), 2) + Math.pow(goal.getLocation().getY() - vertix.getLocation().getY(), 2));

		}

		return sum ;
	}

	public boolean outSideSoldiers(Vertex v) {

		for (int i = 0; i < playerSoldiers.length; i++) {
			//int x = (int) v.getLocation().getX();
			//int y = (int) v.getLocation().getY();

			if (v.getLocation() == playerSoldiers[i]) {	//inside
				return false;
			}
		}

		return true;
	}

	public int countInsideSoldiers() {
		int count = 0;

		for (int i = 0; i < playerSoldiers.length; i++) {
			int x = (int) playerSoldiers[i].getLocation().getX();
			int y = (int) playerSoldiers[i].getLocation().getY();

			if (vertexMat[y][x].content == PlayerEnum.COMPUTER) {	
				count++;
			}
		}
		return count;
	}






	// Setters and Getters
	public boolean isRunning() {
		return run;
	}

	public int getPlayer() {
		return activePlayer;
	}

	public void setActivePlayer(int x) {
		this.activePlayer = x;
	}

	public int getTempY() {
		return tempY;
	}

	public void setTempY(int y) {
		tempY = y;
	}

	public int getTempX() {
		return tempX;
	}

	public void setTempX(int x) {
		tempX = x;
	}

	/*
	 * 
	 * 
	 * public boolean win() {
	 * boolean p1 = true, p2 = true;
	 * for (int i = 0; i < 10 && (p1 || p2); i++) {
	 * p1 = (GraphFacilities.vertexMat[win[i][0]][win[i][1]].content
	 * == PlayerEnum.HUMAN_PLAYER && p1);
	 * p2 = (GraphFacilities.vertexMat[(H - 1) - win[i][0]][win[i][1]].content
	 * == PlayerEnum.CPU_PLAYER && p2);
	 * }
	 * if (p1) {
	 * if (status == 2) {
	 * JOptionPane.showConfirmDialog(null,
	 * "Blue Player Won!",
	 * "Winner",
	 * JOptionPane.CLOSED_OPTION,
	 * JOptionPane.INFORMATION_MESSAGE);
	 * } else {
	 * JOptionPane.showConfirmDialog(null,
	 * "You Won!",
	 * "Winner",
	 * JOptionPane.CLOSED_OPTION,
	 * JOptionPane.INFORMATION_MESSAGE);
	 * }
	 * player = PlayerEnum.NONE;
	 * return true;
	 * } else if (p2) {
	 * if (status == 2) {
	 * JOptionPane.showConfirmDialog(null,
	 * "Red Player Won!",
	 * "Winner",
	 * JOptionPane.CLOSED_OPTION,
	 * JOptionPane.INFORMATION_MESSAGE);
	 * } else {
	 * JOptionPane.showConfirmDialog(null,
	 * "The Computer Won!",
	 * "Winner",
	 * JOptionPane.CLOSED_OPTION,
	 * JOptionPane.INFORMATION_MESSAGE);
	 * }
	 * player = PlayerEnum.NONE;
	 * return true;
	 * }
	 * return false;
	 * }
	 * 
	 * public void Move(int y, int x) {
	 * System.out.println("Player "+this.getPlayer()+": ("+this.getTx()+", "+this.
	 * getTy()+") To ("+x+", "+y+")");
	 * GraphFacilities.vertexMat[y][x].content = player;
	 * GraphFacilities.vertexMat[ty][tx].content = 99;
	 * }
	 * 
	 * public void resetBoard() {
	 * for (int i = 0; i < H; i++) {
	 * for (int j = 0; j < W; j++) {
	 * if (GraphFacilities.vertexMat[i][j] != null
	 * && GraphFacilities.vertexMat[i][j].content > 9) {
	 * GraphFacilities.vertexMat[i][j].content = PlayerEnum.NONE;
	 * }
	 * }
	 * }
	 * GraphFacilities.updateGraph(coordMat);
	 * }
	 * 
	 * public void endTurn() {
	 * resetBoard();
	 * if (!win()) {
	 * player = (player == PlayerEnum.CPU_PLAYER)
	 * ? PlayerEnum.HUMAN_PLAYER
	 * : PlayerEnum.CPU_PLAYER;
	 * ty = 0;
	 * tx = 0;
	 * }
	 * }
	 * 
	 * 
	 * 
	 * // only search for jumps
	 * public Set<Point> optinalPlaysLen3(int x, int y) {
	 * Set<Point> endPoints = new HashSet<>();
	 * Set<CCEdge> edges
	 * = GraphFacilities.g.outgoingEdgesOf(GraphFacilities.vertexMat[y][x]);
	 * Iterator<CCEdge> it = edges.iterator();
	 * while (it.hasNext()) {
	 * CCEdge edge = it.next();
	 * // Source isn't the origin.
	 * if (!edge.getSrcVertx().getLocation().equals(new Point(x, y))) {
	 * // Cell empty
	 * if (edge.getSrcVertx().content == PlayerEnum.NONE) {
	 * if (edge.getSrcVertx().getLocation().y - 2
	 * == edge.getDestVertx().getLocation().y
	 * || edge.getSrcVertx().getLocation().y + 2
	 * == edge.getDestVertx().getLocation().y
	 * || edge.getSrcVertx().getLocation().x - 4
	 * == edge.getDestVertx().getLocation().x
	 * || edge.getSrcVertx().getLocation().x + 4
	 * == edge.getDestVertx().getLocation().x) {
	 * if (player == PlayerEnum.HUMAN_PLAYER) {
	 * edge.getSrcVertx().content
	 * = PlayerEnum.POSSIBLE_HUMAN_TARGET;
	 * } else {
	 * edge.getSrcVertx().content
	 * = PlayerEnum.POSSIBLE_CPU_TARGET;
	 * }
	 * endPoints.add(edge.getSrcVertx().getLocation());
	 * endPoints.addAll(
	 * optinalPlaysLen3(edge.getSrcVertx().getLocation().x,
	 * edge.getSrcVertx().getLocation().y));
	 * }
	 * }
	 * } else {
	 * // Cell empty
	 * if (edge.getDestVertx().content == PlayerEnum.NONE) {
	 * if (edge.getSrcVertx().getLocation().y - 2
	 * == edge.getDestVertx().getLocation().y
	 * || edge.getSrcVertx().getLocation().y + 2
	 * == edge.getDestVertx().getLocation().y
	 * || edge.getSrcVertx().getLocation().x - 4
	 * == edge.getDestVertx().getLocation().x
	 * || edge.getSrcVertx().getLocation().x + 4
	 * == edge.getDestVertx().getLocation().x) {
	 * if (player == PlayerEnum.HUMAN_PLAYER) {
	 * edge.getDestVertx().content
	 * = PlayerEnum.POSSIBLE_HUMAN_TARGET;
	 * } else {
	 * edge.getDestVertx().content
	 * = PlayerEnum.POSSIBLE_CPU_TARGET;
	 * }
	 * endPoints.add(edge.getDestVertx().getLocation());
	 * endPoints.addAll(
	 * optinalPlaysLen3(edge.getDestVertx().getLocation().x,
	 * edge.getDestVertx().getLocation().y));
	 * }
	 * }
	 * }
	 * }
	 * return endPoints;
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public int getStatus() {
	 * return status;
	 * }
	 * 
	 * public void setStatus(int value) {
	 * if (status == 0) {
	 * if (value == 1 || value == 2) {
	 * status = value;
	 * }
	 * }
	 * }
	 * 
	 * public void AI() {
	 * int i;
	 * // If only one player is outside
	 * if (countOutsideSoldiers() == 1) {
	 * CellVertex dest = null;
	 * for (i = 0; i < cpuSoldiers.length; i++) {
	 * if (cpuSoldiers[i].y < 13) {
	 * for (int h = 13; h < GraphFacilities.H; h++) {
	 * for (int j = 9 + (h - 13); j < 16 - (h - 13); j += 2) {
	 * if (GraphFacilities.vertexMat[h][j].content
	 * == PlayerEnum.NONE) {
	 * dest = GraphFacilities.vertexMat[h][j];
	 * }
	 * }
	 * }
	 * List<CellVertex> path = GraphFacilities.findShortestPathLength(
	 * GraphFacilities.vertexMat[cpuSoldiers[i].y][cpuSoldiers[i].x],
	 * dest).getVertexList();
	 * setTx(path.get(0).getLocation().x);
	 * setTy(path.get(0).getLocation().y);
	 * Move(path.get(1).getLocation().y, path.get(1).getLocation().x);
	 * cpuSoldiers[i].setLocation(path.get(1).getLocation());
	 * return;
	 * }
	 * }
	 * }
	 * // If there's an S turn available
	 * if ((i = findCpuPlayerIndex(is_S_TurnExist())) > -1) {
	 * setTx(cpuSoldiers[i].x);
	 * setTy(cpuSoldiers[i].y);
	 * Move(cpuSoldiers[i].y + 4, cpuSoldiers[i].x);
	 * cpuSoldiers[i].y += 4;
	 * } else {
	 * if (cpuSoldiers[6].getLocation().equals(new Point(9, 3))
	 * && GraphFacilities.vertexMat[4][10].content
	 * != PlayerEnum.HUMAN_PLAYER
	 * && GraphFacilities.vertexMat[4][10].content
	 * != PlayerEnum.CPU_PLAYER) {
	 * setTx(9);
	 * setTy(3);
	 * Move(4, 10);
	 * cpuSoldiers[6].setLocation(10, 4);
	 * } else if (cpuSoldiers[9].getLocation().equals(new Point(15, 3))
	 * && GraphFacilities.vertexMat[4][14].content
	 * != PlayerEnum.HUMAN_PLAYER
	 * && GraphFacilities.vertexMat[4][14].content
	 * != PlayerEnum.CPU_PLAYER) {
	 * setTx(15);
	 * setTy(3);
	 * Move(4, 14);
	 * cpuSoldiers[9].setLocation(14, 4);
	 * } else {
	 * CCEdge jump = findFarthestJump();
	 * if (jump != null) {
	 * setTx(jump.getSrcVertx().getLocation().x);
	 * setTy(jump.getSrcVertx().getLocation().y);
	 * Move(jump.getDestVertx().getLocation().y,
	 * jump.getDestVertx().getLocation().x);
	 * cpuSoldiers[findCpuPlayerIndex(
	 * jump.getSrcVertx().getLocation())].setLocation(
	 * jump.getDestVertx().getLocation());
	 * } else {
	 * for (i = 0; i < GraphFacilities.H; i++) {
	 * for (int j = 0; j < GraphFacilities.W; j++) {
	 * if (GraphFacilities.vertexMat[i][j] != null
	 * && GraphFacilities.vertexMat[i][j].content
	 * == PlayerEnum.CPU_PLAYER) {
	 * setTx(j);
	 * setTy(i);
	 * resetBoard();
	 * Set<Point> points = optinalPlays(tx, ty);
	 * Iterator<Point> it = points.iterator();
	 * while (it.hasNext()) {
	 * Point p = it.next();
	 * if ((p.y > ty
	 * && ((Math.abs(tx - GraphFacilities.W / 2)
	 * > Math.abs(p.x - GraphFacilities.W / 2))
	 * || (Math.abs(tx - GraphFacilities.W / 2)
	 * == 0)))) {
	 * Move(p.y, p.x);
	 * cpuSoldiers[findCpuPlayerIndex(
	 * new Point(tx, ty))].setLocation(p);
	 * return;
	 * }
	 * }
	 * it = points.iterator();
	 * while (it.hasNext()) {
	 * Point p = it.next();
	 * if ((p.y == ty
	 * && Math.abs(tx - GraphFacilities.W / 2)
	 * > Math.abs(p.x - GraphFacilities.W / 2))) {
	 * Move(p.y, p.x);
	 * cpuSoldiers[findCpuPlayerIndex(
	 * new Point(tx, ty))].setLocation(p);
	 * return;
	 * }
	 * }
	 * it = points.iterator();
	 * while (it.hasNext()) {
	 * Point p = it.next();
	 * if (p.y > ty) {
	 * Move(p.y, p.x);
	 * cpuSoldiers[findCpuPlayerIndex(
	 * new Point(tx, ty))].setLocation(p);
	 * return;
	 * }
	 * }
	 * }
	 * }
	 * }
	 * }
	 * }
	 * }
	 * }
	 * 
	 * private int countOutsideSoldiers() {
	 * int counter = 10;
	 * for (int i = 13; i < GraphFacilities.H; i++) {
	 * for (int j = 9 + (i - 13); j < 16 - (i - 13); j += 2) {
	 * if (GraphFacilities.vertexMat[i][j].content
	 * == PlayerEnum.CPU_PLAYER) {
	 * counter--;
	 * }
	 * }
	 * }
	 * return counter;
	 * }
	 * 
	 * private CCEdge findFarthestJump() {
	 * CCEdge jump = null;
	 * for (int i = 0; i < GraphFacilities.vertexMat.length; i++) {
	 * for (int j = 0; j < GraphFacilities.vertexMat[i].length; j++) {
	 * if (GraphFacilities.vertexMat[i][j] != null
	 * && GraphFacilities.vertexMat[i][j].content
	 * == PlayerEnum.CPU_PLAYER) {
	 * resetBoard();
	 * optinalPlaysLen3(j, i);
	 * for (int h = i; h < GraphFacilities.vertexMat.length; h++) {
	 * for (int g = 0; g < GraphFacilities.vertexMat[h].length; g++) {
	 * if (GraphFacilities.vertexMat[h][g] != null
	 * && GraphFacilities.vertexMat[h][g].content
	 * == PlayerEnum.POSSIBLE_CPU_TARGET) {
	 * if (jump != null) {
	 * if ((jump.getDestVertx().getLocation().y < h)
	 * || (jump.getDestVertx().getLocation().y == h
	 * && Math.abs(
	 * jump.getDestVertx().getLocation().x
	 * - (GraphFacilities.W / 2))
	 * > Math.abs(g - (GraphFacilities.W / 2)))) {
	 * if ((i < h)
	 * || (i == h
	 * && Math.abs(j - (GraphFacilities.W / 2))
	 * > Math.abs(
	 * g - (GraphFacilities.W / 2)))) {
	 * jump.setSrcVertx(
	 * GraphFacilities.vertexMat[i][j]);
	 * jump.setDestVertx(
	 * GraphFacilities.vertexMat[h][g]);
	 * }
	 * }
	 * } else {
	 * if ((i < h)
	 * || (i == h
	 * && Math.abs(j - (GraphFacilities.W / 2))
	 * > Math.abs(g - (GraphFacilities.W / 2)))) {
	 * jump = new CCEdge(GraphFacilities.vertexMat[i][j],
	 * GraphFacilities.vertexMat[h][g]);
	 * }
	 * }
	 * }
	 * }
	 * }
	 * }
	 * }
	 * }
	 * return jump;
	 * }
	 * 
	 * private Point is_S_TurnExist() {
	 * for (int i = GraphFacilities.vertexMat.length - 1; i >= 0; i--) {
	 * for (int j = GraphFacilities.vertexMat[i].length - 1; j >= 0; j--) {
	 * if (GraphFacilities.vertexMat[i][j] != null
	 * && GraphFacilities.vertexMat[i][j].content
	 * == PlayerEnum.CPU_PLAYER) {
	 * resetBoard();
	 * optinalPlays(j, i);
	 * if (i + 4 < GraphFacilities.vertexMat.length
	 * && GraphFacilities.vertexMat[i + 4][j] != null
	 * && j - 1 > 0 && j + 1 < GraphFacilities.vertexMat[i].length
	 * && GraphFacilities.vertexMat[i + 2][j].content
	 * == PlayerEnum.CPU_PLAYER
	 * && GraphFacilities.vertexMat[i + 4][j].content
	 * == PlayerEnum.POSSIBLE_CPU_TARGET
	 * && ((GraphFacilities.vertexMat[i + 1][j + 1].content
	 * == PlayerEnum.CPU_PLAYER
	 * && GraphFacilities.vertexMat[i + 3][j + 1].content
	 * == PlayerEnum.CPU_PLAYER
	 * && GraphFacilities.vertexMat[i + 2][j + 2].content
	 * == PlayerEnum.POSSIBLE_CPU_TARGET)
	 * || (GraphFacilities.vertexMat[i + 1][j - 1].content
	 * == PlayerEnum.CPU_PLAYER
	 * && GraphFacilities.vertexMat[i + 3][j - 1].content
	 * == PlayerEnum.CPU_PLAYER
	 * && GraphFacilities.vertexMat[i + 2][j - 2].content
	 * == PlayerEnum.POSSIBLE_CPU_TARGET))) {
	 * return new Point(j, i);
	 * }
	 * }
	 * }
	 * }
	 * return null;
	 * }
	 * 
	 * private int findCpuPlayerIndex(Point p) {
	 * if (p != null) {
	 * for (int i = 0; i < cpuSoldiers.length; i++) {
	 * if (cpuSoldiers[i].equals(p)) {
	 * return i;
	 * }
	 * }
	 * }
	 * return -1;
	 * }
	 */
}
