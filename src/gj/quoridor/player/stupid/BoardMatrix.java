package gj.quoridor.player.stupid;

import java.util.ArrayList;
import java.util.List;

import gj.quoridor.player.stupid.exceptions.BadMoveException;
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

	public boolean wallIsVertical(int index) {
		boolean result = false;

		for (int i = 0; i <= 7 && !result; i++) {
			if (index >= (16 * i) && index <= (16 * i + 7)) {
				result = true;
			}
		}

		return result;
	}

	public int[] getMainWallCoord(int index) {
		int[] coord = new int[2];

		coord[1] = index / 8;
		coord[0] = 2 * (index % 8) + (1 - coord[1] % 2);

		return coord;
	}

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

	public boolean isWallAvailable(int[][] wallCoords) {
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

	public void updatePlayerCoords(int player, int oldCoords[], int newCoords[]) {
		int code = BoardMatrix.getPlayerPositionCode(player);

		matrix[oldCoords[0]][oldCoords[1]] = BoardMatrix.EMPTY_POSITION;
		matrix[newCoords[0]][newCoords[1]] = code;
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
