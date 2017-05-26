package main;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;

import gj.quoridor.player.stupid.core.GameManager;
import gj.quoridor.player.stupid.core.engine.PlayerEngine;

public class Main {
	private static SecureRandom sr;
	public static GameManager manager;

	public static void main(String args[]) {
		start();
	}

	private static void start() {
		sr = new SecureRandom();
		for (int i = 0; i < 100000; i++) {
			System.out.println("---- Starting simulation n° " + i + " ----");
			simulation();
			System.out.print("---- Simulation ended ----\nWaiting generation [");
			for (int j = 0; j < 20; j++) {
				System.out.print("|");
				
				try {
					Thread.sleep(50 - i < 0 ? 0 : 50 - i);
				} catch (InterruptedException e) {
					System.exit(-1);
				}
			}
			
			System.out.println("]\nPress a key to continue");
			
			try {
				System.in.read();
			} catch (IOException e) {}
		}
	}

	private static void simulation() {
		manager = new GameManager();

		putRandomWalls(20);
		int[] coord = getRandomCoord();
		manager.setPlayerCoords(getRandomPlayer(), coord);
		System.out.println(manager.board);
		PlayerEngine pe = new PlayerEngine(manager, getRandomPlayer());
		pe.debug();
		int action[] = pe.getBestAction();
		
		System.out.println("Best action is: " + Arrays.toString(action));
	}

	private static int[] getRandomCoord() {
		int coord[] = new int[2];

		coord[0] = Math.abs((sr.nextInt() % 5) * 2);
		coord[1] = Math.abs((sr.nextInt() % 5) * 2);

		return coord;
	}

	private static int getRandomPlayer() {
		int player;
		boolean blueAva = manager.areWallsAvailable(GameManager.BLUE);
		boolean redAva = manager.areWallsAvailable(GameManager.RED);

		if (blueAva && redAva) {
			if (sr.nextBoolean()) {
				player = GameManager.BLUE;
			} else {
				player = GameManager.RED;
			}
		} else if (blueAva) {
			player = GameManager.BLUE;
		} else {
			player = GameManager.RED;
		}

		return player;
	}

	private static void putRandomWalls(int n) {
		for (int i = 0; i < n; i++) {
			boolean ok = false;
			do {
				int player = getRandomPlayer();
				int wallIndex = Math.abs(sr.nextInt(128));
				int[][] wallCoords = manager.board.getWallCoords(wallIndex);

				if (!manager.board.isWallOccupied(wallCoords)) {
					manager.play(player, GameManager.PUT_WALL, wallIndex);
					ok = true;
				}

			} while (!ok);
		}

	}

}
