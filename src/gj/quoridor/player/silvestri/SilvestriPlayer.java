package gj.quoridor.player.silvestri;

import gj.quoridor.player.Player;

public class SilvestriPlayer implements Player {

	@Override
	public int[] move() {
		// TODO [0 => move, != 0 => wall], [coord]
		return new int[] {0,4};
	}

	@Override
	public void start(boolean isFirst) {
		// TODO start game
		
	}

	@Override
	public void tellMove(int[] move) {
		// TODO tell the player move
		
		
	}

}
