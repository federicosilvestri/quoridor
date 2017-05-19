package main;

import gj.quoridor.player.stupid.core.GameManager;
import gj.quoridor.player.stupid.core.engine.PathSearcher;

public class Main {

	public static void main(String args[]) {
		GameManager manager = new GameManager();
		
		manager.play(GameManager.RED, GameManager.PUT_WALL, 10);
		PathSearcher ps = manager.board.buildPathSearcher();
		
		System.out.println(manager.board);
		
		boolean available = ps.pathAvailable(0, 0, 16, 0, 16);
		
		System.out.println("Path available= " + available);
		
	}
}
