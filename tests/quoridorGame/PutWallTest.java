package quoridorGame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gj.quoridor.engine.Board;
import gj.quoridor.player.stupid.BoardMatrix;

@RunWith(Parameterized.class)
public class PutWallTest {

	@Parameters(name = "wallIndex = {0}")
	public static Collection<Object[]> data() {
		List<Object[]> l = new LinkedList<>();

		for (int i = 0; i < 128; i++) {
			l.add(new Object[] { i });
		}

		return l;
	}

	private final static int HORIZONTAL[] = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 22, 23, 32, 33,
			34, 35, 36, 37, 38, 39, 48, 49, 50, 51, 52, 53, 54, 55, 64, 65, 66, 67, 68, 69, 70, 71, 80, 81, 82, 83, 84,
			85, 86, 87, 96, 97, 98, 99, 100, 101, 102, 103, 112, 113, 114, 115, 116, 117, 118, 119 };

	private final static int VERTICAL[] = new int[] { 8, 9, 10, 11, 12, 13, 14, 15, 24, 25, 26, 27, 28, 29, 30, 31, 40,
			41, 42, 43, 44, 45, 46, 47, 56, 57, 58, 59, 60, 61, 62, 63, 72, 73, 74, 75, 76, 77, 78, 79, 88, 89, 90, 91,
			92, 93, 94, 95, 104, 105, 106, 107, 108, 109, 110, 111, 120, 121, 122, 123, 124, 125, 126, 127 };

	private final int index;
	private BoardMatrix boardMatrix;
	
	public PutWallTest(int index) {
		this.index = index;
		boardMatrix = new BoardMatrix(17, 17, new int[] {0, 0}, new int[] {0,0});
	}

	@Test
	public void testIsVertical() {
		int hIndex = Arrays.binarySearch(HORIZONTAL, index);
		int vIndex = Arrays.binarySearch(VERTICAL, index);
		
		if (hIndex != -1) {
			assertEquals(false, boardMatrix.wallIsVertical(index));
		} else if(vIndex != -1) {
			assertEquals(true, boardMatrix.wallIsVertical(index));
		} else {
			fail("Test failed because this index is not recognized as vertical or horizontal");
		}
	}

}
