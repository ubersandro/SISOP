package soluzioniTelegram.garaSci;

import java.util.Random;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.0, Jul 18, 2014
 */
public class Sciatore implements Runnable {

	private final static int MIN = 0;
	private final static int MAX = 1;
	private final static int[] TEMPO = { 500, 700 };

	private Gara g;
	private final int numMaglia;
	private int tempo;
	private int arrivo;

	public Sciatore(Gara g, int numMaglia) {
		this.g = g;
		this.numMaglia = numMaglia;
	}

	@Override
	public void run() {
		try {
			g.partenza(this);
			tempo = discesa();
			arrivo = g.arrivo(this);
			System.out.println(this);
		} catch (InterruptedException e) {
		}
	}

	private int discesa() throws InterruptedException {
		int tempo = new Random().nextInt(TEMPO[MAX] - TEMPO[MIN] + 1)
				+ TEMPO[MIN];
		Thread.sleep(tempo);
		return tempo;
	}

	public int getNumMaglia() {
		return numMaglia;
	}

	public int getTempo() {
		return tempo;
	}

	public void setArrivo(int arrivo) {
		this.arrivo = arrivo;
	}

	@Override
	public String toString() {
		return "Numero " + numMaglia + "\tPosizione " + arrivo + "\tTempo "
				+ tempo;
	}
}
