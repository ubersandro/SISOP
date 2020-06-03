/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2010/2011, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioneTelegram.banca;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.1, May 17, 2012
 */
public class SportelloLC extends Sportello {

	Lock l = new ReentrantLock(true);

	public SportelloLC(Banca b, int id) {
		super(b, id);
	}

	@Override
	protected int effettuaOperazione(int quantita, int tempo)
			throws InterruptedException {
		l.lock();
		try {
			quantita = aggiornaDenaro(quantita, tempo);
		} finally {
			l.unlock();
		}
		return quantita;
	}

}
