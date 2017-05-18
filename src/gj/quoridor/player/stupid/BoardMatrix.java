package gj.quoridor.player.stupid;

import java.util.ArrayList;
import java.util.List;

import gj.quoridor.player.stupid.exceptions.WallUnavailableException;

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

	private static int getPlayerWallCode(int player) {
		switch (player) {
		case Board.BLUE:
			return BLUE_WALL;

		case Board.RED:
			return RED_WALL;

		default:
			throw new RuntimeException("Invalid player code");
		}
	}

	private static int getPlayerPositionCode(int player) {
		return (player == Board.RED) ? RED_POSITION : BLUE_POSITION;
	}

	/**
	 * Create a new BoardMatrix object.
	 * 
	 * @param rows
	 *            number of rows
	 * @param cols
	 *            number of columns
	 * @param redCoords
	 *            initial red player coordinates
	 * @param blueCoords
	 *            initial blue player coordinates
	 */
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

		matrix[blueCoords[1]][blueCoords[0]] = BLUE_POSITION;
		matrix[redCoords[1]][redCoords[0]] = RED_POSITION;
	}

	/**
	 * Get cell coordinates by index
	 * 
	 * @param index
	 *            index of cell
	 * @return array of integer that represents x and y
	 */
	public int[] getCellCoordsByIndex(int index) {
		int coords[] = new int[2];
		int cols = (matrix.length + 1) / 2;

		coords[0] = (index % cols) * 2;
		coords[1] = (index / cols) * 2;

		return coords;
	}

	/**
	 * Get coordinates of adjacent walls, related to passed cell coordinates.
	 * 
	 * @param cellCoords
	 *            cell coordinates
	 * @return List of coordinates (list of array of integers)
	 */
	public List<int[]> getWallCoords(int cellCoords[]) {
		ArrayList<int[]> possibleCoords = new ArrayList<>();
		ArrayList<int[]> coords = new ArrayList<>();

		// forward, back, left, right
		possibleCoords.add(new int[] { cellCoords[0] - 1, cellCoords[1] });
		possibleCoords.add(new int[] { cellCoords[0] + 1, cellCoords[1] });
		possibleCoords.add(new int[] { cellCoords[0], cellCoords[1] + 1 });
		possibleCoords.add(new int[] { cellCoords[0], cellCoords[1] - 1 });

		for (int[] coord : possibleCoords) {
			if (checkWallCoords(coord)) {
				coords.add(coord);
			}
		}

		return coords;
	}

	/**
	 * Check if wall coordinate are corrected or not.
	 * 
	 * @param coord
	 *            coordinate of wall
	 * @return true if it's valid, false if it isn't.
	 */
	public boolean checkWallCoords(int coord[]) {
		if (coord[0] < 0 || coord[0] > matrix.length) {
			return false;
		}

		if (coord[1] < 0 || coord[1] > matrix[coord[0]].length) {
			return false;
		}

		if ((coord[0] % 2) == 0 && (coord[1] % 2 == 0)) {
			return false;
		}

		return true;
	}

	/**
	 * Check if wall is vertical or not vertical (horizontal).
	 * 
	 * @param index
	 *            index of wall
	 * @return true if is vertical, false if is horizontal
	 */
	public boolean wallIsVertical(int index) {
		boolean result = false;

		for (int i = 0; i <= 7 && !result; i++) {
			if (index >= (16 * i) && index <= (16 * i + 7)) {
				result = true;
			}
		}

		return result;
	}

	private int[] getMainWallCoord(int index) {
		int[] coord = new int[2];

		coord[1] = index / 8;
		coord[0] = 2 * (index % 8) + (1 - coord[1] % 2);

		return coord;
	}

	/**
	 * Get coordinates of a wall.
	 * 
	 * @param index
	 *            wall index
	 * @return array of 3 integer which index 0 is main coordinate, and 1 2 are
	 *         second and third
	 */
	public int[][] getWallCoords(int index) {
		int[][] coords = new int[3][2];

		// Get first main coordinates
		int[] main = getMainWallCoord(index);
		coords[0] = main;

		if (wallIsVertical(index)) {
			coords[1] = new int[] { main[0], main[1] + 1 };
			coords[2] = new int[] { main[0], main[1] + 2 };
		} else {
			coords[1] = new int[] { main[0] + 1, main[1] };
			coords[2] = new int[] { main[0] + 2, main[1] };
		}

		return coords;
	}

	private boolean isWallAvailable(int[][] wallCoords) {
		boolean available = true;

		for (int i = 0; i < wallCoords.length && available; i++) {
			int x = wallCoords[i][0];
			int y = wallCoords[i][1];

			if (matrix[y][x] != EMPTY_WALL) {
				available = false;
			}
		}

		return available;
	}

	/**
	 * Check if wall is available on passed position.
	 * 
	 * @param wallIndex
	 *            index of wall
	 * @return true if wall is available, false otherwise
	 */
	public boolean isWallAvailable(int wallIndex) {
		int[][] wallCoords = getWallCoords(wallIndex);

		return isWallAvailable(wallCoords);
	}

	/**
	 * This method checks if wall is available. Caution: if you pass non-wall
	 * coordinates it will return always false.
	 * 
	 * @param coord
	 *            coordinates of wall
	 * @return true if active, false otherwise
	 */
	public boolean isWallActive(int wallValue) {
		return (wallValue == BLUE_WALL) || (wallValue == RED_WALL);
	}

	private boolean isWallActive(int x, int y) {
		return isWallActive(matrix[y][x]);
	}

	/**
	 * Add a wall on matrix. This method controls if wall is already place on
	 * matrix. If is already placed this method will throw a
	 * WallUnavailableException.
	 * 
	 * @param index
	 *            index of wall
	 * @param player
	 *            related player
	 */
	public void addWall(int index, int player) {
		// Get coordinates
		int[][] coords = getWallCoords(index);

		if (!isWallAvailable(coords)) {
			throw new WallUnavailableException();
		}

		int wallCode = BoardMatrix.getPlayerWallCode(player);

		for (int i = 0; i < coords.length; i++) {
			int x = coords[i][0];
			int y = coords[i][1];

			matrix[y][x] = wallCode;
		}
	}

	/**
	 * Get wall coordinates given a path. Warning: passed move must be valid, so
	 * coordinates must be satisfy following formula: |x0 - x1| + |y0 - y1| = 2
	 * 
	 * @TODO implement exception throwing if path is not correct
	 * @param start
	 *            coordinates before move
	 * @param end
	 *            coordinates after move
	 * @return value of wall found in path
	 */
	public int getWallByPath(int start[], int end[]) {
		// First we need to know what wall is affected on this path

		int delta = end[0] - start[0];
		int wallX = start[0];
		int wallY = start[1];

		if (delta == 0) {
			// Y move
			wallY = (start[1] + end[1]) / 2; // if moves are 2-based,
												// number between A and B
												// exists always in N|.
		} else {
			// X Move
			wallX = (start[0] + end[0]) / 2;
		}

		return matrix[wallY][wallX];
	}

	/**
	 * Check if path is walkable, and there are not wall enabled by blue or red
	 * player.
	 * 
	 * @TODO implement exception throwing in case of path is not correct
	 * 
	 * @param start
	 *            coordinates before move
	 * @param end
	 *            coordinates after move
	 * @return true if move is corrected or false is move is not correct
	 */
	public boolean checkPath(int start[], int end[]) {
		int wall = getWallByPath(start, end);

		return !isWallActive(wall);
	}

	/**
	 * Update the coordinates of player from old to new. This method does not
	 * control if coordinates are valid or path is correct, but it update
	 * immediately on matrix
	 * 
	 * @param player
	 *            player in question
	 * @param oldCoords
	 *            old coordinates, or current
	 * 
	 * @param newCoords
	 *            new coordinates, or next
	 */
	public void updatePlayerCoords(int player, int oldCoords[], int newCoords[]) {
		int code = BoardMatrix.getPlayerPositionCode(player);

		// Update old position with empty space
		matrix[oldCoords[1]][oldCoords[0]] = BoardMatrix.EMPTY_POSITION;
		// Update new position with player code
		matrix[newCoords[1]][newCoords[0]] = code;
	}

	public boolean arePathAvailable(int matrix[][]) {
		boolean available = false;

		// Check horizontally if there are walls and put in a queue

		return available;
	}

	public boolean pathAvailable(int lx, int rx, int rrl, int rrr, int y, int dy) {
		assert (lx % 2 == 0 && rx % 2 == 0 && y % 2 == 0);

		System.out.println("Call main pathAvailable(" + lx + "," + rx + "," + y + "," + dy + ")");
		if (y >= dy) {
			System.out.println("destination reached, return true");
			return true;
		}

		if (lx >= rrr) {
			System.out.println("no cells to inspects, return false");
			return false;
		}

		int i = lx;
		boolean wallLocked = false;

		while (i < rrr && isWallActive(i, y + 1) && !(wallLocked = isWallActive(i + 1, y))) {
			i += 2;
		}

		if (i == rx) {
			System.out.println("No way to escape, return false");
			return false;
		}

		int lx0 = i;
		while (i < rrr && !isWallActive(i, y + 1) && !(wallLocked = isWallActive(i + 1, y))) {
			i += 2;
		}

		// left side
		int rx0 = i;
		System.out.println("Left side calling...");
		boolean ls = pathAvailable(lx0, rx0, rrl, rrr, y + 2, dy);

		// right side
		int lx1 = rx0;
		if (wallLocked) {
			// partition again
			lx1 = i + 2;
		}
		System.out.println("Right side calling...");
		boolean rs = pathAvailable(lx1, rx, rrl, rrr, y, dy);

		System.out.println("Left side says: " + ls + ", right says: " + rs + " so: " + (rs || ls));
		return (ls || rs);

	}

	public int[][] generateAdjacencyMatrix() {
		// Calculating size of adjacency matrix
		int cols = (matrix.length + 1) / 2;
		cols = cols * cols;
		int rows = (matrix[0].length + 1) / 2;
		rows = rows * rows;
		int adjMatrix[][] = new int[rows][cols];

		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[i].length; j++) {
				int a[] = getCellCoordsByIndex(i);
				int b[] = getCellCoordsByIndex(j);
				
				// check distance
				int distance = Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
				
				if (distance > 2) {
					adjMatrix[i][j] = -1;
				} else {
					if (checkPath(a, b)) {
						adjMatrix[i][j] = 1;
					}
				}
			}
		}

		return adjMatrix;
	}

	@Override
	public String toString() {
		String s = "";

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				switch (matrix[i][j]) {
				case EMPTY_POSITION:
					s += "0";
					break;
				case EMPTY_WALL:
					s += " ";
					break;
				case BLUE_POSITION:
					s += "B";
					break;
				case RED_POSITION:
					s += "R";
					break;
				case RED_WALL:
					s += "#";
					break;
				case BLUE_WALL:
					s += "@";
					break;
				default:
					s += " " + matrix[i][j] + " ";
				}
				s += " ";
			}

			s += "\n";
		}

		return s;
	}

}
