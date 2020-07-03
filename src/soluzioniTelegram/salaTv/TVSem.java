/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2011/2012, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioniTelegram.salaTv;

import java.util.concurrent.Semaphore;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Jul 16, 2012
 */
public class TVSem extends TV {
	private Semaphore mutex = new Semaphore(1);
	private Semaphore[] canaleDaVedere = new Semaphore[NUM_CANALI];

	public TVSem() {
		super();
		for (int i = 0; i < canaleDaVedere.length; i++) {
			canaleDaVedere[i] = new Semaphore(0);
		}
	}

	@Override
	public void iniziaAGuardare(int canaleScelto) throws InterruptedException {
		mutex.acquire();
		if (canale != canaleScelto && numSpettatori > 0) {
			inAttesaDiVedere[canaleScelto - 1]++;
			mutex.release();
			canaleDaVedere[canaleScelto - 1].acquire();
		} else {
			canale = canaleScelto;
			numSpettatori++;
			mutex.release();
		}
	}

	@Override
	public void finisciDiGuardare() throws InterruptedException {
		mutex.acquire();
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
				canaleDaVedere[maxCanale].release(numSpettatori);
			}
		}
		mutex.release();
	}

}
