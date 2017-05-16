package main;

import gj.quoridor.player.stupid.Board;
import gj.quoridor.player.stupid.BoardMatrix;

public class Main {
	
	public static void main(String args[]) {
		Board board = new Board();
		
		board.play(Board.BLUE, Board.MOVE, Board.FORWARD);
		board.play(Board.RED, Board.MOVE, Board.FORWARD);
		board.play(Board.RED, Board.MOVE, Board.RIGHT);
		
		board.play(Board.FORWARD, Board.PUT_WALL, 3);
		board.play(Board.RED, Board.PUT_WALL, 92);
		board.play(Board.BLUE, Board.MOVE, Board.FORWARD);
		board.play(Board.BLUE, Board.MOVE, Board.FORWARD);
		
		BoardMatrix bm = board.boardMatrix;
		System.out.println(bm);
		
		
		
		
	}
}
