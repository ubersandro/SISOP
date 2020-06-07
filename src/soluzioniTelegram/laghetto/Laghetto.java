package soluzioniTelegram.laghetto;

import java.util.Random;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.1, Jul 2, 2014
 */
public abstract class Laghetto {
	protected final static int PESCATORE = 0;
	protected final static int ADDETTO = 1;
	protected final static int PESCI_ALLA_VOLTA = 10;

	protected final int minPesci;
	protected final int maxPesci;
	protected int numPesci;
	protected int[] numPersoneDentro = { 0, 0 };

	public Laghetto(int min, int max) {
		minPesci = min;
		maxPesci = max;
		numPesci = new Random().nextInt(max - min + 1) + min;
	}

	public abstract void entra(int tipo) throws InterruptedException;

	public abstract void esce(int tipo) throws InterruptedException;

	protected void aggiornaPesci(int tipo) {
		numPesci += tipo * (PESCI_ALLA_VOLTA + 1) - 1;
	}

	protected void test(int[] numPersone) {
		for (int i = 0; i < numPersone.length; i++) {
			for (int j = 0; j < numPersone[i]; j++) {
				new Thread(new Persona(this, i)).start();
			}
		}
	}
}
