package quoridorGame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gj.quoridor.player.stupid.core.GameManager;
import gj.quoridor.player.stupid.core.engine.ExhaustiveResearch;



@RunWith(Parameterized.class)
public class ExhaustiveResearchTest {

	@Parameters(name = "(isRed = {0}, x = {1}, y = {2})")
	public static Collection<Object[]> data() {

		List<Object[]> l = new ArrayList<>();

		for (int t = 0; t < 2; t++) {
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					l.add(new Object[] { t == 0, x * 2, y * 2 });
				}
			}
		}

		return l;
	}

	private ExhaustiveResearch er;
	private int[] coords;
	private boolean isRed;

	public ExhaustiveResearchTest(boolean isRed, int x, int y) {
		this.isRed = isRed;
		int player = isRed ? GameManager.RED : GameManager.BLUE;
		GameManager gameManager = new GameManager();
		gameManager.setPlayerCoords(player, new int[] { x, y });
		er = new ExhaustiveResearch(player, gameManager);

		coords = new int[] { x, y };
	}

	@Test
	public void testGetActions() {
		List<Integer> expectedMoves = isRed ? getExpectedRedMoves() : getExpectedBlueMoves();
		List<int[]> testActions = er.getActions();

		// filter moves from put-walls
		List<Integer> testMoves = new ArrayList<>();
		for (int[] action : testActions) {
			if (action[0] == GameManager.MOVE) {
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
			moves.add(GameManager.LEFT);
			moves.add(GameManager.FORWARD);
		} else if (coords[0] == 16 && coords[1] == 0) {
			// top right corner, left or forward
			moves.add(GameManager.RIGHT);
			moves.add(GameManager.FORWARD);
		} else if (coords[0] == 0 && coords[1] == 16) {
			// bottom left corner, right or back
			moves.add(GameManager.LEFT);
			moves.add(GameManager.BACK);
		} else if (coords[0] == 16 && coords[1] == 16) {
			// bottom right corner, left or back
			moves.add(GameManager.RIGHT);
			moves.add(GameManager.BACK);
		} else if (coords[0] == 0) {
			// left bound, back, forward or right
			moves.add(GameManager.BACK);
			moves.add(GameManager.FORWARD);
			moves.add(GameManager.LEFT);
		} else if (coords[0] == 16) {
			// right bound, back, forward or left
			moves.add(GameManager.BACK);
			moves.add(GameManager.FORWARD);
			moves.add(GameManager.RIGHT);
		} else if (coords[1] == 0) {
			// top bound, forward, left or right
			moves.add(GameManager.FORWARD);
			moves.add(GameManager.LEFT);
			moves.add(GameManager.RIGHT);
		} else if (coords[1] == 16) {
			// bottom bound, back, left or right
			moves.add(GameManager.BACK);
			moves.add(GameManager.LEFT);
			moves.add(GameManager.RIGHT);
		} else {
			// all moves are accepted
			moves.add(GameManager.FORWARD);
			moves.add(GameManager.BACK);
			moves.add(GameManager.LEFT);
			moves.add(GameManager.RIGHT);
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
			moves.add(GameManager.RIGHT);
			moves.add(GameManager.BACK);
		} else if (coords[0] == 16 && coords[1] == 0) {
			// top right corner, left or forward
			moves.add(GameManager.LEFT);
			moves.add(GameManager.BACK);
		} else if (coords[0] == 0 && coords[1] == 16) {
			// bottom left corner, right or back
			moves.add(GameManager.RIGHT);
			moves.add(GameManager.FORWARD);
		} else if (coords[0] == 16 && coords[1] == 16) {
			// bottom right corner, left or back
			moves.add(GameManager.LEFT);
			moves.add(GameManager.FORWARD);
		} else if (coords[0] == 0) {
			// left bound, back, forward or right
			moves.add(GameManager.BACK);
			moves.add(GameManager.FORWARD);
			moves.add(GameManager.RIGHT);
		} else if (coords[0] == 16) {
			// right bound, back, forward or left
			moves.add(GameManager.BACK);
			moves.add(GameManager.FORWARD);
			moves.add(GameManager.LEFT);
		} else if (coords[1] == 0) {
			// top bound, forward, left or right
			moves.add(GameManager.BACK);
			moves.add(GameManager.LEFT);
			moves.add(GameManager.RIGHT);
		} else if (coords[1] == 16) {
			// bottom bound, back, left or right
			moves.add(GameManager.FORWARD);
			moves.add(GameManager.LEFT);
			moves.add(GameManager.RIGHT);
		} else {
			// all moves are accepted
			moves.add(GameManager.FORWARD);
			moves.add(GameManager.BACK);
			moves.add(GameManager.LEFT);
			moves.add(GameManager.RIGHT);
		}

		return moves;
	}

}
