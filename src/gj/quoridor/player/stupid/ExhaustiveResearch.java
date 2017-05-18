package gj.quoridor.player.stupid;

import java.util.List;
import java.util.ArrayList;


public class ExhaustiveResearch {
	/**
	 * Is read or blue boolean.
	 */
	private final int player;

	/**
	 * Board object.
	 */
	private final Board board;

	/**
	 * Create a new exhaustive search object.
	 * 
	 * @param player
	 * @param board
	 */
	public ExhaustiveResearch(int player, Board board) {
		this.player = player;
		this.board = board;

	}

	/**
	 * Get all possible actions.
	 * 
	 * @return List of actions
	 * @TODO: implement walls
	 */
	public List<int[]> getActions() {
		List<int[]> actions = new ArrayList<>();

		// move actions
		for (int move : computeMoves()) {
			actions.add(new int[] { Board.MOVE, move });
		}

		// put-wall action
		for (int wallIndex : computeWalls()) {
			actions.add(new int[] { Board.PUT_WALL, wallIndex });
		}

		return actions;
	}

	private ArrayList<Integer> computeMoves() {
		ArrayList<Integer> moves = new ArrayList<>();

		// create all 4 moves
		int testMoves[] = new int[] { Board.FORWARD, Board.BACK, Board.LEFT, Board.RIGHT };

		// check 4 moves
		for (int move : testMoves) {
			// get coordinates produced by this move
			int coords[] = board.computeMoveCoords(player, move);

			// check if coordinates of player are valid
			if (board.checkCoords(coords)) {
				moves.add(move);
			}
		}

		return moves;
	}

	private ArrayList<Integer> computeWalls() {
		ArrayList<Integer> walls = new ArrayList<>();
		
		if (board.wallAvailable(player)) {
			// in questo caso potrei fare due cose
			// o cercare tutti i muri liberi chiedendo la wall matrix al
			// BoardMatrix, oppure implementare nel wall matrix una hashmap
			// che aggiunge nel costruttore tutti i muri e poi li rimuove

			// per ora itero tutti e 128 i muri
			for (int i = 0; i < 128; i++) {
				if (board.wallAvailable(i)) {
					// also we need to check if path after put-wall-move is legal

					// simulate move and check path
					walls.add(i);
				}
			}
		}
			
		return walls;
	}

}
