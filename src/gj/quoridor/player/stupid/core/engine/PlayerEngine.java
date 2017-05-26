package gj.quoridor.player.stupid.core.engine;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import gj.quoridor.player.stupid.core.GameManager;
import gj.quoridor.player.stupid.core.engine.tree.GameTree;
import gj.quoridor.player.stupid.core.engine.tree.Node;
import gj.quoridor.player.stupid.core.engine.tree.viewer.TreeViewer;

public class PlayerEngine extends Thread {

	/**
	 * Default Max computation time expressed in seconds.
	 */
	private final static int DEFAULT_MAX_COMPUTATION_TIME = 30;

	/**
	 * Game Manager
	 */
	private final GameManager manager;

	/**
	 * Maximum depth to reach during drilling.
	 */
	public final int maxDepth;

	/**
	 * Max computation time.
	 */
	private int maxComputationTime;

	/**
	 * Executor service for Thread Pool.
	 */
	private ThreadPoolExecutor service;

	/**
	 * Is finished or not variable.
	 */
	private boolean finished;

	/**
	 * Game Tree
	 */
	final GameTree gameTree;

	/**
	 * Chosen player
	 */
	public final int player;

	/**
	 * Create a new Player Engine Object.
	 * 
	 * @param manager
	 *            Game Manager
	 * @param player
	 *            Player you want to impersonate
	 */
	public PlayerEngine(GameManager manager, int player) {
		this.manager = manager;
		this.player = player;
		this.maxDepth = 2;
		this.gameTree = new GameTree();
		this.maxComputationTime = DEFAULT_MAX_COMPUTATION_TIME;
	}

	/**
	 * Set max computation time expressed in seconds.
	 * 
	 * @param maxCT
	 *            max computation time in seconds
	 */
	public void setMaxComputationTime(int maxCT) {
		this.maxComputationTime = maxCT;
	}

	/**
	 * Get max computation time.
	 * 
	 * @return max number of seconds to compute
	 */
	public int getMaxComputationTime() {
		return maxComputationTime;
	}

	public void debug() {
		setUpService();
		System.out.println("Instanced first player worker...");
		// First instance of player Worker
		PlayerWorker pw = new PlayerWorker(manager, this, gameTree.getRoot(), 0);
		// Controller
		System.out.print("Starting Computation Controller...");
		this.start();
		System.out.println("done!");
		// First start
		System.out.print("Starting workers factory...");
		new Thread(pw).start();
		System.out.println("done!");

		// Create new Timer
		System.out.println("Do you want to view work?... Instancing viewer");
		// Thread visualization
		TreeViewer viewer = new TreeViewer(gameTree);

		while (!finished) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}

			System.out.println(service.getActiveCount());

			viewer.repaint();
		}
		System.out.println("Computantion ended");
	}

	/**
	 * Execute computation.
	 */
	public void startComputation() {
		// set up services
		setUpService();

		PlayerWorker pw = new PlayerWorker(manager, this, gameTree.getRoot(), 0);
		Thread playerWorkerStart = new Thread(pw);
		playerWorkerStart.start();

		// Start computation controller
		this.start();

		// Wait until thread counter is not finished or is notified by speed-up
		// termination
		try {
			this.join();
		} catch (InterruptedException e) {
		}
	}

	private void setUpService() {
		if (service == null || service.isTerminated()) {
			this.service = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
		} else {
			throw new RuntimeException("Cannot set-up service! Workers are still running!");
		}
	}

	void scale(PlayerWorker playerWorker) {
		if (service == null) {
			throw new RuntimeException("Executor Service is not set up!");
		}
		service.submit(playerWorker);
	}

	synchronized void fireFinished(PlayerWorker playerWorker) {
		// check all futures if they're finished
		int active = service.getActiveCount();
		if (active > 0) {
			finished = false;
		} else {
			// no futures are working, interrupt time controller!
			this.finished = true;
			this.interrupt();
		}
	}

	/**
	 * Get best action to do in currently game status.
	 * 
	 * @return action array
	 */
	public int[] getBestAction() {
		if (!finished) {
			throw new RuntimeException("Result is not available yet!");
		}

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

	@Override
	public void run() {
		/**
		 * This method should sleep until time is not ended.
		 */
		// Wait max computation time
		System.out.println("TC: Time controller is in action...");
		boolean timeout = true;
		try {
			service.awaitTermination(maxComputationTime, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			if (!finished) {
				System.err.println(
						"TC: Received Interruption during Max Computation time counting... but futures are not finished!");
				return;
			} else {
				timeout = false;
			}
		}
		if (timeout) {
			System.out.println("TC: Time is out!");
		} else {
			System.out.println("Computation speed up!");
		}

		// Close service
		List<Runnable> futures = service.shutdownNow();

		for (Runnable r : futures) {
			// Check if this runnable is instance of worker
			if (r instanceof PlayerWorker) {
				PlayerWorker pw = (PlayerWorker) r;
				pw.setStop();
			}
		}

		System.out.println("TC: System is now STOPPED!");
		finished = true;
	}

}
