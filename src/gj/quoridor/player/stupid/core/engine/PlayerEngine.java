package gj.quoridor.player.stupid.core.engine;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import gj.quoridor.player.stupid.core.GameManager;
import gj.quoridor.player.stupid.core.engine.tree.GameTree;
import gj.quoridor.player.stupid.core.engine.tree.Node;
import gj.quoridor.player.stupid.core.engine.tree.viewer.TreeViewer;

public class PlayerEngine {

	/**
	 * Game Manager
	 */
	private final GameManager manager;

	/**
	 * Maximum depth to reach during drilling.
	 */
	public final int maxDepth;

	/**
	 * 
	 */
	public final ExecutorService service;

	/**
	 * Game Tree
	 */
	final GameTree gameTree;

	/**
	 * Chosen player
	 */
	public final int player;

	public PlayerEngine(GameManager manager, int player) {
		this.manager = manager;
		this.player = player;
		this.maxDepth = 2;
		this.gameTree = new GameTree();
		this.service = Executors.newFixedThreadPool(20);
	}

	public void debug() {
		System.out.println("Instancing player worker...");
		PlayerWorker pw = new PlayerWorker(manager, this, gameTree.getRoot(), 0);
		
		// First start
		pw.run();
		
		System.out.println("Instanced..");		
		try {
			service.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		service.shutdown();
		
		System.out.println("Finished!");
		new TreeViewer(gameTree);
	}

	public int[] getBestAction() {
		List<Node> nodes = gameTree.getRoot().childs;

		boolean start = true;
		Node best = null;

		for (Node node : nodes) {
			if (start) {
				start = false;
				best = node;
			} else {
				if (node.getWeight() > best.getWeight()) {
					best = node;
				}
			}
		}

		return best.getAction();
	}

	public String getResult() {
		String s = "==== Montecarlo Search Tree Algorithm ====\n";

		int i = 0;
		for (Node node : gameTree.getRoot().childs) {
			if (node.getWeight() > 0) {
				s += "\n" + i + ")weigth=" + node.getWeight();
				i++;
			} else {
				s += ".";
			}
		}

		return s;
	}

}
