package ccg;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public final class Game {
	private JFrame frame;
	private JButton easyLvl, mediumLvl, hardLvl;
	private boolean run = false;

	public static final int H = 21, W = 33;
	public static Vertex[][] vertexMat = new Vertex[H][W];

	private int tempX = 0, tempY = 0, activePlayer = 2, depth, winner;

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

		tempX = tempY = depth = 1;
		activePlayer = PlayerEnum.PLAYER; // Player stars first
	}

	public void gameStart() {
		JPanel panel = new JPanel();
		easyLvl = new JButton("Easy");
		easyLvl.setActionCommand("Easy");
		easyLvl.addActionListener(new OptionListener());
		easyLvl.setBounds(25, 20, 180, 95);

		mediumLvl = new JButton("Medium");
		mediumLvl.setActionCommand("Medium");
		mediumLvl.addActionListener(new OptionListener());
		mediumLvl.setBounds(25, 130, 180, 95);

		hardLvl = new JButton("Hard");
		hardLvl.setActionCommand("Hard");
		hardLvl.addActionListener(new OptionListener());
		hardLvl.setBounds(25, 240, 180, 95);

		panel.setLayout(null);
		panel.setBounds(0, 0, 300, 450);
		panel.add(easyLvl);
		panel.add(mediumLvl);
		panel.add(hardLvl);

		frame = new JFrame("Select Level Difficulty: ");
		frame.add(panel);
		frame.setDefaultCloseOperation(3);
		frame.setLocationRelativeTo(null);
		frame.setSize(315, 435);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	public class OptionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Easy")) {
				depth = 1; //Game Level
				run = true;
				frame.dispose();

			} else if (e.getActionCommand().equals("Medium")) {
				depth = 2; //Game Level
				run = true;
				frame.dispose();
			}

			else {
				depth = 3; //Game Level
				run = true;
				frame.dispose();
			}
		}
	}

	// Getting all possible next moves
	public ArrayList<Vertex> findNextMoves(int x, int y) {
		ArrayList<Vertex> availableVertices = availableMoves(x, y);
		makeVisitedFalse();
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
			
			availableVertices.add(topRight);
		}

		if (topLeft != null && topLeft.content == 0) {
			
			availableVertices.add(topLeft);
		}

		if (right != null && right.content == 0) {
			
			availableVertices.add(right);
		}

		if (left != null && left.content == 0) {
			
			availableVertices.add(left);
		}

		if (bottomRight != null && bottomRight.content == 0) {
			
			availableVertices.add(bottomRight);
		}

		if (bottomLeft != null && bottomLeft.content == 0) {
			
			availableVertices.add(bottomLeft);
		}

		return hop(availableVertices, x, y);
	}

	ArrayList<Vertex> hop(ArrayList<Vertex> availableVertices, int x, int y) {
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
			availableVertices.add(JTopRight);
			availableVertices = hop(availableVertices, JTopRight.getLocation().x, JTopRight.getLocation().y);
		}

		if (topLeft != null && JTopLeft != null && topLeft.content != 0 && JTopLeft.content == 0
				&& !JTopLeft.isVisited()) {
			vertexMat[y - 2][x - 2].setVisited(true);
			availableVertices.add(JTopLeft);
			availableVertices = hop(availableVertices, JTopLeft.getLocation().x, JTopLeft.getLocation().y);
		}

		if (right != null && JRight != null && right.content != 0 && JRight.content == 0 && !JRight.isVisited()) {
			vertexMat[y][x + 4].setVisited(true);
			availableVertices.add(JRight);
			availableVertices = hop(availableVertices, JRight.getLocation().x, JRight.getLocation().y);
		}

		if (left != null && JLeft != null && left.content != 0 && JLeft.content == 0 && !JLeft.isVisited()) {
			vertexMat[y][x - 4].setVisited(true);
			availableVertices.add(JLeft);
			availableVertices = hop(availableVertices, JLeft.getLocation().x, JLeft.getLocation().y);
		}

		if (bottomRight != null && JBottomRight != null && bottomRight.content != 0 && JBottomRight.content == 0
				&& !JBottomRight.isVisited()) {
			vertexMat[y + 2][x + 2].setVisited(true);
			availableVertices.add(JBottomRight);
			availableVertices = hop(availableVertices, JBottomRight.getLocation().x, JBottomRight.getLocation().y);
		}

		if (bottomLeft != null && JBottomLeft != null && bottomLeft.content != 0 && JBottomLeft.content == 0
				&& !JBottomLeft.isVisited()) {
			vertexMat[y + 2][x - 2].setVisited(true);
			availableVertices.add(JBottomLeft);
			availableVertices = hop(availableVertices, JBottomLeft.getLocation().x, JBottomLeft.getLocation().y);
		}


		return availableVertices;
	}

	public void makeVisitedFalse() {
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

	public void callAI() {
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
				}
			}
		}

		Vertex goal = null;

		for(int i = 0; i < playerSoldiers.length; i++) {
			int x = (int) playerSoldiers[i].getX();
			int y = (int) playerSoldiers[i].getY();

			if (this.vertexMat[y][x].content != 1) {	//Computer
				goal = this.vertexMat[y][x];
				break;
			}
		}


		Movement bestMove = null;
		double bestScore = Double.MAX_VALUE;

		//Getting best score
		for (Vertex currV: vertices) {
			Movement vertexMove = minmax(depth, tempBoard, currV, goal, true);
			if (vertexMove != null && vertexMove.score < bestScore) {
				bestScore = vertexMove.score;
				bestMove = new Movement(currV.getLocation(), vertexMove.dest, bestScore);
				System.out.println(bestScore);
			}
		}
	
		tempX = (int) bestMove.src.getLocation().getX();
		tempY = (int) bestMove.src.getLocation().getY();

		move((int) bestMove.dest.getLocation().getX(), (int) bestMove.dest.getLocation().getY());
	}

	public Movement minmax(int depth, Vertex copyBoard[][], Vertex vertex, Vertex goal , boolean isMaximizing) {
		ArrayList<Vertex> possibleMoves = findNextMoves((int) vertex.getLocation().getX(),
				(int) vertex.getLocation().getY());

	
		Point bestPoint = null;
		Movement bestMove = null, baseBestMove = null;
		
		if (isMaximizing) {
			
			double bestScore = Double.MAX_VALUE;

			for (Vertex vertexMove : possibleMoves) {
				
				Vertex newBoard[][] = new Vertex[H][W];
				
				newBoard = simulateMove((int) vertexMove.getLocation().getX(), (int) vertexMove.getLocation().getY(),
						(int) vertex.getLocation().getX(), (int) vertex.getLocation().getY(), copyBoard);
				if (heuristicFun(newBoard, goal) < bestScore) {
					
					bestScore = heuristicFun(newBoard, goal);
					
					bestPoint = new Point((int) vertexMove.getLocation().getX(), (int) vertexMove.getLocation().getY());

					baseBestMove = new Movement(vertex.getLocation(), bestPoint, bestScore);

				}
			}
			if(depth > 1 && !findNextMoves(copyBoard[bestPoint.y][bestPoint.x].getLocation().x, copyBoard[bestPoint.y][bestPoint.x].getLocation().y).isEmpty()){
				System.out.println("Recursion: "+ depth);
				bestMove = minmax(depth-1, copyBoard, copyBoard[baseBestMove.src.y][baseBestMove.src.x], goal, true);
			}
			else{
				System.out.println("Recursion return: "+ depth);
				return baseBestMove;
			}
			
		}
		else{
			
		}

		return bestMove;
		

	}

	Vertex[][] simulateMove(int destX, int destY, int srcX, int srcY, Vertex[][] copyBoard) {

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


	public double heuristicFun(Vertex copyBoard[][], Vertex goal) {

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
}
