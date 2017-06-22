package gj.quoridor.player.silvestri.core.engine;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

import gj.quoridor.player.silvestri.core.CoordinatesConverter;
import gj.quoridor.player.silvestri.core.GameCostants;
import gj.quoridor.player.silvestri.core.GameManager;
import gj.quoridor.player.silvestri.core.PathSearcher;

public class AssembledEngine {

	/**
	 * Secure Random for random actions.
	 */
	private final static SecureRandom SECURE_RANDOM = new SecureRandom();

	/**
	 * Chosen player
	 */
	private final int player;

	/**
	 * Adversary player
	 */
	private final int adversary;

	/**
	 * Related gameManager
	 */
	private final GameManager manager;

	/**
	 * Winning Y to reach.
	 */
	private final int winningY;

	/**
	 * Adversary winning Y.
	 */
	private final int adversaryWinningY;

	/**
	 * Saved shortest path.
	 */
	private LinkedList<int[]> savedPath;

	/**
	 * Adversary has moved.
	 */
	private boolean playerMoved;

	/**
	 * Assembled Engine
	 * 
	 * @param manager
	 *            Game Manager
	 * @param player
	 *            chosen player
	 */
	public AssembledEngine(GameManager manager, int player) {
		super();
		this.player = player;
		this.adversary = (player == GameManager.RED) ? GameManager.BLUE : GameManager.RED;
		this.manager = manager;
		winningY = (player == GameManager.RED) ? GameCostants.RED_WIN_Y : GameCostants.BLUE_WIN_Y;
		adversaryWinningY = (winningY == GameCostants.BLUE_WIN_Y) ? GameCostants.RED_WIN_Y : GameCostants.BLUE_WIN_Y;
		savedPath = null;
	}

	/**
	 * Get action to execute.
	 * 
	 * @return array that contains action
	 */
	public int[] computeAction() {
		int action[];

		// Instance exhaustive research
		ExhaustiveResearch er = new ExhaustiveResearch(player, manager);

		if (manager.areWallsAvailable(player)) {
			action = computeWall(er);
		} else {
			// execute shortest path algorithm to reach goal
			action = computeMove();
		}

		return action;
	}

	private int[] computeWall(ExhaustiveResearch er) {
		// Turn player on other side, and search ShortestPath
		PathSearcher ps = new PathSearcher(adversaryWinningY, manager.board);
		int[] adversaryCoords = manager.getPlayerCoords(adversary);
		List<int[]> adversaryPath = ps.shortestPath(adversaryCoords[0], adversaryCoords[1]);

		// If shortest path is shorter or equals then our, move
		PathSearcher ops = new PathSearcher(winningY, manager.board);
		int[] ourCoords = manager.getPlayerCoords(player);
		List<int[]> ourPath = ops.shortestPath(ourCoords[0], ourCoords[1]);

		if (ourPath.size() >= adversaryPath.size()) {
			return computeMove();
		}

		// Search legal walls available in shortest path
		List<Integer> walls = er.getWalls();
		int i = 0;
		int wallIndex = -1;
		while (i < adversaryPath.size() - 1 && wallIndex == -1) {
			int[] cellA = adversaryPath.get(i);
			int[] cellB = adversaryPath.get(i + 1);
			wallIndex = manager.board.getWallIndexByPath(cellA, cellB);
			if (!walls.contains(wallIndex)) {
				// Move is illegal, continue
				wallIndex = -1;
			}
			i += 1;
		}

		if (wallIndex == -1) {
			// No wall available in shortest path
			if (playerMoved) {
				playerMoved = false;
				// if player moves, move our player
				return computeMove();
			} else {
				// random walls
				int r = SECURE_RANDOM.nextInt(walls.size());
				wallIndex = walls.get(r);
			}
		}

		return new int[] { GameManager.PUT_WALL, wallIndex };
	}

	private int[] computeMove() {
		// Create a new path searcher
		PathSearcher ps = new PathSearcher(winningY, manager.board);
		// Get current coordinates
		int currentCoords[] = manager.getPlayerCoords(player);

		// Next coordinates
		int nextCoords[] = null;
		// check if we can save path
		if (!manager.areWallsAvailable(GameManager.BLUE) && !manager.areWallsAvailable(GameManager.RED)) {
			// is saved?
			if (savedPath == null) {
				// no, save it
				savedPath = new LinkedList<>(ps.shortestPath(currentCoords[0], currentCoords[1]));
				savedPath.removeFirst();
			}

			// retrieve it
			nextCoords = savedPath.poll();
		} else {
			List<int[]> coords = ps.shortestPath(currentCoords[0], currentCoords[1]);
			nextCoords = coords.get(1);
		}

		if (nextCoords == null) {
			throw new RuntimeException("Coordinates are null after computing. Computing Exception.");
		}

		// convert coordinates to move
		CoordinatesConverter cc = new CoordinatesConverter(player == GameManager.RED);
		int direction = cc.convert(currentCoords, nextCoords);

		return new int[] { GameManager.MOVE, direction };
	}

	/**
	 * Use this method to notify that player has moved player.
	 */
	public void notifyMove() {
		playerMoved = true;
	}
}