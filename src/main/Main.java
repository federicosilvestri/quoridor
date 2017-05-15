package main;

import gj.quoridor.player.stupid.Board;
import gj.quoridor.player.stupid.BoardMatrix;

public class Main {
	
	public static void main(String args[]) {
		Board board = new Board();
		
		board.play(Board.BLUE, 1, 90);
		
		BoardMatrix bm = board.boardMatrix;
		System.out.println(bm);
		
		
		
		
	}
}
