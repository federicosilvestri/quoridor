package gj.quoridor.player.stupid.core;

import java.util.ArrayList;
import java.util.List;

public class Board {

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
	 * Bias of matrix.
	 */
	public static final int BIAS = GameCostants.CELL_WALL_DISTANCE + 1;

	// Utility static methods

	/**
	 * Utility function, get wall code related to player.
	 * 
	 * @param player
	 *            related player
	 * @return wall code
	 */
	public static int getWallCode(int player) {
		switch (player) {
		case GameManager.BLUE:
			return BLUE_WALL;
		case GameManager.RED:
			return RED_WALL;
		default:
			throw new RuntimeException("Invalid player code detected, cannot return wall code.");
		}
	}

	/**
	 * Check if wall is active or not.
	 * 
	 * @param wallValue
	 *            value of wall
	 * @return true if wall is active, false if it's not active
	 */
	public static boolean isWallActive(int wallValue) {
		switch (wallValue) {
		case BLUE_WALL:
		case RED_WALL:
			return true;
		case EMPTY_WALL:
			return false;
		default:
			throw new RuntimeException("Invalid Wall value! " + wallValue);
		}
	}

	/**
	 * Utility function, get player position code related to player.
	 * 
	 * @param player
	 *            related player
	 * @return position code
	 */
	public static int getPlayerPositionCode(int player) {
		switch (player) {
		case GameManager.BLUE:
			return BLUE_POSITION;
		case GameManager.RED:
			return RED_POSITION;
		default:
			throw new RuntimeException("Invalid player code detected, cannot return player position code");
		}
	}

