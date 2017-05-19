package main;

import gj.quoridor.player.stupid.core.GameManager;

public class Main {

	public static void main(String args[]) {
		GameManager manager = new GameManager();

		manager.play(GameManager.RED, GameManager.PUT_WALL, 40);
		manager.play(GameManager.RED, GameManager.PUT_WALL, 17);
		manager.play(GameManager.RED, GameManager.PUT_WALL, 10);
		manager.play(GameManager.RED, GameManager.PUT_WALL, 12);
		manager.play(GameManager.RED, GameManager.PUT_WALL, 21);
		//manager.play(GameManager.RED, GameManager.PUT_WALL, 30);
		//manager.play(GameManager.RED, GameManager.PUT_WALL, 15);

		System.out.println(manager.board);
		manager.board.checkReachability(14, new int[] {2,4}, new int[] {2, 4} );
	}
}
