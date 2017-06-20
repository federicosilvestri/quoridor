package gj.quoridor.player.silvestri.core.engine.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

class GameTreeIterator implements Iterator<Node> {

	private LinkedList<Node> stack;
	
	GameTreeIterator(Node start) {
		stack = new LinkedList<>();
		stack.push(start);
	}
	
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public Node next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		
		Node n = stack.pop();
		
		// Add children to queue
		for (Node child : n.childs) {
			stack.push(child);
		}
		
		return n;
	}

}
