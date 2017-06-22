package gj.quoridor.player.silvestri;

import java.security.SecureRandom;
import java.util.List;

import gj.quoridor.player.Player;
import gj.quoridor.player.silvestri.core.GameManager;
import gj.quoridor.player.silvestri.core.engine.ExhaustiveResearch;


public class StupidPlayer implements Player {
	private int player;
	private int opponent;
	private GameManager gameManager;

	@Override
	public int[] move() {
		ExhaustiveResearch er = new ExhaustiveResearch(player, gameManager);
		List<int[]> actions = er.getActions();
		SecureRandom sr = new SecureRandom();
		int[] action = actions.get(sr.nextInt(actions.size()));
		
		gameManager.play(player, action[0], action[1]);
				
		return action;
	}

	@Override
	public void start(boolean isFirst) {
		gameManager = new GameManager();
		player = isFirst ? GameManager.RED : GameManager.BLUE;
		opponent = isFirst ? GameManager.BLUE : GameManager.RED;
	}

	@Override
	public void tellMove(int[] move) {
		gameManager.play(opponent, move[0], move[1]);
	}

}
