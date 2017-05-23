package gj.quoridor.player.stupid.core.engine;

import gj.quoridor.player.stupid.core.GameManager;

public class PlayerEngine implements Runnable {

	/**
	 * Game Manager
	 */
	private final GameManager manager;

	private int maxDepth;

	private boolean finished;
	
	/**
	 * Chosen player
	 */
	public final int player;

	public PlayerEngine(GameManager manager, int player) {
		this.manager = manager;
		this.player = player;
		maxDepth = 2;
	}

	public int getDepth() {
		return maxDepth;
	}

	public void setDepth(int depth) {
		this.maxDepth = depth;
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
		}
		
		System.out.println("Research ended");
		
	}

	private void computeActions(GameManager manager, int depth) {
		// check depth
		if (depth > maxDepth) {
			return;
		}

		// Base case
		if (manager.isFinished()) {
			return;
		}

		// for each move, calculate moves
		ExhaustiveResearch er = new ExhaustiveResearch(player, manager);

		for (int[] action : er.getActions()) {
			// copy Game Manager
			GameManager gm = manager.getSimulation();
			System.out.println(gm.board);
			// play this move
			gm.play(player, action[0], action[1]);
			// recursion
			computeActions(gm, depth + 1);
		}
	}

	@Override
	public void run() {
		finished = false;
		computeActions(manager, 0);
		finished = true;
	}

}
