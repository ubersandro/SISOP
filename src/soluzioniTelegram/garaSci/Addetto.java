package soluzioniTelegram.garaSci;

import java.util.concurrent.TimeUnit;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.0, Jul 18, 2014
 */
public class Addetto implements Runnable {

	private static final long TEMPO = 200;
	private Gara g;

	public Addetto(Gara g) {
		this.g = g;
	}

	@Override
	public void run() {
		try {
			while (g.prossimo()) {
				TimeUnit.MILLISECONDS.sleep(TEMPO);
			}
		} catch (InterruptedException e) {
		}
	}
}
