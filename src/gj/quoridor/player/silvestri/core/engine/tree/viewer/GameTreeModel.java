package gj.quoridor.player.silvestri.core.engine.tree.viewer;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import gj.quoridor.player.silvestri.core.engine.tree.GameTree;
import gj.quoridor.player.silvestri.core.engine.tree.Node;

public class GameTreeModel implements TreeModel {

	private GameTree gt;
	
	public GameTreeModel(GameTree gt) {
		this.gt = gt;
	}
	
	@Override
	public Object getRoot() {
		return gt.getRoot();
	}

	@Override
	public Object getChild(Object parent, int index) {
		Node n = (Node) parent;
		
		return n.childs.get(index);
	}

	@Override
	public int getChildCount(Object parent) {
		Node n = (Node) parent;
		return n.childs.size();
	}

	@Override
	public boolean isLeaf(Object node) {
		Node n = (Node) node;
		return (n.childs.size() == 0);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		Node p = (Node) parent;
		Node c = (Node) child;
		
		return p.childs.indexOf(c);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {}

}
