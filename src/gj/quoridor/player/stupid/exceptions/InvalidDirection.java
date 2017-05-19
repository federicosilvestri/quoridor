package gj.quoridor.player.stupid.exceptions;

import gj.quoridor.player.stupid.core.GameManager;

public class InvalidDirection extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDirection(int d) {
		super("You have passed invalid direction ("+ d + "), directions must be in set [" + GameManager.FORWARD + ", "
				+ GameManager.RIGHT + "]");
	}
}
