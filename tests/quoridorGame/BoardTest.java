package quoridorGame;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

import gj.quoridor.player.stupid.Board;

public class BoardTest {

	private Board board;
	

	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@Test
	public void testRedForward() {
		System.out.println("Testing RED forward");
		
		board.setPlayerCoords(Board.RED, new int[]{8, 0});
		for (int i = 0; i < 16; i += 2) {
			// forward
			board.play(Board.RED, Board.MOVE, Board.FORWARD);
			
			int expectedCoords[] = {8, i+2};
			int redCoords[] = board.getPlayerCoords(Board.RED);			
			
			System.out.print(Arrays.toString(redCoords));
			
			// We assume to not control walls
			assertEquals(redCoords[0], expectedCoords[0]);
			assertEquals(redCoords[1], expectedCoords[1]);
		}
		
		System.out.println();
	}
	
	@Test
	public void testRedBack() {
		System.out.println("Testing RED back");
		
		board.setPlayerCoords(Board.RED, new int[]{8, 16});
		for (int i = 16; i > 0; i -= 2) {
			// back
			board.play(Board.RED, Board.MOVE, Board.BACK);
			
			int expectedCoords[] = {8, i-2};
			int redCoords[] = board.getPlayerCoords(Board.RED);
			
			System.out.print(Arrays.toString(redCoords));
			
			// We assume to not control walls
			assertEquals(redCoords[0], expectedCoords[0]);
			assertEquals(redCoords[1], expectedCoords[1]);
		}
		
		System.out.println();
	}
	
	@Test
	public void testRedLeft() {
		System.out.println("Testing RED left");
		
		board.setPlayerCoords(Board.RED, new int[]{0, 0});
		for (int i = 0; i < 16; i += 2) {
			// go left
			board.play(Board.RED, Board.MOVE, Board.LEFT);
			
			int expectedCoords[] = {i+2, 0};
			int redCoords[] = board.getPlayerCoords(Board.RED);
			
			System.out.print(Arrays.toString(redCoords));
			
			// We assume to not control walls
			assertEquals(redCoords[0], expectedCoords[0]);
			assertEquals(redCoords[1], expectedCoords[1]);
		}
		
		System.out.println();
	}
	
	@Test
	public void testRedRight() {
		System.out.println("Testing RED right");
		
		board.setPlayerCoords(Board.RED, new int[]{16, 0});
		for (int i = 16; i > 0; i -= 2) {
			// go left
			board.play(Board.RED, Board.MOVE, Board.RIGHT);
			
			int expectedCoords[] = {i-2, 0};
			int redCoords[] = board.getPlayerCoords(Board.RED);
			
			System.out.print(Arrays.toString(redCoords));
			
			// We assume to not control walls
			assertEquals(redCoords[0], expectedCoords[0]);
			assertEquals(redCoords[1], expectedCoords[1]);
		}
		
		System.out.println();
	}

}
