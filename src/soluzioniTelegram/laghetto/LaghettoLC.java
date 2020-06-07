package soluzioniTelegram.laghetto;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Marco Lackovic <mlackovic@dimes.unical.it>
 * @version 1.2, Jul 2, 2014
 */
public class LaghettoLC extends Laghetto {

	private Lock l = new ReentrantLock();
	private Condition ingresso = l.newCondition();

	public LaghettoLC(int min, int max) {
		super(min, max);
	}

	public void entra(int tipo) throws InterruptedException {
		l.lock();
		try {
			while (!mioTurno(tipo)) {
				ingresso.await();
			}
			numPersoneDentro[tipo]++;
		} finally {
			l.unlock();
		}
	}

	private boolean mioTurno(int tipo) {
		return numPersoneDentro[1 - tipo] == 0
				&& minPesci < numPesci - numPersoneDentro[PESCATORE]
				&& numPesci + numPersoneDentro[ADDETTO] * PESCI_ALLA_VOLTA
						+ PESCI_ALLA_VOLTA <= maxPesci;
	}

	public void esce(int tipo) {
		l.lock();
		try {
			numPersoneDentro[tipo]--;
			aggiornaPesci(tipo);
			if (numPersoneDentro[tipo] == 0) {
				ingresso.signalAll();
			}
		} finally {
			l.unlock();
		}
	}

	public static void main(String[] args) {
		new LaghettoSem(50, 200).test(new int[] { 40, 5 });
	}
}
