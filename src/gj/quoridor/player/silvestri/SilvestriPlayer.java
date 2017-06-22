package gj.quoridor.player.silvestri;

import gj.quoridor.player.Player;
import gj.quoridor.player.silvestri.core.GameManager;
import gj.quoridor.player.silvestri.core.engine.AssembledEngine;

/**
 * Silvestri's Player.
 * 
 * @author federicosilvestri
 *
 */
public class SilvestriPlayer implements Player {

	/**
	 * Assembled engine.
	 */
	private AssembledEngine ae;

	/**
	 * Chosen player.
	 */
	private int player;

	/**
	 * Chosen opponent.
	 */
	private int opponent;

	/**
	 * Game Manager object.
	 */
	private GameManager gameManager;

	@Override
	public int[] move() {
		int action[] = ae.computeAction();
		gameManager.play(player, action[0], action[1]);
		return action;
	}

	@Override
	public void start(boolean isRed) {
		player = isRed ? GameManager.RED : GameManager.BLUE;
		opponent = isRed ? GameManager.BLUE : GameManager.RED;
		gameManager = new GameManager();
		ae = new AssembledEngine(gameManager, player);
	}

	@Override
	public void tellMove(int[] action) {
		gameManager.play(opponent, action[0], action[1]);
	}

}
