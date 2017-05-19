package gj.quoridor.player.stupid.core.engine;

import gj.quoridor.player.stupid.core.Board;

/**
 * This object is an implementation of researched algorithm to find if path is
 * available or not
 * 
 * @author federicosilvestri
 * @version 1.7
 *
 */
public class PathSearcher {

	/**
	 * Simulated matrix of Board Object.
	 */
	private final int matrix[][];

	/**
	 * Create a new Path Searcher object.
	 * 
	 * @param simulatedMatrix
	 *            matrix created by Board Object
	 */
	public PathSearcher(int simulatedMatrix[][]) {
		this.matrix = simulatedMatrix;
	}

	private boolean isWallActive(int x, int y) {
		return Board.isWallActive(matrix[y][x]);
	}

	/**
	 * Search a path inside matrix
	 * 
	 * @param start
	 *            player x coordinate
	 * @param l
	 *            max left
	 * @param r
	 *            max right
	 * @param y
	 *            player y coordinate
	 * @param dy
	 *            destination y
	 * @return true if path exists, false otherwise
	 */
	public boolean pathAvailable(int start, int l, int r, int y, int dy) {
		if (y == dy) {
			// reached destination y, stop it
			return true;
		}

		// left side iteration
		boolean leftSide = leftSideIteration(start, l, r, y, dy);
		// right side iteration
		boolean rightSide = rightSideIteration(start, l, r, y, dy);

		return (leftSide || rightSide);
	}

	private boolean leftSideIteration(int start, int l, int r, int y, int dy) {
		boolean result;
		int lx = start;
		boolean foundVerticalWall = false;

		while (lx >= l && !foundVerticalWall && isWallActive(lx, y + 1)) {
			int verticalWallIndex = lx - 1;

			if (verticalWallIndex < l) {
				// we don't have to check anymore
				foundVerticalWall = false;
			} else {
				foundVerticalWall = isWallActive(verticalWallIndex, y);
			}

			lx -= 2;
		}

		if (lx < l || foundVerticalWall) {
			// top is all locked or left side is locked
			result = false;
		} else {
			// found free space
			result = pathAvailable(lx, l, r, y + 2, dy);
		}

		return result;
	}

	private boolean rightSideIteration(int start, int l, int r, int y, int dy) {
		boolean result;
		boolean foundVerticalWall;
		int rx = start;
		do {
			int verticalWallIndex = rx + 1;

			if (verticalWallIndex > r) {
				// we don't have to check anymore
				foundVerticalWall = false;
			} else {
				foundVerticalWall = isWallActive(verticalWallIndex, y);
			}

			rx += 2;

		} while (rx <= r && !foundVerticalWall && isWallActive(rx, y + 1));

		if (rx > r || foundVerticalWall) {
			// top is all locked
			result = false;
		} else {
			// Found free space on top
			result = pathAvailable(rx, l, r, y + 2, dy);
		}

		return result;
	}

}
