package soluzioniTelegram.garaSci;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.0, Jul 18, 2014
 */
public class GaraLC extends Gara {

	private Lock l = new ReentrantLock();
	private Condition[] sciatori;
	private Condition addetto = l.newCondition();
	private Condition sciatoriArrivati = l.newCondition();
	private boolean sciatorePartito = false;

	public GaraLC(int numSciatori) {
		super(numSciatori);
		sciatori = new Condition[numSciatori];
		for (int i = 0; i < sciatori.length; i++) {
			sciatori[i] = l.newCondition();
		}
	}

	@Override
	public void partenza(Sciatore s) throws InterruptedException {
		l.lock();
		try {
			int mioNumero = s.getNumMaglia();
			while (prossimoSciatore != mioNumero) {
				sciatori[mioNumero].await();
			}
			sciatorePartito = true;
			addetto.signal();
		} finally {
			l.unlock();
		}

	}

	@Override
	public int arrivo(Sciatore s) throws InterruptedException {
		int posizione;
		l.lock();
		try {
			posizione = inserisci(s);
			if (sciatoriArrivati()) {
				sciatoriArrivati.signal();
			}
		} finally {
			l.unlock();
		}
		return posizione;
	}

	@Override
	public boolean prossimo() throws InterruptedException {
		l.lock();
		try {
			prossimoSciatore++;
			if (prossimoSciatore < numSciatori) {
				sciatori[prossimoSciatore].signal();
				while (!sciatorePartito) {
					addetto.await();
				}
				sciatorePartito = false;
				return true;
			}
			while (!sciatoriArrivati()) {
				sciatoriArrivati.await();
			}
		} finally {
			l.unlock();
		}
		aggiorna();
		stampa();
		return false;
	}

	public static void main(String[] args) throws InterruptedException {
		int numSciatori = 100;
		new GaraLC(numSciatori).test();
	}
}
