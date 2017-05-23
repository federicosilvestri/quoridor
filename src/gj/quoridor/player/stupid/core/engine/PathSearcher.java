package gj.quoridor.player.stupid.core.engine;

import java.util.Iterator;
import java.util.LinkedList;

import gj.quoridor.player.stupid.core.Board;
import gj.quoridor.player.stupid.core.GameCostants;

/**
 * This object is an implementation of researched algorithm to find if path is
 * available or not
 * 
 * @author federicosilvestri
 * @version 1.7
 *
 */
public class PathSearcher {

	/**
	 * Board object
	 */
	private final Board board;

	/**
	 * Destination to reach.
	 */
	private int destinationY;

	/**
	 * verbose
	 */
	public boolean verbose;

	/**
	 * Create a new Path Searcher object.
	 * 
	 * @param simulatedMatrix
	 *            matrix created by Board Object
	 */
	public PathSearcher(int destinationY, Board board) {
		this.destinationY = destinationY;
		this.board = board;
		verbose = false;
	}

	private void log(String msg, boolean toNewLine) {
		if (verbose) {
			if (toNewLine) {
				System.out.print(msg + "\nPS:");
			} else {
				System.out.print(msg);
			}
		}
	}

	public void setDestinationY(int destinationY) {
		this.destinationY = destinationY;
	}

	private LinkedList<Integer>[] generateAdjList() {
		LinkedList<Integer>[] adj = new LinkedList[GameCostants.CELL_NUMBER];
		
		for (int i = 0; i < adj.length; i++) {
			LinkedList<Integer> a = new LinkedList<>();
			adj[i] = a;
		}

		for (int i = 0; i < GameCostants.CELL_NUMBER; i++) {
			for (int j = 0; j < GameCostants.CELL_NUMBER; j++) {
				// don't check equals
				if (i == j) {
					continue;
				}

				int a[] = board.getCellCoord(i);
				int b[] = board.getCellCoord(j);
				int distance = Board.getMoveDistance(a[0], a[1], b[0], b[1]);

				if (distance < 3 && board.checkBrokenWalls(a[0], a[1], b[0], b[1])) {
					adj[i].add(j);
				}
			}
		}

		return adj;
	}

	/**
	 * Compute destination.
	 * 
	 * @param startX
	 *            start x
	 * @param startY
	 *            start y
	 * @return true if destination exists, false otherwise
	 */
	public boolean compute(int startX, int startY) {
		int startIndex = board.getCellIndex(startX, startY);
		int endIndex = board.getCellIndex(8, destinationY);

		log("Generating adjacency matrix...", false);
		LinkedList<Integer>[] adj = generateAdjList();
		log("done", true);
		log("Checking path from [" + startX + "," + startY + "] to [X," + destinationY + "] ... ", false);
		boolean available = isReacheable(startIndex, endIndex, adj);
		log("done", true);

		return available;
	}

	private boolean isDestination(int index) {
		int coord[] = board.getCellCoord(index);

		return (destinationY == coord[1]);
	}

	private boolean isReacheable(int start, int dest, LinkedList<Integer>[] adj) {
		boolean visited[] = new boolean[GameCostants.CELL_NUMBER];
		LinkedList<Integer> queue = new LinkedList<Integer>();

		visited[start] = true;
		queue.add(start);

		Iterator<Integer> nbIterator;
		while (!queue.isEmpty()) {
			start = queue.poll();
			int n;
			nbIterator = adj[start].listIterator();

			while (nbIterator.hasNext()) {
				n = nbIterator.next();

				if (isDestination(n)) {
					return true;
				}

				if (!visited[n]) {
					visited[n] = true;
					queue.add(n);
				}
			}
		}

		return false;
	}

}
