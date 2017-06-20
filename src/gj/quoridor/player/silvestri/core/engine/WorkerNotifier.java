package gj.quoridor.player.silvestri.core.engine;

/**
 * This class is a separate thread that notify engine a player worker finish.
 * 
 * @author federicosilvestri
 *
 */
class WorkerNotifier extends Thread {

	/**
	 * Related player engine.
	 */
	private final PlayerEngine playerEngine;

	/**
	 * Related player worker.
	 */
	private final PlayerWorker playerWorker;

	WorkerNotifier(PlayerEngine playerEngine, PlayerWorker playerWorker) {
		this.playerEngine = playerEngine;
		this.playerWorker = playerWorker;
	}

	@Override
	public void run() {
		playerEngine.fireFinished(playerWorker);
	}

}
