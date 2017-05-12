package gj.quoridor.player.stupid.exceptions;

import java.util.Arrays;

public class CoordinatesConvertException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CoordinatesConvertException(int[] currentCoords, int nextCoords[]) {
		super("Cannot convert coordinates in moves, incompatible coordinates: " + Arrays.toString(currentCoords)
				+ " to -> " + Arrays.toString(nextCoords));
	}
}
