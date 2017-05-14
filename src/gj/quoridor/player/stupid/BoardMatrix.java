package gj.quoridor.player.stupid;

import java.util.ArrayList;
import java.util.List;

import gj.quoridor.player.stupid.exceptions.BadMoveException;

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
	
	public List<int[]> getWallCoords(int index) {
		List<int[]> coords = new ArrayList<>();
		
		// Get first main coordinates
		int[] main = getMainWallCoord(index);
		coords.add(main);
		
		if (wallIsVertical(index)) {
			
		} else {
			
		}
		
		return coords;
	}
	
	public void putWall(int index, int player) {
		
	}

	@Override
	public String toString() {
		String s = "";

		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[x].length; y++) {
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
