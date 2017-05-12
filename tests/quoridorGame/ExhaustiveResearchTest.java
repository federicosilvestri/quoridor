package quoridorGame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gj.quoridor.player.stupid.Board;
import gj.quoridor.player.stupid.ExhaustiveResearch;

@RunWith(Parameterized.class)
public class ExhaustiveResearchTest {

	@Parameters(name = "(x = {0}, " + "y = {1})")
	public static Collection<Object[]> data() {
		Object[][] params = new Object[162][3];

		int i = 0;
		for (int t = 0; t < 2; t++) {
			for (int y = 0; y < 9; y++) {
				for (int x = 0; x < 9; x++) {
					params[i] = new Object[] {t==0, x, y };
					i += 1;
				}
			}
		}

		return Arrays.asList(params);
	}

	private ExhaustiveResearch er;
	private int[] coords;
	private boolean isRed;

	public ExhaustiveResearchTest(boolean isRed, int x, int y) {
		this.isRed = isRed;
		int player = isRed ? Board.RED : Board.BLUE;
		Board board = new Board();
		board.setPlayerCoords(player, new int[] { x, y });
		er = new ExhaustiveResearch(player, board);

		coords = new int[] { x, y };
	}

	@Test
	public void testGetActions() {
		List<Integer> expectedMoves = isRed ? getExpectedRedMoves() : getExpectedBlueMoves();
		List<int[]> testActions = er.getActions();

		// filter moves from put-walls
		List<Integer> testMoves = new ArrayList<>();
		for (int[] action : testActions) {
			if (action[0] == Board.MOVE) {
				testMoves.add(action[1]);
			}
		}

		// first check size
		assertEquals(expectedMoves.size(), testMoves.size());

		// order arrays
		Collections.sort(expectedMoves);
		Collections.sort(testMoves);

		assertEquals(expectedMoves, testMoves);
	}

	private List<Integer> getExpectedRedMoves() {
		List<Integer> moves = new ArrayList<>();

		/*
		 * All left-right prospective is related to RED player.
		 */
		if (coords[0] == 0 && coords[1] == 0) {
			// top left corner, right and forward
			moves.add(Board.LEFT);
			moves.add(Board.FORWARD);
		} else if (coords[0] == 8 && coords[1] == 0) {
			// top right corner, left or forward
			moves.add(Board.RIGHT);
			moves.add(Board.FORWARD);
		} else if (coords[0] == 0 && coords[1] == 8) {
			// bottom left corner, right or back
			moves.add(Board.LEFT);
			moves.add(Board.BACK);
		} else if (coords[0] == 8 && coords[1] == 8) {
			// bottom right corner, left or back
			moves.add(Board.RIGHT);
			moves.add(Board.BACK);
		} else if (coords[0] == 0) {
			// left bound, back, forward or right
			moves.add(Board.BACK);
			moves.add(Board.FORWARD);
			moves.add(Board.LEFT);
		} else if (coords[0] == 8) {
			// right bound, back, forward or left
			moves.add(Board.BACK);
			moves.add(Board.FORWARD);
			moves.add(Board.RIGHT);
		} else if (coords[1] == 0) {
			// top bound, forward, left or right
			moves.add(Board.FORWARD);
			moves.add(Board.LEFT);
			moves.add(Board.RIGHT);
		} else if (coords[1] == 8) {
			// bottom bound, back, left or right
			moves.add(Board.BACK);
			moves.add(Board.LEFT);
			moves.add(Board.RIGHT);
		} else {
			// all moves are accepted
			moves.add(Board.FORWARD);
			moves.add(Board.BACK);
			moves.add(Board.LEFT);
			moves.add(Board.RIGHT);
		}

		return moves;
	}

	private List<Integer> getExpectedBlueMoves() {
		List<Integer> moves = new ArrayList<>();

		/*
		 * All left-right prospective is related to RED player.
		 */
		if (coords[0] == 0 && coords[1] == 0) {
			// top left corner, right and forward
			moves.add(Board.RIGHT);
			moves.add(Board.BACK);
		} else if (coords[0] == 8 && coords[1] == 0) {
			// top right corner, left or forward
			moves.add(Board.LEFT);
			moves.add(Board.BACK);
		} else if (coords[0] == 0 && coords[1] == 8) {
			// bottom left corner, right or back
			moves.add(Board.RIGHT);
			moves.add(Board.FORWARD);
		} else if (coords[0] == 8 && coords[1] == 8) {
			// bottom right corner, left or back
			moves.add(Board.LEFT);
			moves.add(Board.FORWARD);
		} else if (coords[0] == 0) {
			// left bound, back, forward or right
			moves.add(Board.BACK);
			moves.add(Board.FORWARD);
			moves.add(Board.RIGHT);
		} else if (coords[0] == 8) {
			// right bound, back, forward or left
			moves.add(Board.BACK);
			moves.add(Board.FORWARD);
			moves.add(Board.LEFT);
		} else if (coords[1] == 0) {
			// top bound, forward, left or right
			moves.add(Board.BACK);
			moves.add(Board.LEFT);
			moves.add(Board.RIGHT);
		} else if (coords[1] == 8) {
			// bottom bound, back, left or right
			moves.add(Board.FORWARD);
			moves.add(Board.LEFT);
			moves.add(Board.RIGHT);
		} else {
			// all moves are accepted
			moves.add(Board.FORWARD);
			moves.add(Board.BACK);
			moves.add(Board.LEFT);
			moves.add(Board.RIGHT);
		}

		return moves;
	}

}
