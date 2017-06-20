package quoridorGame;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gj.quoridor.player.silvestri.core.Board;
import gj.quoridor.player.silvestri.core.GameManager;

@RunWith(Parameterized.class)
public class BoardTest {

	private final static int VERTICAL[] = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 22, 23, 32, 33,
			34, 35, 36, 37, 38, 39, 48, 49, 50, 51, 52, 53, 54, 55, 64, 65, 66, 67, 68, 69, 70, 71, 80, 81, 82, 83, 84,
			85, 86, 87, 96, 97, 98, 99, 100, 101, 102, 103, 112, 113, 114, 115, 116, 117, 118, 119 };

	private final static int HORIZONTAL[] = new int[] { 8, 9, 10, 11, 12, 13, 14, 15, 24, 25, 26, 27, 28, 29, 30, 31,
			40, 41, 42, 43, 44, 45, 46, 47, 56, 57, 58, 59, 60, 61, 62, 63, 72, 73, 74, 75, 76, 77, 78, 79, 88, 89, 90,
			91, 92, 93, 94, 95, 104, 105, 106, 107, 108, 109, 110, 111, 120, 121, 122, 123, 124, 125, 126, 127 };

	private static final int[][] WALL_MATRIX_X = new int[16][16];
	private static final int[][] WALL_MATRIX_Y = new int[17][17 / 2];

	@Parameters(name = "wallIndex = {0}")
	public static Collection<Object[]> data() {
		// First calculate row vector and columns vector
		initRowVector();
		initColsVector();

		// second step
		List<Object[]> l = new LinkedList<>();
		for (int i = 0; i < 128; i++) {
			l.add(new Object[] { i });
		}

		return l;
	}

	private static void initMatrix(int[][] matrix, int value) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = value;
			}
		}
	}

	private static void initRowVector() {
		// First initialize all elements to -1
		initMatrix(WALL_MATRIX_Y, -1);

		int r = 0;
		for (int i = 0; i < WALL_MATRIX_Y.length; i++) {
			for (int j = 0; j < WALL_MATRIX_Y[i].length; j++) {
				WALL_MATRIX_Y[i][j] = r++;
			}
		}
	}

	private static void initColsVector() {
		// Same thing, initialize all elements to -1
		initMatrix(WALL_MATRIX_X, -1);

		int kr = 0;
		int kh = 8;
		for (int i = 0; i < WALL_MATRIX_X.length; i++) {
			if ((i % 2) == 0) {

				WALL_MATRIX_X[i][1] = kh;
				kh += 1;
				for (int j = 3; j < WALL_MATRIX_X[i].length; j += 2) {
					WALL_MATRIX_X[i][j] = WALL_MATRIX_X[i][j - 2] + 16;
				}

			} else {
				WALL_MATRIX_X[i][0] = kr;
				kr += 1;
				for (int j = 2; j < WALL_MATRIX_X[i].length; j += 2) {
					WALL_MATRIX_X[i][j] = WALL_MATRIX_X[i][j - 2] + 16;
				}
			}
		}
	}

	public static String stringifyMatrices() {
		initRowVector();
		initColsVector();

		String s = "- WALL MATRIX X -\n";

		for (int y = 0; y < WALL_MATRIX_X.length; y++) {
			for (int x = 0; x < WALL_MATRIX_X[y].length; x++) {
				s += WALL_MATRIX_X[y][x] + "\t";
			}

			s += "\n";
		}

		s += "\n-WALL MATRIX Y -\n";

		for (int y = 0; y < WALL_MATRIX_Y.length; y++) {
			for (int x = 0; x < WALL_MATRIX_Y[y].length; x++) {
				s += WALL_MATRIX_Y[y][x] + "\t";
			}

			s += "\n";
		}

		return s;
	}

	private final int index;
	private Board board;

	public BoardTest(int index) {
		this.index = index;
		board = new GameManager().board;
		
	}

	@Test
	public void testIsVertical() {
		int hIndex = Arrays.binarySearch(HORIZONTAL, index);
		int vIndex = Arrays.binarySearch(VERTICAL, index);

		if (hIndex > -1) {
			assertEquals(false, board.wallIsVertical(index));
		} else if (vIndex > -1) {
			assertEquals(true, board.wallIsVertical(index));
		} else {
			fail("Test failed because this index is not recognized as vertical or horizontal");
		}
	}

	private int[] getCoords(int wallIndex) {
		int[] coords = new int[] { -1, -1 };

		// Searching index of X
		for (int i = 0; i < WALL_MATRIX_X.length; i++) {
			for (int j = 0; j < WALL_MATRIX_X[i].length; j++) {
				if (wallIndex == WALL_MATRIX_X[i][j]) {
					coords[0] = i;
				}
			}
		}

		// Searching Y
		for (int i = 0; i < WALL_MATRIX_Y.length; i++) {
			for (int j = 0; j < WALL_MATRIX_Y[i].length; j++) {
				if (wallIndex == WALL_MATRIX_Y[i][j]) {
					coords[1] = i;
				}
			}
		}

		return coords;
	}

	@Test
	public void getMainWallCoordTest() {
		assertEquals(Arrays.toString(getCoords(index)), Arrays.toString(board.getWallCoords(index)[0]));
	}
	
	@Test
	public void verifySurpassedWall() {
		// Use already test wall matrix
		//int[][] coords = boardMatrix.getWallCoords(index);
	}

	@Test
	public void testPathAvailable() {
		
	}
	
}
