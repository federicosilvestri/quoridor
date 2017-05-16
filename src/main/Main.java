package main;

import gj.quoridor.player.stupid.Board;
import gj.quoridor.player.stupid.BoardMatrix;

public class Main {
	
	public static void main(String args[]) {
		Board board = new Board();
		
		//board.play(Board.RED, Board.PUT_WALL, 124);
		board.play(Board.BLUE, Board.MOVE, Board.FORWARD);
		
		BoardMatrix bm = board.boardMatrix;
		System.out.println(bm);
		
		
		
		
	}
}
