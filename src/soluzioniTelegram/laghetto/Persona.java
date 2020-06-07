package soluzioniTelegram.laghetto;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.1, Jul 2, 2014
 */
public class Persona implements Runnable {
	private static final int[] TEMPO_ATTESA = { 1, 3 };
	private static final int[] MIN_OPERAZIONE = { 200, 300 };
	private static final int[] MAX_OPERAZIONE = { 800, 600 };
	private final Laghetto b;
	private final Random r = new Random();
	private final int tipo;

	public Persona(Laghetto b, int tipo) {
		this.b = b;
		this.tipo = tipo;
	}

	public void run() {
		try {
			while (true) {
				b.entra(tipo);
				operazione();
				b.esce(tipo);
				pausa();
			}
		} catch (InterruptedException e) {
		}
	}

	private void operazione() throws InterruptedException {
		Thread.sleep(r.nextInt(MAX_OPERAZIONE[tipo] - MIN_OPERAZIONE[tipo] + 1)
				+ MIN_OPERAZIONE[tipo]);
	}

	private void pausa() throws InterruptedException {
		TimeUnit.SECONDS.sleep(TEMPO_ATTESA[tipo]);
	}

}
