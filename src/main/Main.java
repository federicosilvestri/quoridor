package main;

import gj.quoridor.player.stupid.core.GameManager;

public class Main {

	public static void main(String args[]) {
		GameManager board = new GameManager();
		
		board.play(GameManager.RED, GameManager.PUT_WALL, 10);
	}
}
