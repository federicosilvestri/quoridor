package gj.quoridor.player.stupid;

import java.util.Arrays;

import gj.quoridor.player.Player;

public class StupidPlayer implements Player {

	@Override
	public int[] move() {
		// TODO Auto-generated method stub
		int a = (int) (Math.random() * 10000);
		a %= 127;
		System.out.println("Wall index " + a);
		return  new int[] {1, a};
	}

	@Override
	public void start(boolean arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void tellMove(int[] arg0) {
		System.out.println(Arrays.toString(arg0));

	}

}
