/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2011/2012, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioniTelegram.salaTv;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Jul 16, 2012
 */
public class TVLC extends TV {
	private Lock lock = new ReentrantLock();
	private Condition[] canaleDisponibile = new Condition[NUM_CANALI];

	public TVLC() {
		for (int i = 0; i < canaleDisponibile.length; i++) {
			canaleDisponibile[i] = lock.newCondition();
		}
	}

	@Override
	public void iniziaAGuardare(int canaleScelto) throws InterruptedException {
		lock.lock();
		try {
			if (numSpettatori == 0) {
				canale = canaleScelto;
				numSpettatori++;
			} else if (canale != canaleScelto) {
				inAttesaDiVedere[canaleScelto - 1]++;
				while (canale != canaleScelto) {
					canaleDisponibile[canaleScelto - 1].await();
				}
			} else {
				numSpettatori++;
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void finisciDiGuardare() throws InterruptedException {
		lock.lock();
		try {
			numSpettatori--;
			if (numSpettatori == 0) {
				int maxNumPersoneInAttesa = 0;
				int maxCanale = 0;
				for (int i = 0; i < inAttesaDiVedere.length; i++) {
					if (inAttesaDiVedere[i] > maxNumPersoneInAttesa) {
						maxNumPersoneInAttesa = inAttesaDiVedere[i];
						maxCanale = i;
					}
				}
				if (maxNumPersoneInAttesa > 0) {
					canale = maxCanale + 1;
					numSpettatori = maxNumPersoneInAttesa;
					inAttesaDiVedere[maxCanale] = 0;
					canaleDisponibile[maxCanale].signalAll();
				}
			}
		} finally {
			lock.unlock();
		}
	}

}
