package gj.quoridor.player.silvestri.exceptions;

public class OutOfStockWallException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OutOfStockWallException(int player, int wallIndex) {
		super("There are no wall available in stock, player=" + player + " wallIndex=" + wallIndex);
	}

}
