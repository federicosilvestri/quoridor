package gj.quoridor.player.silvestri.core.engine.tree;

import java.util.LinkedList;

import gj.quoridor.player.silvestri.core.GameManager;

public class Node {

	/**
	 * True if this node is the root of tree.
	 */
	public final boolean root;

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
	 * Add weight of node.
	 * 
	 * @param weigth
	 *            integer value of weight
	 */
	public void addWeigth(float incr) {
		this.weight += incr;
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
			if (action[0] == GameManager.PUT_WALL) {
				s += "Action: [PUT_WALL," + action[1] + "], w=" + weight;
			} else {
				String a = "";
				switch (action[1]) {
				case GameManager.LEFT:
					a = "LEFT";
					break;
				case GameManager.RIGHT:
					a = "RIGHT";
					break;
				case GameManager.FORWARD:
					a = "FORWARD";
					break;
				case GameManager.BACK:
					a = "BACK";
				}
				
				s += "Action: [MOVE," + a + "], w=" + weight;
			}
		}

		return s;
	}
}
