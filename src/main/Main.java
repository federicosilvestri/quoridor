package main;

import gj.quoridor.player.stupid.Board;
import gj.quoridor.player.stupid.BoardMatrix;
import gj.quoridor.player.stupid.Dijikstra;

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
		Dijikstra d = new Dijikstra(adjMatrix, 0);
		
		d.dijkstra();
	}
}
