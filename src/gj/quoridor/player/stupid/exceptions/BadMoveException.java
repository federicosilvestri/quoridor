package gj.quoridor.player.stupid.exceptions;

import java.util.Arrays;

import gj.quoridor.player.stupid.Board;

public class BadMoveException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadMoveException(int player, int move[]) {
		super("Bad move detected: player " + (player == Board.RED ? "RED" : "BLUE") + " with coords: "
				+ Arrays.toString(move));
	}
}
