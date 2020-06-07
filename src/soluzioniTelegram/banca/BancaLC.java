/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2010/2011, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioniTelegram.banca;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Feb 3, 2012
 */
public class BancaLC extends Banca {

	private Lock l = new ReentrantLock(true);

	public BancaLC() {
		for (int i = 0; i < sportelli.length; i++) {
			sportelli[i] = new SportelloLC(this, i);
		}
	}

	@Override
	public Sportello getSportelloMinDenaro() throws InterruptedException {
		int idMinSportello;
		l.lock();
		try {
			idMinSportello = getIdSportelloMinDenaro();
		} finally {
			l.unlock();
		}
		return sportelli[idMinSportello];
	}

	@Override
	public void aggiornaDenaroSportello(int id, int denaro)
			throws InterruptedException {
		l.lock();
		try {
			denaroSportello[id] = denaro;
		} finally {
			l.unlock();
		}
	}

}
