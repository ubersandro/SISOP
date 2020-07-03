/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2011/2012, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioniTelegram.salaTv;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Jul 16, 2012
 */
public abstract class Sala {

	protected static final int MAX_POSTI = 20;
	protected TV tv;

	public abstract void entra() throws InterruptedException;

	public abstract void esci() throws InterruptedException;

	public TV getTV() {
		return tv;
	};

}
