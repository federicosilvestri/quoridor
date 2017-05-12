package quoridorGame;

import static org.junit.Assert.*;

import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import gj.quoridor.player.stupid.Board;
import gj.quoridor.player.stupid.CoordinatesConverter;
import gj.quoridor.player.stupid.exceptions.CoordinatesConvertException;

public class CoordinatesConverterTest {

	private CoordinatesConverter cvBlue;
	private CoordinatesConverter cvRed;

	@Before
	public void setUp() throws Exception {
		cvRed = new CoordinatesConverter(true);
		cvBlue = new CoordinatesConverter(false);
	}

	@Test
	public void testConvertTooDistance() {
		SecureRandom sr = new SecureRandom();

		for (int i = 0; i < 1000; i++) {
			// generate distance from 2 to 5
			int distance = sr.nextInt(4) + 2;
			int current[] = new int[] { sr.nextInt(9), sr.nextInt(9) };
			int next[] = new int[] { current[0] + distance, current[1] };
			int next2[] = new int[] { current[0], current[1] + distance };

			System.out.println(Arrays.toString(current) + " " + Arrays.toString(next));

			boolean result = false;
			try {
				cvBlue.convert(current, next);
			} catch (CoordinatesConvertException ex) {
				result = true;
			}

			boolean result2 = false;
			try {
				cvBlue.convert(current, next2);
			} catch (CoordinatesConvertException ex) {
				result2 = true;
			}

			if (!(result && result2)) {
				fail("Coordinates are not compatible, but system is trying to convert it!");
			}
		}
	}

	@Test
	public void testRedForward() {
		testForwardConvertCoords(cvRed, Board.FORWARD);
	}

	@Test
	public void testRedBack() {
		testBackConvertCoords(cvRed, Board.BACK);
	}

	@Test
	public void testRedLeft() {
		testLeftConvertCoords(cvRed, Board.LEFT);
	}

	@Test
	public void testRedRight() {
		testRightConvertCoords(cvRed, Board.RIGHT);
	}

	@Test
	public void testBlueForward() {
		testBackConvertCoords(cvBlue, Board.FORWARD);
	}

	@Test
	public void testBlueBack() {
		testForwardConvertCoords(cvBlue, Board.BACK);
	}

	@Test
	public void testBlueLeft() {
		testRightConvertCoords(cvBlue, Board.LEFT);
	}

	@Test
	public void testBlueRight() {
		testLeftConvertCoords(cvBlue, Board.RIGHT);
	}

	private void testForwardConvertCoords(CoordinatesConverter cv, int expectedDirection) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 7; j++) {
				int current[] = new int[] { i, j };
				int next[] = new int[] { i, j + 1 };

				int move = cv.convert(current, next);
				assertEquals(move, expectedDirection);
			}
		}
	}

	private void testBackConvertCoords(CoordinatesConverter cv, int expectedDirection) {
		for (int i = 8; i > -1; i--) {
			for (int j = 8; j > 0; j--) {
				int current[] = new int[] { i, j };
				int next[] = new int[] { i, j - 1 };

				int move = cv.convert(current, next);
				assertEquals(move, expectedDirection);
			}
		}
	}

	private void testRightConvertCoords(CoordinatesConverter cv, int expectedDirection) {
		for (int i = 8; i > -1; i--) {
			for (int j = 8; j > 0; j--) {
				int current[] = new int[] { j, i };
				int next[] = new int[] { j - 1, i };

				int move = cv.convert(current, next);
				assertEquals(move, expectedDirection);
			}
		}
	}

	private void testLeftConvertCoords(CoordinatesConverter cv, int expectedDirection) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 8; j++) {
				int current[] = new int[] { j, i };
				int next[] = new int[] { j + 1, i };

				int move = cv.convert(current, next);
				assertEquals(move, expectedDirection);
			}
		}
	}

}