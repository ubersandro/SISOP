/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2010/2011, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioneTelegram.banca;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Feb 3, 2012
 */
public class Cliente extends Thread {

	private static final int TEMPO_SPOSTAMENTO = 10;
	private static final int MIN_DENARO = 50;
	private static final int MAX_DENARO = 250;

	private Banca sedeCentrale;
	private int numSportelli;

	public Cliente(Banca s) {
		sedeCentrale = s;
		numSportelli = sedeCentrale.getNumSportelli();
	}

	@Override
	public void run() {
		int idSportello, denaro;
		try {
			while (true) {
				idSportello = scegliSportello();
				raggiungiSportello();
				denaro = sedeCentrale.getSportello(idSportello).preleva(
						quantita());
				if (denaro > 0) {
					Thread.sleep(denaro);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int quantita() {
		return (int) (Math.random() * (MAX_DENARO - MIN_DENARO + 1) + MIN_DENARO);
	}

	private int scegliSportello() {
		return (int) (Math.random() * numSportelli);
	}

	private void raggiungiSportello() throws InterruptedException {
		Thread.sleep(TEMPO_SPOSTAMENTO);
	}
}
