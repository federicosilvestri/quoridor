package main;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import gj.quoridor.player.stupid.core.GameManager;
import gj.quoridor.player.stupid.core.engine.ExhaustiveResearch;
import gj.quoridor.player.stupid.core.engine.PlayerEngine;
import gj.quoridor.player.stupid.core.engine.tree.GameTree;
import gj.quoridor.player.stupid.core.engine.tree.Node;
import gj.quoridor.player.stupid.core.engine.tree.viewer.TreeViewer;

public class Main {
	private static SecureRandom sr;
	public static GameManager manager;

	public static void a(String args[]) {
		GameTree gt = new GameTree();

		Node a = gt.getRoot();

		gt.addChild(gt.getRoot(), new int[] { 400, 400 });

		for (int i = 0; i < 30; i++) {
			a = gt.addChild(a, new int[] { i, 0 });
		}

		Iterator<Node> it = gt.getToRootIterator(a);

		while (it.hasNext()) {
			Node b = it.next();
			System.out.println(b);
		}

		TreeViewer tv = new TreeViewer(gt);
	}

	public static void main(String args[]) {
		sr = new SecureRandom();
		manager = new GameManager();

		putRandomWalls(20);

		int player = switchPlayer(manager.RED);
		int i = 0;
		while (!manager.isFinished()) {
			System.out.println("Turn: " + i++);
			System.out.println(manager.board);

			System.out.println("Instancing PlayerEngine");
			PlayerEngine pe = new PlayerEngine(manager, player);
			System.out.println("Starting computation...");
			pe.startComputation();
			System.out.println("Computation ended!...\n-----Results-----");

			int[] action = pe.getBestAction();
			System.out.println("Player is: " + (player == GameManager.RED ? "RED" : "BLUE"));
			System.out.print("RED WALL: " + manager.getAvailableWallNumber(GameManager.RED));
			System.out.println(" BLUE WALL: " + manager.getAvailableWallNumber(GameManager.BLUE));
			System.out.println("BLUE COORD = " + Arrays.toString(manager.getPlayerCoords(GameManager.BLUE)));
			System.out.println("RED COORD = " + Arrays.toString(manager.getPlayerCoords(GameManager.RED)));

			ExhaustiveResearch er = new ExhaustiveResearch(player, manager);
			List<int[]> actions = er.getActions();
			System.out.print("Player available actions: {");
			for (int[] a : actions) {
				System.out.print(Arrays.toString(a) + ", ");
			}
			System.out.println("}");

			System.out.println("Best action is: " + Arrays.toString(action));

			System.out.print("Move correct: ");
			boolean correct;
			if (action[0] == GameManager.MOVE) {
				correct = manager.canMove(player, action[1]);
			} else {
				correct = manager.canPutWall(player, action[1]);
			}

			if (correct) {
				System.out.println("correct");
			} else {
				System.out.println("NOT CORRECT");

				System.out.println();
				System.out.println("----------------------");
				System.out.println("Recomputing move: ");
				PlayerEngine ped = new PlayerEngine(manager, player);
				ped.debug();
				
				System.out.println("Printing all root childs:");
				for (Node a : ped.gameTree.getRoot().childs) {
					System.out.println(")" + a);
				}
				
				
				System.out.println("Press a key to execute move");
				 try {
				 System.in.read();
				 } catch (IOException e1) {
				 // TODO Auto-generated catch block
				 e1.printStackTrace();
				 }
				 
				 
			}
			
			manager.play(player, action[0], action[1]);

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

			player = switchPlayer(player);
		}
	}

	private static int switchPlayer(int player) {
		if (player == manager.RED) {
			return manager.BLUE;
		} else {
			return manager.RED;
		}
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
			} catch (IOException e) {
			}
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
