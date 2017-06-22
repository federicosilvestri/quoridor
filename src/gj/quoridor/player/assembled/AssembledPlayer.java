package gj.quoridor.player.assembled;

import gj.quoridor.player.Player;
import gj.quoridor.player.silvestri.core.GameManager;
import gj.quoridor.player.silvestri.core.engine.AssembledEngine;

public class AssembledPlayer implements Player {
	private AssembledEngine ae;
	private int player;
	private int opponent;
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
