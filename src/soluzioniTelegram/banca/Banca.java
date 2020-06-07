/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2010/2011, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioniTelegram.banca;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Feb 3, 2012
 */
public abstract class Banca {

	protected static final int NUM_SPORTELLI = 4;
	protected Sportello[] sportelli = new Sportello[NUM_SPORTELLI];
	protected int[] denaroSportello = new int[NUM_SPORTELLI];

	public abstract Sportello getSportelloMinDenaro()
			throws InterruptedException;

	public int getNumSportelli() {
		return NUM_SPORTELLI;
	}

	public Sportello getSportello(int idSportello) {
		return sportelli[idSportello];
	}

	protected int getIdSportelloMinDenaro() {
		int idMinSportello = 0;
		int minDenaro = denaroSportello[0];
		for (int i = 1; i < denaroSportello.length; i++) {
			int denaro = denaroSportello[i];
			if (denaro < minDenaro) {
				idMinSportello = i;
				minDenaro = denaro;
			}
		}
		return idMinSportello;
	}

	public abstract void aggiornaDenaroSportello(int id, int denaro)
			throws InterruptedException;

}
