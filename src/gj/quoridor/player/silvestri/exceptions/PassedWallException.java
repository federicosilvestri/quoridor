package gj.quoridor.player.silvestri.exceptions;

import java.util.Arrays;

public class PassedWallException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PassedWallException(int[] current, int[] next) {
		super("Player has passed wall from " + Arrays.toString(current) + " to " + Arrays.toString(next));
	}

}
