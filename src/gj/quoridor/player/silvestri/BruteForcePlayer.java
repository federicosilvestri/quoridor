package gj.quoridor.player.silvestri;


import gj.quoridor.player.Player;
import gj.quoridor.player.silvestri.core.GameManager;
import gj.quoridor.player.silvestri.core.engine.PlayerEngine;

public class BruteForcePlayer implements Player {
	private int player;
	private int opponent;
	private GameManager gameManager;

	@Override
	public int[] move() {
		PlayerEngine pe = new PlayerEngine(gameManager, player);
		pe.startComputation();
//		pe.debug();
		int[] action = pe.getBestAction();
		gameManager.play(player, action[0], action[1]);
		return action;
	}

	@Override
	public void start(boolean isRed) {
		player = isRed ? GameManager.RED : GameManager.BLUE;
		opponent = isRed ? GameManager.BLUE : GameManager.RED;
		gameManager = new GameManager();
	}

	@Override
	public void tellMove(int[] action) {
		gameManager.play(opponent, action[0], action[1]);
	}

}
