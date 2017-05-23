package gj.quoridor.player.stupid.core.engine;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Node implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * True if this node is the root of tree.
	 */
	final boolean root;

	/**
	 * Neighbors.
	 */
	public final List<Node> childs;

	/**
	 * Parents.
	 */
	public Node parent;

	/**
	 * Weight of this node.
	 */
	private int weigth;

	/**
	 * Matrix.
	 */
	private final int[][] matrix;

	/**
	 * Player coordinates.
	 */
	private final int[][] playerCoords;

	/**
	 * Walls available.
	 */
	private final int[] walls;

	/**
	 * ROOT Node instance.
	 */
	public static final Node DEFAULT_ROOT = new Node(null, null, null, true);

	/**
	 * Create a new node.
	 * 
	 * @param matrix
	 *            matrix of board
	 * @param playerCoords
	 *            coordinates of players
	 * @param walls
	 *            walls available
	 */
	public Node(int[][] matrix, int[][] playerCoords, int[] walls) {
		this(matrix, playerCoords, walls, false);
	}

	private Node(int[][] matrix, int[][] playerCoords, int[] walls, boolean root) {
		this.matrix = matrix;
		this.playerCoords = playerCoords;
		this.walls = walls;
		this.childs = new LinkedList<>();
		this.parent = null;
		this.root = root;
	}

	/**
	 * Get weight of this node.
	 * 
	 * @return integer number of weight
	 */
	public int getWeigth() {
		return weigth;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	/**
	 * Set weight of node.
	 * 
	 * @param weigth
	 *            integer value of weight
	 */
	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}

	/**
	 * Get board matrix.
	 * 
	 * @return matrix
	 */
	public int[][] getMatrix() {
		return matrix;
	}

	/**
	 * Get player coordinates.
	 * 
	 * @return array of coordinates
	 */
	public int[][] getPlayerCoords() {
		return playerCoords;
	}

	/**
	 * Get walls available.
	 * 
	 * @return array that contains number walls available per player
	 */
	public int[] getWalls() {
		return walls;
	}

}
