package gj.quoridor.player.stupid.core.engine;

public class PathSearcher {
	
	private final int matrix[][];
	
	public PathSearcher(int simulatedMatrix[][]) {
		this.matrix = simulatedMatrix;
	}
	
	
	private boolean isWallActive(int x, int y) {
		return true; // stub
	}
	
	public boolean pathAvailable(int start, int l, int r, int y, int dy) {
		if (y == dy) {
			// reached destination y, stop it
			return true;
		}

		boolean leftSide;
		boolean rightSide;

		// left side iteration
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
			leftSide = false;
		} else {
			// found free space
			leftSide = pathAvailable(lx, l, r, y + 2, dy);
		}

		// right side iteration
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
			rightSide = false;
		} else {
			// Found free space on top
			rightSide = pathAvailable(rx, l, r, y + 2, dy);
		}

		return (leftSide || rightSide);
	}
	
	
}
