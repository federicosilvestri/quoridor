package main;

import java.security.SecureRandom;
import java.util.Arrays;

import gj.quoridor.player.stupid.core.GameCostants;
import gj.quoridor.player.stupid.core.GameManager;
import gj.quoridor.player.stupid.core.PathSearcher;
import gj.quoridor.player.stupid.core.engine.PlayerEngine;

public class Main {
	private static SecureRandom sr;
	public static GameManager manager;

	public static void main(String args[]) {
		manager = new GameManager();
		sr = new SecureRandom();
		//putRandomWalls(19);

		manager.setPlayerCoords(GameManager.BLUE, new int[] { 12, 16 });
		System.out.println(manager.board);

		// start();

		 PlayerEngine pe = new PlayerEngine(manager, GameManager.BLUE);
		 pe.debug();
		 System.out.println(pe.getResult());
		 int action[] = pe.getBestAction();
		 System.out.println("Best action is: " + Arrays.toString(action));
	}

	private static void start() {
		sr = new SecureRandom();
		for (int i = 0; i < 100000; i++) {
			System.out.println("---- Starting simulation n° " + i + " ----");
			simulation();
			System.out.print("---- Simulation ended ----\nWaiting [");
			for (int j = 0; j < 20; j++) {
				System.out.print("|");
				try {
					Thread.sleep(50 - i < 0 ? 0 : 50 - i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("]");
		}
	}

	private static boolean simulation() {
		manager = new GameManager();

		putRandomWalls(20);
		int[] coord = getRandomCoord();
		int destination = sr.nextBoolean() ? GameCostants.BLUE_WIN_Y : GameCostants.RED_WIN_Y;
		manager.setPlayerCoords(getRandomPlayer(), coord);
		PathSearcher ps = manager.board.buildPathSearcher();
		ps.verbose = true;
		ps.setDestinationY(destination);

		System.out.println(manager.board);
		boolean ava = ps.compute(coord[0], coord[1]);
		System.out.println(ava ? "FOUND" : "NOT FOUND");
		return ava;
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
