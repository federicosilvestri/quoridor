package gj.quoridor.player.silvestri.core.engine.tree;

import java.util.Comparator;

public class NodeWeightComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		float difference = o2.getWeight() - o1.getWeight();
		int diff = Math.round(difference);
		
		return diff;
	}
}
