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
		
		board.setPlayerCoords(Board.RED, new int[]{4, 0});
		for (int i = 0; i < 8; i++) {
			// forward
			board.play(Board.RED, Board.MOVE, Board.FORWARD);
			
			int expectedCoords[] = {4, i+1};
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
		
		board.setPlayerCoords(Board.RED, new int[]{4, 8});
		for (int i = 8; i > 0; i--) {
			// back
			board.play(Board.RED, Board.MOVE, Board.BACK);
			
			int expectedCoords[] = {4, i-1};
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
		for (int i = 0; i < 8; i++) {
			// go left
			board.play(Board.RED, Board.MOVE, Board.LEFT);
			
			int expectedCoords[] = {i+1, 0};
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
		
		board.setPlayerCoords(Board.RED, new int[]{8, 0});
		for (int i = 8; i > 0; i--) {
			// go left
			board.play(Board.RED, Board.MOVE, Board.RIGHT);
			
			int expectedCoords[] = {i-1, 0};
			int redCoords[] = board.getPlayerCoords(Board.RED);
			
			System.out.print(Arrays.toString(redCoords));
			
			// We assume to not control walls
			assertEquals(redCoords[0], expectedCoords[0]);
			assertEquals(redCoords[1], expectedCoords[1]);
		}
		
		System.out.println();
	}

}
