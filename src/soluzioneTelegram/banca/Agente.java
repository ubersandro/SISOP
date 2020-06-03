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
public class Agente extends Thread {

	private static final int TEMPO_SPOSTAMENTO = 10;

	private Banca sedeCentrale;

	public Agente(Banca s) {
		sedeCentrale = s;
	}

	@Override
	public void run() {
		Sportello sportello;
		try {
			while (true) {
				sportello = sedeCentrale.getSportelloMinDenaro();
				raggiungiSportello();
				sportello.rifornisci();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void raggiungiSportello() throws InterruptedException {
		Thread.sleep(TEMPO_SPOSTAMENTO);
	}
}
