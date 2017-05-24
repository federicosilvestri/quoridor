package gj.quoridor.player.stupid.core.engine.tree.viewer;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTree;

import gj.quoridor.player.stupid.core.engine.tree.GameTree;

public class TreeViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameTree gt;
	
	public static void main(String args[]) {
		new TreeViewer(null);
	}
	
	public TreeViewer(GameTree gt) {
		super();
		this.gt = gt;
		
		setLayout(new BorderLayout());
		setSize(1000, 1000);
		setTitle("Tree Viewer");
		setResizable(true);
		JLabel j = new JLabel("JTree");
		add(j, BorderLayout.NORTH);
		addTree();
		setVisible(true);
	}
	
	public void addTree() {
		JTree jt = new JTree();
		jt.setModel(new GameTreeModel(gt));
		add(jt, BorderLayout.CENTER);
	}
}
