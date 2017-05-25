package gj.quoridor.player.silvestri;

import gj.quoridor.player.Player;
import gj.quoridor.player.stupid.core.GameManager;
import gj.quoridor.player.stupid.core.engine.PlayerEngine;

public class SilvestriPlayer implements Player {
	private int player;
	private int opponent;
	private GameManager gameManager;
	
	@Override
	public int[] move() {
		PlayerEngine pe = new PlayerEngine(gameManager, player);
		pe.debug();
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
	public void tellMove(int[] move) {
		gameManager.play(opponent, move[0], move[1]);
	}

}
