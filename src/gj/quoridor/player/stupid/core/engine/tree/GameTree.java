package gj.quoridor.player.stupid.core.engine.tree;

import java.util.Iterator;

public class GameTree {

	/**
	 * ROOT Node instance.
	 */
	public static final Node DEFAULT_ROOT = new Node(null, true);
	
	/**
	 * Root of tree
	 */
	private Node root;

	/**
	 * Create a new GameTree.
	 */
	public GameTree() {
		root = DEFAULT_ROOT;
	}

	/**
	 * Add child to selected parent.
	 * 
	 * @param parent
	 *            parent
	 * @return Added node
	 */
	public synchronized Node addChild(Node parent, int[] action) {
		// create new node with current action
		Node node = new Node(action);
		node.parent = parent;
		parent.childs.add(node);

		return node;
	}

	/**
	 * Get iterator that iterates until it reaches root of tree. 
	 * @param n start note
	 * @return Iterator
	 */
	public Iterator<Node> getToRootIterator(Node n) {
		return new BProagationIterator(n);
	}

	/**
	 * Get standard BFS iterator.
	 * @return Iterator
	 */
	public Iterator<Node> getIterator() {
		return new GameTreeIterator(root);
	}

	/**
	 * Get Tree root.
	 * 
	 * @return root of tree
	 */
	public Node getRoot() {
		return root;
	}

	@Override
	public String toString() {
		String s = "";

		Iterator<Node> it = getIterator();

		while (it.hasNext()) {
			Node n = it.next();
			System.out.println(n);
		}

		return s;
	}
}
