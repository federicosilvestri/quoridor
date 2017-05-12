package gj.quoridor.player.stupid;

import java.util.ArrayList;
import java.util.List;

public class ExhaustiveResearch {

	/**
	 * x and y of the initial state.
	 */
	private final int[] coords;
	private final boolean isRed;

	public ExhaustiveResearch(int player, int[] coords) {
		isRed = (player == Board.RED);
		this.coords = coords;

	}

	public List<Integer[]> getActions() {
		List<Integer[]> actions = new ArrayList();

		return actions;
	}

	/**
	 * Finds out all possible coordinates movements from a starting point.
	 * 
	 * 
	 * @return a List of integer vectors with all possible reacheable coordinates.
	 */
	private List<Integer> calculateCoordinates() {
		List<Integer> moves = new ArrayList();
		moves.add(0); //We only use movement action.
		if(coords[0] == 0 && coords[1] == 0){ //posizione (0,0) angolo in alto a sinistra
			moves.add(coords[0]++); 
			moves.add(coords[1]++); 
		}else if(coords[0] == 8 && coords[1] == 0){ //posizione (8,0) angolo in alto a destra
			moves.add(coords[0]--);
			moves.add(coords[1]++);	
		}else if(coords[0] == 0 && coords[1] == 8){ //posizione (0,8) angolo in basso a sinistra
			moves.add(coords[0]++);
			moves.add(coords[1]--);
		}else if(coords[0] == 8 && coords[1] == 8){ //posizione (8,8) angolo in basso a destra
	    	moves.add(coords[0]--);
	    	moves.add(coords[1]--);
		}else if(coords[0] == 0){ //caso in cui x = 0, quindi la pedina si trova sul confine sinistro
			moves.add(coords[0]++);
			moves.add(coords[1]++);
			moves.add(coords[1]--);
		}else if(coords[0] == 8){ //caso in cui x = 8, quindi la pedina si trova sul confine destro
			moves.add(coords[0]--);
			moves.add(coords[1]++);
			moves.add(coords[1]--);
		}else if(coords[1] == 0){ //caso in cui y = 0, quindi la pedina si trova sul confine anteriore
			moves.add(coords[0]++);
			moves.add(coords[0]--);
			moves.add(coords[1]++);
		}else if(coords[1] == 8){ //caso in cui y = 8, quindi la pedina si trova sul confine posteriore
			moves.add(coords[0]++);
			moves.add(coords[0]--);
			moves.add(coords[1]--);
		}else{ //tutti gli altri casi, i movimenti non sono vincolati in nessuna direzione
			moves.add(coords[0]++);
			moves.add(coords[1]++);
			moves.add(coords[0]--);
			moves.add(coords[1]--);

		}
		return moves;
	}

	/**
	 * Ritorna tutte le possibili coordinate dei muri da mettere.
	 * 
	 * @return
	 */
	private List<Integer> calculateWalls() {
		return null;
	}

}
