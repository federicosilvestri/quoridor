package gj.quoridor.player.stupid.core.engine;

import java.util.Iterator;
import java.util.List;

import gj.quoridor.player.stupid.core.GameManager;
import gj.quoridor.player.stupid.core.engine.tree.Node;

class PlayerWorker implements Runnable {

	/**
	 * Depth to reach before recursion.
	 */
	public final static int THREAD_FORK_DEPTH = 1;

	/**
	 * Depth of current thread
	 */
	private int depth;

	/**
	 * Start node.
	 */
	private final Node start;
	
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
		this.start = start;
		this.startGameManager = startGM;
		this.stop = false;
		this.finished = false;
		this.depth = depth;
		this.engine = pe;

	}

	private boolean checkStop(Node node, GameManager manager, int depth) {
		if (depth > engine.maxDepth || stop) {
			/*
			 * Here we need to setup a back propagation weight but weight must
			 * be calculated in base of distance to victory backPropagate(node,
			 * -2);
			 */
			backPropagate(node, 0.1f);
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
			
			// Check with exhaustive research
			// ExhaustiveResearch er = new ExhaustiveResearch(engine.player, manager);
			//check moves
		}

		return false;
	}

	private void computeActions(Node node, GameManager manager, int depth) {
		if (checkStop(node, manager, depth)) {
			return;
		}

		// Search all possible moves
		ExhaustiveResearch er = new ExhaustiveResearch(engine.player, manager);

		if (depth < THREAD_FORK_DEPTH) {
			// scale
			fork(node, manager, er, depth);
		} else {
			// Here we are adding all sons of first child
			for (int[] action : er.getActions()) {
				// copy Game Manager
				GameManager gm = manager.getSimulation();
				// play this move
				gm.play(engine.player, action[0], action[1]);
				// Add to tree
				Node added = engine.gameTree.addChild(node, gm, action);
				// Inspect
				computeActions(added, gm, depth + 1);
			}
		}
	}

	private void fork(Node node, GameManager manager, ExhaustiveResearch er, int depth) {
		List<int[]> actions = er.getActions();

		for (int[] action : actions) {
			GameManager gm = manager.getSimulation();
			gm.play(engine.player, action[0], action[1]);
			Node added = engine.gameTree.addChild(node, gm, action);
			PlayerWorker playerWorker = new PlayerWorker(gm, engine, added, depth + 1);
			engine.scale(playerWorker);
		}
	}

	private void backPropagate(Node node, float weight) {
		node.setWeigth(weight);
		Iterator<Node> bpi = engine.gameTree.getToRootIterator(node);

		float accumulateWeight = weight;
		
		while (bpi.hasNext()) {
			Node n = bpi.next();
			
			accumulateWeight += n.getWeight();
			
			n.setWeigth(accumulateWeight);
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
		computeActions(start, startGameManager, depth);
		
		finished = true;
		// Start notifier thread
		workerNotifier.start();
	}
}
