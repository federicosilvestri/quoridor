package gj.quoridor.player.stupid.core;

import java.util.LinkedList;
import java.util.List;

import gj.quoridor.player.stupid.core.engine.Node;
import gj.quoridor.player.stupid.exceptions.BadMoveException;
import gj.quoridor.player.stupid.exceptions.InvalidDirection;
import gj.quoridor.player.stupid.exceptions.OutOfStockWallException;
import gj.quoridor.player.stupid.exceptions.PassedWallException;
import gj.quoridor.player.stupid.exceptions.WallUnavailableException;

/**
 * Game Manager Object.
 * 
 * This object is responsible of Game moves
 * 
 * @TODO add move stock check and control
 * 
 * @author federicosilvestri
 *
 */
public class GameManager {
	/**
	 * RED Player code.
	 */
	public final static int RED = 0;

	/**
	 * Blue Player code.
	 */
	public final static int BLUE = 1;

	/**
	 * Move action code.
	 */
	public static final int MOVE = 0;

	/**
	 * Put wall action code.
	 */
	public static final int PUT_WALL = 1;

	/**
	 * Move forward code.
	 */
	public final static int FORWARD = 0;

	/**
	 * Move back code.
	 */
	public final static int BACK = 1;

	/**
	 * Move left code.
	 */
	public final static int LEFT = 2;

	/**
	 * Move right code.
	 */
	public final static int RIGHT = 3;

	/**
	 * Player coordinates.
	 */
	private int[][] playerCoords;

	/**
	 * Stock of walls.
	 */
	private final int[] wallAvailability;

	/**
	 * 
	 */
	private final LinkedList<Integer> wallStock;

	/**
	 * Stock of available moves.
	 */
	private final int[] moveAvailability;

	/**
	 * Board object.
	 */
	public final Board board;

	/**
	 * Create a new game manager.
	 */
	public GameManager() {
		playerCoords = new int[2][2];
		playerCoords[RED] = GameCostants.INITIAL_RED_COORDS;
		playerCoords[BLUE] = GameCostants.INITIAL_BLUE_COORDS;

		wallAvailability = new int[2];
		wallAvailability[RED] = wallAvailability[BLUE] = GameCostants.MAX_WALLS;

		wallStock = new LinkedList<>();
		for (int i = 0; i < GameCostants.TOTAL_WALLS; i++) {
			wallStock.add(i);
		}

		moveAvailability = new int[2];
		moveAvailability[RED] = moveAvailability[BLUE] = GameCostants.MAX_MOVES;

		board = new Board(GameCostants.BOARD_ROWS, GameCostants.BOARD_COLS, GameCostants.INITIAL_RED_COORDS,
				GameCostants.INITIAL_BLUE_COORDS);
	}

	private GameManager(int[][] playerCoords, int[] wallAvailability, LinkedList<Integer> wallStock,
			int[] moveAvailability, Board board) {
		super();
		this.playerCoords = playerCoords;
		this.wallAvailability = wallAvailability;
		this.wallStock = wallStock;
		this.moveAvailability = moveAvailability;
		this.board = board;
	}

	/**
	 * Get player coordinates.
	 * 
	 * @param player
	 *            chosen player
	 * @return array of size 2, {x,y}
	 */
	public int[] getPlayerCoords(int player) {
		return playerCoords[player];
	}

	/**
	 * Get stock of walls.
	 * 
	 * @param player
	 *            chosen player
	 * @return integer value between 0 and 10
	 */
	public int getAvailableWallNumber(int player) {
		return wallAvailability[player];
	}

	/**
	 * Get availability of walls, according with game rules.
	 * 
	 * @param player
	 *            related player
	 * @return true if walls are available, false otherwise
	 */
	public boolean areWallsAvailable(int player) {
		return (wallAvailability[player] > 0);
	}

	/**
	 * Return number of available moves, according with game rules.
	 * 
	 * @param player
	 *            chosen player
	 * @return number of available moves
	 */
	public int getAvailableMovesNumber(int player) {
		return (moveAvailability[player]);
	}

	/**
	 * Perform an action.
	 * 
	 * @param player
	 *            chosen player
	 * @param action
	 *            0 -> move 1-> put wall
	 * @param coords
	 *            coordinates of action
	 */
	public void play(int player, int action, int coords) {
		if (action == MOVE) {
			move(player, coords);
		} else if (action == PUT_WALL) {
			putWall(player, coords);
		} else {
			throw new RuntimeException("Board has received invalid game action (" + action + ")");
		}
	}

	private void putWall(int player, int index) {
		// check if wall is available in stock
		if (wallAvailability[player] < 1) {
			throw new OutOfStockWallException(player, index);
		}

		// Get wall coords
		int[][] wallCoords = board.getWallCoords(index);

		// check if wall can be positioned in current index
		if (board.isWallOccupied(wallCoords)) {
			throw new WallUnavailableException(wallCoords, index);
		}

		// check if wall locks paths
		// boolean reachibility = board.checkReachability(index,
		// playerCoords[BLUE], playerCoords[RED]);
		//
		// if (!reachibility) {
		// throw new RuntimeException("No paths available for players");
		// }

		// Add wall to Board Matrix
		board.addWall(wallCoords, player);

		// Remove index from stock
		wallStock.remove(wallStock.indexOf(index));

		// Remove from stock
		wallAvailability[player] -= 1;
	}

