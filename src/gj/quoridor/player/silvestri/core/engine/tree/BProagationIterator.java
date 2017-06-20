package gj.quoridor.player.silvestri.core.engine.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is an iterator that iterates nodes until it reaches root-1 node. It
 * allows system to visit all nodes of path for back-propagation purposes.
 * 
 * @author federicosilvestri
 * @version 2.0
 *
 */
class BProagationIterator implements Iterator<Node> {

	/**
	 * Cursor node
	 */
	private Node cursor;

	BProagationIterator(Node start) {
		this.cursor = start;
	}

	@Override
	public boolean hasNext() {
		return (!cursor.root);
	}

	@Override
	public Node next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		
		// save current
		Node current = cursor;
		
		// Go back with cursor
		cursor = cursor.parent;
		
		return current;
		
	}

}