	/**
	 * Get distance generated by a move.
	 * 
	 * @param x0
	 *            start x
	 * @param y0
	 *            start y
	 * @param x1
	 *            end x
	 * @param y1
	 *            end y
	 * @return distance of x and y
	 */
	public static int getMoveDistance(int x0, int y0, int x1, int y1) {
		int xDistance = Math.abs(x0 - x1);
		int yDistance = Math.abs(y0 - y1);

		return xDistance + yDistance;
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

	// Instance method

	/**
	 * Internal matrix.
	 */
	private final int matrix[][];

	/**
	 * Create new Board Matrix object.
	 * 
	 * @param rows
	 *            number of rows
	 * @param cols
	 *            number of columns
	 */
	public Board(int rows, int cols, int[] initialRed, int[] initialBlue) {
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
		matrix[initialRed[1]][initialRed[0]] = RED_POSITION;
		matrix[initialBlue[1]][initialBlue[0]] = BLUE_POSITION;
	}

	private Board(int[][] matrix2) {
		matrix = new int[matrix2.length][matrix2[0].length];
		
		for (int i = 0; i < matrix2.length; i++) {
			for (int j = 0; j < matrix2[i].length; j++) {
				matrix[i][j] = matrix2[i][j];
			}
		}
	}

	// Cells management
	/**
	 * Check if player coordinates are valid or not.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return true if they are correct, false otherwise.
	 */
	public boolean checkPlayerCoords(int x, int y) {
		if (x < 0 || x > matrix[0].length) {
			return false;
		}

		if (y < 0 || y > matrix.length) {
			return false;
		}

		if ((x % BIAS) != 0 || (y % BIAS) != 0) {
			return false;
		}

		return true;
	}

	/**
	 * Get cell coordinates by index
	 * 
	 * @param index
	 *            index of cell
	 * @return array of two value, x and y
	 */
	public int[] getCellCoord(int index) {
		int coords[] = new int[2];

		int m = (matrix.length + 1) / 2;
		coords[0] = 2 * (index % m);
		coords[1] = (index / m) * 2;

		return coords;
	}

	/**
	 * Get cell index by coordinates
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return index of cell
	 */
	public int getCellIndex(int x, int y) {
		int index = 0;

		for (int i = 0; i < matrix.length; i += 2) {
			for (int j = 0; j < matrix.length; j += 2) {
				if (j == x && i == y) {
					return index;
				}

				index += 1;
			}
		}

		throw new RuntimeException("Conversion between coordinates and cell index gone wrong!");
	}

	/**
	 * Update player coordinates.
	 * 
	 * @param player
	 *            related player
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public void updatePlayerCoords(int player, int[] oldCoords, int[] newCoords) {
		int code = Board.getPlayerPositionCode(player);

		// Update old position with empty space
		matrix[oldCoords[1]][oldCoords[0]] = Board.EMPTY_POSITION;
		// Update new position with player code
		matrix[newCoords[1]][newCoords[0]] = code;
	}

	// Walls Management

	/**
	 * 
	 * Get coordinates of adjacent walls, related to passed cell coordinates.
	 * 
	 * @param cellCoords
	 *            cell coordinates
	 * @return List of coordinates (list of array of integers)
	 */
	public List<int[]> getAdjacentlyWallCoords(int cellCoords[]) {
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

	private boolean checkWallCoords(int coord[]) {
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
	 * Get coordinates of a wall.
	 * 
	 * @param index
	 *            wall index
	 * @return array of 3 integer which index 0 is main coordinate, and 1 2 are
	 *         second and third
	 */
	public int[][] getWallCoords(int index) {
		int coords[][] = new int[3][2];

		// Get first main coordinates
		coords[0][1] = index / 8;
		coords[0][0] = 2 * (index % 8) + (1 - coords[0][1] % 2);

		// Get related coordinates
		if (wallIsVertical(index)) {
			coords[1] = new int[] { coords[0][0], coords[0][1] + 1 };
			coords[2] = new int[] { coords[0][0], coords[0][1] + 2 };
		} else {
			coords[1] = new int[] { coords[0][0] + 1, coords[0][1] };
			coords[2] = new int[] { coords[0][0] + 2, coords[0][1] };
		}

		return coords;
	}

	/**
	 * Get if wall is active or not.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return true if it's active, false if it is no active
	 */
	public boolean isWallActive(int x, int y) {
		return Board.isWallActive(matrix[y][x]);
	}

	/**
	 * Check if wall is available on passed position.
	 * 
	 * @param wallCoords
	 *            list of coordinates of walls
	 * @return true if wall is available, false otherwise
	 */
	public boolean isWallOccupied(int[][] wallCoords) {
		boolean occupied = false;

		for (int i = 0; i < wallCoords.length && !occupied; i++) {
			int x = wallCoords[i][0];
			int y = wallCoords[i][1];

			if (matrix[y][x] != EMPTY_WALL) {
				occupied = true;
			}
		}

		return occupied;
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
	public void addWall(int wallCoords[][], int player) {
		// Get coordinates
		computeAddWall(wallCoords, player, this.matrix);
	}

	private int[][] computeAddWall(int[][] wallCoords, int player, int matrix[][]) {
		// Get code
		int wallCode = Board.getWallCode(player);

		for (int i = 0; i < wallCoords.length; i++) {
			int x = wallCoords[i][0];
			int y = wallCoords[i][1];

			matrix[y][x] = wallCode;
		}

		return matrix;
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
	 * Check if passed move generates a broken-walls status or not.
	 * 
	 * @param x0
	 *            start x
	 * @param y0
	 *            start y
	 * @param x1
	 *            end x
	 * @param y1
	 *            end y
	 * @return true if walls are not broken, false if walls are broken
	 */
	public boolean checkBrokenWalls(int x0, int y0, int x1, int y1) {
		int wall = getWallByPath(new int[] { x0, y0 }, new int[] { x1, y1 });

		return !isWallActive(wall);
	}

	// Path and external object management

	/**
	 * Check if blue and red can reach winning destination after putting wall.
	 * 
	 * @param wallIndex
	 *            wall to put for simulation
	 * @param blueCoords
	 *            blue coordinates
	 * @param redCoords
	 *            red coordinates
	 * @return true if path is found, false otherwise
	 */
	public boolean checkReachability(int wallIndex, int blueCoords[], int redCoords[]) {
		int wallCoords[][] = getWallCoords(wallIndex);

		// Create simulated environment
		Board simulatedBoard;

		simulatedBoard = new Board(this.matrix);

		computeAddWall(wallCoords, GameManager.BLUE, simulatedBoard.matrix);
		PathSearcher pathSearcher = new PathSearcher(GameCostants.BLUE_WIN_Y, simulatedBoard);
		boolean bluePath = pathSearcher.compute(blueCoords[0], blueCoords[1]);
		pathSearcher.setDestinationY(GameCostants.RED_WIN_Y);
		boolean redPath = pathSearcher.compute(redCoords[0], redCoords[1]);

		return (redPath && bluePath);
	}

	public PathSearcher buildPathSearcher() {
		return new PathSearcher(GameCostants.BLUE_WIN_Y, this);
	}
	
	public Board copy() {
		return new Board(matrix);
	}

	int[][] getMatrix() {
		return matrix;
	}
	
	@Override
	public String toString() {
		String s = "";

		for (int i = 0; i < matrix.length; i++) {
			if (i == 0) {
				s += "y|x";
				for (int j = 0; j < matrix.length; j++) {
					s += j;

					if (j > 9) {
						s += " ";
					} else {
						s += "  ";
					}
				}

				s += "\n";
			}

			if (i > 9) {
				s += i + " ";
			} else {
				s += i + "  ";
			}

			for (int j = 0; j < matrix[i].length; j++) {
				switch (matrix[i][j]) {
				case EMPTY_WALL:
					s += " ";
					break;
				case EMPTY_POSITION:
					s += "•";
					break;
				case BLUE_POSITION:
					s += "B";
					break;
				case RED_POSITION:
					s += "R";
					break;
				case BLUE_WALL:
					s += "#";
					break;
				case RED_WALL:
					s += "@";
					break;
				}

				if (j > 9) {
					s += "  ";
				} else {
					s += "  ";
				}
			}

			s += "\n";
		}

		return s;
	}
}
