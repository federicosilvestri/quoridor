package main;

import gj.quoridor.player.stupid.core.GameManager;

public class Main {

	public static void main(String args[]) {
		GameManager manager = new GameManager();

		
		for (int i = 0; i < 100; i++) {
			manager.play(GameManager.RED, GameManager.MOVE, GameManager.FORWARD);

			System.out.println(manager.board);
			System.out.println("---- END MATRIX -----");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		manager.play(GameManager.RED, GameManager.PUT_WALL, 17);
//		manager.play(GameManager.RED, GameManager.PUT_WALL, 10);
//		manager.play(GameManager.RED, GameManager.PUT_WALL, 12);
//		manager.play(GameManager.RED, GameManager.PUT_WALL, 21);
		//manager.play(GameManager.RED, GameManager.PUT_WALL, 30);
		//manager.play(GameManager.RED, GameManager.PUT_WALL, 15);

		
	}
}
