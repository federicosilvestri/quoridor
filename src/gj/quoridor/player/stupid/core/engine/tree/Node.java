package gj.quoridor.player.stupid.core.engine.tree;

import java.util.Arrays;
import java.util.LinkedList;

public class Node {

	/**
	 * True if this node is the root of tree.
	 */
	final boolean root;

	/**
	 * Neighbors.
	 */
	public final LinkedList<Node> childs;

	/**
	 * Parents.
	 */
	public Node parent;

	/**
	 * Weight of this node.
	 */
	private float weight;

	/**
	 * Move that brings here.
	 */
	private final int[] action;

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
	public Node(int action[]) {
		this(action, false);
	}

	Node(int[] action, boolean root) {
		this.childs = new LinkedList<>();
		this.parent = null;
		this.action = action;
		this.root = root;
	}

	/**
	 * Get weight of this node.
	 * 
	 * @return integer number of weight
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * Set parent node.
	 * 
	 * @param parent
	 *            parent node.
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * Set weight of node.
	 * 
	 * @param weigth
	 *            integer value of weight
	 */
	public void setWeigth(float weigth) {
		this.weight = weigth;
	}

	/**
	 * Get action to reach this node.
	 * 
	 * @return array of action
	 */
	public int[] getAction() {
		return action;
	}

	@Override
	public String toString() {
		String s = "";

		if (root) {
			s += "Root Node";
		} else {
			s += "Action: " + Arrays.toString(action) + ", w=" + weight;
		}

		return s;
	}
}
