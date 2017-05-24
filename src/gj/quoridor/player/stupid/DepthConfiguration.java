package gj.quoridor.player.stupid;

import gj.quoridor.player.stupid.core.GameManager;

public class DepthConfiguration {
	int depth;
	int[]coords;
	GameManager manager;
	
	DepthConfiguration(int depth){
		this.depth = depth;
		manager = new GameManager();
		coords = new int[2];
	}
	
	public void playerPositioner(){
		coords[0] = 4;
		if(depth > 16 || depth < 0){
			//error!
		}else if(depth % 2 != 0){
			coords[1] = depth + 1;
		}else
			coords[1] = depth;
			manager.setPlayerCoords(GameManager.RED, coords);
			
		}
		
			
		
	}
