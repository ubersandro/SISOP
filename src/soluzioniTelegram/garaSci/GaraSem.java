package soluzioniTelegram.garaSci;

import java.util.concurrent.Semaphore;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.0, Jul 18, 2014
 */
public class GaraSem extends Gara {

	private Semaphore mutex = new Semaphore(1);
	private Semaphore[] partito;
	private Semaphore[] via;
	private Semaphore traguardo = new Semaphore(0);

	public GaraSem(int numSciatori) {
		super(numSciatori);
		partito = new Semaphore[numSciatori];
		via = new Semaphore[numSciatori];
		for (int i = 0; i < via.length; i++) {
			via[i] = new Semaphore(0);
			partito[i] = new Semaphore(0);
		}
	}

	@Override
	public void partenza(Sciatore s) throws InterruptedException {
		int numMaglia = s.getNumMaglia();
		via[numMaglia].acquire();
		partito[numMaglia].release();
	}

	@Override
	public int arrivo(Sciatore s) throws InterruptedException {
		mutex.acquire();
		int posizione = inserisci(s);
		mutex.release();
		traguardo.release();
		return posizione;
	}

	@Override
	public boolean prossimo() throws InterruptedException {
		prossimoSciatore++;
		if (prossimoSciatore < numSciatori) {
			via[prossimoSciatore].release();
			partito[prossimoSciatore].acquire();
			return true;
		}
		traguardo.acquire(numSciatori);
		aggiorna();
		stampa();
		return false;
	}

	public static void main(String[] args) throws InterruptedException {
		int numSciatori = 100;
		new GaraSem(numSciatori).test();
	}
}
