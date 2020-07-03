/* =======================================================================
 * Sistemi Operativi, Corso di Laurea in Ingegneria Informatica, DM 270/04
 * A.A. 2011/2012, Facoltà di Ingegneria, Università della Calabria
 * =======================================================================
 */

package soluzioniTelegram.salaTv;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Marco Lackovic <mlackovic@deis.unical.it>
 * @version 1.0, Jul 16, 2012
 */
public class Persona implements Runnable {
	private static final int MIN_SEC_VISIONE = 60;
	private static final int MAX_SEC_VISIONE = 600;
	private Sala sala;
	private TV tv;
	private Random random = new Random();

	public Persona(Sala s) {
		sala = s;
		tv = sala.getTV();
	}

	@Override
	public void run() {
		try {
			while (true) {
				sala.entra();
				tv.iniziaAGuardare(scegliCanale());
				guarda();
				tv.finisciDiGuardare();
				sala.esci();
			}
		} catch (Exception e) {
		}
	}

	private void guarda() throws InterruptedException {
		TimeUnit.SECONDS.sleep(random.nextInt(MAX_SEC_VISIONE - MIN_SEC_VISIONE
				+ 1)
				+ MIN_SEC_VISIONE);

	}

	private int scegliCanale() {
		return random.nextInt(TV.NUM_CANALI) + 1;
	}

}
