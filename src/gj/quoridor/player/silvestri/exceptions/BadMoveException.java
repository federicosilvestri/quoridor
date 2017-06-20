package gj.quoridor.player.silvestri.exceptions;

import java.util.Arrays;

import gj.quoridor.player.silvestri.core.GameManager;

public class BadMoveException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadMoveException(int player, int move[]) {
		super("Bad move detected: player " + (player == GameManager.RED ? "RED" : "BLUE") + " with coords: "
				+ Arrays.toString(move));
	}
}
