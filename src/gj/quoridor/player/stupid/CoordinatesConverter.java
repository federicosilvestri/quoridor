package gj.quoridor.player.stupid;

import gj.quoridor.player.stupid.exceptions.CoordinatesConvertException;

/**
 * This class converts coordinates to moves.
 * 
 * @author federicosilvestri
 *
 */
public class CoordinatesConverter {

	/**
	 * If player is red or blue
	 */
	private final boolean isRed;

	/**
	 * Create a new instance of class.
	 * 
	 * @param isRed
	 *            is red or blue
	 */
	public CoordinatesConverter(boolean isRed) {
		this.isRed = isRed;
	}

	/**
	 * Convert coordinates to moves.
	 * 
	 * @param currentCoords
	 *            coordinates.
	 * @param nextCoords
	 *            coordinates.
	 * @return moves code.
	 */
	public int convert(int currentCoords[], int nextCoords[]) {
		if (!checkDistance(currentCoords, nextCoords)) {
			throw new CoordinatesConvertException(currentCoords, nextCoords);
		}
		
		if (isRed) {
			return redConvert(currentCoords, nextCoords);
		}

		return blueConvert(currentCoords, nextCoords);
	}

	private boolean checkDistance(int currentCoords[], int nextCoords[]) {
		int xDistance = Math.abs(currentCoords[0] - nextCoords[0]);
		int yDistance = Math.abs(currentCoords[1] - nextCoords[1]);

		// Return XOR operation between x and y distances equations.
		return ((xDistance == 2) ^ (yDistance == 2));
		// return (xDistance == 1 && yDistance == 0 || xDistance == 0 &&
		// yDistance == 1);
	}

	private int redConvert(int current[], int next[]) {
		// Check x
		if (next[0] < current[0]) {
			return Board.RIGHT;
		}
		if (next[0] > current[0]) {
			return Board.LEFT;
		}
		// Check y
		if (next[1] > current[1]) {
			return Board.FORWARD;
		}
		if (next[1] < current[1]) {
			return Board.BACK;
		}
		// No move
		return -1;
	}

	private int blueConvert(int current[], int next[]) {
		// Check x
		if (next[0] < current[0]) {
			return Board.LEFT;
		}
		if (next[0] > current[0]) {
			return Board.RIGHT;
		}
		// Check y
		if (next[1] > current[1]) {
			return Board.BACK;
		}
		if (next[1] < current[1]) {
			return Board.FORWARD;
		}
		// No move
		return -1;
	}
}
