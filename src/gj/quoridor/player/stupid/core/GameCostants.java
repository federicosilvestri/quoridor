package gj.quoridor.player.stupid.core;

public class GameCostants {

	/**
	 * Number of Board Rows.
	 */
	public final static int BOARD_ROWS = 17;

	/**
	 * Number of Board Columns.
	 */
	public final static int BOARD_COLS = 17;

	/**
	 * Distance between a cell and a wall.
	 */
	public final static int CELL_WALL_DISTANCE = 1;

	/**
	 * Distance between two cells.
	 */
	public final static int CELLS_DISTANCE = 2;

	/**
	 * Number of max moves.
	 */
	public final static int MAX_MOVES = 500;

	/**
	 * Number of max walls.
	 */
	public final static int MAX_WALLS = 10;
	
	/**
	 * Number of total walls
	 */
	public final static int TOTAL_WALLS = 128;

	/**
	 * Initial red position using coordinates.
	 */
	public final static int[] INITIAL_RED_COORDS = new int[] { 8, 0 };

	/**
	 * Initial blue position using coordinates.
	 */
	public final static int[] INITIAL_BLUE_COORDS = new int[] { 8, 16 };
	
	/**
	 * Blue winning Y coordinate.
	 */
	public static final int BLUE_WIN_Y = 0;
	
	/**
	 * Red winning Y coordinate.
	 */
	public static final int RED_WIN_Y = 16;
}
