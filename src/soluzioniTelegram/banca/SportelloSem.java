/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2010/2011, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioniTelegram.banca;

import java.util.concurrent.Semaphore;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Feb 3, 2012
 */
public class SportelloSem extends Sportello {

	private Semaphore mutex = new Semaphore(1, true);

	public SportelloSem(Banca b, int id) {
		super(b, id);
	}

	@Override
	protected int effettuaOperazione(int quantita, int tempo)
			throws InterruptedException {
		mutex.acquire();
		quantita = aggiornaDenaro(quantita, tempo);
		mutex.release();
		return quantita;
	}
}
