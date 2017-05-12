package gj.quoridor.player.stupid;

import gj.quoridor.player.stupid.exceptions.BadMoveException;

/**
 * Board class.
 * 
 * @author federicosilvestri
 *
 */
public class Board {

	/**
	 * RED Player code.
	 */
	public final static int RED = 0;

	/**
	 * Blue Player code.
	 */
	public final static int BLUE = 1;

	/**
	 * Move action code.
	 */
	public static final int MOVE = 0;
	
	/**
	 * Put wall action code.
	 */
	public static final int PUT_WALL = 1;
	
	/**
	 * Move forward code.
	 */
	public final static int FORWARD = 0;

	/**
	 * Move back code.
	 */
	public final static int BACK = 1;

	/**
	 * Move left code.
	 */
	public final static int LEFT = 2;

	/**
	 * Move right code
	 */
	public final static int RIGHT = 3;

	/**
	 * Player coordinates.
	 */
	private int[][] playerCoords;

	/**
	 * Create a new Board Object starting from standard initial coordinates.
	 */
	public Board() {
		playerCoords = new int[2][];
		playerCoords[RED] = new int[] { 4, 0 };
		playerCoords[BLUE] = new int[] { 4, 8 };
	}

	/**
	 * Perform an action.
	 * 
	 * @param player
	 *            chosen player
	 * @param action
	 *            0 -> move 1-> put wall
	 * @param coords
	 *            coordinates of action
	 */
	public void play(int player, int action, int coords) {
		if (action == MOVE) {
			move(player, coords);
		} else if (action == PUT_WALL) {
			putWall(player, coords);
		} else {
			throw new RuntimeException("Board has received invalid game action (" + action + ")");
		}
	}

	private void putWall(int player, int i) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	private void move(int player, int direction) {
		// simple bias to adjust coordinates increasing or decreasing operations
		int bias = (player == RED) ? 1 : -1;

		// Simulate moves in new array
		int coords[] = new int[2];
		System.arraycopy(playerCoords[player], 0, coords, 0, 2);

		// Check directions
		switch (direction) {
			case FORWARD:
				coords[1] += bias;
				break;
			case BACK:
				coords[1] -= bias;
				break;
			case LEFT:
				coords[0] += bias;
				break;
			case RIGHT:
				coords[0] -= bias;
				break;
			default:
				throw new RuntimeException("Directions must be in range [0,3], you have passed direction=" + direction);
		}

		// Check if move is correct
		if (!checkCoords(coords)) {
			throw new BadMoveException(player, coords);
		}

		// Apply simulation to real coordinates
		System.arraycopy(coords, 0, playerCoords[player], 0, 2);
	}

	/**
	 * Check if this coordinates are valid.
	 * 
	 * @param coords
	 *            coordinates to check
	 * @return true if correct, false otherwise.
	 */
	public boolean checkCoords(int coords[]) {
		if (coords[0] < 0 || coords[0] > 8) {
			return false;
		}

		if (coords[1] < 0 || coords[1] > 8) {
			return false;
		}

		return true;
	}

	/**
	 * Get player coordinates.
	 * 
	 * @param player
	 *            chosen player
	 * @return array of size 2, {x,y}
	 */
	public int[] getPlayerCoords(int player) {
		return playerCoords[player];
	}

	/**
	 * Set player coordinates.
	 * 
	 * @param player
	 *            chosen player
	 * @param coords
	 *            coordinates to set
	 * @throws BadMoveException
	 *             in case of coordinates are not valid
	 */
	public void setPlayerCoords(int player, int[] coords) {
		if (!checkCoords(coords)) {
			throw new BadMoveException(player, coords);
		}

		playerCoords[player] = coords;
	}

}
