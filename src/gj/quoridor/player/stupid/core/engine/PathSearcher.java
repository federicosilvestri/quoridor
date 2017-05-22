package gj.quoridor.player.stupid.core.engine;

import gj.quoridor.player.stupid.core.Board;
import gj.quoridor.player.stupid.core.GameCostants;
import main.Main;

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
	 * Bias of computation direction (up and down)
	 */
	private int dBias;

	/**
	 * verbose
	 */
	public boolean verbose;

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
		verbose = false;
	}

	private void log(String msg, int level) {
		if (verbose) {
			for (int i = 0; i < level; i++) {
				System.out.print(" ");
			}

			System.out.println(level + ")" + msg);
		}
	}

	public void setDestinationY(int destinationY) {
		this.destinationY = destinationY;
	}

	private boolean isWallActive(int x, int y) {
		return Board.isWallActive(matrix[y][x]);
	}

	public boolean compute(int startX, int startY) {
		System.out.print("Check reachibility starting from " + startX + "," + startY + " to " + destinationY + ",X");
		System.out.print(" dBias=" + dBias);
		
		int dBias;
		// Check if you have to move forward or back
		if (startY < destinationY) {
			dBias = 1;
		} else {
			dBias = -1;
		}

		// if (startY > 0) {
		// // First partition bottom
		// log("========BOTTOM==========");
		// bias *= -1;
		// boolean bottom = pathAvailable(startX, startY);
		// bias *= -1;
		// log("=========UP===========");
		// boolean up = false; //pathAvailable(startX, startY);
		//
		// return (bottom || up);
		// }

		long start = System.nanoTime();
		boolean available = pathAvailable(startX, startY, 0, dBias);
		long elapsed = System.nanoTime() - start;
		System.out.println(" elapsed time: " + elapsed + "ns");
		
		return available;
		
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
	private boolean pathAvailable(int startX, int startY, int i, int dBias) {
		if (startY == destinationY) {
			// reached destination y, stop it
			return true;
		}

		// if (startY < 0 || startX < 0) {
		//// System.out.println("Reached start of matrix, adjusting bias...");
		//// // Adjust bias
		//// bias *= -1;
		////
		//// // set coords to
		//// startY = 0;
		// }

		// left side iteration
		boolean leftSide = leftSideIteration(startX, startY, i + 1, dBias);
		// right side iteration
		boolean rightSide = rightSideIteration(startX, startY, i + 1, dBias);

		return (leftSide || rightSide);
	}

	private boolean leftSideIteration(int start, int y, int i, int dBias) {
		log("LFI::" + start + ", " + y, i);
		
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
			return false;
		}

		boolean topResult = pathAvailable(lx, y + (2 * dBias), i + 1, dBias);
		boolean leftResult = leftSideIteration(lx - 2, y, i + 1, dBias);

		return (topResult || leftResult);
	}

	private boolean rightSideIteration(int start, int y, int i, int dBias) {
		log("RTI:: " + start + ", " + y, i);

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
			return false;
		}

		// Found free space on top
		boolean topResult = pathAvailable(rx, y + (2 * dBias), i + 1, dBias);
		// Continue to right part
		boolean rightResult = pathAvailable(rx, y, i + 1, dBias);

		log("RTI:: " + start + ", " + y + ", result=" + topResult, i);
		return (topResult || rightResult);
	}

}