	private void move(int player, int direction) {
		// simulate move
		int[] nextCoords = computeMove(player, direction);

		// check first if coordinates are correct
		if (!board.checkPlayerCoords(nextCoords[0], nextCoords[1])) {
			throw new BadMoveException(player, nextCoords);
		}

		// Check if move is legal
		int moveDistance = Board.getMoveDistance(playerCoords[player][0], playerCoords[player][1], nextCoords[0],
				nextCoords[1]);
		boolean pathValid = (moveDistance <= GameCostants.CELLS_DISTANCE);

		if (!pathValid) {
			throw new BadMoveException(player, nextCoords);
		}

		boolean checkWalls = board.checkBrokenWalls(playerCoords[player][0], playerCoords[player][1], nextCoords[0],
				nextCoords[1]);

		if (!checkWalls) {
			throw new PassedWallException(playerCoords[player], nextCoords);
		}

		// apply move to matrix
		board.updatePlayerCoords(player, playerCoords[player], nextCoords);
		playerCoords[player] = nextCoords;
	}

	/**
	 * Get if this move can be performed or not.
	 * 
	 * @param player
	 *            chosen player
	 * @param direction
	 *            chosen direction
	 * @return true if move is legal, false otherwise
	 */
	public boolean canMove(int player, int direction) {
		// simulate move
		int[] nextCoords = computeMove(player, direction);

		// check first if coordinates are correct
		if (!board.checkPlayerCoords(nextCoords[0], nextCoords[1])) {
			return false;
		}

		// Check if move is legal
		int moveDistance = Board.getMoveDistance(playerCoords[player][0], playerCoords[player][1], nextCoords[0],
				nextCoords[1]);
		boolean pathValid = (moveDistance <= GameCostants.CELLS_DISTANCE);

		if (!pathValid) {
			return false;
		}

		boolean checkWalls = board.checkBrokenWalls(playerCoords[player][0], playerCoords[player][1], nextCoords[0],
				nextCoords[1]);

		return checkWalls;
	}

	/**
	 * Execute a simulation of move with current configuration of matrix and
	 * players.
	 * 
	 * @param player
	 *            chosen player
	 * @param direction
	 *            direction of move
	 * @return array of computed moves
	 */
	public int[] computeMove(int player, int direction) {
		// simple bias to adjust coordinates increasing or decreasing operations
		int moveBias = (player == RED) ? Board.BIAS : -Board.BIAS;
		// simulate moves in new array
		int computedCoords[] = new int[2];
		System.arraycopy(playerCoords[player], 0, computedCoords, 0, 2);

		// check directions
		switch (direction) {
		case FORWARD:
			computedCoords[1] += moveBias;
			break;
		case BACK:
			computedCoords[1] -= moveBias;
			break;
		case LEFT:
			computedCoords[0] += moveBias;
			break;
		case RIGHT:
			computedCoords[0] -= moveBias;
			break;
		default:
			throw new InvalidDirection(direction);
		}

		return computedCoords;
	}

	/**
	 * Set player coordinates.
	 * 
	 * @param player
	 *            chosen player
	 * @param coords
	 *            coordinates to set
	 * @throws BadMoveException
	 *             in case of coordinates are not valid
	 */
	public void setPlayerCoords(int player, int[] coords) {
		if (!board.checkPlayerCoords(coords[0], coords[1])) {
			throw new BadMoveException(player, coords);
		}

		board.updatePlayerCoords(player, playerCoords[player], coords);
		playerCoords[player] = coords;
	}

	/**
	 * Get unused walls indexes. Caution: this list contains also incompatible
	 * walls.
	 * 
	 * @return List of unused wall indexes
	 */
	public List<Integer> getAvailableWalls() {
		return wallStock;
	}

	/**
	 * Know if game is finished or not.
	 * 
	 * @return true if finished, false otherwise
	 */
	public boolean isFinished() {
		return (getWinner() != -1);
	}
	
	/**
	 * Get winner of game.
	 * @return return -1 if there are no winner
	 */
	public int getWinner() {
		if (playerCoords[RED][1] == GameCostants.RED_WIN_Y) {
			return RED;
		} else if (playerCoords[BLUE][1] == GameCostants.BLUE_WIN_Y) {
			return BLUE;
		}
		
		return -1;
	}

	/**
	 * Get simulation of this Game.
	 * 
	 * @return Game Manager Object
	 */
	public GameManager getSimulation() {
		return new GameManager(playerCoords.clone(), wallAvailability.clone(), new LinkedList<Integer>(wallStock),
				moveAvailability.clone(), board.copy());
	}

	/**
	 * Create node.
	 * 
	 * @return instance of node.
	 */
	public Node createNode() {
		Node node = new Node(board.getMatrix(), playerCoords, wallAvailability);

		return node;
	}
}
