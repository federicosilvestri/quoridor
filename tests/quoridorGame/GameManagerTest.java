package quoridorGame;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

import gj.quoridor.player.stupid.core.GameManager;;

public class GameManagerTest {

	private GameManager board;
	

	@Before
	public void setUp() throws Exception {
		board = new GameManager();
	}

	@Test
	public void testRedForward() {
		System.out.println("Testing RED forward");
		
		board.setPlayerCoords(GameManager.RED, new int[]{8, 0});
		for (int i = 0; i < 16; i += 2) {
			// forward
			board.play(GameManager.RED, GameManager.MOVE, GameManager.FORWARD);
			
			int expectedCoords[] = {8, i+2};
			int redCoords[] = board.getPlayerCoords(GameManager.RED);			
			
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
		
		board.setPlayerCoords(GameManager.RED, new int[]{8, 16});
		for (int i = 16; i > 0; i -= 2) {
			// back
			board.play(GameManager.RED, GameManager.MOVE, GameManager.BACK);
			
			int expectedCoords[] = {8, i-2};
			int redCoords[] = board.getPlayerCoords(GameManager.RED);
			
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
		
		board.setPlayerCoords(GameManager.RED, new int[]{0, 0});
		for (int i = 0; i < 16; i += 2) {
			// go left
			board.play(GameManager.RED, GameManager.MOVE, GameManager.LEFT);
			
			int expectedCoords[] = {i+2, 0};
			int redCoords[] = board.getPlayerCoords(GameManager.RED);
			
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
		
		board.setPlayerCoords(GameManager.RED, new int[]{16, 0});
		for (int i = 16; i > 0; i -= 2) {
			// go left
			board.play(GameManager.RED, GameManager.MOVE, GameManager.RIGHT);
			
			int expectedCoords[] = {i-2, 0};
			int redCoords[] = board.getPlayerCoords(GameManager.RED);
			
			System.out.print(Arrays.toString(redCoords));
			
			// We assume to not control walls
			assertEquals(redCoords[0], expectedCoords[0]);
			assertEquals(redCoords[1], expectedCoords[1]);
		}
		
		System.out.println();
	}

}
