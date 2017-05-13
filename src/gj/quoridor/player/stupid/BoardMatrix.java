package gj.quoridor.player.stupid;

public class BoardMatrix {

	/**
	 * Empty position
	 */
	public static final int EMPTY_POSITION = 0;

	/**
	 * No wall.
	 */
	public static final int EMPTY_WALL = 1;

	/**
	 * Red Wall
	 */
	public static final int RED_WALL = 2;

	/**
	 * Blue wall
	 */
	public static final int BLUE_WALL = 3;

	/**
	 * Red position.
	 */
	public static final int RED_POSITION = 4;

	/**
	 * Blue position
	 */
	public static final int BLUE_POSITION = 5;

	/**
	 * Internal matrix
	 */
	private final int matrix[][];

	public BoardMatrix(int rows, int cols, int[] redCoords, int blueCoords[]) {
		matrix = new int[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if ((i % 2) == 0 && (j % 2) == 0) {
					matrix[i][j] = EMPTY_POSITION;
				} else {
					matrix[i][j] = EMPTY_WALL;
				}
			}
		}

		matrix[blueCoords[0]][blueCoords[1]] = BLUE_POSITION;
		matrix[redCoords[0]][redCoords[1]] = RED_POSITION;
	} 
	
	@Override
	public String toString() {
		String s = "";

		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[x].length; y++) {
				int sign = matrix[y][x];

				switch (matrix[y][x]) {
					case EMPTY_WALL:
						s += " ";
						break;
					case BLUE_POSITION:
						s += " B ";
						break;
					case RED_POSITION:
						s += " R ";
						break;
					default:
						s += " " + matrix[y][x] + " ";
				}

			}

			s += "\n";
		}

		return s;
	}

}
