package gj.quoridor.player.stupid;

public class Board {

	/**
	 * RED Player index.
	 */
	private final static int RED = 0;

	/**
	 * Blue Player index.
	 */
	private final static int BLUE = 1;

	/**
	 * Internal matrix for Player positions.
	 */
	private final int[][] matrix;

	/**
	 * Player coordinates.
	 */
	private int[][] playerCoords;

	public Board() {
		matrix = new int[9][9];
		playerCoords = new int[2][];
		playerCoords[RED] = new int[] { 0, 4 };
		playerCoords[BLUE] = new int[] { 8, 4 };
	}

	public void play(boolean isRed, int action[]) {
		if (action[0] == 0) {
			// this is a move
			move(isRed, action[1]);
		} else if (action[0] == 1) {
			putWall(isRed, action[1]);
		} else {
			throw new RuntimeException("Board has received invalid game action (" + action[0] + ")");
		}
	}

	private void putWall(boolean isRed, int i) {
		// TODO Auto-generated method stub

	}

	private void move(boolean isRed, int direction) {
		int bias = -1;
		int player = BLUE;
		
		if (isRed) {
			player = RED;
			bias = 1;
		}
		
		switch(direction) {
			case 0:
				// forward
				playerCoords[player][1] += bias; 
				break;
			case 1:
				// back
				playerCoords[player][1] -= bias; 
				break;
			case 2:
				// left
				playerCoords[player][0] += bias;
				break;
			case 3:
				// right
				playerCoords[player][0] -= bias;
			default:
				// Houston, we have a problem, direction is not correct
				throw new RuntimeException("Directions must be in range [0,3], you have passed direction=" + direction);
		}
	}

}
