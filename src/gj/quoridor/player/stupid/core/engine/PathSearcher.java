package gj.quoridor.player.stupid.core.engine;

import gj.quoridor.player.stupid.core.Board;
import gj.quoridor.player.stupid.core.GameCostants;

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
	 * Right side.
	 */
	private final int r;

	/**
	 * Left side.
	 */
	private final int l;

	/**
	 * Top level.
	 */
	private final int top;

	/**
	 * Bottom level.
	 */
	private final int bottom;

	/**
	 * Destination Y
	 */
	private int destinationY;
	
	/**
	 * Bias of computation
	 */
	private int bias;

	/**
	 * Create a new Path Searcher object.
	 * 
	 * @param simulatedMatrix
	 *            matrix created by Board Object
	 */
	public PathSearcher(int simulatedMatrix[][], int destinationY) {
		this.matrix = simulatedMatrix;
		r = matrix[0].length - 1;
		l = 0;
		top = matrix.length - 1;
		bottom = 0;
		setDestinationY(destinationY);
	}
	
	public void setDestinationY(int destinationY) {
		this.destinationY = destinationY;
	}

	private boolean isWallActive(int x, int y) {
		return Board.isWallActive(matrix[y][x]);
	}

	public boolean compute(int startX, int startY) {
		return true;
//		bias = GameCostants.CELLS_DISTANCE;
//		
//		if (startY > destinationY) {
//			bias *= -1;
//		}
//		return pathAvailable(startX, startY);
	}
	
	/**
	 * Search a path inside matrix
	 * 
	 * @param startX
	 *            player x coordinate
	 * @param l
	 *            max left
	 * @param r
	 *            max right
	 * @param startY
	 *            player y coordinate
	 * @param destinationY
	 *            destination y
	 * @return true if path exists, false otherwise
	 */
	private boolean pathAvailable(int startX, int startY) {
		if (startY == destinationY) {
			// reached destination y, stop it
			return true;
		}

		// left side iteration
		boolean leftSide = leftSideIteration(startX, startY);
		// right side iteration
		boolean rightSide = rightSideIteration(startX, startY);	

		return (leftSide || rightSide);
	}

	private boolean leftSideIteration(int start, int y) {
		System.out.println("Left side iter: " + start + ", " + y);
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
			result = pathAvailable(lx, y + 2);
		}

		System.out.println("Scan finished, result=" + result);
		return result;
	}

	private boolean rightSideIteration(int start, int y) {
		System.out.println("Right side iter: " + start + ", " + y);
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
			result = pathAvailable(rx, y + 2);
		}

		System.out.println("Scan finished, result=" + result);
		return result;
	}

}
