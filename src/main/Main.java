package main;

import java.util.Arrays;

import gj.quoridor.player.stupid.Board;
import gj.quoridor.player.stupid.BoardMatrix;

public class Main {

	public static void main(String args[]) {
		Board board = new Board();

		//
		// board.play(Board.RED, Board.PUT_WALL, 120);
		// board.play(Board.RED, Board.PUT_WALL, 97);
		// board.play(Board.RED, Board.PUT_WALL, 90);
		// board.play(Board.RED, Board.PUT_WALL, 99);
		// board.play(Board.RED, Board.PUT_WALL, 124);
		// board.play(Board.RED, Board.PUT_WALL, 101);
		// board.play(Board.BLUE, Board.PUT_WALL, 69);
		// board.play(Board.BLUE, Board.PUT_WALL, 62);
		// board.play(Board.BLUE, Board.PUT_WALL, 71);
		// board.play(Board.BLUE, Board.PUT_WALL, 103);
		// board.play(Board.BLUE, Board.PUT_WALL, 127);


		// board.play(Board.BLUE, Board.MOVE, Board.FORWARD);
		BoardMatrix bm = board.boardMatrix;
		System.out.println(bm);
		// boolean available = bm.pathAvailable(0, 16, 0, 16, 0, 16);
		// System.out.println("Path available: " + available);
		int[][] adjMatrix = bm.generateAdjacencyMatrix();
		
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix.length; j++) {
				System.out.print(adjMatrix[i][j] + "");
			}
			System.out.println();
		}
		
		System.out.println(adjMatrix[12][3]);
	}
}
