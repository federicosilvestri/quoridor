package gj.quoridor.player.stupid.core.engine;

import java.util.Iterator;

class BProagationIterator implements Iterator<Node> {

	/**
	 * Start node
	 */
	private final Node start;

	/**
	 * Cursor node
	 */
	private final Node cursor;

	BProagationIterator(Node start) {
		this.start = start;
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
