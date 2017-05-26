package gj.quoridor.player.silvestri;

import java.util.Arrays;

import gj.quoridor.player.Player;
import gj.quoridor.player.stupid.core.GameManager;
import gj.quoridor.player.stupid.core.engine.PlayerEngine;

public class SilvestriPlayer implements Player {
	private int player;
	private int opponent;
	private GameManager gameManager;

	@Override
	public int[] move() {
		System.out.println("Move called... Creating PlayerEngine...");
		
		PlayerEngine pe = new PlayerEngine(gameManager, player);
		System.out.println("CURRENT GAME MANAGER: \n" + gameManager.board);
		
		System.out.println("Starting computation...");
		pe.startComputation();
		int[] action = pe.getBestAction();
		System.out.println("Computation ended, best action: " + Arrays.toString(action) 
		 + "\nPlaying it...");
		gameManager.play(player, action[0], action[1]);
		
		System.out.println("AFTER ACTION configuration: \n");
		System.out.println(gameManager.board);
		
		return action;
	}

	@Override
	public void start(boolean isRed) {
		System.out.println("Game is started! is red? " + isRed);
		
		player = isRed ? GameManager.RED : GameManager.BLUE;
		opponent = isRed ? GameManager.BLUE : GameManager.RED;
		gameManager = new GameManager();
	}

	@Override
	public void tellMove(int[] action) {
		System.out.println("Telling move: " + Arrays.toString(action));
		gameManager.play(opponent, action[0], action[1]);
	}

}
