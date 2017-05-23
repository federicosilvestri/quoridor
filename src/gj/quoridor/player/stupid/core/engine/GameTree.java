package gj.quoridor.player.stupid.core.engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

import gj.quoridor.player.stupid.core.GameManager;

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
	 * @param gm
	 *            Game Manager object
	 * @return Added node
	 */
	public Node addChild(Node parent, GameManager gm) {
		Node node = gm.createNode();
		node.parent = parent;
		parent.childs.add(node);

		return node;
	}
	
	public Iterator<Node> getToRootIterator(Node n) {
		return new BProagationIterator(n);
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
}
