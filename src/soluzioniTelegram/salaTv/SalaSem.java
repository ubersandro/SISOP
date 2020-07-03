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
public class SalaSem extends Sala {

	private Semaphore ingressoSala = new Semaphore(MAX_POSTI, true);

	@Override
	public void entra() throws InterruptedException {
		ingressoSala.acquire();
	}

	@Override
	public void esci() throws InterruptedException {
		ingressoSala.release();
	}

}
