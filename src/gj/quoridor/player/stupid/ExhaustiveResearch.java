package gj.quoridor.player.stupid;

import java.util.ArrayList;
import java.util.List;

public class ExhaustiveResearch {
	/**
	 * Is read or blue boolean.
	 */
	private final int player;

	/**
	 * Board object.
	 */
	private final Board board;

	/**
	 * Create a new exhaustive search object.
	 * 
	 * @param player
	 * @param board
	 */
	public ExhaustiveResearch(int player, Board board) {
		this.player = player;
		this.board = board;

	}

	/**
	 * Get all possible actions.
	 * 
	 * @return List of actions
	 * @TODO: implement walls
	 */
	public List<int[]> getActions() {
		List<int[]> actions = new ArrayList<>();

		// move actions
		for (int move : computeMoves()) {
			actions.add(new int[] { Board.MOVE, move });
		}

		// put-wall action
		//for (int wallIndex : computeWalls()) {
			//actions.add(new int[] { Board.PUT_WALL, wallIndex });
		//}

		return actions;
	}

	/**
	 * Finds out all possible coordinates movements from a starting point.
	 * 
	 * 
	 * @return a List of integer vectors with all possible reachable.
	 *         coordinates.
	 */
	private List<Integer> computeMoves() {
		List<Integer> moves = new ArrayList<>();

		// create all 4 moves
		int testMoves[] = new int[] { Board.FORWARD, Board.BACK, Board.LEFT, Board.RIGHT };

		// check 4 moves
		for (int move : testMoves) {
			// get coordinates produced by this move
			int coords[] = board.computeMoveCoords(player, move);

			// check if coordinates of player are valid
			if (board.checkCoords(coords)) {
				moves.add(move);
			}
		}

		return moves;
	}

	private List<Integer> computeWalls() {
		return null;
	}

}
