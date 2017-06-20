package gj.quoridor.player.silvestri.exceptions;

import java.util.Arrays;

public class WallUnavailableException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WallUnavailableException(int[][] wCoords, int index) {
		super("Wall " + index + " is not available with coords: " + Arrays.toString(wCoords[0]));
	}
}
