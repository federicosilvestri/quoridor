package gj.quoridor.player.stupid.core.engine;

import java.util.List;

import gj.quoridor.player.stupid.core.GameManager;

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

		// move actions
		for (int move : computeMoves()) {
			actions.add(new int[] { GameManager.MOVE, move });
		}

		// put-wall action
		for (int wallIndex : computeWalls()) {
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
		int[] redCoords = gameManager.getPlayerCoords(GameManager.RED);
		int[] blueCoords = gameManager.getPlayerCoords(GameManager.BLUE);

		if (!gameManager.areWallsAvailable(player)) {
			return walls;
		}

		// Iterating walls that aren't used yet
		for (Integer wallIndex : gameManager.getAvailableWalls()) {
			int wallCoord[][] = gameManager.board.getWallCoords(wallIndex);
			// But test if they're compatible
			if (!gameManager.board.isWallOccupied(wallCoord)) {
				// also we need to check if path after put-wall-move is
				// legal
				if (gameManager.board.checkReachability(wallIndex, blueCoords, redCoords)) {
					// simulate move and check path
					walls.add(wallIndex);
				}
			}
		}

		return walls;
	}

}
