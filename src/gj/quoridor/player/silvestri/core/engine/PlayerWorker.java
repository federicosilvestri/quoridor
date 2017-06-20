package gj.quoridor.player.silvestri.core.engine;

import java.util.Iterator;
import java.util.List;

import gj.quoridor.player.silvestri.core.GameManager;
import gj.quoridor.player.silvestri.core.engine.tree.Node;

public class PlayerWorker implements Runnable {

	/**
	 * Depth to reach before recursion.
	 */
	public final static int THREAD_FORK_DEPTH = 8;

	/**
	 * Depth of current thread
	 */
	private int depth;

	/**
	 * Start node.
	 */
	private final Node startNode;
	
	/**
	 * Player engine.
	 */
	private final PlayerEngine engine;

	/**
	 * Start Game Manager.
	 */
	private final GameManager startGameManager;

	/**
	 * Stopped worker or not.
	 */
	private boolean stop;

	/**
	 * Is finished or not.
	 */
	private boolean finished;

	PlayerWorker(GameManager startGM, PlayerEngine pe, Node start, int depth) {
		this.startNode = start;
		this.startGameManager = startGM;
		this.stop = false;
		this.finished = false;
		this.depth = depth;
		this.engine = pe;

	}

	private boolean checkStop(Node node, GameManager manager, List<int[]> actions, int depth) {
		if (depth > engine.maxDepth || stop) {
			/*
			 * Here we need to setup a back propagation weight but weight must
			 * be calculated in base of distance to victory backPropagate(node,
			 * -2);
			 */
			backPropagate(node, 0.0f);
			return true;
		}

		// Checking winning, for back-propagation
		int winner = manager.getWinner();
		
		if (winner == engine.player) {
			// Winning
			backPropagate(node, 1.0f);
			return true;
		} else if (winner != -1) {
			// Enemy player wins
			backPropagate(node, -0.5f);
			return true;
		} else {
			// No winning but we should check if there are cycles
			/*
			 * if nextNode is previous node, not good
			 */
			// get first action
			if (actions.isEmpty()) {
				backPropagate(node, -0.4f);
				return true;
			}
		}

		return false;
	}
	
	private void computeActions(Node node, GameManager manager, int depth) {
		// Search all possible moves
		ExhaustiveResearch er = new ExhaustiveResearch(engine.player, manager);
		List<int[]> actions = er.getActions();
		
		if (checkStop(node, manager, actions, depth)) {
			return;
		}
		
		if (depth < THREAD_FORK_DEPTH) {
			// scale
			fork(node, manager, actions, depth);
		} else {
			// Here we are adding all sons of first child
			for (int[] action : actions) {
				// copy Game Manager
				GameManager gm = manager.getSimulation();
				// play this move
				gm.play(engine.player, action[0], action[1]);
				// Add to tree
				Node child = engine.gameTree.addChild(node, action);
				// Inspect
				computeActions(child, gm, depth + 1);
			}
			
		}
	}

	private void fork(Node node, GameManager manager, List<int[]> actions, int depth) {
		// Create a worker for each sub-tree
		for (int[] action : actions) {
			GameManager gm = manager.getSimulation();
			gm.play(engine.player, action[0], action[1]);
			Node added = engine.gameTree.addChild(node, action);
			
			PlayerWorker playerWorker = new PlayerWorker(gm, engine, added, depth + 1);
			engine.distribute(playerWorker);
		}
	}

	// check if synch or not
	private void backPropagate(Node node, float weight) {
		node.setWeigth(weight);
		Iterator<Node> bpi = engine.gameTree.getToRootIterator(node);

		while (bpi.hasNext()) {
			Node n = bpi.next();
			n.setWeigth(weight);
		}
	}

	boolean isFinished() {
		return finished;
	}
	
	void setStop() {
		this.stop = true;
	}

	@Override
	public void run() {
		// Worker Notifier
		finished = false;
		WorkerNotifier workerNotifier = new WorkerNotifier(engine, this);
		
		// Execute actions
		computeActions(startNode, startGameManager, depth);
		
		finished = true;
		// Start notifier thread
		workerNotifier.start();
	}
}
