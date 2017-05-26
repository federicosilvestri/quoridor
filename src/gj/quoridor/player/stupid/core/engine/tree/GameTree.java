package gj.quoridor.player.stupid.core.engine.tree;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

public class GameTree implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Root of tree
	 */
	private Node root;

	public static GameTree unserialize(String filepath) throws IOException {
		FileInputStream fis = new FileInputStream(filepath);
		ObjectInputStream ois = new ObjectInputStream(fis);

		Object obj = null;
		try {
			obj = ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ois.close();
		fis.close();

		GameTree gt = (GameTree) obj;
		return gt;
	}

	/**
	 * Create a new GameTree.
	 */
	public GameTree() {
		root = Node.DEFAULT_ROOT;
	}

	/**
	 * Add child to selected parent.
	 * 
	 * @param parent
	 *            parent
	 * @return Added node
	 */
	public synchronized Node addChild(Node parent, int[] action) {
		if (parent.root) 
		System.out.println("Request to add child: Parent is:" + parent + "\n\taction is= " + Arrays.toString(action));

		// create new node with current action
		Node node = new Node(action);
		node.parent = parent;
		parent.childs.add(node);

		return node;
	}

	public Iterator<Node> getToRootIterator(Node n) {
		return new BProagationIterator(n);
	}

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

	public void serialize(String filepath) throws IOException {
		FileOutputStream fos = new FileOutputStream(filepath);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(this);
		oos.close();
		fos.close();
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
