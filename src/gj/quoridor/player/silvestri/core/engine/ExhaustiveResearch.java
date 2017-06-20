package gj.quoridor.player.silvestri.core.engine;

import java.util.List;

import gj.quoridor.player.silvestri.core.GameManager;

import java.util.ArrayList;

public class ExhaustiveResearch {
	/**
	 * Is read or blue boolean.
	 */
	private final int player;

	/**
	 * Board object.
	 */
	private final GameManager gameManager;

	/**
	 * Create a new exhaustive search object.
	 * 
	 * @param player
	 * @param board
	 */
	public ExhaustiveResearch(int player, GameManager gameManager) {
		this.player = player;
		this.gameManager = gameManager;

	}

	/**
	 * Get all possible actions.
	 * 
	 * @return List of actions
	 * @TODO: implement walls
	 */
	public List<int[]> getActions() {
		List<int[]> actions = new ArrayList<>();

		// Move actions
		ArrayList<Integer> moves = computeMoves();
		for (int move : moves) {
			actions.add(new int[] { GameManager.MOVE, move });
		}

		// Put-wall action
		ArrayList<Integer> walls = computeWalls();
		for (int wallIndex : walls) {
			actions.add(new int[] { GameManager.PUT_WALL, wallIndex });
		}
		
		return actions;
	}

	private ArrayList<Integer> computeMoves() {
		ArrayList<Integer> moves = new ArrayList<>();

		// create all 4 moves
		int testMoves[] = new int[] { GameManager.FORWARD, GameManager.BACK, GameManager.LEFT, GameManager.RIGHT };

		// check 4 moves
		for (int move : testMoves) {
			// check if coordinates of player are valid
			if (gameManager.canMove(player, move)) {
				moves.add(move);
			}
		}

		return moves;
	}

	private ArrayList<Integer> computeWalls() {
		ArrayList<Integer> walls = new ArrayList<>();

		if (!gameManager.areWallsAvailable(player)) {
			return walls;
		}

		// Iterating walls that aren't used yet
		for (Integer wallIndex : gameManager.getAvailableWalls()) {
			if (gameManager.canPutWall(player, wallIndex)) {
				walls.add(wallIndex);
			}
		}

		return walls;
	}

}
