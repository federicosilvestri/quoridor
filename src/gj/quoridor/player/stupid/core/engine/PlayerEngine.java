package gj.quoridor.player.stupid.core.engine;

import java.io.IOException;
import java.util.Iterator;

import gj.quoridor.player.stupid.core.GameManager;

public class PlayerEngine implements Runnable {

	/**
	 * Game Manager
	 */
	private final GameManager manager;

	/**
	 * Maximum depth to reach during drilling.
	 */
	private int maxDepth;

	/**
	 * If process is finished or not.
	 */
	private boolean finished;

	private GameTree gameTree;

	/**
	 * Chosen player
	 */
	public final int player;

	public PlayerEngine(GameManager manager, int player) {
		this.manager = manager;
		this.player = player;
		maxDepth = 1;
		gameTree = new GameTree();
	}

	public int getDepth() {
		return maxDepth;
	}

	public void setDepth(int depth) {
		this.maxDepth = depth;
	}

	public GameTree getTree() {
		return gameTree;
	}

	public void start() {
		System.out.println("Starting Player engine... max depth=" + maxDepth);
		long startTime = System.nanoTime();

		Thread t = new Thread(this);
		t.start();

		while (!finished) {
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				System.exit(-1);
			}

			long elapsed = System.nanoTime() - startTime;
			System.out.println("Research in progress. Elapsed time:" + (elapsed / Math.pow(10, 9)) + "s");
			System.out.print("Saving GameTree...");

			try {
				gameTree.serialize("/Users/federicosilvestri/Desktop/gameTree.bin");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("done!");
		}

		System.out.println("Research ended");
		System.out.println(getResult());

	}

	private void backPropagate(Node node, int weight) {
		Iterator<Node> bpi = gameTree.getToRootIterator(node);

		while (bpi.hasNext()) {
			Node n = bpi.next();
			n.setWeigth(weight);
		}
	}

	private void computeActions(Node node, GameManager manager, int depth) {
		// check depth
		if (depth > maxDepth) {
			// here we need to setup a back propagation weight
			// but weight must be calculated in base of distance
			// to victory
			return;
		}

		// Checking winning, for back-propagation
		int winner = manager.getWinner();
		if (winner == player) {
			// Winning
			backPropagate(node, 2);
			return;
		} else if (winner != -1) {
			// Enemy player wins
			backPropagate(node, 0);
			return;
		}

		// for each move, calculate moves
		ExhaustiveResearch er = new ExhaustiveResearch(player, manager);

		// Here we are adding all sons of first child
		for (int[] action : er.getActions()) {
			// copy Game Manager
			GameManager gm = manager.getSimulation();
			// play this move
			gm.play(player, action[0], action[1]);
			// Add to tree
			Node added = gameTree.addChild(node, gm);
			// recursion
			computeActions(added, gm, depth + 1);
		}
	}

	@Override
	public void run() {
		finished = false;
		computeActions(gameTree.getRoot(), manager, 0);
		finished = true;
	}
	
	public String getResult() {
		String s = "==== Montecarlo Search Tree Algorithm ====\n";

		int i = 0;
		for (Node node : gameTree.getRoot().childs) {
			if (node.getWeigth() > 0) {
				s += "\n" + i + ")weigth=" + node.getWeigth();
				i++;
			} else {
				s += ".";
			}
		}

		return s;
	}

}
