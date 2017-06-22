package gj.quoridor.player.silvestri.core.engine;

import java.security.SecureRandom;
import java.util.Collections;
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
	 * Related gameManager
	 */
	private final GameManager manager;

	public AssembledEngine(GameManager manager, int player) {
		super();
		this.player = player;
		this.manager = manager;
	}

	public int[] computeAction() {
		int action[] = null;

		// Instance exhaustive research
		ExhaustiveResearch er = new ExhaustiveResearch(player, manager);

		// check if walls are available
		if (manager.areWallsAvailable(player)) {
			// Play random walls
			List<Integer> walls = er.getWalls();
			int r = SECURE_RANDOM.nextInt(walls.size());
			int index = walls.get(r);

			action = new int[] { GameManager.PUT_WALL, index };
		} else {
			// Dijkstra
			int winningY = GameCostants.RED_WIN_Y;
			if (player == GameManager.BLUE) {
				winningY = GameCostants.BLUE_WIN_Y;
			}
			
			PathSearcher ps = new PathSearcher(winningY, manager.board);
			int currentCoords[] = manager.getPlayerCoords(player);
			List<int[]> coords = ps.shortestPath(currentCoords[0], currentCoords[1]);
			// get first best move, 0 is current position
			int[] nextCoords = coords.get(1);
			CoordinatesConverter cc = new CoordinatesConverter(player == GameManager.RED);
			action = new int[]{GameManager.MOVE, cc.convert(currentCoords, nextCoords)};
		}

		return action;
	}

}
