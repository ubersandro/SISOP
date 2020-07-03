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
public abstract class TV {
	protected static final int NUM_CANALI = 8;
	protected int canale = 0;
	protected int numSpettatori = 0;
	protected int[] inAttesaDiVedere = new int[NUM_CANALI];

	public abstract void iniziaAGuardare(int canale)
			throws InterruptedException;

	public abstract void finisciDiGuardare() throws InterruptedException;

	public TV() {
		for (int i = 0; i < inAttesaDiVedere.length; i++) {
			inAttesaDiVedere[i] = 0;
		}
	}
}
