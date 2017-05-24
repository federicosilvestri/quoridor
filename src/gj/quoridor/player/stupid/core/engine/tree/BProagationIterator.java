package gj.quoridor.player.stupid.core.engine.tree;

import java.util.Iterator;

class BProagationIterator implements Iterator<Node> {

	/**
	 * Cursor node
	 */
	private final Node cursor;

	BProagationIterator(Node start) {
		this.cursor = start;
	}

	@Override
	public boolean hasNext() {
		return cursor.root;
	}

	@Override
	public Node next() {
		return cursor.parent;
	}

}
