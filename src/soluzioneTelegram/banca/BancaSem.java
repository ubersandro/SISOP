/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2010/2011, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioneTelegram.banca;

import java.util.concurrent.Semaphore;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Feb 3, 2012
 */
public class BancaSem extends Banca {

	private Semaphore mutex = new Semaphore(1);

	public BancaSem() {
		for (int i = 0; i < sportelli.length; i++) {
			sportelli[i] = new SportelloSem(this, i);
		}
	}

	@Override
	public Sportello getSportelloMinDenaro() throws InterruptedException {
		mutex.acquire();
		int idMinSportello = getIdSportelloMinDenaro();
		mutex.release();
		return sportelli[idMinSportello];
	}

	@Override
	public void aggiornaDenaroSportello(int id, int denaro)
			throws InterruptedException {
		mutex.acquire();
		denaroSportello[id] = denaro;
		mutex.release();
	}

}
