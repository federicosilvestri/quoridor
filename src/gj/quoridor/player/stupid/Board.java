package gj.quoridor.player.stupid;

import gj.quoridor.player.stupid.exceptions.BadMoveException;
import gj.quoridor.player.stupid.exceptions.OutOfStockWallException;
import gj.quoridor.player.stupid.exceptions.PassedWallException;
import gj.quoridor.player.stupid.exceptions.WallUnavailableException;

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
	 * Stock of walls
	 */
	private final int[] wallStock;

	/**
	 * Matrix where is located wall and players
	 */
	public final BoardMatrix boardMatrix;

	/**
	 * Create a new Board Object starting from standard initial coordinates.
	 */
	public Board() {
		playerCoords = new int[2][];
		playerCoords[RED] = new int[] { 8, 0 };
		playerCoords[BLUE] = new int[] { 8, 16 };
		wallStock = new int[] { 10, 10 };
		boardMatrix = new BoardMatrix(17, 17, playerCoords[RED], playerCoords[BLUE]);
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
			performMove(player, coords);
		} else if (action == PUT_WALL) {
			putWall(player, coords);
		} else {
			throw new RuntimeException("Board has received invalid game action (" + action + ")");
		}
	}

	private void putWall(int player, int index) {
		// check if wall is available in stock
		if (wallStock[player] < 1) {
			throw new OutOfStockWallException(player, index);
		}

		// check if wall can be positioned in current index
		if (!boardMatrix.isWallAvailable(index)) {
			throw new WallUnavailableException();
		}
		
		// Add wall to Board Matrix
		boardMatrix.addWall(index, player);
		
		// Remove from stock
		wallStock[player] -= 1;
	}

	private void performMove(int player, int direction) {
		// simulate move
		int[] nextCoords = computeMoveCoords(player, direction);

		// check if coordinates are correct
		if (!checkCoords(nextCoords)) {
			throw new BadMoveException(player, nextCoords);
		}
		
		// Check if move is legal
		boolean pathValid = boardMatrix.checkPath(playerCoords[player], nextCoords);
		
		if (!pathValid) {
			throw new PassedWallException(playerCoords[player], nextCoords);
		}

		// apply move to matrix
		boardMatrix.updatePlayerCoords(player, playerCoords[player], nextCoords);
		playerCoords[player] = nextCoords;
	}

	public int[] computeMoveCoords(int player, int direction) {
		// simple bias to adjust coordinates increasing or decreasing operations
		int bias = (player == RED) ? 2 : -2;
		// simulate moves in new array
		int coords[] = new int[2];
		System.arraycopy(playerCoords[player], 0, coords, 0, 2);

		// check directions
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

		return coords;
	}

	/**
	 * Check if this coordinates are valid.
	 * 
	 * @param coords
	 *            coordinates to check
	 * @return true if correct, false otherwise.
	 */
	public boolean checkCoords(int coords[]) {
		if (coords[0] < 0 || coords[0] > 16) {
			return false;
		}

		if (coords[1] < 0 || coords[1] > 16) {
			return false;
		}
		
		if (coords[0] % 2 != 0 || coords[1] % 2 != 0) {
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

	/**
	 * Get stock of walls.
	 * 
	 * @param player
	 *            chosen player
	 * @return integer value between 0 and 10
	 */
	public int getWallStock(int player) {
		return wallStock[player];
	}

	/**
	 * Get availability of walls.
	 * 
	 * @param player
	 *            related player
	 * @return true if walls are available, false otherwise
	 */
	public boolean wallAvailable(int player) {
		return (wallStock[player] > 1);
	}
}
