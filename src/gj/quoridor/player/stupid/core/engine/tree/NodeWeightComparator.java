package gj.quoridor.player.stupid.core.engine.tree;

import java.util.Comparator;

public class NodeWeightComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		float difference = o1.getWeight() - o2.getWeight();
		int diff = Math.round(difference);
		
		return diff;
	}

	

}
